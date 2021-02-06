package com.liamcoalstudio.maialt.injector;

import org.objectweb.asm.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.objectweb.asm.Opcodes.*;

public final class Injector {
    private Injector() {}

    public static byte[] inject(byte[] clazz, InjectLocation loc, Method.Bundle method, Method.Bundle inject, Inject annot) {
        ClassReader reader = new ClassReader(clazz);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor;
        AtomicInteger matchCount = new AtomicInteger(0);
        AtomicReference<String> className = new AtomicReference<>();
        switch (loc) {
            case START:
                visitor = new ClassVisitor(ASM8, writer) {
                    @Override
                    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                        super.visit(version, access, name, signature, superName, interfaces);
                        className.set(name);
                    }

                    @Override
                    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                        if(method.matches(method.className, name, descriptor)) {
                            Injector.inject(mv, method, inject, className.get());
                        }
                        return mv;
                    }
                };
                break;
            case END:
                visitor = new ClassVisitor(ASM8, writer) {
                    @Override
                    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                        super.visit(version, access, name, signature, superName, interfaces);
                        className.set(name);
                    }

                    @Override
                    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                        return new MethodVisitor(ASM8, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                            @Override
                            public void visitInsn(int insn) {
                                if(method.matches(method.className, name, descriptor) && insn == RETURN) {
                                    Injector.inject(this, method, inject, className.get());
                                }
                                super.visitInsn(insn);
                            }
                        };
                    }
                };
                break;
            case BEFORE_INVOKE:
                visitor = new ClassVisitor(ASM8, writer) {
                    @Override
                    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                        super.visit(version, access, name, signature, superName, interfaces);
                        className.set(name);
                    }

                    int index = 0;

                    @Override
                    public MethodVisitor visitMethod(int access, String methodName, String methodDescriptor, String signature, String[] exceptions) {
                        return new MethodVisitor(ASM8, super.visitMethod(access, methodName, methodDescriptor, signature, exceptions)) {
                            @Override
                            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                                AtomicInteger i = new AtomicInteger(index);
                                methodInject(this, owner, name, descriptor, method,
                                        inject, methodName, methodDescriptor,
                                        annot, i, className.get(), true);
                                index = i.get();
                                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                            }
                        };
                    }
                };
                break;
            case AFTER_INVOKE:
                visitor = new ClassVisitor(ASM8, writer) {
                    @Override
                    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                        super.visit(version, access, name, signature, superName, interfaces);
                        className.set(name);
                    }

                    int index = 0;

                    @Override
                    public MethodVisitor visitMethod(int access, String methodName, String methodDescriptor, String signature, String[] exceptions) {
                        return new MethodVisitor(ASM8, super.visitMethod(access, methodName, methodDescriptor, signature, exceptions)) {
                            @Override
                            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                                AtomicInteger i = new AtomicInteger(index);
                                methodInject(this, owner, name, descriptor, method,
                                        inject, methodName, methodDescriptor,
                                        annot, i, className.get(), true);
                                index = i.get();
                            }
                        };
                    }
                };
                break;
            case BEFORE_FIELD:
                visitor = new ClassVisitor(ASM8, writer) {
                    int index = 0;

                    @Override
                    public MethodVisitor visitMethod(int access, String methodName, String methodDescriptor, String signature, String[] exceptions) {
                        return new MethodVisitor(ASM8, super.visitMethod(access, methodName, methodDescriptor, signature, exceptions)) {
                            @Override
                            public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                                AtomicInteger i = new AtomicInteger(index);
                                methodInject(this, owner, name, descriptor, method,
                                        inject, methodName, methodDescriptor,
                                        annot, i, className.get(), true);
                                index = i.get();
                                super.visitFieldInsn(opcode, owner, name, descriptor);
                            }
                        };
                    }
                };
                break;
            case AFTER_FIELD:
                visitor = new ClassVisitor(ASM8, writer) {
                    int index = 0;

                    @Override
                    public MethodVisitor visitMethod(int access, String methodName, String methodDescriptor, String signature, String[] exceptions) {
                        return new MethodVisitor(ASM8, super.visitMethod(access, methodName, methodDescriptor, signature, exceptions)) {
                            @Override
                            public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                                super.visitFieldInsn(opcode, owner, name, descriptor);
                                AtomicInteger i = new AtomicInteger(index);
                                methodInject(this, owner, name, descriptor, method,
                                        inject, methodName, methodDescriptor,
                                        annot, i, className.get(), true);
                                index = i.get();
                            }
                        };
                    }
                };
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + loc);
        }
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    private static void methodInject(
            MethodVisitor mv, String owner, String name, String descriptor,
            Method.Bundle method, Method.Bundle inject, String methodName,
            String methodDescriptor, Inject annot, AtomicInteger index,
            String className, boolean hasTarget
    ) {
        Method.Bundle b = new Method.Bundle(annot);
        if((!hasTarget || b.matches(owner, name, descriptor)) &&
                method.matches(method.className, methodName, methodDescriptor) &&
                (index.getAndIncrement() == b.requiredIndex || b.requiredIndex == -1)) {
            Injector.inject(mv, method, inject, className);
        }
    }

    private static void inject(MethodVisitor mv, Method.Bundle method, Method.Bundle inject, String className) {
        mv.visitCode();
        if ("()V".equals(inject.descriptor)) {
            mv.visitMethodInsn(
                    INVOKESTATIC,
                    inject.className,
                    inject.methodName,
                    inject.descriptor,
                    false
            );
        } else if (("(L" + className + ";Lcom/liamcoalstudio/maialt/injector/Arguments;)V").equals(inject.descriptor)) {
            mv.visitIntInsn(ALOAD, 0);
            int c = countArgs(method.descriptor);
            generateArgumentsArg(mv, c, charArgs(method.descriptor, c));
            mv.visitMethodInsn(
                    INVOKESTATIC,
                    inject.className,
                    inject.methodName,
                    inject.descriptor,
                    false
            );
        } else if (("(Lcom/liamcoalstudio/maialt/injector/Arguments;)V").equals(inject.descriptor)) {
            int c = countArgs(method.descriptor);
            generateArgumentsArg(mv, c, charArgs(method.descriptor, c));
            mv.visitMethodInsn(
                    INVOKESTATIC,
                    inject.className,
                    inject.methodName,
                    inject.descriptor,
                    false
            );
        } else throw new IllegalStateException("Invalid inject descriptor " + inject.descriptor);
        mv.visitMaxs(-1, -1);
    }

    private static void generateArgumentsArg(MethodVisitor mv, int argc, char[] args) {
        mv.visitTypeInsn(NEW, "com/liamcoalstudio/maialt/injector/Arguments");
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0+argc);
        mv.visitTypeInsn(ANEWARRAY, "com/liamcoalstudio/maialt/injector/ArgumentReference");
        for (int i = 0; i < argc; i++) {
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0+i);
            mv.visitTypeInsn(NEW, "com/liamcoalstudio/maialt/injector/ArgumentReference");
            mv.visitInsn(DUP);
            mv.visitIntInsn(getLoadInstruction(args[i]), i+1);
            mv.visitMethodInsn(INVOKESPECIAL, "com/liamcoalstudio/maialt/injector/ArgumentReference", "<init>", "(" + getRefDesc(args[i]) + ")V", false);
            mv.visitInsn(AASTORE);
        }
        mv.visitMethodInsn(INVOKESPECIAL,
                "com/liamcoalstudio/maialt/injector/Arguments",
                "<init>",
                "([Lcom/liamcoalstudio/maialt/injector/ArgumentReference;)V",
                false);
    }

    private static int getLoadInstruction(char arg) {
        switch (arg) {
            case 'L': return ALOAD;
            case 'S':
            case 'I':
            case 'C':
            case 'B':
            case 'Z': return ILOAD;
            case 'J': return LLOAD;
            default: return RETURN;
        }
    }

    private static String getRefDesc(char arg) {
        switch (arg) {
            case 'L': return "Ljava/lang/Object;";
            default: return "" + arg;
        }
    }

    private static int countArgs(String a) {
        String args = a.substring(a.indexOf('(')+1, a.indexOf(')'));
        boolean countArgs = true;
        int count = 0;
        for(char c : args.toCharArray()) {
            switch (c) {
                case 'L':
                    if(!countArgs) break;
                    countArgs = false;
                    count++;
                    break;
                case ';':
                    countArgs = true;
                    break;
                default:
                    if(!countArgs) break;
                    count++;
                    break;
                case '[':
                    break;
            }
        }
        return count;
    }

    private static char[] charArgs(String a, int c) {
        String args = a.substring(a.indexOf('(')+1, a.indexOf(')'));
        return args.toCharArray();
    }
}

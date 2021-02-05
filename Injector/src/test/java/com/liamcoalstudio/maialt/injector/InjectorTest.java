package com.liamcoalstudio.maialt.injector;

import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class InjectorTest {
    private static byte[] resultBytes = new byte[1000000];

    @BeforeClass
    public static void setUp() throws IOException {
        Object.class.getResourceAsStream("/java/lang/Object.class").read(resultBytes);
    }

    @Test public void testInjectStart() {
        resultBytes = Injector.inject(
                resultBytes,
                InjectLocation.START,
                new Method.Bundle("java/lang/Object", new Method() {
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return Method.class;
                    }

                    @Override
                    public String value() {
                        return "hashCode()I";
                    }
                }),
                new Method.Bundle(
                        "com/liamcoalstudo/maialt/injector/InjectorTest",
                        "testInject",
                        "(Ljava/lang/Object;Lcom/liamcoalstudio/maialt/injector/Arguments;)V"
                ),
                new Inject() {
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return Inject.class;
                    }

                    @Override
                    public Method method() {
                        return null;
                    }

                    @Override
                    public String target() {
                        return "";
                    }

                    @Override
                    public int index() {
                        return -1;
                    }

                    @Override
                    public InjectLocation at() {
                        return InjectLocation.START;
                    }
                }
        );
    }
    @Test public void testInjectEnd() {
        resultBytes = Injector.inject(
                resultBytes,
                InjectLocation.END,
                new Method.Bundle("java/lang/Object", new Method() {
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return Method.class;
                    }

                    @Override
                    public String value() {
                        return "hashCode()I";
                    }
                }),
                new Method.Bundle(
                        "com/liamcoalstudo/maialt/injector/InjectorTest",
                        "testInject",
                        "(Ljava/lang/Object;Lcom/liamcoalstudio/maialt/injector/Arguments;)V"
                ),
                new Inject() {
                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return Inject.class;
                    }

                    @Override
                    public Method method() {
                        return null;
                    }

                    @Override
                    public String target() {
                        return "";
                    }

                    @Override
                    public int index() {
                        return -1;
                    }

                    @Override
                    public InjectLocation at() {
                        return InjectLocation.START;
                    }
                }
        );
    }

    public static void testInject(Object o, Arguments args) {
        o.notify();
    }
}

/*
 * MIT License
 *
 * Copyright (c) 2021 Liam Cole
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.liamcoalstudio.maialt.injector;

import com.liamcoalstudio.maialt.classes.ValidationException;
import com.liamcoalstudio.maialt.classes.classes.AnnotationClassMatcher;
import com.liamcoalstudio.maialt.classes.classes.ClassMatcher;
import com.liamcoalstudio.maialt.classes.classes.ClassSearcher;
import com.liamcoalstudio.maialt.classes.members.MemberSearcher;
import com.liamcoalstudio.maialt.common.Streams;
import com.liamcoalstudio.maialt.injector.Method.Bundle;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Processes inject annotations.
 *
 * @see InjectsInto
 * @see Inject
 */
public class AnnotationProcessor {
    /**
     * Gets all injector classes in a classpath location.
     * @param cp Path to search.
     * @return All classes in that path with the {@link InjectsInto} annotation.
     * @throws ValidationException Thrown by {@link ClassMatcher#validate()}.
     * @throws IOException Thrown when an IO error occurs.
     */
    public static @NotNull Stream<Class<?>> allClasses(File cp) throws ValidationException, IOException {
        return ClassSearcher.stream(cp, new AnnotationClassMatcher<>(InjectsInto.class));
    }

    /**
     * Gets all methods in a class.
     *
     * @param clazz Class to search.
     * @return All methods in that class.
     */
    public static @NotNull Stream<Method> allMethods(Class<?> clazz) {
        return MemberSearcher.streamMethods(Streams.single(clazz));
    }

    /**
     * Processes all classes in a classpath location.
     *
     * @param cp Path to search.
     * @param ldr {@link HijackedClassLoader} to load into. Has to be hijacked
     *            because {@link ClassLoader#defineClass(String, byte[], int, int)}
     *            is protected. Thankfully its protected, so we can still sort of
     *            access it.
     * @throws ValidationException Thrown by {@link ClassMatcher#validate()}
     * @throws IOException Thrown when an IO error occurs.
     */
    public static void injectAll(File cp, HijackedClassLoader ldr) throws IOException, ValidationException {
        allClasses(cp).forEach(c -> {
            try {
                inject(c, ldr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Processes a single class.
     *
     * @param c Class to process.
     * @param ldr {@link HijackedClassLoader} to load into. Has to be hijacked
     *            because {@link ClassLoader#defineClass(String, byte[], int, int)}
     *            is protected. Thankfully its protected, so we can still sort of
     *            access it.
     * @throws IOException Thrown when an IO error occurs.
     */
    public static void inject(Class<?> c, HijackedClassLoader ldr) throws IOException {
        InjectsInto into = c.getAnnotation(InjectsInto.class);
        Class<?> clazz = into.value();
        String path = "/" + clazz.getName().replace('.', '/') + ".class";
        final byte[][] bytes = {new byte[64000000]};
        try {
            clazz.getResourceAsStream(path).read(bytes[0]);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read class at " + path, e);
        }
        allMethods(c).forEach(method -> {
            if(!method.getDeclaringClass().isAnnotationPresent(InjectsInto.class)) {
                throw new IllegalStateException(method.getDeclaringClass() + " is not set up for injectors");
            }
            if(method.isAnnotationPresent(Inject.class)) {
                Inject i = method.getAnnotation(Inject.class);
                bytes[0] = Injector.inject(
                        bytes[0],
                        i.at(),
                        new Bundle(
                                clazz.getName().replace('.', '/'),
                                i.method()
                        ),
                        new Bundle(
                                method.getDeclaringClass().getName().replace('.', '/'),
                                method.getName(),
                                getDescriptor(method)
                        ),
                        i
                );
            }
        });
        ldr.define(bytes[0]);
        if(!into.saveTo().isEmpty())
            Files.write(Paths.get(into.saveTo()), bytes[0]);
    }

    /**
     * Gets the arguments and return type of a method in internal java
     * method descriptor form.
     *
     * @param method Method to construct descriptor for.
     * @return Method Descriptor
     */
    private static String getDescriptor(Method method) {
        StringBuilder b = new StringBuilder();
        b.append('(');
        for(Class<?> p : method.getParameterTypes()) {
            b.append(descriptorize(p));
        }
        b.append(")");
        b.append(descriptorize(method.getReturnType()));
        return b.toString();
    }

    /**
     * Converts a type into its descriptor form.
     *
     * @param p Class to convert.
     * @return String
     */
    private static String descriptorize(Class<?> p) {
        if(p.isPrimitive()) {
            if(p == void.class) return "V";
            if(p == byte.class) return "B";
            if(p == char.class) return "C";
            if(p == short.class) return "S";
            if(p == int.class) return "I";
            if(p == double.class) return "D";
            if(p == float.class) return "F";
            if(p == long.class) return "J";
            if(p == boolean.class) return "Z";
            throw new IllegalArgumentException("invalid primitive");
        }
        if(p.isArray()) return p.getName().replace('.', '/');
        return "L" + p.getName().replace('.', '/') + ";";
    }
}

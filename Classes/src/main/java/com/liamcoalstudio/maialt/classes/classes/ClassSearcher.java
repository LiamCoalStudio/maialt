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

package com.liamcoalstudio.maialt.classes.classes;

import com.liamcoalstudio.maialt.classes.ValidationException;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Searches a classpath for classes that match a given criteria. Can be used to
 * get every class in a classpath, or find handler classes.
 *
 * Used by <code>maialt-injector</code> to find <code>@InjectsInto</code> classes.
 */
public class ClassSearcher {
    /**
     * Gets all classes in a location. Can search in a ZIP file (.jar) or a
     * directory.
     *
     * @param cp The location to search.
     * @return An array of all classes in <code>cp</code>
     * @throws IOException Thrown if an IO error occurred.
     */
    public static Class<?>[] all(File cp) throws IOException {
        List<Class<?>> classes = new LinkedList<>();
        ClassLoader ldr = new URLClassLoader(new URL[]{cp.toURI().toURL()});
        if(cp.isDirectory()) {
            FileUtils.listFiles(cp, new String[]{"class"}, true).stream()
                .map(f -> {
                    try {
                        return ldr.loadClass(f.getPath().replace('/', '.').substring(0, f.getPath().length()-6));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .forEach(classes::add);
        } else {
            ZipFile zip = new ZipFile(cp);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while(entries.hasMoreElements()) {
                ZipEntry e = entries.nextElement();
                if(!e.getName().endsWith(".class") || e.isDirectory()) continue;
                try {
                    classes.add(ldr.loadClass(e.getName().replace('/', '.').substring(0, e.getName().length()-6)));
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return classes.toArray(new Class<?>[0]);
    }

    /**
     * Filters classes with {@link ClassMatcher#matches(Class)}.
     *
     * @param stream Stream to filter.
     * @param matchers All matchers to use for filtering.
     * @return {@link Class}[], converted from return value of
     *         {@link #stream(Stream, ClassMatcher...)}
     * @throws ValidationException Thrown by {@link ClassMatcher#validate()}
     */
    public static Class<?>[] all(Stream<Class<?>> stream, ClassMatcher... matchers) throws ValidationException {
        return stream(stream, matchers).toArray(Class<?>[]::new);
    }

    /**
     * Filters classes with {@link ClassMatcher#matches(Class)}.
     *
     * @param stream Stream to filter.
     * @param matchers All matchers to use for filtering.
     * @return {@link Stream}&lt;{@link Class}&gt; without elements that didn't match
     * @throws ValidationException Thrown by {@link ClassMatcher#validate()}
     */
    public static Stream<Class<?>> stream(Stream<Class<?>> stream, ClassMatcher... matchers) throws ValidationException {
        for(ClassMatcher m : matchers)
            m.validate();
        for(ClassMatcher m : matchers)
            stream = stream.filter(m::matches);
        return stream;
    }

    /**
     * Filters all {@link Class} objects in an array by an array of matchers.
     *
     * @param array Array of {@link Class class}es to check.
     * @param matchers Matchers to match against.
     * @return Return value of Uses {@link #stream(Class[], ClassMatcher...)},
     *         converted to an array.
     * @throws ValidationException Thrown by {@link ClassMatcher#validate()}
     */
    public static Class<?>[] all(Class<?>[] array, ClassMatcher... matchers) throws ValidationException {
        return stream(array, matchers).toArray(Class<?>[]::new);
    }

    /**
     * Filters an array of classes by {@link ClassMatcher#matches(Class)}.
     *
     * @param array Array of classes
     * @param matchers Matchers to filter against
     * @return Filtered stream
     * @throws ValidationException Thrown by {@link ClassMatcher#validate()}
     */
    public static Stream<Class<?>> stream(Class<?>[] array, ClassMatcher... matchers) throws ValidationException {
        return stream(Arrays.stream(array), matchers);
    }

    /**
     * Gets a {@link Stream} of all {@link Class}es in a location.
     *
     * @param cp Location to search
     * @return Filtered stream.
     * @throws IOException Thrown if an IO error occurred.
     */
    public static @NotNull Stream<Class<?>> stream(File cp) throws IOException {
        return Arrays.stream(all(cp));
    }

    /**
     * Gets an array of all classes in a location that match every
     * matcher in <code>matchers</code>.
     *
     * @param cp Location to search.
     * @param matchers Matchers to filter against.
     * @return Filtered array
     * @throws IOException Thrown if an IO error occurred
     * @throws ValidationException Thrown by {@link ClassMatcher#validate()}
     */
    public static Class<?>[] all(File cp, ClassMatcher... matchers) throws IOException, ValidationException {
        return stream(cp, matchers).toArray(Class<?>[]::new);
    }

    /**
     * Gets a stream of all classes in a location that match every
     * matcher in <code>matchers</code>.
     *
     * @param cp Location to search
     * @param matchers Matchers to filter against.
     * @return Filtered stream
     * @throws IOException Thrown if an IO error occurred
     * @throws ValidationException Thrown by {@link ClassMatcher#validate()}
     */
    public static Stream<Class<?>> stream(File cp, ClassMatcher... matchers) throws IOException, ValidationException {
        Stream<Class<?>> stream = stream(cp);
        for(ClassMatcher m : matchers) {
            m.validate();
        }
        for (ClassMatcher m : matchers) {
            stream = stream.filter(m::matches);
        }
        return stream;
    }
}

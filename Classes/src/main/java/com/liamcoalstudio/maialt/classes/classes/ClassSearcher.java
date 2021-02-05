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
 * get every class in a classpath,
 */
public class ClassSearcher {
    public static Class<?>[] all(File cp) throws ClassNotFoundException, IOException {
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
                classes.add(ldr.loadClass(e.getName().replace('/', '.').substring(0, e.getName().length()-6)));
            }
        }
        return classes.toArray(new Class<?>[0]);
    }

    public static Class<?>[] all(Stream<Class<?>> stream, ClassMatcher... matchers) throws ValidationException {
        return stream(stream, matchers).toArray(Class<?>[]::new);
    }

    public static Stream<Class<?>> stream(Stream<Class<?>> stream, ClassMatcher... matchers) throws ValidationException {
        for(ClassMatcher m : matchers)
            m.validate();
        for(ClassMatcher m : matchers)
            stream = stream.filter(m::matches);
        return stream;
    }

    public static Class<?>[] all(Class<?>[] array, ClassMatcher... matchers) throws ValidationException {
        return stream(array, matchers).toArray(Class<?>[]::new);
    }

    public static Stream<Class<?>> stream(Class<?>[] array, ClassMatcher... matchers) throws ValidationException {
        return stream(Arrays.stream(array), matchers);
    }

    public static @NotNull Stream<Class<?>> stream(File cp) throws IOException, ClassNotFoundException {
        return Arrays.stream(all(cp));
    }

    public static Class<?>[] all(File cp, ClassMatcher... matchers) throws IOException, ClassNotFoundException, ValidationException {
        return stream(cp, matchers).toArray(Class<?>[]::new);
    }

    public static Stream<Class<?>> stream(File cp, ClassMatcher... matchers) throws IOException, ClassNotFoundException, ValidationException {
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

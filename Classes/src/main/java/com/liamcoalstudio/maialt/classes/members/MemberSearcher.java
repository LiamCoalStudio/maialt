package com.liamcoalstudio.maialt.classes.members;

import com.liamcoalstudio.maialt.classes.ValidationException;
import com.liamcoalstudio.maialt.classes.members.searchers.MemberMatcher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class MemberSearcher {
    public static Field[] allFields(Stream<Class<?>> classes) {
        AtomicReference<Field[]> output = new AtomicReference<>(new Field[0]);
        classes.map(c -> {
            try {
                return c.getDeclaredFields();
            } catch (NoClassDefFoundError e) {
                e.printStackTrace();
                return new Field[0];
            }
        }).distinct().forEach(fields -> {
            Field[] n = new Field[output.get().length + fields.length];
            int i = 0;
            for (Field field : output.get()) {
                n[i++] = field;
            }
            for (Field field : fields) {
                n[i++] = field;
            }
            output.set(n);
        });
        return output.get();
    }

    public static Stream<Field> streamFields(Stream<Class<?>> classes) {
        return Arrays.stream(allFields(classes));
    }

    public static Method[] allMethods(Stream<Class<?>> classes) {
        AtomicReference<Method[]> output = new AtomicReference<>(new Method[0]);
        classes.map(c -> {
            try {
                return c.getDeclaredMethods();
            } catch (NoClassDefFoundError e) {
                e.printStackTrace();
                return new Method[0];
            }
        }).distinct().forEach(methods -> {
            Method[] n = new Method[output.get().length + methods.length];
            int i = 0;
            for (Method field : output.get()) {
                n[i++] = field;
            }
            for (Method field : methods) {
                n[i++] = field;
            }
            output.set(n);
        });
        return output.get();
    }

    public static Stream<Method> streamMethods(Stream<Class<?>> classes) {
        return Arrays.stream(allMethods(classes));
    }

    public static Stream<Field> streamFields(Stream<Class<?>> classes, MemberMatcher... matchers) throws ValidationException {
        Stream<Field> s = streamFields(classes);
        for(MemberMatcher m : matchers) {
            m.validate();
        }
        for(MemberMatcher m : matchers) {
            s = s.filter(m::matches);
        }
        return s;
    }

    public static Stream<Method> streamMethods(Stream<Class<?>> classes, MemberMatcher... matchers) throws ValidationException {
        Stream<Method> s = streamMethods(classes);
        for(MemberMatcher m : matchers) {
            m.validate();
        }
        for(MemberMatcher m : matchers) {
            s = s.filter(m::matches);
        }
        return s;
    }

    public static Field[] allFields(Stream<Class<?>> classes, MemberMatcher... matchers) throws ValidationException {
        return streamFields(classes, matchers).toArray(Field[]::new);
    }

    public static Method[] allMethods(Stream<Class<?>> classes, MemberMatcher... matchers) throws ValidationException {
        return streamMethods(classes, matchers).toArray(Method[]::new);
    }
}

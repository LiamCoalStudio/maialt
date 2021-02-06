package com.liamcoalstudio.maialt.classes.members;

import com.liamcoalstudio.maialt.classes.ValidationException;
import com.liamcoalstudio.maialt.classes.classes.ClassSearcher;
import com.liamcoalstudio.maialt.classes.members.searchers.MemberMatcher;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * Searches for members inside classes. You can use any
 * {@link ClassSearcher#stream} to get the classes.
 */
public class MemberSearcher {
    /**
     * Gets all fields in a stream of classes.
     *
     * @param classes Classes to search
     * @return Array of every field in every class in the input.
     */
    public static @NotNull Field[] allFields(@NotNull Stream<Class<?>> classes) {
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

    /**
     * Gets a stream of all fields returned by {@link #allFields(Stream)}, and
     * has the same restrictions.
     *
     * @param classes Classes to search
     * @return Stream of every field in every class in the input.
     */
    public static @NotNull Stream<Field> streamFields(@NotNull Stream<Class<?>> classes) {
        return Arrays.stream(allFields(classes));
    }

    /**
     * Gets all methods in a stream of classes.
     *
     * @param classes Classes to search
     * @return Array of every method in every class in the input.
     */
    public static @NotNull Method[] allMethods(@NotNull Stream<Class<?>> classes) {
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

    /**
     * Gets a stream of all methods returned by {@link #allMethods(Stream)}, and
     * has the same restrictions.
     *
     * @param classes Classes to search
     * @return Stream of every method in every class in the input.
     */
    public static @NotNull Stream<Method> streamMethods(@NotNull Stream<Class<?>> classes) {
        return Arrays.stream(allMethods(classes));
    }

    /**
     * Gets a stream of all fields that match a criteria, using
     * {@link MemberMatcher#matches(Field)}. Gets the fields using
     * {@link #allFields(Stream)}, and comes with the same restrictions.
     *
     * @param classes Classes to search
     * @param matchers Matchers to filter against.
     * @return All fields that match every matcher.
     * @throws ValidationException Thrown by {@link MemberMatcher#validate()}
     */
    public static @NotNull Stream<Field> streamFields(@NotNull Stream<Class<?>> classes, @NotNull MemberMatcher... matchers) throws ValidationException {
        Stream<Field> s = streamFields(classes);
        for(MemberMatcher m : matchers) {
            m.validate();
        }
        for(MemberMatcher m : matchers) {
            s = s.filter(m::matches);
        }
        return s;
    }

    /**
     * Gets a stream of all methods that match a criteria, using
     * {@link MemberMatcher#matches(Method)}. Gets the fields using
     * {@link #allMethods(Stream)}, and comes with the same restrictions.
     *
     * @param classes Classes to search
     * @param matchers Matchers to filter against.
     * @return All methods that match every matcher.
     * @throws ValidationException Thrown by {@link MemberMatcher#validate()}
     */
    public static @NotNull Stream<Method> streamMethods(@NotNull Stream<Class<?>> classes, @NotNull MemberMatcher... matchers) throws ValidationException {
        Stream<Method> s = streamMethods(classes);
        for(MemberMatcher m : matchers) {
            m.validate();
        }
        for(MemberMatcher m : matchers) {
            s = s.filter(m::matches);
        }
        return s;
    }

    /**
     * Gets an array of all fields that match a criteria, using
     * {@link #streamFields(Stream, MemberMatcher...)} to get the fields,
     * and {@link Stream#toArray(IntFunction)} to convert it.
     *
     * @param classes Classes to search
     * @param matchers Matchers to filter against.
     * @return All fields that matcher every matcher.
     * @throws ValidationException Thrown by {@link MemberMatcher#validate()}
     */
    public static @NotNull Field[] allFields(@NotNull Stream<Class<?>> classes, @NotNull MemberMatcher... matchers) throws ValidationException {
        return streamFields(classes, matchers).toArray(Field[]::new);
    }

    /**
     * Gets an array of all methods that match a criteria, using
     * {@link #streamMethods(Stream, MemberMatcher...)} to get the fields,
     * and {@link Stream#toArray(IntFunction)} to convert it.
     *
     * @param classes Classes to search
     * @param matchers Matchers to filter against.
     * @return All methods that matcher every matcher.
     * @throws ValidationException Thrown by {@link MemberMatcher#validate()}
     */
    public static @NotNull Method[] allMethods(@NotNull Stream<Class<?>> classes, @NotNull MemberMatcher... matchers) throws ValidationException {
        return streamMethods(classes, matchers).toArray(Method[]::new);
    }
}

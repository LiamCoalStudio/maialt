package com.liamcoalstudio.maialt.classes.classes;

import com.liamcoalstudio.maialt.classes.ValidationException;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ClassSearcherTest {
    @Test public void testArrayArray() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.all(new Class[]{First.class, Second.class, Third.class}),
                new Class<?>[]{First.class, Second.class, Third.class}
        );
    }
    @Test public void testArrayStream() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.all(Arrays.stream(new Class<?>[]{First.class, Second.class, Third.class})),
                new Class<?>[]{First.class, Second.class, Third.class}
        );
    }
    @Test public void testStreamArray() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.stream(new Class<?>[]{First.class, Second.class, Third.class})
                        .toArray(Class<?>[]::new),
                new Class<?>[]{First.class, Second.class, Third.class}
        );
    }
    @Test public void testStreamStream() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.stream(Arrays.stream(new Class<?>[]{First.class, Second.class, Third.class}))
                        .toArray(Class<?>[]::new),
                new Class<?>[]{First.class, Second.class, Third.class}
        );
    }

    static class Superclass {}
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Thing {}

    static class First extends Superclass {}
    @Thing static class Second {}
    @Thing static class Third extends Superclass {}
}
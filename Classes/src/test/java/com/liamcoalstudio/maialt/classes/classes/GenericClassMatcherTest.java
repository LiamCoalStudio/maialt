package com.liamcoalstudio.maialt.classes.classes;

import com.liamcoalstudio.maialt.classes.ValidationException;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class GenericClassMatcherTest {
    @Test public void testArrayArrayGenericTrue() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.all(new Class[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}, new GenericClassMatcher(c -> true)),
                new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}
        );
    }
    @Test public void testArrayStreamGenericTrue() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.all(Arrays.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}), new GenericClassMatcher(c -> true)),
                new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}
        );
    }
    @Test public void testStreamArrayGenericTrue() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}, new GenericClassMatcher(c -> true))
                        .toArray(Class<?>[]::new),
                new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}
        );
    }
    @Test public void testStreamStreamGenericTrue() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.stream(Arrays.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}), new GenericClassMatcher(c -> true))
                        .toArray(Class<?>[]::new),
                new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}
        );
    }

    @Test public void testArrayArrayGenericFalse() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.all(new Class[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}, new GenericClassMatcher(c -> false)),
                new Class<?>[]{}
        );
    }
    @Test public void testArrayStreamGenericFalse() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.all(Arrays.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}), new GenericClassMatcher(c -> false)),
                new Class<?>[]{}
        );
    }
    @Test public void testStreamArrayGenericFalse() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}, new GenericClassMatcher(c -> false))
                        .toArray(Class<?>[]::new),
                new Class<?>[]{}
        );
    }
    @Test public void testStreamStreamGenericFalse() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.stream(Arrays.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}), new GenericClassMatcher(c -> false))
                        .toArray(Class<?>[]::new),
                new Class<?>[]{}
        );
    }
}

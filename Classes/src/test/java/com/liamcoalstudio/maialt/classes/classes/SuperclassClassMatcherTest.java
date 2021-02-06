package com.liamcoalstudio.maialt.classes.classes;

import com.liamcoalstudio.maialt.classes.ValidationException;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class SuperclassClassMatcherTest {
    @Test public void testArrayArraySubtype() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.all(new Class[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}, new SuperclassClassMatcher(ClassSearcherTest.Superclass.class)),
                new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Third.class}
        );
    }
    @Test public void testArrayStreamSubtype() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.all(Arrays.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}), new SuperclassClassMatcher(ClassSearcherTest.Superclass.class)),
                new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Third.class}
        );
    }
    @Test public void testStreamArraySubtype() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}, new SuperclassClassMatcher(ClassSearcherTest.Superclass.class))
                        .toArray(Class<?>[]::new),
                new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Third.class}
        );
    }
    @Test public void testStreamStreamSubtype() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.stream(Arrays.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}), new SuperclassClassMatcher(ClassSearcherTest.Superclass.class))
                        .toArray(Class<?>[]::new),
                new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Third.class}
        );
    }
}

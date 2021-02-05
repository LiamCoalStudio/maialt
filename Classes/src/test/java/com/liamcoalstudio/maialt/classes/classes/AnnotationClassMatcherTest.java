package com.liamcoalstudio.maialt.classes.classes;

import com.liamcoalstudio.maialt.classes.ValidationException;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AnnotationClassMatcherTest {
    @Test public void testArrayArrayAnnotation() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.all(new Class[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}, new AnnotationClassMatcher<>(ClassSearcherTest.Thing.class)),
                new Class<?>[]{ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}
        );
    }
    @Test public void testArrayStreamAnnotation() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.all(Arrays.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}), new AnnotationClassMatcher<>(ClassSearcherTest.Thing.class)),
                new Class<?>[]{ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}
        );
    }
    @Test public void testStreamArrayAnnotation() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}, new AnnotationClassMatcher<>(ClassSearcherTest.Thing.class))
                        .toArray(Class<?>[]::new),
                new Class<?>[]{ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}
        );
    }
    @Test public void testStreamStreamAnnotation() throws ValidationException {
        assertArrayEquals(
                ClassSearcher.stream(Arrays.stream(new Class<?>[]{ClassSearcherTest.First.class, ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}), new AnnotationClassMatcher<>(ClassSearcherTest.Thing.class))
                        .toArray(Class<?>[]::new),
                new Class<?>[]{ClassSearcherTest.Second.class, ClassSearcherTest.Third.class}
        );
    }
}

package com.liamcoalstudio.maialt.classes.members;

import com.liamcoalstudio.maialt.classes.ValidationException;
import com.liamcoalstudio.maialt.common.Streams;
import com.liamcoalstudio.maialt.classes.members.searchers.AnnotationMemberMatcher;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertArrayEquals;

public class AnnotationMemberMatcherTest {
    @Test public void testAllFieldsAnnotation() throws NoSuchFieldException, ValidationException {
        assertArrayEquals(MemberSearcher.allFields(Streams.single(MemberSearcherTest.TestClass.class), new AnnotationMemberMatcher<>(MemberSearcherTest.Thing.class)), new Field[] {
                MemberSearcherTest.TestClass.class.getDeclaredField("meow"),
        });
    }
    @Test public void testStreamFieldsAnnotation() throws NoSuchFieldException, ValidationException {
        assertArrayEquals(MemberSearcher.streamFields(Streams.single(MemberSearcherTest.TestClass.class), new AnnotationMemberMatcher<>(MemberSearcherTest.Thing.class)).toArray(Field[]::new), new Field[] {
                MemberSearcherTest.TestClass.class.getDeclaredField("meow"),
        });
    }
    @Test public void testAllMethodsAnnotation() throws NoSuchMethodException, ValidationException {
        assertArrayEquals(MemberSearcher.allMethods(Streams.single(MemberSearcherTest.TestClass.class), new AnnotationMemberMatcher<>(MemberSearcherTest.Thing.class)), new Method[] {
                MemberSearcherTest.TestClass.class.getDeclaredMethod("setMeow", int.class),
        });
    }
    @Test public void testStreamMethodsAnnotation() throws NoSuchMethodException, ValidationException {
        assertArrayEquals(MemberSearcher.streamMethods(Streams.single(MemberSearcherTest.TestClass.class), new AnnotationMemberMatcher<>(MemberSearcherTest.Thing.class)).toArray(Method[]::new), new Method[] {
                MemberSearcherTest.TestClass.class.getDeclaredMethod("setMeow", int.class),
        });
    }
}

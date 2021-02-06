package com.liamcoalstudio.maialt.classes.members;

import com.liamcoalstudio.maialt.classes.ValidationException;
import com.liamcoalstudio.maialt.common.Streams;
import com.liamcoalstudio.maialt.classes.members.searchers.DescriptorsMemberMatcher;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertArrayEquals;

public class DescriptorsMemberMatcherTest {
    @Test public void testAllFieldsSignature() throws NoSuchFieldException, ValidationException {
        assertArrayEquals(MemberSearcher.allFields(Streams.single(MemberSearcherTest.TestClass.class), new DescriptorsMemberMatcher(int.class)), new Field[] {
                MemberSearcherTest.TestClass.class.getDeclaredField("cat"),
        });
    }
    @Test public void testStreamFieldsSignature() throws NoSuchFieldException, ValidationException {
        assertArrayEquals(MemberSearcher.streamFields(Streams.single(MemberSearcherTest.TestClass.class), new DescriptorsMemberMatcher(int.class)).toArray(Field[]::new), new Field[] {
                MemberSearcherTest.TestClass.class.getDeclaredField("cat"),
        });
    }
    @Test public void testAllMethodsSignature() throws NoSuchMethodException, ValidationException {
        assertArrayEquals(MemberSearcher.allMethods(Streams.single(MemberSearcherTest.TestClass.class), new DescriptorsMemberMatcher(void.class, new Class[]{int.class})), new Method[] {
                MemberSearcherTest.TestClass.class.getDeclaredMethod("setMeow", int.class),
        });
    }
    @Test public void testStreamMethodsSignature() throws NoSuchMethodException, ValidationException {
        assertArrayEquals(MemberSearcher.streamMethods(Streams.single(MemberSearcherTest.TestClass.class), new DescriptorsMemberMatcher(void.class, new Class[]{int.class})).toArray(Method[]::new), new Method[] {
                MemberSearcherTest.TestClass.class.getDeclaredMethod("setMeow", int.class),
        });
    }
}

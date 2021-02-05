package com.liamcoalstudio.maialt.classes.members;

import com.liamcoalstudio.maialt.classes.ValidationException;
import com.liamcoalstudio.maialt.common.Streams;
import com.liamcoalstudio.maialt.classes.members.searchers.GenericMemberMatcher;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GenericMemberMatcherTest {
    @Test public void testAllFieldsAnyTrue() throws NoSuchFieldException, ValidationException {
        assertArrayEquals(MemberSearcher.allFields(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.any(m -> true)), new Field[] {
                MemberSearcherTest.TestClass.class.getDeclaredField("meow"),
                MemberSearcherTest.TestClass.class.getDeclaredField("cat"),
        });
    }
    @Test public void testStreamFieldsAnyTrue() throws NoSuchFieldException, ValidationException {
        assertArrayEquals(MemberSearcher.streamFields(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.any(m -> true)).toArray(Field[]::new), new Field[] {
                MemberSearcherTest.TestClass.class.getDeclaredField("meow"),
                MemberSearcherTest.TestClass.class.getDeclaredField("cat"),
        });
    }
    @Test public void testAllMethodsAnyTrue() throws NoSuchMethodException, ValidationException {
        assertArrayEquals(MemberSearcher.allMethods(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.any(m -> true)), new Method[] {
                MemberSearcherTest.TestClass.class.getDeclaredMethod("setMeow", int.class),
        });
    }
    @Test public void testStreamMethodsAnyTrue() throws NoSuchMethodException, ValidationException {
        assertArrayEquals(MemberSearcher.streamMethods(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.any(m -> true)).toArray(Method[]::new), new Method[] {
                MemberSearcherTest.TestClass.class.getDeclaredMethod("setMeow", int.class),
        });
    }

    @Test public void testAllFieldsAnyFalse() throws ValidationException {
        assertArrayEquals(MemberSearcher.allFields(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.any(m -> false)), new Field[]{});
    }
    @Test public void testStreamFieldsAnyFalse() throws ValidationException {
        assertArrayEquals(MemberSearcher.streamFields(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.any(m -> false)).toArray(Field[]::new), new Field[]{});
    }
    @Test public void testAllMethodsAnyFalse() throws ValidationException {
        assertArrayEquals(MemberSearcher.allMethods(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.any(m -> false)), new Method[]{});
    }
    @Test public void testStreamMethodsAnyFalse() throws ValidationException {
        assertArrayEquals(MemberSearcher.streamMethods(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.any(m -> false)).toArray(Method[]::new), new Method[]{});
    }


    @Test public void testAllMethodsMethodTrue() throws NoSuchMethodException, ValidationException {
        assertArrayEquals(MemberSearcher.allMethods(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.method(m -> true)), new Method[] {
                MemberSearcherTest.TestClass.class.getDeclaredMethod("setMeow", int.class),
        });
    }
    @Test public void testStreamMethodsMethodTrue() throws NoSuchMethodException, ValidationException {
        assertArrayEquals(MemberSearcher.streamMethods(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.method(m -> true)).toArray(Method[]::new), new Method[] {
                MemberSearcherTest.TestClass.class.getDeclaredMethod("setMeow", int.class),
        });
    }

    @Test public void testAllMethodsMethodFalse() throws ValidationException {
        assertArrayEquals(MemberSearcher.allMethods(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.method(m -> false)), new Method[]{});
    }
    @Test public void testStreamMethodsMethodFalse() throws ValidationException {
        assertArrayEquals(MemberSearcher.streamMethods(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.method(m -> false)).toArray(Method[]::new), new Method[]{});
    }


    @Test public void testAllFieldsFieldTrue() throws NoSuchFieldException, ValidationException {
        assertArrayEquals(MemberSearcher.allFields(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.field(m -> true)), new Field[] {
                MemberSearcherTest.TestClass.class.getDeclaredField("meow"),
                MemberSearcherTest.TestClass.class.getDeclaredField("cat"),
        });
    }
    @Test public void testStreamFieldsFieldTrue() throws NoSuchFieldException, ValidationException {
        assertArrayEquals(MemberSearcher.streamFields(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.field(m -> true)).toArray(Field[]::new), new Field[] {
                MemberSearcherTest.TestClass.class.getDeclaredField("meow"),
                MemberSearcherTest.TestClass.class.getDeclaredField("cat"),
        });
    }

    @Test public void testAllFieldsFieldFalse() throws ValidationException {
        assertArrayEquals(MemberSearcher.allFields(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.field(m -> false)), new Field[]{});
    }
    @Test public void testStreamFieldsFieldFalse() throws ValidationException {
        assertArrayEquals(MemberSearcher.streamFields(Streams.single(MemberSearcherTest.TestClass.class), GenericMemberMatcher.field(m -> false)).toArray(Field[]::new), new Field[]{});
    }
}

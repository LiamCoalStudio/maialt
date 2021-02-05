package com.liamcoalstudio.maialt.classes.members;

import com.liamcoalstudio.maialt.common.Streams;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class MemberSearcherTest {
    @Test public void testAllFields() throws NoSuchFieldException {
        assertArrayEquals(MemberSearcher.allFields(Streams.single(TestClass.class)), new Field[] {
                TestClass.class.getDeclaredField("meow"),
                TestClass.class.getDeclaredField("cat"),
        });
    }
    @Test public void testStreamFields() throws NoSuchFieldException {
        assertArrayEquals(MemberSearcher.streamFields(Streams.single(TestClass.class)).toArray(Field[]::new), new Field[] {
                TestClass.class.getDeclaredField("meow"),
                TestClass.class.getDeclaredField("cat"),
        });
    }
    @Test public void testAllMethods() throws NoSuchMethodException {
        assertArrayEquals(MemberSearcher.allMethods(Streams.single(TestClass.class)), new Method[] {
                TestClass.class.getDeclaredMethod("setMeow", int.class),
        });
    }
    @Test public void testStreamMethods() throws NoSuchMethodException {
        assertArrayEquals(MemberSearcher.streamMethods(Streams.single(TestClass.class)).toArray(Method[]::new), new Method[] {
                TestClass.class.getDeclaredMethod("setMeow", int.class),
        });
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD})
    @interface Thing {}

    static class TestClass {
        @Thing
        private String meow;
        private int cat;

        @Thing
        private void setMeow(int v) {
            cat = v;
        }
    }
}
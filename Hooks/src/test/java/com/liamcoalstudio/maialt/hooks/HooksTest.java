package com.liamcoalstudio.maialt.hooks;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.*;

public class HooksTest {
    @BeforeClass
    public static void setUp() {
        Hooks.register("test", new TestHook());
    }

    @Test
    public void testRegister() {
        Hooks.register("test2", new TestHook());
        assertTrue(Hooks.get("test2") instanceof TestHook);
    }

    @Test
    public void testGet() {
        assertTrue(Hooks.get("test") instanceof TestHook);
    }

    @Test
    public void testSyncUsing() {
        Hooks.syncUsing(TestUsing.class, TestUsing.class.getAnnotation(Using.class));
        assertTrue(TestUsing.hookTest instanceof TestHook);
    }

    /*
     * This is just a test!
     *
     * I never said it would be a _good_ test!
     */
    private static class TestHook implements Hook {
        @Override
        public <T> CompletableFuture<T> request(Class<T> c, Object o) {
            return null;
        }

        @Override
        public <T> CompletableFuture<Void> send(Class<T> c, Object o) {
            return null;
        }
    }

    @Using("test")
    private static class TestUsing {
        public static Hook hookTest;
    }
}
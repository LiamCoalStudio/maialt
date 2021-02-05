package com.liamcoalstudio.maialt.hooks;

import com.liamcoalstudio.maialt.common.Registry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Hooks {
    private static final Registry<String, Hook> REGISTRY = new Registry<>();

    public static synchronized void register(String name, Hook hook) {
        REGISTRY.register(name, hook);
    }

    public static synchronized Hook get(String name) {
        return REGISTRY.get(name);
    }

    public static synchronized void syncUsing(Class<?> c, Using using) {
        String fieldName = "hook" + upperFirstLetter(using.value());
        Field f;
        try {
            f = c.getField(fieldName);
        } catch (NoSuchFieldException e) {
            return;
        }
        f.setAccessible(true);
        if(!f.getType().isAssignableFrom(Hook.class)) {
            throw new IllegalStateException(c.getName() + "#" + fieldName + " is not a valid hook listener");
        }
        if(!Modifier.isStatic(f.getModifiers())) {
            throw new IllegalStateException(c.getName() + "#" + fieldName + " is not static");
        }
        try {
            f.set(null, get(using.value()));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(c.getName() + "#" + fieldName + " was not accessible", e);
        }
    }

    private static String upperFirstLetter(String value) {
        String first = value.substring(0, 1);
        return first.toUpperCase() + value.substring(1);
    }
}

/*
 * MIT License
 *
 * Copyright (c) 2021 Liam Cole
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.liamcoalstudio.maialt.hooks;

import com.liamcoalstudio.maialt.common.Registrable;
import com.liamcoalstudio.maialt.common.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * General functions for {@link Hook}s.
 */
public class Hooks {
    private static final Registry<String, Hook> REGISTRY = new Registry<>();

    /**
     * Registers a hook that can be used in {@link #get} or {@link #syncUsing}.
     * @param name Name of the hook.
     * @param hook The hook to register.
     */
    public static synchronized void register(@NotNull String name, @NotNull Hook hook) {
        REGISTRY.register(name, hook);
    }

    /**
     * Gets a hook that was registered with {@link #register(String, Hook)}.
     *
     * @param name Name of the hook.
     * @return {@link Hook} instance if present, <code>null</code> otherwise.
     */
    public static synchronized @Nullable Hook get(String name) {
        return REGISTRY.get(name);
    }

    /**
     * Sets a field in a class to a hook value based on a {@link Using}
     * annotation. The field name is generated by using {@link String#toUpperCase()}
     * on the first letter of {@link Using#value()}, then prepending "hook".
     *
     * @param c Class to process.
     * @param using Annotation to refer to.
     */
    public static synchronized void syncUsing(@NotNull Class<?> c, @NotNull Using using) {
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

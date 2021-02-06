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

package com.liamcoalstudio.maialt.injector;

import org.jetbrains.annotations.NotNull;

/**
 * A class loader that allows you to define classes using the elusive
 * {@link ClassLoader#defineClass(String, byte[], int, int)} method,
 * packaged as {@link #define(byte[])}.
 */
public class HijackedClassLoader extends ClassLoader {
    /**
     * Constructs a new {@link HijackedClassLoader}. You should use the class
     * loader that loaded the original class as the parent.
     *
     * @param parent The parent of this loader.
     */
    public HijackedClassLoader(@NotNull ClassLoader parent) {
        super(parent);
    }

    /**
     * @deprecated This constructor should not be used as the system class loader
     *             is not usually what you want. Instead of this, use
     *             {@link #HijackedClassLoader(ClassLoader)}, passing
     *             <code>[class name].class.{@link Class#getClassLoader()
     *             getClassLoader()}</code> as the argument.
     */
    @Deprecated
    public HijackedClassLoader() {
        super(ClassLoader.getSystemClassLoader());
    }

    /**
     * Defines a method from a byte[].
     *
     * @param bytes Class bytes.
     * @see #defineClass(String, byte[], int, int)
     */
    public void define(byte[] bytes) {
        super.defineClass(null, bytes, 0, bytes.length);
    }
}

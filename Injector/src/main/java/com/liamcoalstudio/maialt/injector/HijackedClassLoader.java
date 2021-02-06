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

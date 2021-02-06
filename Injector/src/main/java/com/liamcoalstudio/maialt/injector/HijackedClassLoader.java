package com.liamcoalstudio.maialt.injector;

public class HijackedClassLoader extends ClassLoader {
    public HijackedClassLoader(ClassLoader parent) {
        super(parent);
    }

    public HijackedClassLoader() {
        super(ClassLoader.getSystemClassLoader());
    }

    public void define(byte[] bytes) {
        super.defineClass(null, bytes, 0, bytes.length);
    }
}

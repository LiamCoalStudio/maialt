package com.liamcoalstudio.maialt.injector;

public class HijackedClassLoader extends ClassLoader {
    public void define(byte[] bytes) {
        super.defineClass(null, bytes, 0, bytes.length);
    }
}

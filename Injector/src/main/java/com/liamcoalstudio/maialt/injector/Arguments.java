package com.liamcoalstudio.maialt.injector;

import java.util.Arrays;

public class Arguments {
    private final Object[] objects;

    public Arguments(Object... objects) {
        this.objects = objects;
    }

    @SuppressWarnings("unchecked")
    public<T> T get(int i) {
        return (T)objects[i];
    }

    @Override
    public String toString() {
        return "Arguments{" +
                "objects=" + Arrays.toString(objects) +
                '}';
    }
}

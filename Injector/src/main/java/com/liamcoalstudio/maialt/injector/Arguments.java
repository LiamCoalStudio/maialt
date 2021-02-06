package com.liamcoalstudio.maialt.injector;

import java.util.Arrays;

public class Arguments {
    private final ArgumentReference[] objects;

    public Arguments(ArgumentReference... objects) {
        this.objects = objects;
    }

    public ArgumentReference get(int i) {
        return objects[i];
    }

    public void set(int i, ArgumentReference r) {
        objects[i] = r;
    }

    @Override
    public String toString() {
        return "Arguments{" +
                "objects=" + Arrays.toString(objects) +
                "}";
    }
}

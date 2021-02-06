package com.liamcoalstudio.maialt.injector;

import org.jetbrains.annotations.Contract;

import java.util.Arrays;

/**
 * Array of arguments that were passed to the original method used inside
 * injects.
 */
public class Arguments {
    private final ArgumentReference[] objects;

    @Contract(pure = true)
    public Arguments(ArgumentReference... objects) {
        this.objects = objects;
    }

    @Contract(pure = true)
    public ArgumentReference get(int i) {
        return objects[i];
    }

    @Override
    public String toString() {
        return "Arguments{" +
                "objects=" + Arrays.toString(objects) +
                "}";
    }
}

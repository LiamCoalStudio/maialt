package com.liamcoalstudio.maialt.classes.classes;

import java.util.function.Function;

public class GenericClassMatcher implements ClassMatcher {
    private final Function<Class<?>, Boolean> function;

    public GenericClassMatcher(Function<Class<?>, Boolean> function) {
        this.function = function;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return function.apply(clazz);
    }

    @Override
    public void validate() {}
}

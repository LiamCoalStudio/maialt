package com.liamcoalstudio.maialt.classes.classes;

import java.util.function.Function;

/**
 * Matches a class based on a function.
 */
public class GenericClassMatcher implements ClassMatcher {
    private final Function<Class<?>, Boolean> function;

    /**
     * Creates a new Generic Class Matcher with the specified function.
     *
     * @param function If this function returns <code>false</code>,
     *                 then the match fails. If it returns <code>true</code>,
     *                 the match succeeds
     */
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

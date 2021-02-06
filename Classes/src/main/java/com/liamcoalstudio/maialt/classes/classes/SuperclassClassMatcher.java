package com.liamcoalstudio.maialt.classes.classes;

import org.jetbrains.annotations.NotNull;

/**
 * Matches classes based on their subtype. This class only matches based on the
 * direct subtype.
 */
public class SuperclassClassMatcher implements ClassMatcher {
    private final Class<?> type;

    /**
     * Creates a new Subtype Class Matcher
     * @param type S
     */
    public SuperclassClassMatcher(@NotNull Class<?> type) {
        this.type = type;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return clazz.getSuperclass() == type;
    }

    @Override
    public void validate() {}
}

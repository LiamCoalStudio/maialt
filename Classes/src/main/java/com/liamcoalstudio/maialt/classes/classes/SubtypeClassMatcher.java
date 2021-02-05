package com.liamcoalstudio.maialt.classes.classes;

import com.liamcoalstudio.maialt.classes.ValidationException;

public class SubtypeClassMatcher implements ClassMatcher {
    private final Class<?> type;

    public SubtypeClassMatcher(Class<?> type) {
        this.type = type;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return clazz.getSuperclass() == type;
    }

    @Override
    public void validate() throws ValidationException {
        if(type == null) throw new ValidationException("Type cannot be null");
    }
}

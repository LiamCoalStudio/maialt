package com.liamcoalstudio.maialt.classes.classes;

import com.liamcoalstudio.maialt.classes.ValidationException;

public interface ClassMatcher {
    boolean matches(Class<?> clazz);
    void validate() throws ValidationException;
}

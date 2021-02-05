package com.liamcoalstudio.maialt.classes.members.searchers;

import com.liamcoalstudio.maialt.classes.ValidationException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface MemberMatcher {
    boolean matches(Field field);
    boolean matches(Method method);
    default void validate() throws ValidationException {};
}

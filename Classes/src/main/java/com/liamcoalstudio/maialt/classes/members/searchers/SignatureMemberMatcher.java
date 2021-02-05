package com.liamcoalstudio.maialt.classes.members.searchers;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class SignatureMemberMatcher implements MemberMatcher {
    private final Class<?> type;
    private final Class<?>[] parameters;

    public SignatureMemberMatcher(@NotNull Class<?> type) {
        this.type = type;
        parameters = new Class<?>[0];
    }

    public SignatureMemberMatcher(@NotNull Class<?> type, @NotNull Class<?>[] parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    @Override
    public boolean matches(Field field) {
        return field.getType() == type;
    }

    @Override
    public boolean matches(Method method) {
        return method.getReturnType() == type && Arrays.equals(method.getParameterTypes(), parameters);
    }
}

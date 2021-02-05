package com.liamcoalstudio.maialt.classes.members.searchers;

import com.liamcoalstudio.maialt.classes.ValidationException;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotationMemberMatcher<T extends Annotation> implements MemberMatcher {
    private final Class<T> a;

    public AnnotationMemberMatcher(Class<T> a) {
        this.a = a;
    }

    @Override
    public boolean matches(Field field) {
        return field.isAnnotationPresent(a);
    }

    @Override
    public boolean matches(Method method) {
        return method.isAnnotationPresent(a);
    }

    @Override
    public void validate() throws ValidationException {
        if(!a.isAnnotation())
            throw new ValidationException("Invalid annotation");
        if(a.isAnnotationPresent(Target.class)) {
            if(Arrays.stream(a.getAnnotation(Target.class).value()).noneMatch(
                    t -> t == ElementType.FIELD || t == ElementType.METHOD)) {
                throw new ValidationException("@ " + a.getSimpleName() + " cannot target fields or methods");
            }
        }
    }
}

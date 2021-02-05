package com.liamcoalstudio.maialt.classes.classes;

import com.liamcoalstudio.maialt.classes.ValidationException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;
import java.util.Arrays;

public class AnnotationClassMatcher<T extends Annotation> implements ClassMatcher {
    private final Class<T> a;

    public AnnotationClassMatcher(@NotNull Class<T> annotation) {
        this.a = annotation;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return clazz.isAnnotationPresent(a);
    }

    @Override
    public void validate() throws ValidationException {
        if(!a.isAnnotation())
            throw new ValidationException("Invalid annotation");
        if(a.isAnnotationPresent(Target.class)) {
            if(Arrays.stream(a.getAnnotation(Target.class).value()).noneMatch(
                    t -> t == ElementType.TYPE)) {
                throw new ValidationException("@ " + a.getSimpleName() + " cannot target types");
            }
        }
    }
}

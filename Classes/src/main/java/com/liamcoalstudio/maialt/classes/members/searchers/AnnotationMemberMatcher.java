package com.liamcoalstudio.maialt.classes.members.searchers;

import com.liamcoalstudio.maialt.classes.ValidationException;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Matches class members based on the annotations they have.
 *
 * @param <T> Annotation type to search for.
 */
public class AnnotationMemberMatcher<T extends Annotation> implements MemberMatcher {
    private final Class<T> a;

    /**
     * Creates a new Annotation Member Matcher, searching for
     * the provided annotation type.
     *
     * @param a Annotation to search for.
     */
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

    /**
     * Ensures that {@link #a} is actually an annotation and can be applied
     * to either fields or methods. (or both)
     *
     * @throws ValidationException Thrown if one if these conditions isn't
     *                             met.
     */
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

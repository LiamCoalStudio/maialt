package com.liamcoalstudio.maialt.classes.classes;

import com.liamcoalstudio.maialt.classes.ValidationException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;
import java.util.Arrays;

/**
 * Matches annotations on a class.
 *
 * @param <T> The class of the annotation being matched.
 */
public class AnnotationClassMatcher<T extends Annotation> implements ClassMatcher {
    private final Class<T> a;

    /**
     * Constructions an {@link AnnotationClassMatcher} that searches for the
     * annotation passed here.
     *
     * @param annotation Annotation to search for. Should not be null.
     */
    public AnnotationClassMatcher(@NotNull Class<T> annotation) {
        this.a = annotation;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return clazz.isAnnotationPresent(a);
    }

    /**
     * Validates this matcher.
     *
     * @throws ValidationException Thrown if the arguments passed to
     *   {@link #AnnotationClassMatcher(Class)} are null
     *   or cannot be applied to types.
     */
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

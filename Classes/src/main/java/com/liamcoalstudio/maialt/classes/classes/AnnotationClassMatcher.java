/*
 * MIT License
 *
 * Copyright (c) 2021 Liam Cole
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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

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

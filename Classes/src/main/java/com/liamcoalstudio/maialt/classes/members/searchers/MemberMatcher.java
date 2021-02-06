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
import com.liamcoalstudio.maialt.classes.members.MemberSearcher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Matches members of classes.
 *
 * @see AnnotationMemberMatcher
 * @see GenericMemberMatcher
 * @see DescriptorsMemberMatcher
 * @see MemberSearcher
 */
public interface MemberMatcher {
    /**
     * Attempts to match a field.
     *
     * @param field Field to match.
     * @return <code>true</code> if matching, <code>false</code> otherwise
     */
    boolean matches(Field field);

    /**
     * Attempts to match a method.
     *
     * @param method Method to match.
     * @return <code>true</code> if matching, <code>false</code> otherwise
     */
    boolean matches(Method method);

    /**
     * Checks if this matcher is valid.
     *
     * Optional.
     *
     * @throws ValidationException Thrown if not valid.
     */
    default void validate() throws ValidationException {};
}

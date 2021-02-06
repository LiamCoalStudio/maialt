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

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Matches classes using a function. However, you need to use the
 * {@link #any}, {@link #field}, and {@link #method} methods to
 * construct this class. Each method has it's own limitations.
 */
public class GenericMemberMatcher implements MemberMatcher {
    private final boolean canBeUsedOnFields;
    private final boolean canBeUsedOnMethods;
    private final Function<Member, Boolean> function;

    private GenericMemberMatcher(boolean canBeUsedOnFields, boolean canBeUsedOnMethods, Function<Member, Boolean> function) {
        this.canBeUsedOnFields = canBeUsedOnFields;
        this.canBeUsedOnMethods = canBeUsedOnMethods;
        this.function = function;
    }

    /**
     * Constructs a Generic Member Matcher that can match
     * both fields and methods.
     *
     * @param function Function getting passed a {@link Member}, and returning
     *                 either <code>true</code> or <code>false</code>.
     * @return New instance.
     */
    public static GenericMemberMatcher any(Function<Member, Boolean> function) {
        return new GenericMemberMatcher(true, true, function);
    }

    /**
     * Constructs a Generic Member Matcher that can match
     * only fields. Attempting to use this matcher on methods
     * will throw an {@link IllegalStateException}.
     *
     * @param function Function getting passed a {@link Field}, and returning
     *                 either <code>true</code> or <code>false</code>.
     * @return New instance.
     */
    public static GenericMemberMatcher field(Function<Field, Boolean> function) {
        return new GenericMemberMatcher(true, false, m -> function.apply((Field) m));
    }

    /**
     * Constructs a Generic Member Matcher that can match
     * only methods. Attempting to use this matcher on fields
     * will throw an {@link IllegalStateException}.
     *
     * @param function Function getting passed a {@link Method}, and returning
     *                 either <code>true</code> or <code>false</code>.
     * @return New instance.
     */
    public static GenericMemberMatcher method(Function<Method, Boolean> function) {
        return new GenericMemberMatcher(false, true, m -> function.apply((Method) m));
    }

    @Override
    public boolean matches(Field field) {
        if(!canBeUsedOnFields) throw new IllegalStateException("This GenericMemberMatcher cannot be applied to fields");
        return function.apply(field);
    }

    @Override
    public boolean matches(Method method) {
        if(!canBeUsedOnMethods) throw new IllegalStateException("This GenericMemberMatcher cannot be applied to methods");
        return function.apply(method);
    }
}

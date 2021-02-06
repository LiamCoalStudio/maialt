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

import java.util.function.Function;

/**
 * Matches a class based on a function.
 */
public class GenericClassMatcher implements ClassMatcher {
    private final Function<Class<?>, Boolean> function;

    /**
     * Creates a new Generic Class Matcher with the specified function.
     *
     * @param function If this function returns <code>false</code>,
     *                 then the match fails. If it returns <code>true</code>,
     *                 the match succeeds
     */
    public GenericClassMatcher(Function<Class<?>, Boolean> function) {
        this.function = function;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return function.apply(clazz);
    }

    @Override
    public void validate() {}
}

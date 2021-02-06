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

package com.liamcoalstudio.maialt.hooks;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

/**
 * <p>Specifies that this class contains a hook field. The naming for this field
 * is specified in {@link Hooks#syncUsing(Class, Using)}.</p>
 *
 * <p>You can use this annotation multiple times on the same class, because
 * {@link Repeatable @Repeatable} was set to {@link Plural}.</p>
 */
@Repeatable(Using.Plural.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Using {
    /**
     * Name of the hook to use.
     *
     * @return String
     */
    @NotNull String value();

    /**
     * Represents multiple {@link Using} annotations. It's best not to use this
     * specifically, just put {@link Using} multiple times on the same class and
     * it's good enough.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @interface Plural {
        Using[] value();
    }
}

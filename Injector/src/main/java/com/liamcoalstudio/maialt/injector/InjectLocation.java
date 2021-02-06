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

package com.liamcoalstudio.maialt.injector;

/**
 * The point to inject callbacks into.
 */
public enum InjectLocation {
    /**
     * Injects at the start of the function. Requires no extra arguments.
     */
    START,

    /**
     * Injects at the return values of the function. Requires no extra arguments.
     */
    END,

    /**
     * Injects right before a field access.
     * <b>Requires {@link Inject#target()}</b>
     */
    BEFORE_FIELD,

    /**
     * Injects right after a field access.
     * <b>Requires {@link Inject#target()}</b>
     */
    AFTER_FIELD,

    /**
     * Injects right before a method invoke.
     * <b>Requires {@link Inject#target()}</b>
     *
     * TODO the resulting bytecode with this point looks a bit messy, should
     *      clean it up
     */
    BEFORE_INVOKE,

    /**
     * Injects right after a method invoke.
     * <b>Requires {@link Inject#target()}</b>
     */
    AFTER_INVOKE
}

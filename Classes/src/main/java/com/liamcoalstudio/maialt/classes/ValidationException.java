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

package com.liamcoalstudio.maialt.classes;

import com.liamcoalstudio.maialt.classes.classes.ClassMatcher;
import com.liamcoalstudio.maialt.classes.members.searchers.MemberMatcher;

/**
 * Thrown by matchers validate functions if they don't meet the requirements.
 *
 * @see ClassMatcher#validate()
 * @see MemberMatcher#validate()
 */
public class ValidationException extends Exception {
    /**
     * Constructs a validation error that occurred because another exception
     * was thrown. Matchers should not use this for user errors, as it is
     * generally undescriptive.
     *
     * @param cause Cause of the error.
     */
    public ValidationException(Throwable cause) {
        super("An unexpected error occurred.", cause);
    }

    /**
     * Constructs a validation error that occurred because the user made
     * a mistake while configuring the matcher. Matchers should not use
     * this for external errors, as it does not provide a helpful stack
     * trace for that error.
     *
     * @param s Message.
     */
    public ValidationException(String s) {
        super(s);
    }
}

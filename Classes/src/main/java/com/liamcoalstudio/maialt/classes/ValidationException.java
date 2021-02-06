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

package com.liamcoalstudio.maialt.classes.classes;

import com.liamcoalstudio.maialt.classes.ValidationException;

/**
 * Matches a class against a condition.
 *
 * @see AnnotationClassMatcher
 * @see GenericClassMatcher
 * @see SuperclassClassMatcher
 * @see ClassSearcher
 */
public interface ClassMatcher {
    /**
     * Runs match operation.
     *
     * @param clazz Class to search.
     * @return <code>true</code> if matching, <code>false</code> otherwise.
     */
    boolean matches(Class<?> clazz);

    /**
     * Checks arguments.
     *
     * @throws ValidationException Thrown if an argument was invalid.
     */
    void validate() throws ValidationException;
}

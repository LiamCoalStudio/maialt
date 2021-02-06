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

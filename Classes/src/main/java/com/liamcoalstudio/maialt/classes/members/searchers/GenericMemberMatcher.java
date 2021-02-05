package com.liamcoalstudio.maialt.classes.members.searchers;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.function.Function;

public class GenericMemberMatcher implements MemberMatcher {
    private final boolean canBeUsedOnFields;
    private final boolean canBeUsedOnMethods;
    private final Function<Member, Boolean> function;

    private GenericMemberMatcher(boolean canBeUsedOnFields, boolean canBeUsedOnMethods, Function<Member, Boolean> function) {
        this.canBeUsedOnFields = canBeUsedOnFields;
        this.canBeUsedOnMethods = canBeUsedOnMethods;
        this.function = function;
    }

    public static GenericMemberMatcher any(Function<Member, Boolean> function) {
        return new GenericMemberMatcher(true, true, function);
    }

    public static GenericMemberMatcher field(Function<Field, Boolean> function) {
        return new GenericMemberMatcher(true, false, m -> function.apply((Field) m));
    }

    public static GenericMemberMatcher method(Function<Method, Boolean> function) {
        return new GenericMemberMatcher(false, true, m -> function.apply((Method) m));
    }


    @Override
    public boolean matches(Field field) {
        if(!canBeUsedOnFields) throw new IllegalStateException("This GenericMemberMatcher cannot be applied to Field");
        return function.apply(field);
    }

    @Override
    public boolean matches(Method method) {
        if(!canBeUsedOnMethods) throw new IllegalStateException("This GenericMemberMatcher cannot be applied to Method");
        return function.apply(method);
    }
}

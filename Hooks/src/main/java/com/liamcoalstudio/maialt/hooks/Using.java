package com.liamcoalstudio.maialt.hooks;

import java.lang.annotation.*;


@Repeatable(Using.Plural.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Using {
    String value();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Plural {
        Using[] value();
    }
}

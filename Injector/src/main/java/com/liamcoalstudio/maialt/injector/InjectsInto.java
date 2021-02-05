package com.liamcoalstudio.maialt.injector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectsInto {
    Class<?> value();
    String saveTo() default "";
}

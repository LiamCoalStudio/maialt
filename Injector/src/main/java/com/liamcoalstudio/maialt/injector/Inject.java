package com.liamcoalstudio.maialt.injector;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Inject {
    Method method();
    String target() default "";
    int index() default -1;
    InjectLocation at();
}

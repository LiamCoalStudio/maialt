package com.liamcoalstudio.maialt.injector;

import org.intellij.lang.annotations.Pattern;

import java.lang.annotation.*;

/**
 * Marks a method as an injector.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Inject {
    /** @return Method to inject into */
    Method method();
    /** @return The target of FIELD and METHOD points */
    @Pattern("^[^.]+;.+(\\((\\[*([BCSIJFDZ]|L.+;))*\\)|:)\\[*([BCSIJFDZV]|L.+;)$")
    String target() default "";
    /** @return Which FIELD or METHOD point to use, or -1 for all of them */
    int index() default -1;
    /** @return The type of opcode to inject at */
    InjectLocation at();
}

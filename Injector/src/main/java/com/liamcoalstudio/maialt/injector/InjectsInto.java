package com.liamcoalstudio.maialt.injector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the injection target for an injector class.
 *
 * @see AnnotationProcessor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InjectsInto {
    /**
     * @return The class to inject to.
     */
    Class<?> value();

    /**
     * @return A file to save the output to. If <code>""</code>, then no output
     *         files are produced. This does not modify where modified classes
     *         are placed on the classpath, just a small debug feature.
     */
    String saveTo() default "";
}

package com.liamcoalstudio.maialt.hooks;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

/**
 * <p>Specifies that this class contains a hook field. The naming for this field
 * is specified in {@link Hooks#syncUsing(Class, Using)}.</p>
 *
 * <p>You can use this annotation multiple times on the same class, because
 * {@link Repeatable @Repeatable} was set to {@link Plural}.</p>
 */
@Repeatable(Using.Plural.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Using {
    /**
     * Name of the hook to use.
     *
     * @return String
     */
    @NotNull String value();

    /**
     * Represents multiple {@link Using} annotations. It's best not to use this
     * specifically, just put {@link Using} multiple times on the same class and
     * it's good enough.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @interface Plural {
        Using[] value();
    }
}

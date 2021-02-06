package com.liamcoalstudio.maialt.injector;

/**
 * The point to inject callbacks into.
 */
public enum InjectLocation {
    /**
     * Injects at the start of the function. Requires no extra arguments.
     */
    START,

    /**
     * Injects at the return values of the function. Requires no extra arguments.
     */
    END,

    /**
     * Injects right before a field access.
     * <b>Requires {@link Inject#target()}</b>
     */
    BEFORE_FIELD,

    /**
     * Injects right after a field access.
     * <b>Requires {@link Inject#target()}</b>
     */
    AFTER_FIELD,

    /**
     * Injects right before a method invoke.
     * <b>Requires {@link Inject#target()}</b>
     *
     * TODO the resulting bytecode with this point looks a bit messy, should
     *      clean it up
     */
    BEFORE_INVOKE,

    /**
     * Injects right after a method invoke.
     * <b>Requires {@link Inject#target()}</b>
     */
    AFTER_INVOKE
}

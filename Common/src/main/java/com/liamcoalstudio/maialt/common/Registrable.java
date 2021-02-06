package com.liamcoalstudio.maialt.common;

/**
 * An object that can be registered onto a {@link Registry}.
 *
 * @param <N> ID type of the Registry.
 * @param <T> Value type of the Registry.
 */
public interface Registrable<N, T extends Registrable<N, T>> {
    /**
     * Called when this object is registered.
     *
     * @param reg The registry this was registered to.
     */
    void onRegister(Registry<N, T> reg);
}

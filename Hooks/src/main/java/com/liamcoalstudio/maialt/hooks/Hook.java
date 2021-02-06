package com.liamcoalstudio.maialt.hooks;

import com.liamcoalstudio.maialt.common.Registrable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Allows multi-threaded communication.
 */
public interface Hook extends Registrable<String, Hook> {
    /**
     * Requests a value from this Hook. This method should throw an
     * {@link IllegalArgumentException} if an invalid object is requested.
     * Should also take care to complete the {@link CompletableFuture} that
     * is returned.
     *
     * @param <T> Type of the object to request.
     * @param c Class object of the type to request.
     * @param o Some {@link Enum} (or similar) indicated which object to
     *          get. Can be null.
     * @return A {@link CompletableFuture} that will be completed with the
     *         result value.
     */
    <T> @NotNull CompletableFuture<@Nullable T> request(@NotNull Class<T> c, @Nullable Object o);

    /**
     * Sends a value to this Hook. This method should throw an
     * {@link IllegalArgumentException} if an invalid object is sent, or
     * the class is invalid. Should also take care to complete the
     * {@link CompletableFuture} that is returned.
     * @param <T> Type of the object to send.
     * @param c Class object of the type to send.
     * @param o Some {@link Enum} (or similar) indicated which object to
     *          get. Can be null.
     * @param v The value to send.
     * @return A {@link CompletableFuture} that will be completed when
     *         the operation finishes.
     */
    <T> @NotNull CompletableFuture<Void> send(@NotNull Class<T> c, @Nullable Object o, @Nullable Object v);
}

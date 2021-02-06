package com.liamcoalstudio.maialt.common;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * {@link Streams#single(Object) Streams.single(Object)}
 */
public class Streams {
    /**
     * Gets a stream with a single element.
     *
     * @param <T> Type of the object.
     * @param t Object to wrap in a stream
     * @return Stream containing <code>T</code>
     */
    @SuppressWarnings("unchecked")
    @Contract(pure = true)
    public static<T> @NotNull Stream<T> single(@Nullable T t) {
        return (Stream<T>)Arrays.stream(new Object[]{t});
    }
}

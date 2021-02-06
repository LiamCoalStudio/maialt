package com.liamcoalstudio.maialt.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * A list of objects that can be added to, but not removed from.
 *
 * @param <N> The ID Type of the Registry.
 * @param <T> The Value Type of this Registry.
 */
public class Registry<N, T extends Registrable<N, T>> {
    private final ConcurrentMap<Integer, T> map = new ConcurrentHashMap<>();

    /**
     * Adds an object to this list.
     *
     * @param a ID
     * @param b Value
     */
    public synchronized void register(N a, T b) {
        map.put(a.hashCode(), b);
    }

    /**
     * Gets all values in this list.
     *
     * @return Stream of all values.
     */
    public synchronized Stream<T> values() {
        return map.values().parallelStream();
    }

    /**
     * Gets a value from this list, by ID. If no value was assigned to the ID,
     * then null is returned.
     *
     * @param a ID
     * @return Value assigned to ID
     */
    public synchronized T get(N a) {
        return map.get(a.hashCode());
    }
}

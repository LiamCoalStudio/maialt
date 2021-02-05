package com.liamcoalstudio.maialt.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

public class Registry<N, T> {
    private final ConcurrentMap<Integer, T> map = new ConcurrentHashMap<>();

    public synchronized void register(N a, T b) {
        map.put(a.hashCode(), b);
    }

    public synchronized Stream<T> values() {
        return map.values().parallelStream();
    }

    public synchronized T get(N a) {
        return map.get(a.hashCode());
    }
}

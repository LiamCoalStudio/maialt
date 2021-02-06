/*
 * MIT License
 *
 * Copyright (c) 2021 Liam Cole
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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

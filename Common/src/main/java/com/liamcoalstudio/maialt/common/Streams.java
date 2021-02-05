package com.liamcoalstudio.maialt.common;

import java.util.Arrays;
import java.util.stream.Stream;

public class Streams {
    @SuppressWarnings("unchecked")
    public static<T> Stream<T> single(T t) {
        return (Stream<T>)Arrays.stream(new Object[]{t});
    }
}

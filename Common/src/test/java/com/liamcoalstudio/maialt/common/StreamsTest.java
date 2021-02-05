package com.liamcoalstudio.maialt.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class StreamsTest {
    @Test
    public void testSingle() {
        assertEquals((int)Streams.single(7).findFirst().orElse(543), 7);
    }
}
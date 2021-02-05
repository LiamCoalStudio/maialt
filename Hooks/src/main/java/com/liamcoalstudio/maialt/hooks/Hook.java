package com.liamcoalstudio.maialt.hooks;

import java.util.concurrent.CompletableFuture;

public interface Hook {
    <T> CompletableFuture<T> request(Class<T> c, Object o);
    <T> CompletableFuture<Void> send(Class<T> c, Object o);
}

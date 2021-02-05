package com.liamcoalstudio.maialt.common;

public interface Registrable<N, T> {
    void onRegister(Registry<N, T> reg);
}

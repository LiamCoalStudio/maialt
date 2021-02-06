package com.liamcoalstudio.maialt.injector;

import org.jetbrains.annotations.Contract;

public class ArgumentReference {
    private Object object;

    @Contract(pure = true) public ArgumentReference(Object object) {
        this.object = object;
    }
    @Contract(pure = true) public ArgumentReference(int object) {
        this.object = object;
    }

    public<T> T get() {
        return (T)getObject();
    }
    public<T> void set(T t) {
        setObject(t);
    }
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    public int getInt() {
        return (int) object;
    }
    public void setInt(int object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return  "ArgumentReference{" +
                "object=" + object +
                ", type=" + object.getClass().getName() +
                "}";
    }
}

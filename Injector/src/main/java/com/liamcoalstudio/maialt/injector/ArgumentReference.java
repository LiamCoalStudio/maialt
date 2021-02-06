package com.liamcoalstudio.maialt.injector;

import org.jetbrains.annotations.Contract;

/**
 * References to arguments of a function, used in injectors to provide
 * a relatively compatible and easily implemented way to access arguments.
 */
public class ArgumentReference {
    private Object object;

    @Contract(pure = true) public ArgumentReference(Object object) {
        this.object = object;
    }
    @Contract(pure = true) public ArgumentReference(byte object) {
        this.object = object;
    }
    @Contract(pure = true) public ArgumentReference(char object) {
        this.object = object;
    }
    @Contract(pure = true) public ArgumentReference(short object) {
        this.object = object;
    }
    @Contract(pure = true) public ArgumentReference(int object) {
        this.object = object;
    }
    @Contract(pure = true) public ArgumentReference(long object) {
        this.object = object;
    }
    @Contract(pure = true) public ArgumentReference(float object) {
        this.object = object;
    }
    @Contract(pure = true) public ArgumentReference(double object) {
        this.object = object;
    }
    @Contract(pure = true) public ArgumentReference(boolean object) {
        this.object = object;
    }

    @SuppressWarnings("unchecked")
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
    public byte getByte() {
        return (byte) object;
    }
    public void setByte(byte object) {
        this.object = object;
    }
    public char getChar() {
        return (char) object;
    }
    public void setChar(char object) {
        this.object = object;
    }
    public short getShort() {
        return (short) object;
    }
    public void setShort(short object) {
        this.object = object;
    }
    public int getInt() {
        return (int) object;
    }
    public void setInt(int object) {
        this.object = object;
    }
    public long getLong() {
        return (long) object;
    }
    public void setLong(long object) {
        this.object = object;
    }
    public float getFloat() {
        return (float) object;
    }
    public void setFloat(float object) {
        this.object = object;
    }
    public double getDouble() {
        return (double) object;
    }
    public void setDouble(double object) {
        this.object = object;
    }
    public boolean getBoolean() {
        return (boolean) object;
    }
    public void setBoolean(boolean object) {
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

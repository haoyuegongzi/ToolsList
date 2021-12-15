package com.coroutines.retrofit.lib;

public class MyClass {
    public static void main(String[] args) {
        System.out.println("试试打印：" + BUFFER_SIZE);
    }



    static final int BUFFER_SIZE;
    static {
        BUFFER_SIZE = Math.max(1, Integer.getInteger("rx2.buffer-size", 128));
    }
}
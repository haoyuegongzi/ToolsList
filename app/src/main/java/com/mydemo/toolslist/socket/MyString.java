package com.mydemo.toolslist.socket;

public class MyString {
    // 运行时String对象会直接在堆内存中创建，不会常量池中创建。
    private String memory;
    private String region;
    // 代码编译加载时，会在常量池中创建常量“abc”，运行时，返回常量池中的字符串引用。
    private String name = "abc";
    // 代码编译时，会在常量池中创建常量“abc”；在调用关键字“new”时，会在堆内存中创建String对象，
    // 并引用常量池中的字符串对象char[]数组，并返回字符串String的对象引用。
    private String sex = new String("abc");



}

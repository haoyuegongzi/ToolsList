package com.mydemo.toolslist.kkk;

/**
 * 作者：Created by chen1 on 2020/4/11.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class ExtendsClass {
    private static final ExtendsClass INSTANCE = new ExtendsClass();

    public static ExtendsClass getInstance(){
        return INSTANCE;
    }

    static ExtendsAndIpmlement andIpmlement = new ExtendsAndIpmlement();
    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                andIpmlement.first();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                andIpmlement.second();
            }
        }).start();
    }
}

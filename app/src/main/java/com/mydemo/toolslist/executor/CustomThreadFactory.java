package com.mydemo.toolslist.executor;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * 作者：Created by chen1 on 2020/3/22.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class CustomThreadFactory implements ThreadFactory {
    private int counter;
    private String name;
    private List<String> stats;

    public CustomThreadFactory(String name) {
        counter = 1;
        this.name = name;
        stats = new ArrayList<>();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread t = new Thread(runnable, name + "-Thread_" + counter);
        counter++;
        Log.i("TAGTAG", "自定义的ThreadFactory的线程优先级：" + t.getPriority());
        stats.add(String.format("Created thread %d with name %s on %s \n", t.getId(), t.getName(), new Date()));
        return t;
    }
}

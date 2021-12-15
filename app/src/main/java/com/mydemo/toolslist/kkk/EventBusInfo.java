package com.mydemo.toolslist.kkk;

/**
 * 作者：Created by chen1 on 2020/4/17.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class EventBusInfo {
    private String info;

    public EventBusInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info == null ? "" : info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

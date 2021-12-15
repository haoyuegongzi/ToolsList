package com.mydemo.toolslist.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mydemo.toolslist.log.Logs;

/**
 * 作者：Created by chen1 on 2020/4/27.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class BroadcastManifest03 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Logs.log("BroadcastManifest03打印时间：" + System.currentTimeMillis());
    }
}

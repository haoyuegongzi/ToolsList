package com.mydemo.toolslist.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * @author:
 * @ProjectName: 收银台
 * @CreateDate: 2021/6/24 15:42
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class ChildThread extends Thread{
    public Handler childHandler = null;
    private Context mContext;
    public ChildThread(Context context){
        mContext = context;
    }

    @Override
    public void run() {
        super.run();
        Log.e("TAGTAG", "run: 这里运行能干出什么娄娄来？");
        Looper.prepare();
        Looper childLooper = Looper.myLooper();
        childHandler = new Handler(childLooper, new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Log.e("TAGTAG", "handleMessage: " + Thread.currentThread().getName());
                Toast.makeText(mContext, "当前线程是：" + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        Toast.makeText(mContext, "当前线程是：" + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}

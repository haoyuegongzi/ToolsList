package com.mydemo.toolslist.kkk;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import com.mydemo.toolslist.log.Logs;

import androidx.annotation.Nullable;

/**
 * 作者：Created by chen1 on 2020/4/10.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class CustomerService extends Service {
    final MyIBinder myIBinder = new MyIBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Logs.log("CustomerService的onCreate方法");
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logs.log("CustomerService的onStartCommand方法");
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logs.log("CustomerService的onUnbind方法");
        return super.onUnbind(intent);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Logs.log("CustomerService的unbindService方法");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myIBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logs.log("CustomerService的onDestroy方法");
    }

    public class MyIBinder extends Binder {
        public CustomerService getService() {
            return CustomerService.this;
        }
    }
}

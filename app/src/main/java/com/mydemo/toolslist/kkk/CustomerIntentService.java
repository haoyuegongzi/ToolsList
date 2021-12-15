package com.mydemo.toolslist.kkk;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.net.SocketKeepalive;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.mydemo.toolslist.log.Logs;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;

/**
 * 作者：Created by chen1 on 2020/4/14.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class CustomerIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public CustomerIntentService() {
        super("xiaoxiao");
    }

    public CustomerIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //在这里调用该方法，他服务设置为可视进程，欺骗系统把自己当成一个一直在通知栏的Notification；
        keepAlive();
    }

    private void keepAlive() {
        try {
            Notification notification = new Notification();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
            // 设置为前台服务避免kill，Android4.3及以上需要设置id为0时通知栏才不显示该通知；
            startForeground(0, notification);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String name = "";
        if (null != intent){
            name = intent.getStringExtra("name");
        }
        info = "线程信息：" + Thread.currentThread().getName() +
                "\n 线程ID：" + Thread.currentThread().getId() + "\n name：" + name;
        Logs.log(info);
        Logs.log("BindService模式下会执行这里么？=================================");
        EventBus.getDefault().post(new EventBusInfo(info));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logs.log("服务停止并销毁了");
    }

    String info = "";
    final MyBinder myBinder = new MyBinder();

    public class MyBinder extends Binder{
        public String getHandleIntentInfo(){
            return info;
        }
    }
}

package com.mydemo.toolslist;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mydemo.toolslist.handler.ChildThread;
import com.mydemo.toolslist.kkk.CustomerIntentService;
import com.mydemo.toolslist.kkk.EventBusInfo;
import com.mydemo.toolslist.log.Logs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

import static com.mydemo.toolslist.log.Logs.log;

public class MainActivity extends BaseActivity {
    AppCompatTextView autotvMany;
    AppCompatTextView autotvLittle;

    MainActivity activity;
    ClickListenerTools tools;

    // 创建Handler的几种方式：Handler(Looper looper)、Handler(Looper looper, Callback callback)两种方式；
    // 其他方式官方已经不推荐使用了。
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "handleMessage: " + "=================");
        }
    };

    Handler nHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            activity.loge("会内存泄漏吗？");
            return false;
        }
    });

    static final int key = 0xfdca;
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void notes() {
        //Looper、Message、MessageQueue都是final类型的class类，不可被继承，方法也不能被复写。
        Looper.prepare();//void,setLooper--->sThreadLocal.set(new Looper(quitAllowed));
        Looper.prepareMainLooper();//void,给sMainLooper赋值--->sMainLooper = myLooper();
        Looper looper = Looper.getMainLooper();//同步锁的方法，返回一个Looper对象--->sMainLooper
        Looper.loop();//void,释放looper对象
        Thread thread = Looper.myLooper().getThread();
        MessageQueue messageQueue0 = Looper.myLooper().getQueue();
        MessageQueue messageQueue1 = Looper.myQueue();

        Message message = handler.obtainMessage();
        message = Message.obtain();
        message = Message.obtain(handler);// 将当前 handler 与 message绑定

        handler.sendMessage(handler.obtainMessage());//sendMessageAtTime——>>enqueueMessage
        handler.sendEmptyMessage(1);//sendMessageAtTime——>>enqueueMessage
        handler.sendEmptyMessageDelayed(1, 10000L);//sendMessageAtTime——>>enqueueMessage
        handler.sendEmptyMessageAtTime(1, 10000L);//sendMessageAtTime——>>enqueueMessage
        handler.sendMessageDelayed(handler.obtainMessage(), 10000L);//sendMessageAtTime——>>enqueueMessage
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage());// ——>>enqueueMessage
        handler.sendMessageAtTime(handler.obtainMessage(), 10000L);// ——>>enqueueMessage
        handler.post(thread);//sendMessageAtTime——>>enqueueMessage
        handler.postDelayed(thread, 100L);//sendMessageAtTime——>>enqueueMessage
        handler.postAtFrontOfQueue(thread); // ——>>enqueueMessage
        handler.postAtTime(new Thread(){
            @Override
            public void run() {
                super.run();
            }
        }, 100);//sendMessageAtTime——>>enqueueMessage
        nHandler.postDelayed(new Thread(){
            @Override
            public void run() {
                super.run();
            }
        }, "", 100);//sendMessageAtTime——>>enqueueMessage
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO:
            }
        }, 1000L);
        handler.post(new Runnable() {
            @Override
            public void run() {


            }
        });

        handler.post(thread);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000L);

        // ChildThread childThread = new ChildThread();
        // childThread.start();
        // childThread.childHandler.sendMessage(childThread.childHandler.obtainMessage());

        Window window = getWindow();
        ViewGroup group;
    }

    CustomerIntentService intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        EventBus.getDefault().register(this);
        log("onCreate");

        autotvMany = findViewById(R.id.autotvMany);
        autotvMany.setText(Logs.many);

        autotvLittle = findViewById(R.id.autotvLittle);
        autotvLittle.setText(Logs.little);

        setOnListener();
    }

    private void setOnListener() {
        intentService = new CustomerIntentService("老婆wife");
        tools = new ClickListenerTools(activity, autotvMany, autotvLittle, intentService);

        tools.openViewListener();
        tools.rxJavaListener();
        tools.serviceListener();

        loadImageWithGlide("");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //在这个方法里面通过thread.interrupt()或者thread.stop()关闭线程，不会造成内存泄露
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //TODO：这里获取 View的宽高
            log("");
        }
    }

    @SuppressLint("CheckResult")
    private void loadImageWithGlide(Object path) {
        path = "http://img4.imgtn.bdimg.com/it/u=68625945,479175229&fm=26&gp=0.jpg";
        ImageView ivImage = findViewById(R.id.ivImage);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .fitCenter()
                .override(100, 100)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .apply(requestOptions)
                .into(ivImage);

        //        Glide.with(this).clear(ivImage);
    }

    private void mActivity(){
        WindowManager windowManager = getWindowManager();
        Window window = getWindow();
        View decorView = window.getDecorView();
        decorView.getWindowToken();
    }

    BroadcastReceiver receiver;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBusInfo(EventBusInfo info) {
        Toast.makeText(MainActivity.this, info.getInfo(), Toast.LENGTH_SHORT).show();
        log("Activity里面的打印：" + info.getInfo());
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log("onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        log("onDestroy");
    }

    @Override
    public void onSaveInstanceState(@androidx.annotation.NonNull Bundle outState, @androidx.annotation.NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@androidx.annotation.NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}

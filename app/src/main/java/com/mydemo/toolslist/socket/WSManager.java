package com.mydemo.toolslist.socket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.mydemo.toolslist.App;
import com.mydemo.toolslist.log.Logs;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @Author: Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist.socket
 * @ClassName: WSManager
 * @CreateDate: 2020/8/20 14:18
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class WSManager {
    public final int DATE_NORMAL = 0;
    private static Handler mHandler;
    private static WSManager sInstance;
    public WebSocket mWebSocket;
    private OkHttpClient mClient;

    //连接的websocket地址
    private String mWbSocketUrl;
    public boolean isReceivePong;

    private static ArrayList<WeakReference<WebSocketDataListener>> sWeakRefListeners;

    public static WSManager getInstance() {
        if (sInstance == null) {
            synchronized (WSManager.class) {
                if (sInstance == null) {
                    sInstance = new WSManager();
                    sWeakRefListeners = new ArrayList<>();
                    mHandler = new Handler(App.app.getMainLooper());
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化WebSocket
     */
    public void init(String url) {
        //使用模拟服务器（不支持断线重连）
        mWbSocketUrl = url;
        //使用测试url（支持断线重连）
        mWbSocketUrl = "ws://echo.websocket.org";

        Logs.log("mWbSocketUrl=" + mWbSocketUrl);
        mClient = new OkHttpClient.Builder()
                //用来设置WebSocket连接的保活。
                .pingInterval(10, TimeUnit.SECONDS)
                .build();
        connect();
    }

    public void connect() {
        Request request = new Request.Builder()
                .url(mWbSocketUrl)
                .build();
        //newWebSocket()：进行WebSocket的初始化和连接。
        mWebSocket = mClient.newWebSocket(request, new WsListener());
    }

    /**
     * 发送心跳包
     */
    Handler heartHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what != 10) {
                return false;
            }

            final String message = "{\"action\":\"ping\"}";
            if (isReceivePong) {
                send(message);
                isReceivePong = false;
                heartHandler.sendEmptyMessageDelayed(10, 10000);
            } else {
                //没有收到pong命令，进行重连
                disconnect(1001, "断线重连");
            }
            return false;
        }
    });

    /**
     * 遍历监听者，发送消息
     *
     * @param type
     * @param info
     */
    public static void onWSDataChanged(final int type, final String info) {
        Iterator<WeakReference<WebSocketDataListener>> iterator = sWeakRefListeners.iterator();
        while (iterator.hasNext()) {
            WeakReference<WebSocketDataListener> ref = iterator.next();
            if (ref == null) {
                break;
            }
            final WebSocketDataListener listener = ref.get();
            if (listener == null) {
                iterator.remove();
            } else {
                // To fresh UI
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onWebSocketData(type, info);
                        }
                    }
                });
            }
        }
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void send(final String message) {
        if (mWebSocket != null) {
            mWebSocket.send(message);
        }
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void send(final ByteString message) {
        if (mWebSocket != null) {
            mWebSocket.send(message);
        }
    }

    /**
     * 主动断开连接
     *
     * @param code
     * @param reason
     */
    public void disconnect(int code, String reason) {
        if (mWebSocket != null) {
            mWebSocket.close(code, reason);
        }
    }

    /**
     * 注册监听者
     *
     * @param listener
     */
    public void registerWSDataListener(WebSocketDataListener listener) {
        if (!sWeakRefListeners.contains(listener)) {
            sWeakRefListeners.add(new WeakReference<>(listener));
        }
    }

    /**
     * 解绑监听
     *
     * @param listener
     */
    public void unregisterWSDataListener(WebSocketDataListener listener) {
        Iterator<WeakReference<WebSocketDataListener>> iterator = sWeakRefListeners.iterator();
        while (iterator.hasNext()) {
            WeakReference<WebSocketDataListener> ref = iterator.next();
            if (ref == null) {
                break;
            }
            if (ref.get() == null) {
                iterator.remove();
            }
            if (ref.get() == listener) {
                iterator.remove();
                break;
            }
        }
    }

    class WsListener extends WebSocketListener {
        @Override
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosed(webSocket, code, reason);
            Logs.log("onClosed！返回码：code===" + code);
            //断线重连
            if (code == 1001) {
                Logs.log("断线重连！");
                connect();
            }
        }

        @Override
        public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            super.onFailure(webSocket, t, response);
            Logs.log("onFailure！" + t.getMessage());
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
            super.onMessage(webSocket, text);
            Logs.log("客户端收到消息:" + text);
            if (text.contains("pong")) {
                //简易写法，是否为pong包
                isReceivePong = true;
                return;
            }
            onWSDataChanged(DATE_NORMAL, text);
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            super.onOpen(webSocket, response);
            Logs.log("连接成功！");
            mWebSocket = webSocket;
            //测试发消息
            webSocket.send("我是客户端，你好啊");
            //主动发送心跳包
            isReceivePong = true;
            heartHandler.sendEmptyMessage(10);
        }
    }

    public interface WebSocketDataListener {
        void onWebSocketData(int type, String data);
    }
}

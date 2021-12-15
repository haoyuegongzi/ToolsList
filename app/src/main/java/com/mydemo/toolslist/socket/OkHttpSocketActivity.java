package com.mydemo.toolslist.socket;

import android.os.Bundle;
import android.view.View;

import com.mydemo.toolslist.R;
import com.mydemo.toolslist.log.Logs;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * @author Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist.socket
 * @ClassName: WSManager
 * @CreateDate: 2020/8/20 14:18
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class OkHttpSocketActivity extends AppCompatActivity {

    private WebSocket mWebSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_socket);
        findViewById(R.id.btnOpenWebSocket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctreateThread();
            }
        });

        findViewById(R.id.btnCloseWebSocket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebSocket != null) {
                    mWebSocket.close(1001, "请求断开");
                }
            }
        });
    }

    private void ctreateThread() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                initMockServer();
            }
        });
    }

    /**
     * 初始化虚拟服务器
     */
    private void initMockServer() {
        MockWebServer mMockWebServer = new MockWebServer();
        MockResponse response = new MockResponse()
                .withWebSocketUpgrade(new WebSocketListener() {
                    @Override
                    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                        super.onOpen(webSocket, response);
                        //有客户端连接时回调
                        try {
                            Logs.log("onOpen==>服务器收到客户端连接成功回调：" + response.toString() +
                                    "\n或者：" + response.body().string());
                            mWebSocket = webSocket;
                            mWebSocket.send("我是服务器，你好呀");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                        super.onMessage(webSocket, text);
                        Logs.log("onMessage==>服务器收到消息：" + text);
                    }

                    @Override
                    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                        super.onClosed(webSocket, code, reason);
                        Logs.log("onClosed==>关闭原因：" + reason);
                    }
                });

        mMockWebServer.enqueue(response);

        //获取连接url，初始化websocket客户端
        String websocketUrl = "ws://" + mMockWebServer.getHostName() + ":" + mMockWebServer.getPort() + "/";
        Logs.log("websocketUrl========" + websocketUrl);
        WSManager.getInstance().init(websocketUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebSocket != null) {
            mWebSocket.close(1001, "");
        }
    }
}
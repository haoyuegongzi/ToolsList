package com.mydemo.toolslist.domain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mydemo.toolslist.R;

/**
 * @author Nixon
 */
public class PingDomainActivity extends AppCompatActivity {

    private String sDomain = "";
    private TextView tvPingResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_domain);

        tvPingResult = findViewById(R.id.tvPingResult);

        findViewById(R.id.tvPingAlibaba).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDomain = "alibaba.com";
                // sDomain = "ant.sh21nbi.com";
                btnPingDomain(sDomain);
            }
        });

        findViewById(R.id.tvPingBaidu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDomain = "baidu.com";
                // sDomain = "ant.jbsci19.com";
                btnPingDomain(sDomain);
            }
        });

        findViewById(R.id.tvPingGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sDomain = "ant.mks17ec.com";
                // sDomain = "ant.cevf18r.com";
                sDomain = "google.com";
                btnPingDomain(sDomain);
            }
        });
        
        findViewById(R.id.tvLiveData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] bytes = new byte[1024 * 1024 * 50];
                Log.e("TAGTAG", "onClick: bytes.length====" + bytes.length);
                byteLiveData.postValue(bytes);
            }
        });

        byteLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                byte[] bytes = (byte[]) o;
                int bytesSize = bytes.length;
                tvPingResult.setText("bytesSize====" + bytesSize);
            }
        });
    }

    private MutableLiveData byteLiveData = new MutableLiveData<byte[]>();

    private void btnPingDomain(String sDomain) {
        String url = "system/bin/ping -c 1 -w 100 " + sDomain;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec(url);

                    int pingStatus = process.waitFor();
                    int exitValue = process.exitValue();

                    String result = "域名 " + sDomain + "    ping的状态：" + pingStatus + "    ping的退出码：" + exitValue;
                    handler.sendMessage(handler.obtainMessage(1, result));
                } catch (Exception e) {

                }
            }
        }).start();
    }

    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            String result = (String) msg.obj;
            tvPingResult.setText("域名ping的结果：\n" + result);
            return false;
        }
    });
}
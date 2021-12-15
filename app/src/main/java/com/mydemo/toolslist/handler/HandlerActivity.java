package com.mydemo.toolslist.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mydemo.toolslist.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

import okhttp3.*;
import okhttp3.OkHttpClient.Builder;

/**
 * @author Nixon
 */
public class HandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        Button btnHandler = findViewById(R.id.btnHandler);

        ChildThread childThread = new ChildThread(this);
        childThread.start();
        btnHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == childThread.childHandler){
                    return;
                }
                childThread.childHandler.sendEmptyMessage(1);
            }
        });

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });

        ImageView imageView = new ImageView(this);
        imageView.getDrawingCache();
    }
}
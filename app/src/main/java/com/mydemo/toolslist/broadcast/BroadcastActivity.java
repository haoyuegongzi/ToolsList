package com.mydemo.toolslist.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.mydemo.toolslist.R;

public class BroadcastActivity extends AppCompatActivity {

    BroadcastCodeRegister01 cast01;
    BroadcastCodeRegister02 cast02;
    BroadcastCodeRegister03 cast03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        findViewById(R.id.tvStatic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                broadCastReceiver("BroadcastManifest","com.mydemo.toolslist.broadcast.BroadcastManifest02");
                broadCastReceiver("BroadcastManifest","com.mydemo.toolslist.broadcast.BroadcastManifest03");
                broadCastReceiver("BroadcastManifest","com.mydemo.toolslist.broadcast.BroadcastManifest01");
            }
        });

        findViewById(R.id.tvDynamic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeRegister();
            }
        });
    }

    private void broadCastReceiver(String action, String className){
        Intent intent = new Intent(action);
        intent.setClassName(this, className);
        sendBroadcast(intent);
    }

    private void codeRegister(){
        IntentFilter intentFilter02 = new IntentFilter("BroadcastCodeRegister******");
        cast02 = new BroadcastCodeRegister02();
        registerReceiver(cast02, intentFilter02);

        IntentFilter intentFilter01 = new IntentFilter("BroadcastCodeRegister******");
        cast01 = new BroadcastCodeRegister01();
        registerReceiver(cast01, intentFilter01);

        IntentFilter intentFilter03 = new IntentFilter("BroadcastCodeRegister******");
        cast03 = new BroadcastCodeRegister03();
        registerReceiver(cast03, intentFilter03);

        broadCastReceiver("BroadcastCodeRegister******");
    }

    private void broadCastReceiver(String action){
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(cast01);
        unregisterReceiver(cast02);
        unregisterReceiver(cast03);
    }
}

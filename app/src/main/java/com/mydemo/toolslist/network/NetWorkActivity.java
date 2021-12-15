package com.mydemo.toolslist.network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.mydemo.toolslist.BaseActivity;
import com.mydemo.toolslist.R;

public class NetWorkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);





        ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            // unconnect network
        }else {
            // connect network
        }
    }



    private void telephonyManager(){
        final TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        mTelephonyMgr.listen(new PhoneStateListener(){
            @Override
            public void onDataConnectionStateChanged(int state) {
                switch(state){
                    case TelephonyManager.DATA_DISCONNECTED://网络断开
                        break;
                    case TelephonyManager.DATA_CONNECTING://网络正在连接
                        break;
                    case TelephonyManager.DATA_CONNECTED://网络连接上
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);








        mManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = mManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = mManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        Log.e("Teaphy", "mobNetInfo: " + mobNetInfo.toString());
        Log.e("Teaphy", "wifiNetInfo: " + wifiNetInfo.toString());

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            Log.e(TAG, "telephonyManager: " + "当前网络状态：移动数据和wifi网络均不可用");
        } else {
            if (mobNetInfo.isConnected()) {
                Log.e(TAG, "telephonyManager: " + "当前网络状态：数据连接可用");
            } else {
                Log.e(TAG, "telephonyManager: " + "当前网络状态：数据连接不可用");
            }

            if (wifiNetInfo.isConnected()) {
                Log.e(TAG, "telephonyManager: " + "当前网络状态：wifi可用");
            } else {
                Log.e(TAG, "telephonyManager: " + "当前网络状态：wifi不可用");
            }
        }
    }

    ConnectivityManager mManager;
}
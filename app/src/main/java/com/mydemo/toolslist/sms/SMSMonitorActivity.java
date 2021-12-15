package com.mydemo.toolslist.sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mydemo.toolslist.R;
import com.mydemo.toolslist.log.Logs;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import java.util.List;

/**
 * @author chen1
 */
public class SMSMonitorActivity extends AppCompatActivity {
    private EditText etPassword;
    private TextView tvSMS, tvHandlerMsg;
//    private SmsContent content;
//    private AutoGetCode autoGetCode;
    private SMSBroadcastReceiver mSMSBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_m_s_monitor);

        etPassword = findViewById(R.id.etPassword);
        tvSMS = findViewById(R.id.tvSMS);
        tvHandlerMsg = findViewById(R.id.tvHandlerMsg);

        String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        Logs.log("PATH=========" + PATH);

//        PermissionX.init(this)
//                .permissions(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS)
//                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam() {
//                    @Override
//                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest) {
//                        scope.showRequestReasonDialog(deniedList, "短信监听的核心功能需要这些权限", "OK", "Cancel");
//                    }
//                })
//                .onForwardToSettings(new ForwardToSettingsCallback() {
//                    @Override
//                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
//                        scope.showForwardToSettingsDialog(deniedList, "您需要在设置中手动允许必要的权限", "OK", "Cancel");
//                    }})
//                .request(new RequestCallback() {
//                    @Override
//                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
//                        if (allGranted) {
//                            Toast.makeText(SMSMonitorActivity.this,
//                                    "All permissions are granted", Toast.LENGTH_LONG).show();
//                            startReceiver();
////                            registerContentObserver();
////                            registerAutoGetCode();
//                        } else {
//                            Toast.makeText(SMSMonitorActivity.this,
//                                    "These permissions are denied: " + deniedList, Toast.LENGTH_LONG).show();
//                        }
//                    }
//        });

        startReceiver();
    }

    //SMSBroadcastReceiver
    private void startReceiver(){
        //生成广播处理
        mSMSBroadcastReceiver = new SMSBroadcastReceiver(handler);
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter(SMSBroadcastReceiver.SMS_RECEIVED_ACTION);
        intentFilter.setPriority(1000);
        //注册广播
        this.registerReceiver(mSMSBroadcastReceiver, intentFilter);
        mSMSBroadcastReceiver.setOnReceivedMessageListener(new SMSBroadcastReceiver.MessageListener() {
            @Override
            public void onReceived(SMSMessageBean smsMessageBean) {
                etPassword.setText(smsMessageBean.getContent());
                tvSMS.setText(smsMessageBean.toString());
            }
        });
    }

//    //SmsContent
//    private void registerContentObserver(){
//        content = new SmsContent(handler, this, 6);
//        //注册短信变化监听
//        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, content);
//    }

//    //AutoGetCode
//    private void registerAutoGetCode(){
//        //regist_code 要显示验证码的EditText
//        autoGetCode = new AutoGetCode(this, handler);
//        // 注册短信变化监听
//        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, autoGetCode);
//    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            int what = msg.what;
            switch (msg.what){
                case 0:
                    String code = (String) msg.obj;
                    Logs.log("SmsContent监控到的短信验证码：" + code);
                    break;
                case 1:
                    String smsContent = (String) msg.obj;
                    Logs.log("AutoGetCode监控到的短信内容：" + smsContent);
                    break;
                case 2:
                    SMSMessageBean smsMessageBean =  (SMSMessageBean) msg.obj;
                    tvHandlerMsg.setText(smsMessageBean.toString());
                    Logs.log("SMSBroadcastReceiver监控到的短信信息：" + smsMessageBean.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销短信监听广播
        unregisterReceiver(mSMSBroadcastReceiver);
//        getContentResolver().unregisterContentObserver(content);
//        getContentResolver().unregisterContentObserver(autoGetCode);
    }
}
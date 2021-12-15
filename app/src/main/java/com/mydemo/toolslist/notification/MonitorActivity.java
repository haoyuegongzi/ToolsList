package com.mydemo.toolslist.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mydemo.toolslist.R;
import com.mydemo.toolslist.log.Logs;

public class MonitorActivity extends AppCompatActivity {

    TextView tvStatusNotificationMsg, tvNotification, tvBundle;
    ImageView ivIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        // 通知栏监控器开关
        Button notificationMonitorOnBtn = findViewById(R.id.notification_monitor_on_btn);
        Button notificationMonitorOffBtn = findViewById(R.id.notification_monitor_off_btn);

        tvStatusNotificationMsg = findViewById(R.id.tvStatusNotificationMsg);
        tvNotification = findViewById(R.id.tvNotification);
        tvBundle = findViewById(R.id.tvBundle);
        ivIcon = findViewById(R.id.ivIcon);

        registerReceiver();

        final String action = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
        notificationMonitorOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEnabled()) {
                    startActivity(new Intent(action));
                } else {
                    Toast.makeText(getApplicationContext(), "监控器开关已打开", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MonitorActivity.this, NotificationMonitorService.class);
                    startService(intent);
                }
            }
        });

        notificationMonitorOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnabled()) {
                    startActivity(new Intent(action));
                } else {
                    Toast.makeText(getApplicationContext(), "监控器开关已关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 判断是否打开了通知监听权限
    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void registerReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(NotificationMonitorService.SEND_MSG_BROADCAST);
        registerReceiver(broadcastReceiver,filter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            StatusNotificationBean statusNotification = intent.getParcelableExtra(NotificationMonitorService.STATUS_MSG);
            tvStatusNotificationMsg.setText(statusNotification.toString());
            Logs.log("通知栏StatusBarNotification对象外层包含的基本数据：" + statusNotification.toString());

            NotificationBean notificationBean = intent.getParcelableExtra(NotificationMonitorService.NOTY_MSG);
            tvNotification.setText(notificationBean.toString());
//            ivIcon.setImageResource(notificationBean.getIcon());
            Logs.log("通知栏Notification对象外层包含的基本数据：" + notificationBean.toString());

            String bundle = intent.getStringExtra(NotificationMonitorService.BUNDLE);
            NotificationBundle notificationBundle = intent.getParcelableExtra(NotificationMonitorService.BUNDLE_MSG);
            tvBundle.setText(notificationBundle.toString() + "\n\n" + bundle);
            Logs.log("通知栏实际携带的核心数据NotificationBundle：" + notificationBundle.toString());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

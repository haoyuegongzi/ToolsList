package com.mydemo.toolslist.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.RemoteViews;

import com.mydemo.toolslist.log.Logs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：Nixon
 * date：2020/8/14.
 * 邮箱：jan.romon@gmail.com
 * TODO：
 */
@SuppressLint("OverrideAbstract")
public class NotifyService extends NotificationListenerService {
    public static String SEND_MSG_BROADCAST = "notify_msg";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Logs.log("获取到的通知信息：" + sbn.toString());
        String packageName = sbn.getPackageName();
        Logs.log("packageName：" + packageName);
        String content = null;
        //获取通知栏消息中的文字
        if (sbn.getNotification().tickerText != null) {
            content = sbn.getNotification().tickerText.toString();
        }
        //如果获取文字失败,通过反射获取
        if (content == null) {
            Map<String, Object> notiInfo = getNotiInfo(sbn.getNotification());
            if (null != notiInfo) {
                for (String key : notiInfo.keySet()){
                    Logs.log("TAGTAG====>>>key：" + key + "    value：" + notiInfo.get(key));
                }
                Logs.log("TAGTAG====>>>notiInfoMap：" + notiInfo);
                content = notiInfo.get("title") + ":" + notiInfo.get("text");
            }
        }

        if (content == null || content.length() == 1) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("msg", content);
        intent.setAction(SEND_MSG_BROADCAST);
        sendBroadcast(intent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    /**
     * 当有通知弹出在通知栏时，NotificationListenerService的onNotificationPosted会回调，
     * 返回参数是StatusBarNotification的对象。
     * 能拿到StatusBarNotfication对象跳转的方式很简单方便，通过获取它的PendingIntent，
     * 调用PendingIntent的send方法即可模拟发送此条通知的意图。
     *
     * @param sbn
     * @param rankingMap
     */
    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {

    }

    private Map<String, Object> getNotiInfo(Notification notification) {
        int key = 0;
        if (notification == null) {
            return null;
        }
        RemoteViews views = notification.contentView;
        if (views == null) {
            return null;
        }
        Class secretClass = views.getClass();
        try {
            Map<String, Object> text = new HashMap<>();
            Field outerFields[] = secretClass.getDeclaredFields();
            for (int i = 0; i < outerFields.length; i++) {
                Logs.log("outerFields：" + outerFields[i].getName());
                if (!outerFields[i].getName().equals("mActions")) {
                    continue;
                }
                outerFields[i].setAccessible(true);
                ArrayList<Object> actions = (ArrayList<Object>) outerFields[i].get(views);
                for (Object action : actions) {
                    Field innerFields[] = action.getClass().getDeclaredFields();
                    Object value = null;
                    Integer type = null;
                    for (Field field : innerFields) {
                        Logs.log("field：" + field.getName());
                        field.setAccessible(true);
                        if (field.getName().equals("value")) {
                            value = field.get(action);
                        } else if (field.getName().equals("type")) {
                            type = field.getInt(action);
                        }
                    }
                    // 经验所得 type 等于9 10为短信title和内容，不排除其他厂商拿不到的情况
                    if (type != null && (type == 9 || type == 10)) {
                        if (key == 0) {
                            text.put("title", value != null ? value.toString() : "");
                        } else if (key == 1) {
                            text.put("text", value != null ? value.toString() : "");
                        } else {
                            text.put(Integer.toString(key), value != null ? value.toString() : null);
                        }
                        key++;
                    }
                }
                key = 0;
            }
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

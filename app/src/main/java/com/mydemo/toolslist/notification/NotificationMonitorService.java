package com.mydemo.toolslist.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.mydemo.toolslist.log.Logs;

/**
 * 作者：Nixon
 * date：2020/8/17.
 * 邮箱：jan.romon@gmail.com
 * TODO：
 */
@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService {
    int id;
    int uid;
    long postTime;

    String key;
    String tag;
    String opPkg;
    String groupKey;
    //对应变量pkg；
    String packageName;
    String overrideGroupKey;

    Notification notification;

    public static final String STATUS_MSG = "statusMsg";
    public static final String NOTY_MSG = "notyMsg";
    public static final String BUNDLE_MSG = "bundleMsg";
    public static final String BUNDLE = "BUNDLE";
    public static final String SEND_MSG_BROADCAST = "SEND_MSG_BROADCAST";

    // 在收到消息时触发
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        try {
            id = sbn.getId();
            packageName = sbn.getPackageName();
            key = sbn.getKey();
            uid = sbn.getUid();
            postTime = sbn.getPostTime();
            overrideGroupKey = sbn.getOverrideGroupKey();
            tag = sbn.getTag();
            opPkg = sbn.getOpPkg();
            groupKey = sbn.getGroupKey();

            StatusNotificationBean statusNotificationBean = new StatusNotificationBean(
                    sbn.getId(), sbn.getUid(), sbn.getPostTime(), sbn.getKey(), sbn.getTag(), sbn.getOpPkg(),
                    sbn.getGroupKey(), sbn.getPackageName(), sbn.getOverrideGroupKey());

            notification = sbn.getNotification();
            int mBadgeIcon = notification.getBadgeIconType();
            int mGroupAlertBehavior = notification.getGroupAlertBehavior();
            int color = notification.color;
            int flags = notification.flags;
            int iconLevel = notification.iconLevel;
            int icon = notification.icon;
            int number = notification.number;
            int visibility = notification.visibility;
            long when = notification.when;
            long mTimeout = notification.getTimeoutAfter();
//            boolean mAllowSystemGeneratedContextualActions = notification.getAllowSystemGeneratedContextualActions();

            String mSettingsText = notification.getSettingsText() + "";
            String tickerText = notification.tickerText + "";
            String group = notification.getGroup() + "";
            String mSortKey = notification.getSortKey() + "";
            String mChannelId = notification.getChannelId() + "";
            String mShortcutId = notification.getShortcutId() + "";
            String category = notification.category + "";
            String notificationString = notification.toString();

            Icon mSmallIcon = notification.getSmallIcon();
            Icon mLargeIcon = notification.getLargeIcon();

            //int mBadgeIcon, int mGroupAlertBehavior, int color, int flags, int iconLevel,
            // int icon, int number, int visibility, long when, long mTimeout,
            // boolean mAllowSystemGeneratedContextualActions, String mSettingsText,

            NotificationBean notificationBean = new NotificationBean(
                    mBadgeIcon, mGroupAlertBehavior, color, flags, iconLevel, icon, number,
                    visibility, when, mTimeout, false,
                    mSettingsText, tickerText, group, mSortKey, mChannelId, mShortcutId,
                    category, notificationString, mSmallIcon, mLargeIcon);
            PendingIntent contentIntent = notification.contentIntent;
            PendingIntent deleteIntent = notification.deleteIntent;
            PendingIntent fullScreenIntent = notification.fullScreenIntent;

            Bundle extras = notification.extras;
            String title = extras.getCharSequence(Notification.EXTRA_TITLE) + "";// 获取接收消息的抬头
            String bigTitle = extras.getCharSequence(Notification.EXTRA_TITLE_BIG) + "";
            String text = extras.getCharSequence(Notification.EXTRA_TEXT) + "";// 获取接收消息的内容
            String subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT) + "";
            String remoteInputHistory = extras.getCharSequence(Notification.EXTRA_REMOTE_INPUT_HISTORY) + "";
            String infoText = extras.getCharSequence(Notification.EXTRA_INFO_TEXT) + "";
            String summaryText = extras.getCharSequence(Notification.EXTRA_SUMMARY_TEXT) + "";
            String bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT) + "";
            int progress = extras.getInt(Notification.EXTRA_PROGRESS);
            int max = extras.getInt(Notification.EXTRA_PROGRESS_MAX);
            boolean indeterminate = extras.getBoolean(Notification.EXTRA_PROGRESS_INDETERMINATE);
            //        boolean remoteInputSpinner = extras.getBoolean(Notification.EXTRA_h);
            //        boolean hideSmartReplies = extras.getBoolean(Notification.EXTRA_HIDE_SMART_REPLIES);

            NotificationBundle notificationBundle = new NotificationBundle(
                    title, bigTitle, text, subText, remoteInputHistory, infoText,
                    summaryText, bigText, progress, max, indeterminate);

            Intent intent = new Intent();
            intent.putExtra(STATUS_MSG, statusNotificationBean);
            intent.putExtra(NOTY_MSG, notificationBean);
            intent.putExtra(BUNDLE_MSG, notificationBundle);
            intent.putExtra(BUNDLE, extras.toString());
            intent.setAction(SEND_MSG_BROADCAST);
            sendBroadcast(intent);
            Logs.log("statusNotificationBean：" + statusNotificationBean.toString());
            Logs.log("notificationBean：" + notificationBean.toString());
            Logs.log("notificationBundle：" + notificationBundle.toString());
        } catch (Exception e) {
            Logs.log(e.getMessage());
        }
    }

    // 在删除消息时触发
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        Log.i("XSL_Test", "Notification removed " + notificationTitle + " & " + notificationText);
    }
}

package com.mydemo.toolslist.sms;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

/**
 * 作者：Nixon
 * date：2020/8/18.
 * 邮箱：jan.romon@gmail.com
 * TODO：监听短信数据库，自动获取短信验证码
 */
public class AutoGetCode extends ContentObserver {
    private Cursor cursor = null;
    private Activity activity;
    private Handler mHandler;
    private String smsContent = "";

    public AutoGetCode(Activity activity, Handler handler) {
        super(handler);
        this.activity = activity;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        // 读取收件箱中指定号码的短信
        cursor = activity.managedQuery(Uri.parse("content://sms/inbox"),
                new String[]{"_id", "address", "read", "body"}, "address=? and read=?",
                new String[]{"639178300084", "0"}, "_id desc");
        // 按短信id排序，如果按date排序的话，修改手机时间后，读取的短信就不准了
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                String smsbody = cursor.getString(cursor.getColumnIndex("body"));
                String regEx = "(?<![0-9])([0-9]{" + 6 + "})(?![0-9])";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(smsbody.toString());
                while (m.find()) {
                    smsContent = m.group();
                    Message msg = Message.obtain();
                    msg.what =1;
                    msg.obj = smsContent;
                    mHandler.sendMessage(msg);
                }
            }
        }
    }
}

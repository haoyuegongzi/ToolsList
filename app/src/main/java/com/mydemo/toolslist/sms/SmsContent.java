package com.mydemo.toolslist.sms;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：Nixon
 * date：2020/8/18.
 * 邮箱：jan.romon@gmail.com
 * TODO：
 */
public class SmsContent extends ContentObserver {
    private Activity mActivity;
    private int codelength;
    private Handler handler;

    public SmsContent(Handler handler, Activity activity, int codelength) {
        super(handler);
        this.mActivity = activity;
        this.codelength = codelength;//截取验证码的长度
        this.handler = handler;
    }

    // 收到短信一般来说都是执行了两次onchange方法.第一次一般都是raw的这个.这个时候虽然收到了短信.但是短信还没有写入	到收件箱里面
    // 然后才是另外一个,后面的数字是该短信在收件箱中的位置
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        String code = getsmsCode(codelength);
        if (!TextUtils.isEmpty(code)) {
            // 利用handler将得到的验证码发送给主界面
            Message msg = Message.obtain();
            msg.what =0;
            msg.obj = code;
            handler.sendMessage(msg);
        }
    }

    public String getsmsCode(int codelength) {
        ContentResolver contentresolver = mActivity.getContentResolver();
        // 设置一个uri来查看收件箱中短信内容
        Uri uri = Uri.parse("content://sms/inbox");
        //需要查询的字段 address 号码，read 0未读 1已读，body短信内容
        String[] projection = new String[]{"_id", "address", "read", "body"};
        //需要查询未读短信
        //        String selection = "address=? and read=?"; //注意这里填写的号码如何是公司里固定的号码可以是 （xxxxxxx）但是测试使用的号码时看有没有（+86xxxxxxx）别	写错了，代码没问题，好好检查O(∩_∩)O哈哈~，当然你可以不写需要查询的号码，查询收件箱数据库中的短信
        //        String [] selections = new String[]{"这里填写需要查询的号码","0"};
        // 按短信id倒序排序，如果按date排序的话，修改手机时间后，读取的短信就不准了
        String sortOrder = "_id desc";
        // 读取收件箱中指定号码的短信
        Cursor cur = contentresolver.query(uri, projection, null, null, sortOrder);
        if (null == cur) {
            return null;
        }
        if (cur.getCount() <= 0) {
            return null;
        }
        if (!cur.moveToFirst()) {
            return null;
        }

        String number = cur.getString(cur.getColumnIndex("address"));// 手机号
        String body = cur.getString(cur.getColumnIndex("body"));//短信类容
        // 最后用完游标千万记得关闭
        cur.close();
        // 在这里我们的短信提供商的号码如果是固定的话.我们可以再加一个判断,这样就不会受到别的短信应用的验证码的影响了
        if (!number.startsWith("这里填写你的号码以xxxx开头的数字")) {
            return null;
        }

        if (!body.contains("这里填写你的过滤的名称")){
            return null;
        }
        return getyzm(body, codelength);
    }

    /**
     * 从短信字符窜提取验证码
     *
     * @param body       短信内容
     * @param codelength 验证码的长度 一般4位
     * @return 接取出来的验证码
     */
    public String getyzm(String body, int codelength) {
        Pattern p = Pattern.compile("(?<![0-9])([0-9]{" + codelength + "})(?![0-9])");
        Matcher matcher = p.matcher(body);
        if (matcher.find()) {
            //            ContentValues values = new ContentValues();
            //            values.put("read", "1"); // 修改短信为已读模式
            return matcher.group(0);
        }
        return null;
    }
}

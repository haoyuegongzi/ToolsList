package com.mydemo.toolslist.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;

import com.mydemo.toolslist.log.Logs;

/**
 * 作者：Nixon
 * date：2020/8/18.
 * 邮箱：jan.romon@gmail.com
 * TODO：
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {
    private static MessageListener mMessageListener;
    private Handler mHandler;
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public SMSBroadcastReceiver(Handler handler) {
        super();
        mHandler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");

            for(Object pdu:pdus) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte [])pdu);
                //手机号码
                String sender = smsMessage.getDisplayOriginatingAddress();
                //短信内容
                String content = smsMessage.getDisplayMessageBody();

                SmsMessageInfo messageInfo = new SmsMessageInfo(
                    smsMessage.getMessageBody(), smsMessage.getOriginatingAddress(),
                    smsMessage.getDisplayMessageBody(), smsMessage.getDisplayOriginatingAddress(),
                    smsMessage.getTimestampMillis() + "", smsMessage.getEmailBody(),
                    smsMessage.getEmailFrom(), smsMessage.getPseudoSubject(),
                    smsMessage.getServiceCenterAddress()
                );
                Logs.log(messageInfo.toString());

                //过滤不需要读取的短信的发送号码
//                if ("+8613450214963".equals(sender)) {
                    SMSMessageBean smsMessageBean = new SMSMessageBean(smsMessage, sender, content);
                    mMessageListener.onReceived(smsMessageBean);
                    abortBroadcast();
                    // 利用handler将得到的验证码发送给主界面
                    Message msg = Message.obtain();
                    msg.what =2;
                    msg.obj = smsMessageBean;
                    mHandler.sendMessage(msg);
//                }
            }
        }
    }

    //回调接口
    public interface MessageListener {
        void onReceived(SMSMessageBean smsMessageBean);
    }

    public void setOnReceivedMessageListener(MessageListener messageListener) {
        mMessageListener = messageListener;
    }
}

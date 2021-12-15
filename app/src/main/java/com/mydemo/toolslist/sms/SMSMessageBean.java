package com.mydemo.toolslist.sms;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.SmsMessage;

/**
 * 作者：Nixon
 * date：2020/8/18.
 * 邮箱：jan.romon@gmail.com
 * TODO：
 */
public class SMSMessageBean implements Parcelable {
    private SmsMessage smsMessage;
    private String sender;
    private String content;

    @Override
    public String toString() {
        return "SMSMessageBean{" +
                "\nsmsMessage=" + smsMessage +
                ", \nsender='" + sender + '\'' +
                ", \ncontent='" + content + '\'' +
                '}';
    }

    public SMSMessageBean(SmsMessage smsMessage, String sender, String content) {
        this.smsMessage = smsMessage;
        this.sender = sender;
        this.content = content;
    }

    public SmsMessage getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(SmsMessage smsMessage) {
        this.smsMessage = smsMessage;
    }

    public String getSender() {
        return sender == null ? "" : sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.smsMessage, flags);
        dest.writeString(this.sender);
        dest.writeString(this.content);
    }

    protected SMSMessageBean(Parcel in) {
        this.smsMessage = in.readParcelable(SmsMessage.class.getClassLoader());
        this.sender = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<SMSMessageBean> CREATOR = new Parcelable.Creator<SMSMessageBean>() {
        @Override
        public SMSMessageBean createFromParcel(Parcel source) {
            return new SMSMessageBean(source);
        }

        @Override
        public SMSMessageBean[] newArray(int size) {
            return new SMSMessageBean[size];
        }
    };
}

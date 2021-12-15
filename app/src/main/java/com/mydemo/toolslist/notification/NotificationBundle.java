package com.mydemo.toolslist.notification;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：Nixon
 * date：2020/8/17.
 * 邮箱：jan.romon@gmail.com
 * TODO：
 */
public class NotificationBundle implements Parcelable {
    private String title;
    private String bigTitle;
    private String text;
    private String subText;
    private String remoteInputHistory;
    private String infoText;
    private String summaryText;
    private String bigText;
    private int progress;
    private int max;
    private boolean indeterminate;

    @Override
    public String toString() {
        return "NotificationBundle{\n" +
                "title='" + title + '\'' +
                ", \nbigTitle='" + bigTitle + '\'' +
                ", \ntext='" + text + '\'' +
                ", \nsubText='" + subText + '\'' +
                ", \nremoteInputHistory='" + remoteInputHistory + '\'' +
                ", \ninfoText='" + infoText + '\'' +
                ", \nsummaryText='" + summaryText + '\'' +
                ", \nbigText='" + bigText + '\'' +
                ", \nprogress=" + progress +
                ", \nmax=" + max +
                ", \nindeterminate=" + indeterminate +
                '}';
    }

    public NotificationBundle(String title, String bigTitle, String text, String subText, String remoteInputHistory, String infoText, String summaryText, String bigText, int progress, int max, boolean indeterminate) {
        this.title = title;
        this.bigTitle = bigTitle;
        this.text = text;
        this.subText = subText;
        this.remoteInputHistory = remoteInputHistory;
        this.infoText = infoText;
        this.summaryText = summaryText;
        this.bigText = bigText;
        this.progress = progress;
        this.max = max;
        this.indeterminate = indeterminate;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBigTitle() {
        return bigTitle == null ? "" : bigTitle;
    }

    public void setBigTitle(String bigTitle) {
        this.bigTitle = bigTitle;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubText() {
        return subText == null ? "" : subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public String getRemoteInputHistory() {
        return remoteInputHistory == null ? "" : remoteInputHistory;
    }

    public void setRemoteInputHistory(String remoteInputHistory) {
        this.remoteInputHistory = remoteInputHistory;
    }

    public String getInfoText() {
        return infoText == null ? "" : infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public String getSummaryText() {
        return summaryText == null ? "" : summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public String getBigText() {
        return bigText == null ? "" : bigText;
    }

    public void setBigText(String bigText) {
        this.bigText = bigText;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isIndeterminate() {
        return indeterminate;
    }

    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.bigTitle);
        dest.writeString(this.text);
        dest.writeString(this.subText);
        dest.writeString(this.remoteInputHistory);
        dest.writeString(this.infoText);
        dest.writeString(this.summaryText);
        dest.writeString(this.bigText);
        dest.writeInt(this.progress);
        dest.writeInt(this.max);
        dest.writeByte(this.indeterminate ? (byte) 1 : (byte) 0);
    }

    protected NotificationBundle(Parcel in) {
        this.title = in.readString();
        this.bigTitle = in.readString();
        this.text = in.readString();
        this.subText = in.readString();
        this.remoteInputHistory = in.readString();
        this.infoText = in.readString();
        this.summaryText = in.readString();
        this.bigText = in.readString();
        this.progress = in.readInt();
        this.max = in.readInt();
        this.indeterminate = in.readByte() != 0;
    }

    public static final Parcelable.Creator<NotificationBundle> CREATOR = new Parcelable.Creator<NotificationBundle>() {
        @Override
        public NotificationBundle createFromParcel(Parcel source) {
            return new NotificationBundle(source);
        }

        @Override
        public NotificationBundle[] newArray(int size) {
            return new NotificationBundle[size];
        }
    };
}

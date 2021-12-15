package com.mydemo.toolslist.notification;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：Nixon
 * date：2020/8/17.
 * 邮箱：jan.romon@gmail.com
 * TODO：StatusBarNotification对象外层包含的基本数据
 */
public class StatusNotificationBean implements Parcelable {
    private int id;
    private int uid;
    private long postTime;

    private String key;
    private String tag;
    private String opPkg;
    private String groupKey;
    /**
     * 对应变量pkg
     */
    private String packageName;
    private String overrideGroupKey;

    @Override
    public String toString() {
        return "StatusNotificationBean{\n" +
                "id=" + id +
                ", \nuid=" + uid +
                ", \npostTime=" + postTime +
                ", \nkey='" + key + '\'' +
                ", \ntag='" + tag + '\'' +
                ", \nopPkg='" + opPkg + '\'' +
                ", \ngroupKey='" + groupKey + '\'' +
                ", \npackageName='" + packageName + '\'' +
                ", \noverrideGroupKey='" + overrideGroupKey + '\'' +
                '}';
    }

    public StatusNotificationBean(int id, int uid, long postTime, String key, String tag, String opPkg, String groupKey, String packageName, String overrideGroupKey) {
        this.id = id;
        this.uid = uid;
        this.postTime = postTime;
        this.key = key;
        this.tag = tag;
        this.opPkg = opPkg;
        this.groupKey = groupKey;
        this.packageName = packageName;
        this.overrideGroupKey = overrideGroupKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTag() {
        return tag == null ? "" : tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOpPkg() {
        return opPkg == null ? "" : opPkg;
    }

    public void setOpPkg(String opPkg) {
        this.opPkg = opPkg;
    }

    public String getGroupKey() {
        return groupKey == null ? "" : groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getPackageName() {
        return packageName == null ? "" : packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getOverrideGroupKey() {
        return overrideGroupKey == null ? "" : overrideGroupKey;
    }

    public void setOverrideGroupKey(String overrideGroupKey) {
        this.overrideGroupKey = overrideGroupKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.uid);
        dest.writeLong(this.postTime);
        dest.writeString(this.key);
        dest.writeString(this.tag);
        dest.writeString(this.opPkg);
        dest.writeString(this.groupKey);
        dest.writeString(this.packageName);
        dest.writeString(this.overrideGroupKey);
    }

    protected StatusNotificationBean(Parcel in) {
        this.id = in.readInt();
        this.uid = in.readInt();
        this.postTime = in.readLong();
        this.key = in.readString();
        this.tag = in.readString();
        this.opPkg = in.readString();
        this.groupKey = in.readString();
        this.packageName = in.readString();
        this.overrideGroupKey = in.readString();
    }

    public static final Parcelable.Creator<StatusNotificationBean> CREATOR = new Parcelable.Creator<StatusNotificationBean>() {
        @Override
        public StatusNotificationBean createFromParcel(Parcel source) {
            return new StatusNotificationBean(source);
        }

        @Override
        public StatusNotificationBean[] newArray(int size) {
            return new StatusNotificationBean[size];
        }
    };
}

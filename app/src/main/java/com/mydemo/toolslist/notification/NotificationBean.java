package com.mydemo.toolslist.notification;

import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

/**
 * 作者：Nixon
 * date：2020/8/17.
 * 邮箱：jan.romon@gmail.com
 * TODO：Notification对象外层包含的基本数据
 */
public class NotificationBean implements Parcelable {
    private int mBadgeIcon;
    private int mGroupAlertBehavior;
    private int color;
    private int flags;
    private int iconLevel;
    private int icon;
    private int number;
    private int visibility;
    private long when;
    private long mTimeout;
    private boolean mAllowSystemGeneratedContextualActions;
    private String mSettingsText;
    private String tickerText;
    private String group;
    private String mSortKey;
    private String mChannelId;
    private String mShortcutId;
    private String category;
    private String notificationString;
    private Icon mSmallIcon;
    private Icon mLargeIcon;

    @Override
    public String toString() {
        return "NotificationBean{\n" +
                "mBadgeIcon=" + mBadgeIcon +
                ", \nmGroupAlertBehavior=" + mGroupAlertBehavior +
                ", \ncolor=" + color +
                ", \nflags=" + flags +
                ", \niconLevel=" + iconLevel +
                ", \nicon=" + icon +
                ", \nnumber=" + number +
                ", \nvisibility=" + visibility +
                ", \nwhen=" + when +
                ", \nmTimeout=" + mTimeout +
                ", \nmAllowSystemGeneratedContextualActions=" + mAllowSystemGeneratedContextualActions +
                ",\nmSettingsText='" + mSettingsText + '\'' +
                ", \ntickerText='" + tickerText + '\'' +
                ", \ngroup='" + group + '\'' +
                ", \nmSortKey='" + mSortKey + '\'' +
                ", \nmChannelId='" + mChannelId + '\'' +
                ", \nmShortcutId='" + mShortcutId + '\'' +
                ", \ncategory='" + category + '\'' +
                ", \nnotificationString='" + notificationString + '\'' +
                ", \nmSmallIcon=" + mSmallIcon +
                ", \nmLargeIcon=" + mLargeIcon +
                '}';
    }

    public NotificationBean(int mBadgeIcon, int mGroupAlertBehavior, int color, int flags, int iconLevel, int icon, int number, int visibility, long when, long mTimeout, boolean mAllowSystemGeneratedContextualActions, String mSettingsText, String tickerText, String group, String mSortKey, String mChannelId, String mShortcutId, String category, String notificationString, Icon mSmallIcon, Icon mLargeIcon) {
        this.mBadgeIcon = mBadgeIcon;
        this.mGroupAlertBehavior = mGroupAlertBehavior;
        this.color = color;
        this.flags = flags;
        this.iconLevel = iconLevel;
        this.icon = icon;
        this.number = number;
        this.visibility = visibility;
        this.when = when;
        this.mTimeout = mTimeout;
        this.mAllowSystemGeneratedContextualActions = mAllowSystemGeneratedContextualActions;
        this.mSettingsText = mSettingsText;
        this.tickerText = tickerText;
        this.group = group;
        this.mSortKey = mSortKey;
        this.mChannelId = mChannelId;
        this.mShortcutId = mShortcutId;
        this.category = category;
        this.notificationString = notificationString;
        this.mSmallIcon = mSmallIcon;
        this.mLargeIcon = mLargeIcon;
    }

    public int getmBadgeIcon() {
        return mBadgeIcon;
    }

    public void setmBadgeIcon(int mBadgeIcon) {
        this.mBadgeIcon = mBadgeIcon;
    }

    public int getmGroupAlertBehavior() {
        return mGroupAlertBehavior;
    }

    public void setmGroupAlertBehavior(int mGroupAlertBehavior) {
        this.mGroupAlertBehavior = mGroupAlertBehavior;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getIconLevel() {
        return iconLevel;
    }

    public void setIconLevel(int iconLevel) {
        this.iconLevel = iconLevel;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public long getWhen() {
        return when;
    }

    public void setWhen(long when) {
        this.when = when;
    }

    public long getmTimeout() {
        return mTimeout;
    }

    public void setmTimeout(long mTimeout) {
        this.mTimeout = mTimeout;
    }

    public boolean ismAllowSystemGeneratedContextualActions() {
        return mAllowSystemGeneratedContextualActions;
    }

    public void setmAllowSystemGeneratedContextualActions(boolean mAllowSystemGeneratedContextualActions) {
        this.mAllowSystemGeneratedContextualActions = mAllowSystemGeneratedContextualActions;
    }

    public String getmSettingsText() {
        return mSettingsText == null ? "" : mSettingsText;
    }

    public void setmSettingsText(String mSettingsText) {
        this.mSettingsText = mSettingsText;
    }

    public String getTickerText() {
        return tickerText == null ? "" : tickerText;
    }

    public void setTickerText(String tickerText) {
        this.tickerText = tickerText;
    }

    public String getGroup() {
        return group == null ? "" : group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getmSortKey() {
        return mSortKey == null ? "" : mSortKey;
    }

    public void setmSortKey(String mSortKey) {
        this.mSortKey = mSortKey;
    }

    public String getmChannelId() {
        return mChannelId == null ? "" : mChannelId;
    }

    public void setmChannelId(String mChannelId) {
        this.mChannelId = mChannelId;
    }

    public String getmShortcutId() {
        return mShortcutId == null ? "" : mShortcutId;
    }

    public void setmShortcutId(String mShortcutId) {
        this.mShortcutId = mShortcutId;
    }

    public String getCategory() {
        return category == null ? "" : category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotificationString() {
        return notificationString == null ? "" : notificationString;
    }

    public void setNotificationString(String notificationString) {
        this.notificationString = notificationString;
    }

    public Icon getmSmallIcon() {
        return mSmallIcon;
    }

    public void setmSmallIcon(Icon mSmallIcon) {
        this.mSmallIcon = mSmallIcon;
    }

    public Icon getmLargeIcon() {
        return mLargeIcon;
    }

    public void setmLargeIcon(Icon mLargeIcon) {
        this.mLargeIcon = mLargeIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mBadgeIcon);
        dest.writeInt(this.mGroupAlertBehavior);
        dest.writeInt(this.color);
        dest.writeInt(this.flags);
        dest.writeInt(this.iconLevel);
        dest.writeInt(this.icon);
        dest.writeInt(this.number);
        dest.writeInt(this.visibility);
        dest.writeLong(this.when);
        dest.writeLong(this.mTimeout);
        dest.writeByte(this.mAllowSystemGeneratedContextualActions ? (byte) 1 : (byte) 0);
        dest.writeString(this.mSettingsText);
        dest.writeString(this.tickerText);
        dest.writeString(this.group);
        dest.writeString(this.mSortKey);
        dest.writeString(this.mChannelId);
        dest.writeString(this.mShortcutId);
        dest.writeString(this.category);
        dest.writeString(this.notificationString);
        dest.writeParcelable(this.mSmallIcon, flags);
        dest.writeParcelable(this.mLargeIcon, flags);
    }

    protected NotificationBean(Parcel in) {
        this.mBadgeIcon = in.readInt();
        this.mGroupAlertBehavior = in.readInt();
        this.color = in.readInt();
        this.flags = in.readInt();
        this.iconLevel = in.readInt();
        this.icon = in.readInt();
        this.number = in.readInt();
        this.visibility = in.readInt();
        this.when = in.readLong();
        this.mTimeout = in.readLong();
        this.mAllowSystemGeneratedContextualActions = in.readByte() != 0;
        this.mSettingsText = in.readString();
        this.tickerText = in.readString();
        this.group = in.readString();
        this.mSortKey = in.readString();
        this.mChannelId = in.readString();
        this.mShortcutId = in.readString();
        this.category = in.readString();
        this.notificationString = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.mSmallIcon = in.readParcelable(Icon.class.getClassLoader());
            this.mLargeIcon = in.readParcelable(Icon.class.getClassLoader());
        }
    }

    public static final Parcelable.Creator<NotificationBean> CREATOR = new Parcelable.Creator<NotificationBean>() {
        @Override
        public NotificationBean createFromParcel(Parcel source) {
            return new NotificationBean(source);
        }

        @Override
        public NotificationBean[] newArray(int size) {
            return new NotificationBean[size];
        }
    };
}

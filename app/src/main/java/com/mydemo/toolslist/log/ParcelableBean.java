package com.mydemo.toolslist.log;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author: Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist.log
 * @ClassName: ParcelableBean
 * @CreateDate: 2020/12/13 22:16
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
class ParcelableBean implements Parcelable {

    private String key;
    private String value;

    //描述当前 Parcelable 实例的对象类型，比如说，如果对象中有文件描述符，
    //这个方法就会返回上面的CONTENTS_FILE_DESCRIPTOR，其他情况会返回一个位掩码
    @Override
    public int describeContents() {
        return 0;
    }

    //将对象转换成一个Parcel对象，dest表示要写入的Parcel对象，flags示这个对象将如何写入
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.value);
    }

    public ParcelableBean() {
    }

    protected ParcelableBean(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
    }

    //实现类必须有一个Creator属性，用于反序列化，将Parcel对象转换为Parcelable
    public static final Parcelable.Creator<ParcelableBean> CREATOR = new Parcelable.Creator<ParcelableBean>() {
        //反序列化的方法，将Parcel还原成Java对象
        @Override
        public ParcelableBean createFromParcel(Parcel source) {
            return new ParcelableBean(source);
        }

        //提供给外部类反序列化这个数组使用。
        @Override
        public ParcelableBean[] newArray(int size) {
            return new ParcelableBean[size];
        }
    };
}

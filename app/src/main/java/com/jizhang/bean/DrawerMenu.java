package com.jizhang.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pengchong on 2017/5/15.
 * 左侧侧滑菜单中的对象
 * name为条目名称，value为对应值
 */

public class DrawerMenu implements Parcelable {

    String name ;
    String value;

    public DrawerMenu(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    protected DrawerMenu(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Creator<DrawerMenu> CREATOR = new Creator<DrawerMenu>() {
        @Override
        public DrawerMenu createFromParcel(Parcel source) {
            return new DrawerMenu(source);
        }

        @Override
        public DrawerMenu[] newArray(int size) {
            return new DrawerMenu[size];
        }
    };
}

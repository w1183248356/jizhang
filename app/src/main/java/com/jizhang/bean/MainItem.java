package com.jizhang.bean;

/**
 * Created by huntero on 17-5-24.
 */

public class MainItem {
    public int id;
    public String name;
    public int iconResId;
    public int tipCount;

    public MainItem(int id, String name, int iconResId) {
        this(id, name, iconResId, 0);
    }

    public MainItem(int id, String name, int iconResId, int tipCount) {
        this.id = id;
        this.name = name;
        this.iconResId = iconResId;
        this.tipCount = tipCount;
    }
}

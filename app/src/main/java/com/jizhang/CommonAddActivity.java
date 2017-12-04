package com.jizhang;

import android.app.Activity;
import android.content.Intent;

import com.jizhang.controller.AddCommonControl;
import com.jizhang.controller.AndroidDisplay;
import com.jizhang.controller.Controller;

/**
 * Created by wangjian on 2017/12/4.
 * 添加常用信息界面
 */

public class CommonAddActivity extends BaseActivity {
    public static final String ADDTYPE = "addtype";
    public static final int ADD_USER = 1;//添加客户
    public static final int ADD_PRDT = 2; //添加产品
    public static final int ADD_RESAON = 3;//添加支出原因

    public static void launch(Activity activity, int addType) {
        Intent intent = new Intent(activity, CommonAddActivity.class);
        intent.putExtra(ADDTYPE, addType);
        activity.startActivity(intent);
    }

    @Override
    protected void handleIntent(Intent intent, AndroidDisplay display) {
        display.showAddCommonFragment(intent);
    }

    @Override
    public Controller getController() {
        return AddCommonControl.getInstance();
    }
}

package com.jizhang.controller;


/**
 * Created by wangjian on 2017/12/4.
 * 添加常用信息
 */

public class AddCommonControl extends Controller<AddCommonControl.AddUi, AddCommonControl.AddCallback> {
    private static AddCommonControl instance;

    private AddCommonControl() {
    }

    public static AddCommonControl getInstance() {
        if (instance == null) {
            instance = new AddCommonControl();
        }
        return instance;
    }

    @Override
    protected AddCallback createUiCallback(AddUi ui) {
        return new AddCallback() {
        };
    }

    public interface AddUi extends Controller.Ui<AddCallback> {

    }

    public interface AddCallback {

    }
}

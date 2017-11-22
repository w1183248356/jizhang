package com.jizhang.controller;

import android.text.TextUtils;

/**
 * Created by huntero on 16-12-26.
 */

public abstract class Controller<U extends Controller.Ui, UC> {


    public interface Ui<UC>{
        void setCallback(UC callback);
        void showProgress(boolean show, String message);
        void showToast(String msg);
    }

    private AndroidDisplay mDisplay;
    private boolean mInited;
    protected U mUiView;

    public final void init() {
        mInited=true;
        onInited();
    }

    public final void suspend() {
        onSuspended();
        mInited = false;
    }

    public synchronized final void attach(U ui) {
        mUiView = ui;

        ui.setCallback(createUiCallback(ui));
        if (isInited()) {
            //已经初始化
            //1.设置标题
            updateTitle(getUiTitle(ui));
            //
            onAttached();
            //2.填充界面
            populateUi(ui);
        }
    }

    protected void updateTitle(String title) {
        if (this.mDisplay != null && !TextUtils.isEmpty(title)) {
            this.mDisplay.setActionBarTitle(title);
        }
    }

    public synchronized final void dettach(U ui) {
        onDettached();
        ui.setCallback(null);
    }

    public final boolean isInited() {
        return mInited;
    }

    public void setDisplay(AndroidDisplay display) {
        this.mDisplay = display;
    }

    public AndroidDisplay getDisplay() {
        return mDisplay;
    }

    protected abstract UC createUiCallback(U ui);

    protected void onInited() {}
    protected void onSuspended() {}
    protected void onAttached() {}
    protected void onDettached() {}
    protected String getUiTitle(U ui) {
        return null;
    }
    protected void populateUi(U ui){

    }
}

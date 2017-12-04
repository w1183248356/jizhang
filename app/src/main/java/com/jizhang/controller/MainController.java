package com.jizhang.controller;

import android.text.TextUtils;
import android.view.View;

import com.viewhigh.libs.adapter.ClickRecyclerViewAdapter;


/**
 * Created by huntero on 17-4-11.
 */

public class MainController extends Controller<MainController.MainUi, MainController.MainCallback> {
    private static MainController instance;


    private MainController() {
    }

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }


    public interface MainUi extends Controller.Ui<MainCallback> {


    }

    public interface MainCallback  {
    }

    @Override
    protected MainCallback createUiCallback(MainUi ui) {
        return new MainCallback() {

        };
    }

    @Override
    protected void onAttached() {
        super.onAttached();

    }


    @Override
    protected void onInited() {
        super.onInited();

    }

    @Override
    protected void onSuspended() {
        super.onSuspended();

    }


    @Override
    protected void onDettached() {
        super.onDettached();
    }
}

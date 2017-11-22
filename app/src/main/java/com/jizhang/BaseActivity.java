package com.jizhang;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.jizhang.controller.AndroidDisplay;
import com.jizhang.controller.Controller;
import com.viewhigh.libs.LongPressActivityHelper;
import com.viewhigh.libs.fragback.BackHandleHelper;

/**
 * Created by huntero on 17-1-17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private AndroidDisplay mDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.app_bar_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.title_bar_back);

        mDisplay = new AndroidDisplay(this);
        Controller controller = getController();
        if (controller != null) {
            controller.setDisplay(mDisplay);
            controller.init();
        }

        handleIntent(getIntent(), getDisplay());
    }

    protected abstract void handleIntent(Intent intent, AndroidDisplay display);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && !BackHandleHelper.handleBackPress(this)) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!BackHandleHelper.handleBackPress(this))
            super.onBackPressed();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return LongPressActivityHelper.getInstance().with(this).dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Controller controller = getController();
        if (controller != null) {
            controller.suspend();
        }
    }

    public void addCollapsingView(View view, int index, int scrollFlags) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundResource(android.R.color.transparent);

        CollapsingToolbarLayout.LayoutParams params = new CollapsingToolbarLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mCollapsingToolbarLayout.addView(view, index, params);

        if(scrollFlags > 0) {
            //设置滚动
            AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams();
            lp.setScrollFlags(
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
            mCollapsingToolbarLayout.setLayoutParams(lp);
        }
    }

    public Controller getController() {
        return null;
    }

    //-----------------------------------------------
    boolean isInitProgressDialog = false;
    public void initProgressDialog() {
        initProgressDialog(true);
    }

    public void initProgressDialog(boolean cancelable) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(cancelable);
        if (cancelable) {
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    //终止网络请求
                }
            });
        }
        isInitProgressDialog = true;
    }

    public void showProgress(boolean show, String message) {
        if (!isInitProgressDialog()) {
            initProgressDialog();
        }
        if (show) {
            showProgressDialog(message);
        } else {
            hideProgressDialog();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isInitProgressDialog() {
        return isInitProgressDialog;
    }

    protected void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void showProgressDialog(int resid) {
        progressDialog.setMessage(getString(resid));
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public AndroidDisplay getDisplay() {
        return mDisplay;
    }
}

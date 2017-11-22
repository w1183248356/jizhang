package com.viewhigh.libs.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.viewhigh.libs.R;

/**
 * Created by pengchong on 2017/5/27.
 *
 * 修改背景色为透明
 * 修改弹出时状态栏变黑的问题
 *
 */

public class FixedBottomSheetDialog extends BottomSheetDialog {

    private Activity mContext;

    public FixedBottomSheetDialog(@NonNull Activity context) {
        super(context);
        this.mContext = context;
    }

    public FixedBottomSheetDialog(@NonNull Activity context, @StyleRes int theme) {
        super(context, theme);
        this.mContext = context;
    }

    protected FixedBottomSheetDialog(@NonNull Activity context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenHeight = getScreenHeight(mContext);
        int statusBarHeight = getStatusBarHeight(mContext);
        int dialogHeight = screenHeight - statusBarHeight;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
    }

    private static int getScreenHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}

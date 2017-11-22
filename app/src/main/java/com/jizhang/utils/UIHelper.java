package com.jizhang.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

/**
 * Created by huntero on 16-12-27.
 */

public class UIHelper {

    //    public static void progress(String text)
    public static void showDialog(final Activity context, String title, String message,
            DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        showDialog(context, title, message, "确定", positive, "取消", negative);
    }

    public static void showDialog(final Activity context, String title, String message,String positive,
            DialogInterface.OnClickListener positiveListener, String negative, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(message).setPositiveButton(positive, positiveListener)
                .setNegativeButton(negative, negativeListener);
        builder.create().show();
    }

    public static boolean isNetworkOk(final Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }
}

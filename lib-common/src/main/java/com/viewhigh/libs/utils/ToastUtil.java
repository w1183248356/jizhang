package com.viewhigh.libs.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by pengchong on 2017/4/13.
 */

public class ToastUtil {

    private  static  Toast toast;
    private static long oneTime;
    private static long twoTime;
    private static String oldMsg;

    public static void showToast(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);

            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

}

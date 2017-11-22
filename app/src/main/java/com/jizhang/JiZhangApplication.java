package com.jizhang;

import android.app.Application;
import android.content.Context;

/**
 * Created by huntero on 17-8-10.
 */

public class JiZhangApplication extends Application {
    static JiZhangApplication instance;

    public JiZhangApplication() {
    }

    public JiZhangApplication(final Context context) {
        this();
        attachBaseContext(context);
    }

    public static JiZhangApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
}

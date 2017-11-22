package com.viewhigh.libs;

import android.app.Application;
import android.content.Context;

/**
 * Created by huntero on 17-10-11.
 */

public abstract class BaseApplication extends Application {
    private static BaseApplication context;

    public BaseApplication() {
    }
    public BaseApplication(final Context context) {
        this();
        attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        onInitGlobalConfig();
    }

    public static BaseApplication getGlobalContext(){
        return context;
    }

    protected abstract void onInitGlobalConfig();
}

package com.jizhang.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by huntero on 17-2-13.
 */

public class PreferenceHelper {
    static PreferenceHelper instance = null;
    private static SharedPreferences preferences;

    private PreferenceHelper(){}

    public static PreferenceHelper getInstance(final Context context) {
        if (instance == null) {
            instance = new PreferenceHelper();
            preferences = context.getSharedPreferences("pref_local", Context.MODE_PRIVATE);
        }
        return instance;
    }

    public SharedPreferences preference() {
        return preferences;
    }


    public void saveLoginUser(String user) {
        preferences.edit().putString("login_user",user).commit();
    }

    public String getLoginUser() {
        return preferences.getString("login_user", null);
    }

    public void savePassword(String password) {
        preferences.edit().putString("login_password", password).commit();
    }

    public String getPassword() {
        return preferences.getString("login_password", null);
    }

    public void saveLogoutState(int state) {
        preferences.edit().putInt("login_state", state).commit();
    }

    public int getLogoutState() {
        return preferences.getInt("login_state", 0);
    }
//
//    public void saveCompCopy(CompBean compBean) {
//        preferences.edit().putString("comp_copy", compBean == null?null:JSON.toJSONString(compBean)).apply();
//    }
//
//    public CompBean getCompCopy(){
//        String json = preferences.getString("comp_copy", null);
//        return json == null?null:JSON.parseObject(json,CompBean.class);
//    }
}

package com.jizhang.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.jizhang.CommonAddActivity;
import com.jizhang.MainActivity;
import com.jizhang.R;
import com.jizhang.SettingsActivity;
import com.jizhang.fragment.AddCommonFragment;
import com.jizhang.fragment.MainFragment;

/**
 * Created by huntero on 16-12-27.
 */

public class AndroidDisplay {
    AppCompatActivity mActivity;

    public AndroidDisplay(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    public void setActionBarTitle(CharSequence title) {
        ActionBar ab = mActivity.getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
    }


    public void backspace() {
        mActivity.finish();
    }

    public void showMainActivity() {
//        Intent intent = new Intent(mActivity, MainActivity.class);
        Intent intent = new Intent(mActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    public void startActivity(Intent intent, Bundle options) {
        ActivityCompat.startActivity(mActivity, intent, options);
    }

    public void showServerConfig() {
        Intent intent = new Intent(mActivity, SettingsActivity.class);
        mActivity.startActivity(intent);
    }

    //---------------------------------------------------------------------------
    public void showFragmentContent(Fragment fragment) {
        final FragmentManager manager = mActivity.getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, fragment).commit();
    }

    public void launchAddCommonActivity(int addType){
        CommonAddActivity.launch(mActivity, addType);
    }

    public void showAddCommonFragment(Intent intent){
        AddCommonFragment mainFragment = new AddCommonFragment();
        if(intent != null) {
            mainFragment.setArguments(intent.getExtras());
        }
        showFragmentContent(mainFragment);
    }

}

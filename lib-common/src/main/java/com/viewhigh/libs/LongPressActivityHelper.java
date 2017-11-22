package com.viewhigh.libs;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import java.util.List;

/**
 * Created by huntero on 17-8-1.
 */

public final class LongPressActivityHelper {
    static LongPressActivityHelper instance;
    public interface OnLongPressListener{
        boolean onLongPressed();
    }

    private static final int LONG_PRESS = 0x41;
    private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout() + ViewConfiguration.getTapTimeout();
    float downX,downY;
    boolean isInLongPress = false;
    FragmentActivity mActivity;
    Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LONG_PRESS) {
                //长按事件发生了
                isInLongPress = true;
                handleInFragment();
            }
        }
    };

    public static LongPressActivityHelper getInstance() {
        if (instance == null) {
            instance = new LongPressActivityHelper();
        }
        return instance;
    }

    public LongPressActivityHelper with(FragmentActivity activity) {
        this.mActivity = activity;
        return this;
    }

    private void handleInFragment() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment item : fragments) {
                if (isFragmentLongPressHandler(item)) {
                    return;
                }
            }
        }
    }

    private static boolean isFragmentLongPressHandler(Fragment fragment) {
        return fragment != null
                && fragment.isVisible()
                && fragment.getUserVisibleHint()
                && fragment instanceof OnLongPressListener
                && ((OnLongPressListener)fragment).onLongPressed();
    }

    /**
     *
     * @param ev
     * @return If false it means doing nothing
     */
    public boolean dispatchTouchEvent(MotionEvent ev){
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();

                mHandler.removeMessages(LONG_PRESS);
                mHandler.sendEmptyMessageAtTime(LONG_PRESS, ev.getDownTime() + LONGPRESS_TIMEOUT);
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(downX - ev.getX()) >= 10 || Math.abs(downY - ev.getY()) >= 10) {
                    //滑动了
                    mHandler.removeMessages(LONG_PRESS);
                    isInLongPress = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isInLongPress) {
                    isInLongPress = false;
                    return true;
                } else {
                    mHandler.removeMessages(LONG_PRESS);
                }
                break;
        }
        return false;
    }
}

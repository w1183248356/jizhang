package com.viewhigh.libs.fragback;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by huntero on 17-7-28.
 */

public final class BackHandleHelper {
    public static boolean handleBackPress(FragmentActivity activity){
        return handleBackPress(activity.getSupportFragmentManager());
    }

    private static boolean handleBackPress(FragmentManager fragmentManager) {
        List<Fragment> frags = fragmentManager.getFragments();
        if (frags == null) {
            return false;
        }
        for(int i = frags.size() - 1;i>=0;i--) {
            Fragment child = frags.get(i);

            if (isFragmentBackHandled(child)) {
                return true;
            }
        }

        if (fragmentManager.getBackStackEntryCount() > 0) {
            //都未处理，则弹出栈
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    private static boolean isFragmentBackHandled(Fragment fragment) {
        return fragment != null
                && fragment.isVisible()
                && fragment.getUserVisibleHint()
                && fragment instanceof IBackHandler
                && ((IBackHandler) fragment).onBackPressed();
    }
}

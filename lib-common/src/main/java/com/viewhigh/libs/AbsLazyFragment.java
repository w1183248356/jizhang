package com.viewhigh.libs;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 实现Fragment数据的延迟加载
 * Created by huntero on 17-6-9.
 */

public abstract class AbsLazyFragment extends Fragment {

    private boolean hasCreateView;
    private boolean isFirstDisplay = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!hasCreateView) {
            return;
        }
        if (isVisibleToUser) {
            //Lazy

            onLazyPopulate(isFirstDisplay);
            if (isFirstDisplay) {
                isFirstDisplay = false;
            }
        } else {
            //Lazy over
            onLazyTerminate();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);

        if (!hasCreateView && getUserVisibleHint()) {
            //第一个Fragment，没有缓存，所以在调用setUserVisibleHint->true时，并不会调用lazy
            //isFirstDisplay = true;
            onLazyPopulate(isFirstDisplay);
            if (isFirstDisplay) {
                isFirstDisplay = false;
            }
        }

        hasCreateView = true;
        return view;
    }

    @Override
    public void onDestroyView() {
        hasCreateView = false;
        isFirstDisplay = true;

        super.onDestroyView();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void onLazyPopulate(boolean isFirstDisplay);

    protected abstract void onLazyTerminate();

}

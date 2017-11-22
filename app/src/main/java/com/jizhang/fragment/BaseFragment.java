package com.jizhang.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jizhang.BaseActivity;
import com.jizhang.controller.Controller;
import com.viewhigh.libs.fragback.IBackHandler;

import butterknife.ButterKnife;

/**
 * Created by huntero on 17-1-18.
 */

public abstract class BaseFragment<T extends Controller.Ui> extends Fragment implements
        IBackHandler{
    public static final String TAG = "BaseFragment";
    Activity mActivity;
    CollapseViewHolder mCollapseViewHolder = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        final CollapseViewHolder collapsingHolder = addCollapseView();
        if (collapsingHolder != null) {
            mCollapseViewHolder = collapsingHolder;
            if (mActivity instanceof BaseActivity) {
                //NOTE by Huntero
                //此处暂由BaseActivity完成Collapse视图添加（后续完整应交由当前Fragment所attach的Activity）
                final View collapsingView = LayoutInflater.from(mActivity).inflate(collapsingHolder.setLayout(), null);
                ButterKnife.inject(collapsingHolder.setHolderTarget(), collapsingView);
                ((BaseActivity)mActivity).addCollapsingView(collapsingView, 0, collapsingHolder.getCollapseScrollFlags());
            }
        }
    }

    protected CollapseViewHolder addCollapseView(){
        return null;
    }

    protected abstract class CollapseViewHolder {
        public final static int SCROLL_FLAGS_SCROLL = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                |AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
        public abstract int setLayout();

        /**
         * @see AppBarLayout.LayoutParams#setScrollFlags
         * @return
         */
        public int getCollapseScrollFlags(){
            return 0;
        }
        public CollapseViewHolder setHolderTarget() {
            return this;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View content = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.inject(this, content);
        onInitView(content);

        getController().attach(getAttachTarget());
        return content;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        getController().dettach(getAttachTarget());
    }

    protected abstract void onInitView(View content);

    @Override
    public void onResume() {
        super.onResume();
//        Log.i(TAG, "onResume: --------");
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.i(TAG, "onPause: --------");

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public void showProgress(boolean show, String message) {
        Activity a = getActivity();
        if (a instanceof BaseActivity) {
            ((BaseActivity)a).showProgress(show, message);
        }
    }

    public void showToast(String message) {
        Activity a = getActivity();
        if (a instanceof BaseActivity) {
            ((BaseActivity)a).showToast(message);
        }
    }
    protected Controller getController() {
        final FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return ((BaseActivity)activity).getController();
        }
        return null;
    }

    protected final T getAttachTarget(){
        return (T) this;
    }

    @LayoutRes
    protected abstract int getLayoutId();

//    protected void setTitleCenter(String title){
//        if(mActivity instanceof BaseActivity){
//            ((BaseActivity) mActivity).setTitleCenter(title);
//        }
//    }

}

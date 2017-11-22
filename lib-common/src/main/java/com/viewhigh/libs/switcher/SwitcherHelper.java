package com.viewhigh.libs.switcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * Created by huntero on 17-5-27.
 */

public class SwitcherHelper<DATA> implements ISwitcher<DATA> {

    private ViewGroup mParentView;
    private final View mOriginalView;
    private LayoutParams mOriginalParams;
    private int mOriginalIndex;

    private View mCurrentView;
    private View mEmptyView;
    private View mLoadingView;
    private View mNoNetwork;

    IDataAdapter mAdapter;

    SwitcherHelper(View originalView) {
        this.mOriginalView = originalView;
        init();
    }

    private void init() {
        mOriginalParams = this.mOriginalView.getLayoutParams();
        if (mOriginalView.getParent() != null) {
            mParentView = (ViewGroup) mOriginalView.getParent();
        } else {
            mParentView = (ViewGroup) mOriginalView.getRootView().findViewById(android.R.id.content);
        }
        final int count = mParentView.getChildCount();
        for (int i = 0; i < count; i++) {
            if (mOriginalView == mParentView.getChildAt(i)) {
                mOriginalIndex = i;
                break;
            }
        }
        mCurrentView = mOriginalView;
    }

    @Override
    public void showLoading() {
        if (mLoadingView != null) {
            showView(mLoadingView);
        }
    }

    @Override
    public void showNoNetwork() {
        if (mNoNetwork != null) {
            showView(mNoNetwork);
        }
    }

    @Override
    public void updateData(DATA data) {
        if (data == null) {
            if (mEmptyView != null) {
                showView(mEmptyView);
            }
            return;
        }

        mAdapter.setData(data);
        if (mAdapter.isEmpty()) {
            if (mEmptyView != null) {
                showView(mEmptyView);
            }
        } else {
            restoreView();
        }
    }

    @Override
    public void restoreView() {
        showView(mOriginalView);
    }

    @Override
    public void setVisible(boolean visible) {
        mCurrentView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void showView(View view) {
        setVisible(true);
        if (mParentView.getChildAt(mOriginalIndex) == view) {
            //无需切换
            return;
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        mParentView.removeViewAt(mOriginalIndex);
        mParentView.addView(view, mOriginalIndex, mOriginalParams);
        mCurrentView = view;
    }



    public static class Builder {
        private final View originalView;
        private View emptyView;
        private View loadingView;
        private View noNetwork;
        private View.OnClickListener retryListener;
        IDataAdapter adapter;

        public Builder(View originalView) {
            this.originalView = originalView;
        }

        public Builder setEmpty(int empty, View.OnClickListener retryListener) {
            emptyView = inflator(empty);
            this.retryListener = retryListener;
            return this;
        }

        public Builder setEmpty(View empty, View.OnClickListener retryListener) {
            this.emptyView = empty;
            this.retryListener = retryListener;
            return this;
        }

        public Builder setLoading(int loading) {
            loadingView = inflator(loading);
            return this;
        }

        public Builder setLoading(View loading) {
            this.loadingView = loading;
            return this;
        }

        public Builder setNoNetwork(int noNetwork) {
            this.noNetwork = inflator(noNetwork);
            return this;
        }

        public Builder setNoNetwork(View noNetwork) {
            this.noNetwork = noNetwork;
            return this;
        }

        public Builder setDataAdatper(IDataAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public SwitcherHelper create() {
            final SwitcherHelper helper = new SwitcherHelper(this.originalView);
            helper.mAdapter = this.adapter;
            helper.mLoadingView = this.loadingView;
            helper.mNoNetwork = this.noNetwork;
            helper.mEmptyView = this.emptyView;
            if(this.retryListener != null) {
                emptyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        helper.showLoading();
                        retryListener.onClick(v);
                    }
                });
            }
            return helper;
        }

        private View inflator(int layoutId) {
            return LayoutInflater.from(originalView.getContext()).inflate(layoutId, null);
        }
    }
}

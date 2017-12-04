package com.jizhang.fragment;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.jizhang.MainActivity;
import com.jizhang.R;
import com.jizhang.controller.Controller;
import com.jizhang.controller.MainController;
import com.viewhigh.libs.switcher.SwitcherHelper;
import com.viewhigh.libs.utils.ToastUtil;


import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by huntero on 17-4-11.
 */

public class FragmentMain extends BaseFragment<MainController.MainUi> implements
        MainController.MainUi {

    private MainController.MainCallback mCallback;

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    @InjectView(R.id.tv_main_scrollToTop)
    FloatingActionButton scrollToTop;

    @InjectView(R.id.sr_main_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private SwitcherHelper mSwitcherHelper;

    @Override
    protected void onInitView(View content) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mAdapter = new GoodsOrderAdapter(mActivity);
//        recyclerView.setAdapter(mAdapter);
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (firstPosition > 0) {
                    scrollToTop.setVisibility(View.VISIBLE);
                } else {
                    scrollToTop.setVisibility(View.INVISIBLE);
                }
            }
        });


//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_blue));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCallback != null) {
//                    mCallback.onRefresh();
                }
            }
        });

        mSwitcherHelper = new SwitcherHelper.Builder(swipeRefreshLayout)
                .setLoading(R.layout.view_loading)
//                .setDataAdatper(mAdapter)
                .setNoNetwork(R.layout.view_no_network)
                .setEmpty(R.layout.view_empty, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mCallback.onRefresh();
                    }
                })
                .create();
    }



    @Override
    public void onResume() {
        super.onResume();
        if (mCallback != null) {
//            mCallback.updateData();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void setCallback(MainController.MainCallback callback) {
        mCallback = callback;

    }



    @Override
    protected Controller getController() {
        if (mActivity instanceof MainActivity) {
            return ((MainActivity) mActivity).getController();
        }
        return super.getController();
    }


    @OnClick(R.id.tv_main_scrollToTop)
    protected void onClick(View view) {
        if (view.getId() == R.id.tv_main_scrollToTop) {
            recyclerView.getLayoutManager().scrollToPosition(0);
        }
    }
}

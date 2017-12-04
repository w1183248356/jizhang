package com.jizhang.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jizhang.R;
import com.jizhang.adapter.MainMenuItemAdapter;
import com.jizhang.bean.MainItem;
import com.jizhang.controller.Controller;
import com.jizhang.controller.MainController;

import java.util.List;

import butterknife.InjectView;

public class MainFragment extends BaseFragment<MainController.MainUi> implements
        MainController.MainUi {
    private MainController.MainCallback mCallback;

    @InjectView(R.id.menu_list)
    RecyclerView mMenuList;
    MainMenuItemAdapter mAdapter;

    @Override
    public void setCallback(MainController.MainCallback callback) {
        mCallback = callback;
    }

    @Override
    protected void onInitView(View content) {
        mMenuList.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        mMenuList.setLayoutManager(manager);
        mAdapter = new MainMenuItemAdapter(mActivity);

        mMenuList.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
//        mAdapter.setItemClickListener(mCallback);
    }

    @Override
    protected Controller getController() {
        return MainController.getInstance();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_content;
    }
}

package com.jizhang;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jizhang.adapter.MainDrawerAdapter;
import com.jizhang.bean.DrawerMenu;
import com.jizhang.controller.AndroidDisplay;
import com.jizhang.controller.MainController;
import com.jizhang.fragment.MainFragment;
import com.jizhang.utils.PreferenceHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @InjectView(R.id.recyclerView_drawer)
    RecyclerView drawerRecyclerView;

    @InjectView(R.id.tv_username)
    TextView userName;
    private MainController mController;
    private ArrayList<DrawerMenu> dataList;
    private MainDrawerAdapter mainDrawerAdapter;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer_activity);
        ButterKnife.inject(this);

        AndroidDisplay display= new AndroidDisplay(this);
        mController = MainController.getInstance();
        if (mController != null) {
            mController.setDisplay(display);
            mController.init();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundResource(android.R.color.transparent);
        setSupportActionBar(toolbar
        );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        View collapsing = LayoutInflater.from(this).inflate(R.layout.toolbar_main, null);
        CollapsingToolbarLayout.LayoutParams params = new CollapsingToolbarLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setCollapseMode(CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN);
        mCollapsingToolbarLayout.addView(collapsing, 0, params);


        final FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, new MainFragment()).commit();

        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainDrawerAdapter = new MainDrawerAdapter(this);
        drawerRecyclerView.setAdapter(mainDrawerAdapter);
        preferenceHelper = PreferenceHelper.getInstance(this);
        loadData();
    }

    private void loadData() {
        String name = "";//HightrackApplication.getInstance().getLoginInfo().accountName;
        if (name == null) {
            name = "";
        }
        userName.setText(name);
        dataList = new ArrayList<>();
        DrawerMenu comp = new DrawerMenu("单位:","未选择");
        DrawerMenu copy = new DrawerMenu("账套:","未选择");
        dataList.add(comp);
        dataList.add(copy);
        mainDrawerAdapter.setData(dataList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mController != null) {
            mController.suspend();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    protected void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @OnClick(R.id.btn_logout)
    protected void logout() {
        PreferenceHelper ph = PreferenceHelper.getInstance(JiZhangApplication.getInstance());
        ph.saveLogoutState(1);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        refreshCompCopy();
//        mainDrawerAdapter.setItemClickListener(new ClickRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClickListener(View view, int position) {
//                if(position == 0 || position == 1){
//                    ChooseCompCopyActivity.launch(MainActivity.this);
//                }
//            }
//        });
    }

    private void refreshCompCopy() {

//        CompBean compBean = preferenceHelper.getCompCopy();
//        if (dataList != null && dataList.size() > 0 && compBean != null) {
//            dataList.get(0).setValue(TextUtils.isEmpty(compBean.getCompName()) ? "未选择" : compBean.getCompName());
//            CompBean.CopyBean copyBean = compBean.getCopys().get(0);
//            dataList.get(1).setValue(TextUtils.isEmpty(copyBean.getCopyName()) ? "未选择" : copyBean.getCopyName());
//        }
//        mainDrawerAdapter.notifyDataSetChanged();

    }
}

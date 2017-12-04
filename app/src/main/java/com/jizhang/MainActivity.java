package com.jizhang;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jizhang.adapter.MainDrawerAdapter;
import com.jizhang.controller.AndroidDisplay;
import com.jizhang.controller.MainController;
import com.jizhang.fragment.FragmentMain;
import com.viewhigh.libs.adapter.ClickRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.recyclerView_drawer)
    RecyclerView drawerRecyclerView;

    MainController mController;
    CollapsingToolbarLayoutState state;
    private MainDrawerAdapter mainDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("详情展示");
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, new FragmentMain())
                .commit();

        mController = MainController.getInstance();
        mController.setDisplay(new AndroidDisplay(this));
        mController.init();

        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainDrawerAdapter = new MainDrawerAdapter(this);
        drawerRecyclerView.setAdapter(mainDrawerAdapter);
        loadData();
    }

    private void loadData() {
        List<String> list = new ArrayList<>();
        list.add("添加客户");
        list.add("添加产品");
        list.add("添加消费原因");
        mainDrawerAdapter.setData(list);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mainDrawerAdapter.setItemClickListener(new ClickRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                switch (position) {
                    case 0://添加客户
                        CommonAddActivity.launch(MainActivity.this, CommonAddActivity.ADD_USER);
                        break;
                    case 1://添加产品
                        CommonAddActivity.launch(MainActivity.this, CommonAddActivity.ADD_PRDT);
                        break;
                    case 2://添加消费原因
                        CommonAddActivity.launch(MainActivity.this, CommonAddActivity.ADD_RESAON);
                        break;
                }
            }
        });
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (state == CollapsingToolbarLayoutState.EXPANDED || state == CollapsingToolbarLayoutState.INTERNEDIATE) {
            menu.clear();
        } else {
//
        }
        return true;
    }

    public MainController getController() {
        return mController;
    }


    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
        }
        return super.onOptionsItemSelected(item);
    }
}

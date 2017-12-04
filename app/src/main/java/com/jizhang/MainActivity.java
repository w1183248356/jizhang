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
import android.widget.TextView;

import com.jizhang.adapter.MainDrawerAdapter;
import com.jizhang.bean.DrawerMenu;
import com.jizhang.controller.AndroidDisplay;
import com.jizhang.controller.MainController;
import com.jizhang.fragment.FragmentMain;
import com.viewhigh.libs.adapter.ClickRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

//    @InjectView(R.id.button_normal_check)
//    TextView button;

    @InjectView(R.id.recyclerView_drawer)
    RecyclerView drawerRecyclerView;

//    @InjectView(R.id.toolbar_title_center)
//    TextView titleCenter;


    MainController mController;
    CollapsingToolbarLayoutState state;
    private MainDrawerAdapter mainDrawerAdapter;
    private List<DrawerMenu> dataList;
    private DrawerMenu guishu;
    private DrawerMenu compDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        titleCenter.setText("物资盘点");
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

//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (verticalOffset == 0) {
//                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
//                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
//                        supportInvalidateOptionsMenu();
//                    }
//                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
//                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
//                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
//                        supportInvalidateOptionsMenu();
//                    }
//                } else {
//                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
//                        if(state == CollapsingToolbarLayoutState.COLLAPSED){
//                            //由折叠变为中间状态
//                            supportInvalidateOptionsMenu();
//                        }
//                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
//                    }
//                }
//            }
//        });


        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainDrawerAdapter = new MainDrawerAdapter(this);
        drawerRecyclerView.setAdapter(mainDrawerAdapter);
        loadData();
    }

    private void loadData() {
        dataList = new ArrayList<>();
        DrawerMenu menu = new DrawerMenu("账套:", "未选择");
        guishu = new DrawerMenu("资产归属:", "未选择");
        compDrawer = new DrawerMenu("单位:","未选择");
        dataList.add(compDrawer);
        dataList.add(menu);
        dataList.add(guishu);
        mainDrawerAdapter.setData(dataList);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mainDrawerAdapter.setItemClickListener(new ClickRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                switch (position){
                    case 0://账套

                        break;

                    case 1://归属
                        break;
                }
            }
        });
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (state == CollapsingToolbarLayoutState.EXPANDED || state == CollapsingToolbarLayoutState.INTERNEDIATE) {
            menu.clear();
//            button.setVisibility(View.VISIBLE);
        } else {
//            button.setVisibility(View.GONE);
//            menu.add(0, 1, 0, "盘点").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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

//    @OnClick(R.id.button_normal_check)
//    public void onClick(View view) {
//        ChooseDeptStoreActivity.launch(MainActivity.this);
//    }

    /**
     *
     */
    @OnClick(R.id.btn_logout)
    public void clickLogout() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
        }
        return super.onOptionsItemSelected(item);
    }
}

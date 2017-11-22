package com.jizhang.controller;

import android.view.View;

import com.jizhang.R;
import com.jizhang.bean.MainItem;
import com.viewhigh.libs.adapter.ClickRecyclerViewAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by huntero on 17-2-24.
 */
public class MainController extends Controller<MainController.MainUi, MainController.MainCallback> {

    private List<MainItem> mData;

    public interface MainUi extends Controller.Ui<MainCallback> {

        void refreshMenuList(List<MainItem> data);
 
        void showNoCompCopyDialog();
    }

    public interface MainCallback extends ClickRecyclerViewAdapter.OnItemClickListener {
    }


    private static MainController instance = null;
    private MainController() {
    }

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    @Override
    protected MainCallback createUiCallback(MainUi ui) {
        return new MainCallback(){

            @Override
            public void onItemClickListener(View view, int position) {
                final MainItem item = mData.get(position);
                switch (item.id) {
//                    case R.id.item_todo: //待办
//                        getDisplay().launchToDo();
//                        break;
//                    case R.id.item_loan: //借款单
//                        getDisplay().launchLoan();
//                        break;
//                    case R.id.item_expense: //报销单
//                        getDisplay().launchExpense();
//                        break;

                }
            }
        };
    }

    @Override
    protected void onAttached() {
        super.onAttached();
        mData = Arrays.asList(
                new MainItem(R.id.item_todo, "验收", R.drawable.main_todo),
                new MainItem(R.id.item_loan, "出库", R.drawable.main_loan),
                new MainItem(R.id.item_expense,"床边", R.drawable.main_expense));

//                new MainItem(R.id.item_picknote,"拣货", R.drawable.main_picknote),
//                new MainItem(R.id.item_inspect,"巡检", R.drawable.main_inspect),
//                new MainItem(R.id.item_failure,"报修", R.drawable.main_failure),
//                new MainItem(R.id.item_assert,"资产盘点", R.drawable.main_assert),
//                new MainItem(R.id.item_goods,"物料盘点", R.drawable.main_goods));
        mUiView.refreshMenuList(mData);
//        CompBean compBean = PreferenceHelper.getInstance(FinanceApplication.getInstance()).getCompCopy();
//        if(compBean == null){
//            mUiView.showNoCompCopyDialog();
//        }
    }

}


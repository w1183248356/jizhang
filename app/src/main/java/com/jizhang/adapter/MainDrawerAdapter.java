package com.jizhang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jizhang.R;
import com.viewhigh.libs.adapter.ClickRecyclerViewAdapter;

import java.util.List;

/**
 * Created by pengchong on 2017/5/15.
 */

public class MainDrawerAdapter extends
        ClickRecyclerViewAdapter<MainDrawerAdapter.AccountTaoHolder> {

    List<String> mDataList;

    public MainDrawerAdapter(Context context) {
        super(context);
    }

    public void setData(List<String> data){
        this.mDataList = data;
        notifyDataSetChanged();
    }

    @Override
    public AccountTaoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountTaoHolder(mInflater.inflate(R.layout.item_drawer_menu,parent,false),mOnItemClickListener,mOnItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(AccountTaoHolder holder, int position) {
        if(mDataList.size() <= position){
            return;
        }
        holder.name.setText(mDataList.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataList ==  null ? 0:mDataList.size();
    }

    public static class AccountTaoHolder extends ClickRecyclerViewAdapter.ViewHolder{

        private TextView name;

        public AccountTaoHolder(View itemView, OnItemClickListener click, OnItemLongClickListener longClick) {
            super(itemView, click, longClick);
            name = (TextView) itemView.findViewById(R.id.tv_drawer_menu_name);
        }
    }
}

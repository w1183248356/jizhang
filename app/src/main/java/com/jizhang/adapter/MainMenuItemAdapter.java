package com.jizhang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jizhang.R;
import com.jizhang.bean.MainItem;
import com.viewhigh.libs.adapter.BaseClickableAdapter;
import com.viewhigh.libs.adapter.ClickRecyclerViewAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by huntero on 17-5-24.
 */

public class MainMenuItemAdapter extends
        BaseClickableAdapter<MainItem, MainMenuItemAdapter.MenuItemViewHolder> {

    public MainMenuItemAdapter(Context context) {
        super(context);
    }

    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuItemViewHolder(mInflater.inflate(R.layout.item_main_menu, parent, false),
                mOnItemClickListener,
                mOnItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, int position) {
        final MainItem item = mData.get(position);
        holder.name.setText(item.name);
        holder.icon.setImageResource(item.iconResId);
        if (item.tipCount > 0) {
            holder.tip.setVisibility(View.VISIBLE);
            holder.tip.setText(String.valueOf(item.tipCount));
        } else {
            holder.tip.setVisibility(View.GONE);
        }
    }

    public static class MenuItemViewHolder extends ClickRecyclerViewAdapter.ViewHolder{

        @InjectView(R.id.item_icon)
        ImageView icon;
        @InjectView(R.id.item_name)
        TextView name;
        @InjectView(R.id.item_tip)
        TextView tip;
        public MenuItemViewHolder(View itemView, OnItemClickListener click,
                OnItemLongClickListener longClick) {
            super(itemView, click, longClick);
            ButterKnife.inject(this, itemView);
        }
    }

}

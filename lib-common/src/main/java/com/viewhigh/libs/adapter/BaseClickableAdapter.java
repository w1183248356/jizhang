package com.viewhigh.libs.adapter;

import android.content.Context;

import com.viewhigh.libs.switcher.IDataAdapter;

import java.util.List;

/**
 * Created by huntero on 17-1-18.
 */

public abstract class BaseClickableAdapter<T, U extends ClickRecyclerViewAdapter.ViewHolder> extends ClickRecyclerViewAdapter<U>
implements IDataAdapter<List<T>>{
    protected List<T> mData = null;
    public BaseClickableAdapter(Context context) {
        super(context);
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public boolean isEmpty() {
        return mData == null || mData.size() <= 0;
    }

    @Override
    public int getItemCount() {
        return this.mData == null ? 0 : this.mData.size();
    }
}

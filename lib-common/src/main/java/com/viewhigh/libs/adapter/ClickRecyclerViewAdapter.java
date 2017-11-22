package com.viewhigh.libs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by huntero on 16-11-2.
 */

public abstract class ClickRecyclerViewAdapter<V extends ClickRecyclerViewAdapter.ViewHolder> extends RecyclerView.Adapter<V> {
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    protected LayoutInflater mInflater = null;

    public ClickRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClickListener(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }
    public void setItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        protected OnItemClickListener click;
        protected OnItemLongClickListener longClick;

        public ViewHolder(View itemView, OnItemClickListener click, OnItemLongClickListener longClick) {
            super(itemView);
            this.click = click;
            this.longClick = longClick;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (click != null) {
                click.onItemClickListener(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (longClick != null) {
                return longClick.onItemLongClickListener(view, getAdapterPosition());
            }
            return false;
        }
    }
}

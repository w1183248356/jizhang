package com.jizhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.viewhigh.libs.adapter.BaseClickableAdapter;
import com.viewhigh.libs.adapter.ClickRecyclerViewAdapter;
import com.viewhigh.libs.adapter.ItemSlideHelper;

/**
 * Created by pengchong on 2017/6/1.
 * 目前需要item最外层布局是LinearLayout，可以用LinearLayout套在CardView外，并添加需要画出的控件。
 * 但是嵌套之后，CardView 的点击事件需要设置OnClick，貌似不能直接mAdapter.setItemClickListener，这样没有响应
 *
 */

public abstract class SlideClickableAdapter<T, U extends ClickRecyclerViewAdapter.ViewHolder> extends
        BaseClickableAdapter<T, U> implements ItemSlideHelper.Callback {
    private RecyclerView mRecyclerView;

    public SlideClickableAdapter(Context context) {
        super(context);
    }

    /**
     * 需要某些item不可滑动出删除按钮，则通过重写，添加判断条件，使其return 0
     * 需要滑出的，返回滑出部分的宽度
     * @param holder
     * @return
     */
    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            return viewGroup.getChildAt(1).getLayoutParams().width;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mRecyclerView.getContext(), this));
    }
}

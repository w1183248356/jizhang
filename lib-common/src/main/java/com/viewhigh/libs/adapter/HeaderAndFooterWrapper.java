package com.viewhigh.libs.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.viewhigh.libs.switcher.IDataAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pengchong on 2017/5/25.
 * <p>
 * ClickRecyclerViewAdapter 的包装类，创建时将创建好的ClickRecyclerViewAdapter对象传入，
 * 并将HeaderAndFooterWrapper对象作为adapter赋给recyclerview
 */

public abstract class HeaderAndFooterWrapper<A extends BaseClickableAdapter> extends
        ClickRecyclerViewAdapter implements IDataAdapter<List> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    public static final String TAG = "HeaderFooter";

    //    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    //    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();
    private SparseArrayCompat<ClickRecyclerViewAdapter.ViewHolder> mHeaderViewHolders = new SparseArrayCompat<>();
    private SparseArrayCompat<ClickRecyclerViewAdapter.ViewHolder> mFootViewHolders = new SparseArrayCompat<>();

    private A mInnerAdapter;

    public HeaderAndFooterWrapper(Context context, A adapter) {
        super(context);
        mInnerAdapter = adapter;
    }

    public A getOrignalAdapter() {
        return mInnerAdapter;
    }

    @Override
    public void setData(List ts) {
        mInnerAdapter.setData(ts);
        notifyDataSetChanged();
    }


    @Override
    public boolean isEmpty() {
        return (getHeadersCount() + getFootersCount() + getRealItemCount()) <= 0;
    }

    private boolean isHeaderViewPos(int position) {
//        Log.i(TAG, "isHeaderViewPos: pos=" + position + ", headers=" + getHeadersCount());
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
//        Log.i(TAG, "isFooterViewPos: pos=" + position + ", above=" + (getHeadersCount() + getRealItemCount()));
        return position >= getHeadersCount() + getRealItemCount();
    }


    /**
     * 添加RecyclerView的Header
     * 传入的ViewHoler，创建时传入的item，需要提前手动设置LayoutParams。
     * exm:  itemView.setLayoutParams(new LayoutParams(XXXX,XXXX));
     *
     * @param headerViewHolder
     */
    public void addHeaderView(ClickRecyclerViewAdapter.ViewHolder headerViewHolder) {
        mHeaderViewHolders.put(mHeaderViewHolders.size() + BASE_ITEM_TYPE_HEADER, headerViewHolder);
        notifyDataSetChanged();
    }

    /**
     * 添加RecyclerView的Footer
     * 传入的ViewHoler，创建时传入的item，需要提前手动设置LayoutParams。
     * exm:  itemView.setLayoutParams(new LayoutParams(XXXX,XXXX));
     *
     * @param footerViewHolder
     */
    public void addFootView(ClickRecyclerViewAdapter.ViewHolder footerViewHolder) {
        mFootViewHolders.put(mFootViewHolders.size() + BASE_ITEM_TYPE_FOOTER, footerViewHolder);
        notifyDataSetChanged();
    }

    public int getHeadersCount() {
        return mHeaderViewHolders.size();
    }

    public int getFootersCount() {
        return mFootViewHolders.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViewHolders.get(viewType) != null) {
            return mHeaderViewHolders.get(viewType);

        } else if (mFootViewHolders.get(viewType) != null) {
            return mFootViewHolders.get(viewType);
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViewHolders.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViewHolders.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            onBindHeaderViewHolder(holder, position);
            return;
        }
        if (isFooterViewPos(position)) {
            onBindFooterViewHolder(holder, position - getHeadersCount() - getRealItemCount());
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    public abstract void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int headerPosition);

    public abstract void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int footerPosition);

    @Override
    public int getItemCount() {
        int count = getHeadersCount() + getFootersCount() + getRealItemCount();
//        Log.i(TAG, "getItemCount: >>" + count + "\n" + Arrays.toString(Thread.currentThread().getStackTrace()));
        return count;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
        mInnerAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager
                    .getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViewHolders.get(viewType) != null) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    } else if (mFootViewHolders.get(viewType) != null) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    if (spanSizeLookup != null)
                        return spanSizeLookup.getSpanSize(position);
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    public View getHeaderViewAtPosition(int position) {
        return mHeaderViewHolders.get(position + BASE_ITEM_TYPE_HEADER).itemView;
    }

    public View getFooterViewAtPosition(int position) {
        return mFootViewHolders.get(position + BASE_ITEM_TYPE_FOOTER).itemView;
    }


    public void removeLastHeader() {
        mHeaderViewHolders.delete(BASE_ITEM_TYPE_HEADER + mHeaderViewHolders.size() - 1);
        notifyDataSetChanged();
    }

    public void removeLastFooter() {
        mFootViewHolders.delete(BASE_ITEM_TYPE_FOOTER + mFootViewHolders.size() - 1);
        notifyDataSetChanged();
    }
}

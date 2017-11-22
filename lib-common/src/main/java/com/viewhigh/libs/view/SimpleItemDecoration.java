package com.viewhigh.libs.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by pengchong on 2017/4/12.
 */

public class SimpleItemDecoration extends RecyclerView.ItemDecoration {
    private int top,bottom,left,right;
    private Paint mPaint;

    public SimpleItemDecoration(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public SimpleItemDecoration(int top, int bottom, int left, int right, int color){
        this(top,bottom,left,right);
        mPaint = new Paint();
        mPaint.setColor(color);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = bottom;
        outRect.top = top;
        outRect.left = left;
        outRect.right = right;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(mPaint!=null) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin +
                        Math.round(ViewCompat.getTranslationY(child));
                final int bottom = top + this.bottom;
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }else{
            super.onDrawOver(c, parent, state);
        }
    }
}

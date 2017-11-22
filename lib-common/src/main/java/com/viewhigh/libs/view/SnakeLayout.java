package com.viewhigh.libs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.viewhigh.libs.R;

/**
 * Created by huntero on 16-10-20.
 */

public class SnakeLayout extends ViewGroup {
    private static final boolean DEBUG = false;
    private static final String TAG = SnakeLayout.class.getSimpleName();

    /**
     *  measure子布局时，需用（具体宽度值mChildWidth + 父控件的左右paddin之和）作为parentWidthMeasureSpec参数
     */
    private int mWidthContentSpec;
    private int mHeightContentSpec;

    /**
     * This view's padding
     */
    private Rect mSnakePadding = new Rect();

    // 水平/垂直方向间距
    private int mSpaceHorizontal;
    private int mSpaceVertical;
    // 列数
    private int mColumnCount = 2;
    // 子项的宽高
    private int mChildWidth;
    private int mChildHeight;

    // 连线
    private Drawable mDivider;
    private int mDividerHeight;

    /**
     * The adapter containing the data to be displayed by this view to be set
     */
    private SnakeAdapter mAdapter = null;
    private int mItemCount;
    private AdapterDataSetObserver mDataSetObserver;

    //点击事件相关
    private int mMotionPosition;
    private static final int INVALID_POSITION = -1;
    private Rect mTouchFrame = null;
    private PerformItemClick mPerformItemClick = null;
    private OnItemClickListener mOnItemClickListener = null;

    public SnakeLayout(Context context) {
        this(context, null);
    }

    public SnakeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnakeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int styleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SnakeLayout, styleAttr, 0);

        final Drawable divider = a.getDrawable(R.styleable.SnakeLayout_divider);
        if (divider != null) {
            setDivider(divider);
        }
        final int dividerHeight = a.getDimensionPixelSize(R.styleable.SnakeLayout_divider_height, 0);
        if (dividerHeight != 0) {
            setDividerHeight(dividerHeight);
        }
        mSpaceHorizontal = a.getDimensionPixelSize(R.styleable.SnakeLayout_spaceHorizontal, 0);
        mSpaceVertical = a.getDimensionPixelSize(R.styleable.SnakeLayout_spaceVertical, 0);
        mColumnCount = a.getInt(R.styleable.SnakeLayout_columnCount, 1);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(DEBUG)
            Log.i(TAG, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final Rect snakePadding = mSnakePadding;
        snakePadding.left = getPaddingLeft();
        snakePadding.top = getPaddingTop();
        snakePadding.right = getPaddingRight();
        snakePadding.bottom = getPaddingBottom();

        final int paddingLeftAndRight = snakePadding.left + snakePadding.right;
        //1.计算布局的宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            widthSize = paddingLeftAndRight;    //保证最小宽度
        }
        //2.取得子项的宽度
        int childrenWidth = widthSize - paddingLeftAndRight
                - (mColumnCount - 1) * mSpaceHorizontal;
        mChildWidth = childrenWidth / mColumnCount;
        mWidthContentSpec = MeasureSpec.makeMeasureSpec(mChildWidth + paddingLeftAndRight, widthMode);

        //3.计算总高度，忽略子项的margin值，用于设置子项间的连线
        mChildHeight = 0;
        int childrenHeight = 0;
        mItemCount = mAdapter == null?0:mAdapter.getCount();
        if (mItemCount > 0) {
            View child;
            //否则当存在padding时，子布局会出现空白
            for (int i = 0; i < mItemCount; i++) {
                child = obtainView(i);
                measureScrapChild(child, i, mWidthContentSpec, 0);
                int measuredHeight = child.getMeasuredHeight();
                mChildHeight = Math.max(measuredHeight, mChildHeight);
            }
            childrenHeight = (mItemCount + mColumnCount - 1) / mColumnCount * mChildHeight;
        }
        mHeightContentSpec = MeasureSpec.makeMeasureSpec(mChildHeight, MeasureSpec.EXACTLY);

        int heightSize = childrenHeight + snakePadding.top + snakePadding.bottom
                + ((mItemCount + mColumnCount - 1) / mColumnCount - 1) * mSpaceVertical;
        setMeasuredDimension(widthSize, heightSize);
    }

    /**
     * Measure child
     * @param child
     * @param position
     * @param widthMeasureSpec
     */
    private void measureScrapChild(View child, int position, int widthMeasureSpec, int heightMeasureSpec) {
        //取到view的layoutparams
        LayoutParams p = child.getLayoutParams();
        //如果为空，则创建
        if (p == null) {
            p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            child.setLayoutParams(p);
        }
        //measure width
        int childWidthSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec,mSnakePadding.left + mSnakePadding.right, p.width);
        //measure height
        int childHeightSpec;
        if(heightMeasureSpec > 0){
            childHeightSpec = ViewGroup.getChildMeasureSpec(
                    heightMeasureSpec, 0, p.height);
        }else {
            childHeightSpec = ViewGroup.getChildMeasureSpec(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 0, p.height);
        }

        child.measure(childWidthSpec, childHeightSpec);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(DEBUG)
            Log.i(TAG, "onLayout: childCount=" + getChildCount() + ", itemCount=" + mItemCount);
        detachAllViewsFromParent();

        int childrenCount = mItemCount;
        int lineNum = 1;
        int direction = 1;
        int offsetX;
        int offsetY = mSnakePadding.top;
        View child;
        for (int i = 0; i < childrenCount; i++) {
            child = obtainView(i);
            setupChild(child, i);

            if(i >= lineNum * mColumnCount){
                //换行
                lineNum++;
                direction = 1 - direction;
                offsetY += mChildHeight + mSpaceVertical;
            }

            offsetX = mSnakePadding.left;
            int indexInLine = i - i / mColumnCount * mColumnCount;
            if(direction == 0){
                //从右向左
                offsetX += (mColumnCount - indexInLine - 1) * (mSpaceHorizontal +  mChildWidth);

            }else{
                //从左向右
                offsetX += indexInLine * (mSpaceHorizontal + mChildWidth);
            }

            child.layout(offsetX, offsetY, offsetX + mChildWidth, offsetY + mChildHeight);
        }
    }

    /**
     * Add child view to layout
     * @param child
     * @param position
     */
    private void setupChild(View child, int position) {
        measureScrapChild(child, position, mWidthContentSpec, mHeightContentSpec);
        LayoutParams p = child.getLayoutParams();
        addViewInLayout(child, -1, p, true);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(DEBUG)
            Log.i(TAG, "dispatchDraw: ");

        int childCount = mItemCount;
        if(childCount >= 2
                && mDivider != null && mDividerHeight > 0){
            int direction = 1;
            int lineNum = 1;

            //内容区域的总宽度
            final int contentWidthInLine = (mColumnCount * mChildWidth + (mColumnCount - 1) * mSpaceHorizontal);
            final int fixToCenterForVerticalLine = (mChildWidth - mDividerHeight) / 2;
            final int fixToCenterFoHorizontalLine = (mChildHeight - mDividerHeight) / 2;

            Rect bounds;
            for (int i = 1; i < childCount; i++) {
                bounds = new Rect();
                bounds.top = getPaddingTop();
                bounds.left = getPaddingLeft();

                int indexInLine = i - i / mColumnCount * mColumnCount;

                //是否新起一行
                if (i == lineNum * mColumnCount) {
                    //拐角
                    lineNum++;
                    direction = 1 - direction;

                    bounds.top += (lineNum - 1) * (mChildHeight + mSpaceVertical) - mSpaceVertical;
                    if (direction == 0) {
                        //从右往左
                        bounds.left += (mChildWidth + mSpaceHorizontal) * (mColumnCount - 1) + fixToCenterForVerticalLine;
                    } else {
                        //从左往右
                        bounds.left += fixToCenterForVerticalLine;
                    }
                    bounds.right = bounds.left + mSpaceVertical;
                    bounds.bottom = bounds.top + mDividerHeight;

                    drawDivider(canvas, bounds, true);
                } else {
                    //水平方向
                    //水平方向的top值不会改变
                    bounds.top += fixToCenterFoHorizontalLine + (lineNum - 1) * (mChildHeight + mSpaceVertical);
                    if (direction == 0) {
                        // <---
                        bounds.left += contentWidthInLine - indexInLine * (mChildWidth + mSpaceHorizontal);
                    } else {
                        // --->
                        bounds.left += indexInLine * mChildWidth + (indexInLine - 1) * mSpaceHorizontal;
                    }
                    bounds.right = bounds.left + mSpaceHorizontal;
                    bounds.bottom = bounds.top + mDividerHeight;
                    drawDivider(canvas, bounds, false);
                }
            }

        }

    }

    /**
     * Obtain child view(no cache)
     * @param position
     * @return
     */
    private View obtainView(int position) {
        View child = mAdapter.getView(position,null, SnakeLayout.this);
        return child;
    }

    /**
     * Draw divider during dispatchDraw
     * @param canvas
     * @param bounds
     * @param needRotation
     */
    private void drawDivider(Canvas canvas, Rect bounds, boolean needRotation) {
        if(DEBUG)
            Log.i(TAG, "drawDivider: bounds=" + bounds.toString());
        final Drawable divider = mDivider;

        if (needRotation) {
            //调整坐标值：平移一个高度
            bounds.left = bounds.left + mDividerHeight;
            bounds.right = bounds.left + mSpaceVertical;

            canvas.save();
            canvas.rotate(90, bounds.left, bounds.top);
        }
        divider.setBounds(bounds);
        divider.draw(canvas);

        if (needRotation) {
            canvas.restore();
        }
    }

    private boolean showChild(View child) {
        return child != null && child.getVisibility() != GONE;
    }

    public Drawable getDivider() {
        return mDivider;
    }

    public void setDivider(Drawable divider) {
        if(DEBUG)
            Log.i(TAG, "setDivider: ");
        if (divider != null) {
            mDividerHeight = divider.getIntrinsicHeight();
        } else {
            mChildHeight = 0;
        }
        mDivider = divider;
        //
        requestLayout();
        invalidate();
    }

    public void setDividerHeight(int height) {
        mDividerHeight = height;
        //
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            //
            return isClickable() || isLongClickable();
        }
        final int action = event.getAction();
        int motionPosition;
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                final int x = (int) event.getX();
                final int y = (int) event.getY();
                motionPosition = pointToPosition(x, y);
                mMotionPosition = motionPosition;
                //
                View c = null;
                if(motionPosition != INVALID_POSITION && (c = getChildAt(motionPosition)) !=null){
                    c.setPressed(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                final int moveX = (int) event.getX();
                final int moveY = (int) event.getY();
                motionPosition = pointToPosition(moveX, moveY);
                if(motionPosition != mMotionPosition){
                    View v = null;
                    if (mMotionPosition != INVALID_POSITION && (v = getChildAt(
                            mMotionPosition)) != null) {
                        v.setPressed(false);
                    }
                    break;
                }
                final View child = getChildAt(motionPosition);

                final float upX = event.getX();
                final boolean inSnakeLayout = upX > mSnakePadding.left && upX < getWidth() - mSnakePadding.right;
                if (child != null && inSnakeLayout) {
                    positionSelector(motionPosition, child);
                    if (mPerformItemClick == null) {
                        mPerformItemClick = new PerformItemClick();
                    }
                    postDelayed(mPerformItemClick, ViewConfiguration.getPressedStateDuration());
                    child.setPressed(false);
                }
                break;
        }
        return true;
    }

    public interface OnItemClickListener {
        void onItemClick(SnakeLayout parent, View view, int position, long id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private class PerformItemClick implements Runnable {

        @Override
        public void run() {
            final SnakeAdapter adapter = mAdapter;
            final int motionPosition = mMotionPosition;
            if (adapter != null && mItemCount > 0 &&
                    motionPosition != INVALID_POSITION &&
                    motionPosition < adapter.getCount()) {
                final View child = getChildAt(motionPosition);
                if (child != null) {
                    performItemClick(child, motionPosition, adapter.getItemId(motionPosition));
                }
            }
        }
    }

    private void performItemClick(View view, int position, long id) {
        //1.view state update
        //2.dispatch sub item
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(this, view, position, id);
        }


        mMotionPosition = INVALID_POSITION;
    }

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    private int pointToPosition(int x, int y) {
        Rect frame = mTouchFrame;
        if (frame == null) {
            mTouchFrame = new Rect();
            frame = mTouchFrame;
        }
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View v = getChildAt(i);
            if (v.getVisibility() == View.VISIBLE) {
                v.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return i;
                }
            }
        }
        return INVALID_POSITION;
    }

    private void positionSelector(int position, View view) {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mAdapter != null && mDataSetObserver == null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
        Log.i(TAG, "onAttachedToWindow: Snake adapter " + (mAdapter == null ? "is null" : "not null"));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mDataSetObserver = null;
        }
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            Log.i(TAG, "onChanged: ");
            super.onChanged();
            mItemCount = getAdapter().getCount();

            Log.i(TAG, "onChanged: observer count=" + mItemCount);
//            invalidate();
            requestLayout();
        }
    }

    /**
     * Copy from BaseAdapter
     */
    public static abstract class SnakeAdapter implements Adapter{
        private final DataSetObservable mDataSetObservable = new DataSetObservable();

        public boolean hasStableIds() {
            return false;
        }

        public void registerDataSetObserver(DataSetObserver observer) {
            Log.i(TAG, "registerDataSetObserver: ");
            mDataSetObservable.registerObserver(observer);
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            mDataSetObservable.unregisterObserver(observer);
        }

        /**
         * Notifies the attached observers that the underlying data has been changed
         * and any View reflecting the data set should refresh itself.
         */
        public void notifyDataSetChanged() {
            Log.i(TAG, "notifyDataSetChanged: ");
            mDataSetObservable.notifyChanged();

        }

        /**
         * Notifies the attached observers that the underlying data is no longer valid
         * or available. Once invoked this adapter is no longer valid and should
         * not report further data set changes.
         */
        public void notifyDataSetInvalidated() {
            mDataSetObservable.notifyInvalidated();
        }

        public boolean areAllItemsEnabled() {
            return true;
        }

        public boolean isEnabled(int position) {
            return true;
        }

        public int getItemViewType(int position) {
            return 0;
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean isEmpty() {
            return getCount() == 0;
        }

    }

    public void setAdapter(SnakeAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        resetSnakeLayout();

        mAdapter = adapter;

        if (mAdapter != null) {
            mItemCount = mAdapter.getCount();

            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
        requestLayout();
    }

    private void resetSnakeLayout() {
        removeAllViewsInLayout();
        invalidate();
    }

    public SnakeAdapter getAdapter() {
        return this.mAdapter;
    }
}

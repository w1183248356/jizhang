package com.viewhigh.libs.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.viewhigh.libs.R;
import com.viewhigh.libs.utils.DisplayUtil;

import static com.viewhigh.libs.utils.DisplayUtil.dip2px;

/**
 * @author wangjian
 * @title NumView
 * @description 更改数字控件
 * @modifier
 * @date
 * @since 2017/7/12 16:04
 **/
public class NumView extends View {
    private static final String TAG = "SimpleProcessView";
    private static final int SCALE_ANIMATOR_DURATION = 200;


    private int mWidth;
    private int mHeight;

    private int mRectLength;//矩形边长
    private float mRound;//矩形圆角
    private int mIntervalLength;//间隔
    private int mTextSize;//默认文字大小
    private int mTextColor;
    private Rect mBounds = new Rect();//用来计算文字宽度
    private int mTextLength;//默认可以显示文字个数
    private int mNum;//默认显示文字
    private int mRectColor;//方框颜色
    private int mChangeNum;//点击改变的大小
    private int mMinNum;//最小数字
    private int mMaxNum;//最大数字
    private boolean isStartAnima;


    private int mTextWidth;//文字宽度
    //判断是否加减true add  fase sub
    private boolean isAddOrSub;
    //是否点到加减号
    private boolean isPointToPosition;
    private Rect mAddBounds;
    private Rect mSubBounds;
    //文字画笔
    private TextPaint mTextPaint;
    //矩形画笔
    private Paint mRectPaint;
    //线
    private Paint mLinePaint;

    //点击事件
    private OnAddOrSubListener l;
    private ScaleAnimator mScaleAnimator;
    private OnNumChangeListener mOnNumChangeListener;

    public NumView(Context context) {
        this(context, null);
    }

    public NumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAtts(context, attrs);
    }


    public int getmRectLength() {
        return mRectLength;
    }

    public void setmRectLength(int mRectLength) {
        this.mRectLength = mRectLength;
    }

    public float getmRound() {
        return mRound;
    }

    public void setmRound(float mRound) {
        this.mRound = mRound;
    }

    public int getmIntervalLength() {
        return mIntervalLength;
    }

    public void setmIntervalLength(int mIntervalLength) {
        this.mIntervalLength = mIntervalLength;
    }

    public int getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getmTextLength() {
        return mTextLength;
    }

    public void setmTextLength(int mTextLength) {
        this.mTextLength = mTextLength;
    }

    public int getmNum() {
        return mNum;
    }

    public void setmNum(int mNum) {
        this.mNum = mNum;
        invalidate();
    }

    public int getmRectColor() {
        return mRectColor;
    }

    public void setmRectColor(int mRectColor) {
        this.mRectColor = mRectColor;
    }

    public int getmChangeNum() {
        return mChangeNum;
    }

    public void setmChangeNum(int mChangeNum) {
        this.mChangeNum = mChangeNum;
    }

    public int getmMinNum() {
        return mMinNum;
    }

    public void setmMinNum(int mMinNum) {
        this.mMinNum = mMinNum;
    }

    public int getmMaxNum() {
        return mMaxNum;
    }

    public void setmMaxNum(int mMaxNum) {
        this.mMaxNum = mMaxNum;
    }

    public boolean isStartAnima() {
        return isStartAnima;
    }

    public void setStartAnima(boolean startAnima) {
        isStartAnima = startAnima;
    }

    public void initAtts(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.numview);

        mTextLength = typedArray.getInt(R.styleable.numview_nvNumLength, 6);
        mTextSize = DisplayUtil.sp2px(context, typedArray.getInt(R.styleable.numview_nvNumSize, 18));
        mTextColor = typedArray.getColor(R.styleable.numview_nvNumColor, Color.BLACK);
        mNum = typedArray.getInt(R.styleable.numview_nvNum, 0);
        mChangeNum = typedArray.getInt(R.styleable.numview_nvChangeNum, 1);
        mMaxNum = typedArray.getInt(R.styleable.numview_nvMaxNum, 99999);
        mMinNum = typedArray.getInt(R.styleable.numview_nvMinNum, 0);
        mRound = typedArray.getFloat(R.styleable.numview_nvRound, dip2px(context, 2));
        mRectLength = typedArray.getDimensionPixelSize(R.styleable.numview_nvRectLength, 20);//dip2px(context, typedArray.getInt(R.styleable.numview_nvRectLength, 20));
        mIntervalLength = dip2px(context, typedArray.getInt(R.styleable.numview_nvIntervalLength, 5));
        mRectColor = typedArray.getColor(R.styleable.numview_nvRectColor, Color.BLACK);
        isStartAnima = typedArray.getBoolean(R.styleable.numview_nvIsStartAnima, false);


        //文字画笔
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        //方框画笔
        mRectPaint = new Paint();
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(dip2px(context, 0.5f));
        mRectPaint.setDither(true);
        mRectPaint.setAntiAlias(true);
        mRectPaint.setColor(mRectColor);

        //直线画笔
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(dip2px(context, 1));
        mLinePaint.setDither(true);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(mRectColor);
    }


    /**
     * 修改数字
     *
     * @param num
     */
    public void setNum(int num) {
        mNum = num;
        if (isStartAnima) {
            startScaleAnimator();
        }
        invalidate();
    }

    /**
     * 设置监听事件
     *
     * @param l
     */
    public void setOnAddOrSubListener(OnAddOrSubListener l) {
        this.l = l;
    }

    public void setOnNumChangeListener(OnNumChangeListener l) {
        mOnNumChangeListener = l;
    }

    /**
     * 获得数字
     *
     * @return
     */
    public int getNum() {
        return mNum;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;


        int smallWidth = 0;//控件最小宽度
        smallWidth += 2 * mRectLength;//宽度+矩形边长
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mTextLength; i++) {
            sb.append("0");
        }

        mTextPaint.getTextBounds(sb.toString(), 0, mTextLength, mBounds);
        mTextWidth = mBounds.width();
        smallWidth += mTextWidth + 2 * mRectLength;//文字宽度+ 两个矩形边距
        if (widthMode == MeasureSpec.EXACTLY && widthSize > smallWidth) {
            width = widthSize;
        } else {
            width = smallWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = Math.max(mRectLength, height);
        } else {
            mTextPaint.getTextBounds("0", 0, 1, mBounds);
            height = Math.max(mRectLength, mBounds.height());
        }

        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();
        Log.i(TAG, "onMeasure: width=" + width + ", height=" + height);
        mWidth = width;
        mHeight = height;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");

        int cStart = canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());

        //矩形坐标
        RectF targetRect = new RectF(0, 0, mRectLength, mRectLength);

        //减号 加号 配置
        float left = targetRect.left + mIntervalLength;
        float top = targetRect.top + mIntervalLength;
        float right = targetRect.right - mIntervalLength;
        float bottom = targetRect.bottom - mIntervalLength;
        float centerX = (targetRect.left + targetRect.right) / 2;
        float centerY = (targetRect.top + targetRect.bottom) / 2;

        //画横线
        canvas.drawLine(left, centerY, right, centerY, mLinePaint);
        //画第一个方框
        canvas.drawRoundRect(targetRect, mRound, mRound, mRectPaint);

        canvas.restoreToCount(cStart);
        cStart = canvas.save();
        canvas.translate(getPaddingLeft() + 3 * mRectLength + mTextWidth, getPaddingTop());
        //画横线
        canvas.drawLine(left, centerY, right, centerY, mLinePaint);
        //画竖线
        canvas.drawLine(centerX, top, centerX, bottom, mLinePaint);
        //2.画第二个方框
        canvas.drawRoundRect(targetRect, mRound, mRound, mRectPaint);
        //回到1处
        canvas.restoreToCount(cStart);
        canvas.translate(getPaddingLeft(), getPaddingTop());
//        Rect bounds = new Rect();
//        mTextPaint.getTextBounds("0", 0, 1, bounds);
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
//        int baseline = (targetRect.bottom + targetRect.top + bounds.height()) / 2;
//        int baseline = (targetRect.bottom + targetRect.top + fontMetricsInt.bottom - 5 * fontMetricsInt.top) / 2;
        float baseline = (targetRect.bottom + targetRect.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2;
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mNum + "", mRectLength * 2 + mTextWidth / 2, baseline, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                isPointToPosition = isRightPosition(x, y);
                break;
            case MotionEvent.ACTION_UP:
                int xUp = (int) event.getX();
                int yUp = (int) event.getY();
                boolean isInMoreUp = isRightPosition(xUp, yUp);
                if (isInMoreUp && isPointToPosition) {
                    if (l == null) {
                        if (isAddOrSub) {
                            if (mNum <= mMaxNum - mChangeNum) {
                                mNum += mChangeNum;
                                if (mOnNumChangeListener != null) {
                                    mOnNumChangeListener.onChange(mNum);
                                }
                            } else {
                                mNum = mMaxNum;
                            }
                        } else {
                            if (mNum >= mMinNum + mChangeNum) {
                                mNum -= mChangeNum;
                                if (mOnNumChangeListener != null) {
                                    mOnNumChangeListener.onChange(mNum);
                                }
                            } else {
                                mNum = mMinNum;
                            }
                        }
                        if (isStartAnima) {
                            startScaleAnimator();
                        }
                        invalidate();
                    } else {
                        if (isAddOrSub) {
                            l.onAdd(mNum);
                        } else {
                            l.onSub(mNum);
                        }
                        if (mOnNumChangeListener != null) {
                            mOnNumChangeListener.onChange(mNum);
                        }
                    }
                } else {
                    return super.onTouchEvent(event);
                }
                break;
        }
        return true;
    }

    /**
     * 是否点击到 + -号
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isRightPosition(int x, int y) {
        isPointToPosition = false;
        if (mSubBounds == null) {
            mSubBounds = new Rect();
            mSubBounds.left = getPaddingLeft();
            mSubBounds.top = getPaddingTop();
            mSubBounds.right = getPaddingLeft() + mRectLength;
            mSubBounds.bottom = getPaddingTop() + mRectLength;
        }
        if (mAddBounds == null) {
            mAddBounds = new Rect();
            mAddBounds.left = getPaddingLeft() + 3 * mRectLength + mTextWidth;
            mAddBounds.top = getPaddingTop();
            mAddBounds.right = getPaddingLeft() + 4 * mRectLength + mTextWidth;
            mAddBounds.bottom = getPaddingTop() + mRectLength;
        }
        if (isPointToPosition(x, y, mSubBounds)) {
            isAddOrSub = false;
            isPointToPosition = true;
        }
        if (isPointToPosition(x, y, mAddBounds)) {
            isAddOrSub = true;
            isPointToPosition = true;
        }
        return isPointToPosition;

    }

    public boolean isPointToPosition(int x, int y, Rect rect) {
        return x >= rect.left && x <= rect.right
                && y >= rect.top && y <= rect.bottom;
    }

    public void startScaleAnimator() {
        cancelScaleAnimator();
        mScaleAnimator = new ScaleAnimator(mTextSize, 120);
        mScaleAnimator.start();
    }

    public void cancelScaleAnimator() {
        if (mScaleAnimator != null) {
            mScaleAnimator.cancel();
            mScaleAnimator = null;
        }
    }

    private class ScaleAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {

        /**
         * 构建一个缩放动画
         * <p>
         * 从一个矩阵变换到另外一个矩阵
         *
         * @param smallSize 小字
         * @param bigSize   大字
         */
        public ScaleAnimator(int smallSize, int bigSize) {
            this(smallSize, bigSize, SCALE_ANIMATOR_DURATION);
        }

        public ScaleAnimator(int smallSize, int bigSize, long duration) {
            super();
            setFloatValues(bigSize, smallSize);
            setDuration(duration);
            addUpdateListener(this);
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) animation.getAnimatedValue();
            mTextPaint.setTextSize(value);
            invalidate();
        }
    }


    /**
     * 加减号设置监听 如何不设置用默认的
     */
    public interface OnAddOrSubListener {
        void onAdd(int num);

        void onSub(int num);
    }

    public interface OnNumChangeListener {
        void onChange(int num);
    }
}

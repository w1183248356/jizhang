package com.viewhigh.libs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.viewhigh.libs.R;
import com.viewhigh.libs.utils.DisplayUtil;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 时间轴控件
 * Created by pengchong on 2017/7/5.
 */

public class TimesLineView extends View {

    private static final int SOLID = 0;
    private static final int DASH = 1;

    private Rect mBounds = new Rect();

    //节点圆圈半径
    private float mRadius = 20;
    //节点颜色
    private int dotColor;
    //连线颜色
    private int lineColor;
    //日期文本颜色
    private int textColorDate;
    //标题文本颜色
    private int textColorTitle;
    //标题文本大小
    private int titleTextSize;
    //日期文本大小
    private int dateTextSize;
    //目前只加了虚线
    private PathEffect dashPathEffect;
    private Path mPath;
    //线型  实线0    虚线1
    private int lineMode;
    //虚线大小
    private int dashSize;
    //节点数量
    private int dotCount = 5;
    //线宽
    private int lineWidth = 6;
    //字体大小，不单独设置title和date的字体大小话，默认全为此大小
    private int textSize = 30;
    //时间轴与文字，文字与文字之间距离
    private int lineGap = 10;//行间距

    private Paint circlePaint;
    private Paint linePaint;
    private Paint textPaintTitle;
    private Paint textPaintDate;

    //标题内容集合
    private List<String> mTitles;
    //时间字符串集合
    private List<String> mDates;

    private Paint.FontMetrics fontMetrics;
    //节点图案res Id
    private int dotDrawableId;
    //节点图案Drawable
    private Drawable dotDrawable;
    private float leftSpace;
    private float eachLineLength;

    public TimesLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAtts(context, attrs);
    }

    public TimesLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAtts(context, attrs);
    }


    private void initAtts(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.timesLine);
        dotCount = typedArray.getInt(R.styleable.timesLine_dotCount, 0);
        dotColor = typedArray.getColor(R.styleable.timesLine_dotColor, Color.BLUE);
        dotDrawableId = typedArray.getResourceId(R.styleable.timesLine_dotDrawable,0);
        if(dotDrawableId!=0) {
            dotDrawable = getResources().getDrawable(dotDrawableId);
        }
        lineColor = typedArray.getColor(R.styleable.timesLine_lineColor, Color.BLUE);
        textColorTitle = typedArray.getColor(R.styleable.timesLine_textColorTitle, Color.BLACK);
        textColorDate = typedArray.getColor(R.styleable.timesLine_textColorDate, Color.BLACK);
        textSize = typedArray.getDimensionPixelSize(R.styleable.timesLine_textSize, DisplayUtil.dip2px(context,12));
        titleTextSize = typedArray.getDimensionPixelSize(R.styleable.timesLine_titleTextSize, 0);
        dateTextSize = typedArray.getDimensionPixelSize(R.styleable.timesLine_dateTextSize, 0);
        lineGap = typedArray.getDimensionPixelSize(R.styleable.timesLine_lineGap, DisplayUtil.dip2px(context,5));
        lineMode = typedArray.getInt(R.styleable.timesLine_lineMode, 0);
        lineWidth = typedArray.getDimensionPixelSize(R.styleable.timesLine_timeLineWidth, DisplayUtil.dip2px(context,1));
        mRadius = typedArray.getDimensionPixelSize(R.styleable.timesLine_dotRadius, DisplayUtil.dip2px(context,8));
        dashSize = typedArray.getDimensionPixelSize(R.styleable.timesLine_dashSize, DisplayUtil.dip2px(context,5));
        typedArray.recycle();
        dashPathEffect = new DashPathEffect(new float[]{dashSize, dashSize}, 0);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = 0;
        int h = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        initPaint();
        float titleTotalWidth = 0;
        float dateTotalWidth = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            w = widthSize;
        } else {
            if((mTitles!=null && mTitles.size()>0) || (mDates!=null && mDates.size()>0)) {
                for (int i = 0; i<(Math.max(mTitles.size(),mDates.size())); i++){
                    if(mTitles.size()>i){
                        String title = mTitles.get(i);
                        textPaintTitle.getTextBounds(title, 0, title.length(), mBounds);
                        titleTotalWidth += mBounds.width();
                    }
                    if(mDates.size()>i){
                        String date = mDates.get(i);
                        textPaintTitle.getTextBounds(date, 0, date.length(), mBounds);
                        dateTotalWidth += mBounds.width();
                    }
                }
            }
            float dotTotalWidth = mRadius * 2 * (dotCount * 2 - 1);
            float maxViewWidth = Math.max(Math.max(titleTotalWidth,dateTotalWidth),dotTotalWidth);
            w = (int) Math.max(maxViewWidth, getWidth() - getPaddingRight() - getPaddingLeft());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            h = heightSize;
        } else {
            Paint textPaintTmp = new Paint();
            textPaintTmp.setTextSize(titleTextSize == 0 ? textSize : titleTextSize);
            Paint.FontMetrics fontMetrics = textPaintTmp.getFontMetrics();
            float textHeightTitle = fontMetrics.bottom - fontMetrics.top;
            textPaintTmp.setTextSize(dateTextSize == 0 ? textSize : dateTextSize);
            fontMetrics = textPaintTmp.getFontMetrics();
            float textHeightDate = fontMetrics.bottom - fontMetrics.top;
            h = (int) Math.max(mRadius+lineGap*2, mRadius * 2 + getPaddingTop() + getPaddingBottom() + textHeightTitle + textHeightDate + lineGap * 4);
        }
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if((mTitles==null || mTitles.size() == 0) && (mDates==null || mDates.size() == 0)){
            return;
        }
        initPaint();
        float textTop = fontMetrics.top;
        float textHeight = fontMetrics.bottom - fontMetrics.top;
        float textWidth;
        leftSpace = getLeftSpace();
        eachLineLength = (getWidth() - mRadius * 2 * dotCount - leftSpace * 2) / (dotCount - 1);
        for (int i = 0; i < dotCount; i++) {
            //画圆点
            if(dotDrawableId==0) {
                circlePaint.setColor(dotColor);
                canvas.drawCircle(i * (eachLineLength + 2 * mRadius) + mRadius + leftSpace, mRadius + lineGap, mRadius, circlePaint);
            }else{
                dotDrawable.setBounds((int)(i * (eachLineLength + 2 * mRadius) + leftSpace),lineGap,(int)(i * (eachLineLength + 2 * mRadius) + leftSpace+ mRadius*2),(int)(mRadius*2 + lineGap));
                dotDrawable.draw(canvas);
            }

            //画标题文字
            if (mTitles != null && mTitles.size() > i) {
                textPaintTitle.setTextSize(titleTextSize == 0 ? textSize : titleTextSize);
                String title = mTitles.get(i);
                textPaintTitle.getTextBounds(title, 0, title.length(), mBounds);
                textWidth = mBounds.width();
                textPaintTitle.setColor(textColorTitle);
                canvas.drawText(mTitles.get(i), i * (eachLineLength + 2 * mRadius) + mRadius + leftSpace - textWidth / 2, mRadius * 2 + lineGap * 2 - textTop, textPaintTitle);
            }

            //画时间文字
            if (mDates != null && mDates.size() > i) {
                textPaintDate.setTextSize(dateTextSize == 0 ? textSize : dateTextSize);
                String date = mDates.get(i);
                textPaintDate.getTextBounds(date, 0, date.length(), mBounds);
                textWidth = mBounds.width();
                textPaintDate.setColor(textColorDate);
                canvas.drawText(mDates.get(i), i * (eachLineLength + 2 * mRadius) + mRadius + leftSpace - textWidth / 2, mRadius * 2 + lineGap * 2 - textTop + textHeight + lineGap, textPaintDate);
            }

            //画连线
            if (i > 0) {
                mPath.moveTo((i - 1) * (eachLineLength + 2 * mRadius) + mRadius * 2 + leftSpace, mRadius + lineGap);
                mPath.lineTo(i * (eachLineLength + 2 * mRadius) + leftSpace, mRadius + lineGap);
                canvas.drawPath(mPath, linePaint);
            }
        }
    }

    private void initPaint() {
        if (circlePaint == null) {
            circlePaint = new Paint();
            circlePaint.setAntiAlias(true);
            circlePaint.setStrokeWidth(0);
            circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        if (linePaint == null) {
            linePaint = new Paint();
            linePaint.setAntiAlias(true);
            linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            linePaint.setStrokeWidth(lineWidth);
            linePaint.setColor(lineColor);
            if (lineMode == DASH) {
                linePaint.setPathEffect(dashPathEffect);
            }
        }

        if (textPaintTitle == null) {
            textPaintTitle = new Paint();
            textPaintTitle.setColor(textColorDate);
            textPaintTitle.setTextSize(titleTextSize == 0 ? textSize : titleTextSize);
            fontMetrics = textPaintTitle.getFontMetrics();
        }

        if (textPaintDate == null) {
            textPaintDate = new Paint();
            textPaintDate.setColor(textColorDate);
            textPaintDate.setTextSize(dateTextSize == 0 ? textSize : dateTextSize);
            fontMetrics = textPaintDate.getFontMetrics();
        }
    }

    private float getLeftSpace() {
        float textWidthTmp = 0;

        if (mTitles != null && mTitles.size() > 0) {
            String tmpTitle = mTitles.get(0);
            textPaintTitle.getTextBounds(tmpTitle, 0, tmpTitle.length(), mBounds);
            textWidthTmp = Math.max(textWidthTmp, mBounds.width());
        }
        if (mDates != null && mDates.size() > 0) {
            String tmpDate = mDates.get(0);
            textPaintDate.getTextBounds(tmpDate, 0, tmpDate.length(), mBounds);
            textWidthTmp = Math.max(textWidthTmp, mBounds.width());
        }
        return Math.max(textWidthTmp / 2 - mRadius + lineGap, lineGap);
    }

    public int getDotCount() {
        return dotCount;
    }

    public void setDotCount(int dotCount) {
        this.dotCount = dotCount;
    }
    public float getmRadius() {
        return mRadius;
    }

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
        requestLayout();
        invalidate();
    }

    public int getDotColor() {
        return dotColor;
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
        invalidate();
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        invalidate();
    }

    public int getTextColorDate() {
        return textColorDate;
    }

    public void setTextColorDate(int textColorDate) {
        this.textColorDate = textColorDate;
        invalidate();
    }

    public int getTextColorTitle() {
        return textColorTitle;
    }

    public void setTextColorTitle(int textColorTitle) {
        this.textColorTitle = textColorTitle;
        invalidate();
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        requestLayout();
        invalidate();
    }

    public int getDateTextSize() {
        return dateTextSize;
    }

    public void setDateTextSize(int dateTextSize) {
        this.dateTextSize = dateTextSize;
        requestLayout();
        invalidate();
    }

    public int getLineMode() {
        return lineMode;
    }

    public void setLineMode(int lineMode) {
        this.lineMode = lineMode;
        invalidate();
    }

    public int getDashSize() {
        return dashSize;
    }

    public void setDashSize(int dashSize) {
        this.dashSize = dashSize;
        invalidate();
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        invalidate();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        requestLayout();
        invalidate();
    }

    public int getLineGap() {
        return lineGap;
    }

    public void setLineGap(int lineGap) {
        this.lineGap = lineGap;
        requestLayout();
        invalidate();
    }

    public void setTitles(List<String> mTitles) {
        this.mTitles = mTitles;
        requestLayout();
//        invalidate();
    }

    public void setDates(List<String> mDates) {
        this.mDates = mDates;
        requestLayout();
//        invalidate();
    }

    public List<String> getmTitles() {
        return mTitles;
    }

    public List<String> getmDates() {
        return mDates;
    }
}

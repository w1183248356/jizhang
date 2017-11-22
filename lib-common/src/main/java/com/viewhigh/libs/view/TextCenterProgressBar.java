package com.viewhigh.libs.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by huntero on 16-11-8.
 */

public class TextCenterProgressBar extends ProgressBar {

    Paint mPaint;
    String text;

    public TextCenterProgressBar(Context context) {
        this(context, null);
    }

    public TextCenterProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextCenterProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xFF4B4B4B);
    }

    @Override
    public synchronized void setProgress(int progress) {
        setTextProgress(progress);
        super.setProgress(progress);
    }


    @Override
    public synchronized void setMax(int max) {
        setTextMax(max);
        super.setMax(max);
    }
    private void setTextProgress(int progress) {
        setText(progress, getMax());
    }

    private void setTextMax(int max) {
        setText(getProgress(), max);
    }

    private void setText(int progress, int max) {
        this.text = progress + "/" + max;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(this.text)) {
            //文字为空
            return;
        }
        this.mPaint.setTextSize(getHeight() - 8);

        final Rect bounds = new Rect();
        this.mPaint.getTextBounds(text, 0, text.length(), bounds);
        final int offsetX = (getWidth() / 2) - bounds.centerX();
        final int offsetY = (getHeight() / 2) - bounds.centerY();
        canvas.drawText(this.text, offsetX, offsetY, this.mPaint);
    }
}

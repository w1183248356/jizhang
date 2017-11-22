package com.viewhigh.libs.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by huntero on 17-1-22.
 */

public class DrawableCenterTextView extends TextView {

    public DrawableCenterTextView(Context context) {
        super(context);
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取设置的图片
        Drawable[] drawables = getCompoundDrawables();
        int drawablePadding = 0;
        int drawableWidth = 0;
        if (drawables != null) {
            //第一个是left
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                drawablePadding = getCompoundDrawablePadding();
                drawableWidth = drawableLeft.getIntrinsicWidth();
            }
        }

        //获取文字的宽度
        float textWidth = getPaint().measureText(getText().toString());
        float bodyWidth = textWidth + drawableWidth + drawablePadding;
        int y = getWidth() - getPaddingLeft() - getPaddingRight();
        canvas.translate((y - bodyWidth) / 2, 0);

        super.onDraw(canvas);
    }
}

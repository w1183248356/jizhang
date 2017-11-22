package com.viewhigh.libs.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viewhigh.libs.R;

/**
 * Created by huntero on 16-10-27.
 */

public class PicknoteProgressAndTextLayout extends RelativeLayout {
    private TextView mTextView;
    private ProgressBar mProgress;
    private int max;
    private int progress;
    public PicknoteProgressAndTextLayout(Context context) {
        this(context, null);
    }

    public PicknoteProgressAndTextLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PicknoteProgressAndTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = null;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressAndText);
        final int d = a.getInt(R.styleable.ProgressAndText_progressDrawable, 1);
        if (d == 2) {
            v = inflater.inflate(R.layout.view_progress_yellow_layout, this, true);
        }else{
            v = inflater.inflate(R.layout.view_progress_blue_layout, this, true);
        }

        mTextView = (TextView) v.findViewById(R.id.text);
        mProgress = (ProgressBar) v.findViewById(R.id.progressBar);
        final ColorStateList textColor = a.getColorStateList(R.styleable.ProgressAndText_textColor);
        if (textColor != null) {
            mTextView.setTextColor(textColor);
        }
        a.recycle();
    }

    public void setMax(int max){
        this.max = max;
    }
    public void setProgress(int progress){
        if (progress < 0) {
            progress = 0;
        }
        if (progress > max) {
            progress = max;
        }
        this.progress = progress;
        update();
    }

    private void update() {
        this.mProgress.setMax(this.max);
        this.mProgress.setProgress(this.progress);
        this.mTextView.setText(this.progress + "/" + this.max);
    }
}

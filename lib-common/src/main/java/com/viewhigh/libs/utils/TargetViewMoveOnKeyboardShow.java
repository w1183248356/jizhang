package com.viewhigh.libs.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by wangjian on 2017/10/17.
 * 键盘弹出如果view被遮盖则上移布局
 */

public class TargetViewMoveOnKeyboardShow {

    private ValueAnimator mAnimator;
    private int animaNowY;
    private int mBtnBottom;
    private int mYMove;
    private int mOldYMove;
    private boolean isAnimaStart;//动画是否开启
    private boolean isAnimaRequestLayout;//是否动画最后一次刷新界面
    int rectBottom = -1;

    private View mDecorView;
    private View rootView;

    /**
     * @param activity
     * @param rootView 根布局
     */
    public TargetViewMoveOnKeyboardShow(Activity activity, View rootView) {
        this.mDecorView = activity.getWindow().getDecorView();
        this.rootView = rootView;
    }


    public void bindingView(final View moveView) {
        mDecorView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                targetViewMove(bottom);
            }
        });

        moveView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mBtnBottom = bottom;
            }
        });
    }

    private void anima(int oldValue, int value) {
        mAnimator = ValueAnimator.ofInt(oldValue, value);
        mAnimator.setDuration(300);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int v = (int) animation.getAnimatedValue();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rootView.getLayoutParams();
                params.topMargin = v;
                animaNowY = v;
                rootView.requestLayout();
                mDecorView.requestLayout();
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimaStart = true;
                isAnimaRequestLayout = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimaStart = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();
    }

    private void targetViewMove(int bottom) {
        //获取View可见区域的bottom
        Rect rect = new Rect();
        mDecorView.getWindowVisibleDisplayFrame(rect);
        //这里是软键盘只有一个状态的时候判断动画执行状态不判断是否隐藏
        //为了判断部分机型切换中英文输入法先隐藏软键盘在弹出的情况
        if (rect.bottom == rectBottom || rectBottom == -1) {
            if (isAnimaStart) return;
            if (!isAnimaStart && isAnimaRequestLayout) {
                isAnimaRequestLayout = false;
                return;
            }
        }
        //隐藏  这里判断bottom - rect.bottom <= bottom /12
        // 是因为考虑到部分机型虚拟按键 bottom - rect.bottom不一定为0
        if (bottom != 0 && bottom - rect.bottom <= bottom / 12) {
            mYMove = 0;
        } else {
            //如果可见区域的坐标大于不能遮盖view的坐标则不移动
            if (rect.bottom - mBtnBottom > 0) {
                mYMove = rect.bottom - mBtnBottom;
            }
        }
        if (isAnimaStart) {
            mAnimator.cancel();
            anima(animaNowY, mYMove);
            mOldYMove = mYMove;
        } else {
            if (mOldYMove != mYMove) {
                anima(mOldYMove, mYMove);
                mOldYMove = mYMove;
            }
        }
        rectBottom = rect.bottom;
    }
}

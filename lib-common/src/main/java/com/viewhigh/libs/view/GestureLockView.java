package com.viewhigh.libs.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.viewhigh.libs.R;
import com.viewhigh.libs.utils.DisplayUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peng on 17-8-15.
 */

public class GestureLockView extends View {
    private static final String TAG = "GestureLockView";
    private static final String TAG_INPUTTIMES = "inputTimes";
    private Context mContext;
    private int lines;//列数
    private int rows;//行数
    private int radiusCircle;
    private int circleStroke;
    private int circleStroke_pressed;
    private int radiusDot;
    private Paint circlePaint;
    private Paint dotPaint;
    private List<ItemIcon> items;
    private List<ItemIcon> pressedItems;
    private Paint circlePaint_pressed;
    private ItemIcon gestureItem;
    private Paint linePaint_pressed;
    private Paint dotPaint_error;
    private Paint linePaint_error;
    private Paint circlePaint_error;
    private OnCheckResultListener onCheckResultListener;
    private SharedPreferences sp;
    private String passWord;
    private boolean isError = false;
    private boolean isDelaying;
    private int smallestPoints = 4;
    private int circleColorNormal;
    private int circleColorPressed;
    private int circleColorError;
    private int dotColorPressed;
    private int dotColorError;
    private int lineColorPressed;
    private int lineColorError;
    private int lineWidth;

    public GestureLockView(Context context) {
        this(context, null);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAtts(context, attrs);
        init();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isDelaying = false;
            if (msg.what == 1) {//重置状态
                reset();
            }
            super.handleMessage(msg);
        }
    };

    private void initAtts(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.gestureLock);
        rows = typedArray.getInt(R.styleable.gestureLock_gl_rows, 3);
        lines = typedArray.getColor(R.styleable.gestureLock_gl_lines, 3);
        radiusCircle = typedArray.getDimensionPixelSize(R.styleable.gestureLock_gl_radiusCircle, DisplayUtil.dip2px(mContext,30));
        radiusDot = typedArray.getDimensionPixelSize(R.styleable.gestureLock_gl_radiusDot,DisplayUtil.dip2px(mContext,8));
        smallestPoints = typedArray.getInt(R.styleable.gestureLock_gl_smallestPoints, 4);
        circleColorNormal = typedArray.getColor(R.styleable.gestureLock_gl_circle_color_normal, getResources().getColor(android.R.color.holo_blue_dark));
        circleColorPressed = typedArray.getColor(R.styleable.gestureLock_gl_circle_color_pressed, getResources().getColor(android.R.color.holo_blue_bright));
        circleColorError = typedArray.getColor(R.styleable.gestureLock_gl_circle_color_error, getResources().getColor(android.R.color.holo_red_light));
        dotColorPressed = typedArray.getColor(R.styleable.gestureLock_gl_dot_color_pressed, getResources().getColor(android.R.color.holo_green_light));
        dotColorError = typedArray.getColor(R.styleable.gestureLock_gl_dot_color_error, getResources().getColor(android.R.color.holo_red_light));
        lineColorPressed = typedArray.getColor(R.styleable.gestureLock_gl_line_color_pressed, getResources().getColor(android.R.color.holo_green_light));
        lineColorError = typedArray.getColor(R.styleable.gestureLock_gl_line_color_error,getResources().getColor(android.R.color.holo_red_light));
        circleStroke = typedArray.getDimensionPixelSize(R.styleable.gestureLock_gl_circle_stroke, DisplayUtil.dip2px(mContext,1));
        circleStroke_pressed = typedArray.getDimensionPixelSize(R.styleable.gestureLock_gl_circle_stroke_pressed, circleStroke*2);
        lineWidth = typedArray.getDimensionPixelSize(R.styleable.gestureLock_gl_line_width, radiusDot);
    }

    private void init() {

        sp = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);

        //圈圈的画笔
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circleStroke);
        circlePaint.setColor(circleColorNormal);

        //按下后的圈圈的画笔
        circlePaint_pressed = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint_pressed.setStyle(Paint.Style.STROKE);
        circlePaint_pressed.setStrokeWidth(circleStroke_pressed);
        circlePaint_pressed.setColor(circleColorPressed);

        //错误后的圈圈的画笔
        circlePaint_error = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint_error.setStyle(Paint.Style.STROKE);
        circlePaint_error.setStrokeWidth(circleStroke_pressed);
        circlePaint_error.setColor(circleColorError);

        //按下后连线的画笔
        linePaint_pressed = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint_pressed.setStrokeWidth(lineWidth);
        linePaint_pressed.setColor(lineColorPressed);

        //错误后连线画笔
        linePaint_error = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint_error.setStrokeWidth(lineWidth);
        linePaint_error.setColor(lineColorError);

        //按下后中间点点的画笔
        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(dotColorPressed);

        //错误后中间点点的画笔
        dotPaint_error = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint_error.setColor(dotColorError);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float totalWidth = getMeasuredWidth();
        float totalHeight = getMeasuredHeight();
        float pointsWidth = totalWidth - 2 * radiusCircle - getPaddingLeft() - getPaddingRight();
        float pointsHeight = totalHeight - 2 * radiusCircle - getPaddingTop() - getPaddingBottom();
        //两点间距离
        float pointDistance = Math.min(pointsWidth / (lines - 1), pointsHeight / (rows - 1));

        if(pointDistance < radiusCircle*2.5){
            radiusCircle = (int) (pointDistance/2.5);
        }
        if(radiusDot>radiusCircle/2){
            radiusDot = radiusCircle/2;
        }
        if(lineWidth>radiusDot){
            lineWidth = radiusDot;
            linePaint_pressed.setStrokeWidth(lineWidth);
            linePaint_error.setStrokeWidth(lineWidth);
        }

        float startPointX = (totalWidth - pointDistance * (lines - 1)) / 2;
        float startPointY = (totalHeight - pointDistance * (rows - 1)) / 2;
        items = new ArrayList<>();
        pressedItems = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < lines; j++) {
                Point point = new Point((int) (startPointX + j * pointDistance), (int) (startPointY + i * pointDistance));
                ItemIcon itemIcon = new ItemIcon();
                itemIcon.setPoint(point);
                itemIcon.setCode(String.valueOf(i) + j);
                items.add(itemIcon);
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        //画圆圈
        for (int i = 0; i < items.size(); i++) {
            ItemIcon itemIcon = items.get(i);
            Point point = itemIcon.getPoint();
            if (itemIcon.isPressed) {
                if (isError) {
                    canvas.drawCircle(point.x, point.y, radiusCircle - circleStroke_pressed / 2, circlePaint_error);
                    canvas.drawCircle(point.x, point.y, radiusDot, dotPaint_error);
                } else {
                    canvas.drawCircle(point.x, point.y, radiusCircle - circleStroke_pressed / 2, circlePaint_pressed);
                    canvas.drawCircle(point.x, point.y, radiusDot, dotPaint);
                }
            } else {
                canvas.drawCircle(point.x, point.y, radiusCircle - circleStroke_pressed / 2, circlePaint);
            }
        }

        //画线
        if (pressedItems != null && pressedItems.size() > 1) {
            for (int i = 1; i < pressedItems.size(); i++) {
                Point start = pressedItems.get(i - 1).getPoint();
                Point end = pressedItems.get(i).getPoint();
                if (isError) {
                    canvas.drawLine(start.x, start.y, end.x, end.y, linePaint_error);
                } else {
                    canvas.drawLine(start.x, start.y, end.x, end.y, linePaint_pressed);
                }
            }
        }

        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX;
        float eventY;
        if(isDelaying){
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                eventX = event.getX();
                eventY = event.getY();
                Point point = new Point((int) eventX, (int) eventY);
                changeBg(point);
                break;

            case MotionEvent.ACTION_UP:
                removeGestureItem();
                checkResult();
                break;
        }
        return true;
    }

    //输入完毕后，移除pressedItems中的gestureItem
    private void removeGestureItem() {
        if (pressedItems != null && pressedItems.contains(gestureItem)) {
            pressedItems.remove(gestureItem);
        }
    }

    //如果没有保存密码，为设置密码
    //如果有保存密码，检查手势结果，是否与保存的密码相同
    private void checkResult() {

        //获取手势结果
        String gesture = "";
        for (ItemIcon itemIcon : pressedItems) {
            gesture += itemIcon.getCode();
        }
        gesture = getMd5(gesture);
        int inputTimes = sp.getInt(TAG_INPUTTIMES,0);
        if(inputTimes < 2){//正在设置密码
            if(pressedItems.size()<smallestPoints){
                Toast.makeText(mContext, "密码设置过短，最少需要连接"+smallestPoints+"个点", Toast.LENGTH_SHORT).show();
                isDelaying = true;
                mHandler.sendEmptyMessageDelayed(1,500);//延迟重置
                return;
            }
            if(inputTimes == 0){
                sp.edit().putString(TAG,gesture).apply();
                sp.edit().putInt(TAG_INPUTTIMES,++inputTimes).apply();
                Toast.makeText(mContext, "请再设置一次", Toast.LENGTH_SHORT).show();
            }else if(inputTimes == 1){
                String savedPsw = sp.getString(TAG,"");
                if(gesture.equals(savedPsw)) {
                    sp.edit().putInt(TAG_INPUTTIMES,++inputTimes).apply();
                    Toast.makeText(mContext, "设置成功", Toast.LENGTH_SHORT).show();
                    passWord = gesture;
                    onCheckResultListener.needInitPassword(false);
                }else{
                    isError = true;
                    invalidate();
                    Toast.makeText(mContext, "两次设置的密码不同，请重新设置", Toast.LENGTH_SHORT).show();
                    resetPsw();
                }
            }
            isDelaying = true;
            mHandler.sendEmptyMessageDelayed(1,500);//延迟重置
            return;
        }

        //比对
        if (gesture.equals(passWord)) {
            if (onCheckResultListener != null) {
                onCheckResultListener.checkSucc();
            }
        } else {
            isError = true;
            invalidate();
            if (onCheckResultListener != null) {
                onCheckResultListener.checkFail();
            }
        }
        isDelaying = true;
        mHandler.sendEmptyMessageDelayed(1,800);//延迟重置

    }

    private void reset() {
        for (ItemIcon itemIcon : items) {
            itemIcon.setPressed(false);
        }
        pressedItems.clear();
        isError = false;
        invalidate();
    }

    //根据触摸点与各个圆圈的中心点距离，设置对应状态
    private void changeBg(Point point) {
        if (gestureItem == null) {
            gestureItem = new ItemIcon();
            gestureItem.setPoint(new Point(point.x, point.y));
        }
        Point gesturePoint = gestureItem.getPoint();
        gesturePoint.set(point.x, point.y);
        gestureItem.setPoint(gesturePoint);
        for (ItemIcon itemIcon : items) {
            Point itemPoint = itemIcon.getPoint();
            int dis = getDistanceBetweenTwoPoints(itemPoint, point);
            if (dis <= radiusCircle) {
                itemIcon.setPressed(true);
                addToPressedItems(itemIcon);
            } else {
                addToPressedItems(gestureItem);
            }
        }
        invalidate();
    }

    //添加到列表，这个列表是选中过的所有item和当前触摸点的集合，用来画线和得到密码结果，Action_Up后移除触摸点的Item
    private void addToPressedItems(ItemIcon itemIcon) {
        if (itemIcon == gestureItem || !pressedItems.contains(itemIcon)) {
            if (pressedItems.contains(gestureItem)) {
                pressedItems.set(pressedItems.size() - 1, itemIcon);
            } else {
                pressedItems.add(itemIcon);
            }
        }
    }

    //获取两点间距离，用来与圆圈半径比较
    private int getDistanceBetweenTwoPoints(Point itemPoint, Point point) {

        int xDis = itemPoint.x - point.x;
        int yDis = itemPoint.y - point.y;
        int dis = (int) Math.sqrt(xDis * xDis + yDis * yDis);
        return dis;
    }

    //清空保存的密码，可以重新设置
    public void resetPsw() {
        if(sp!=null) {
            sp.edit().putString(TAG, "").apply();
            sp.edit().putInt(TAG_INPUTTIMES, 0).apply();
            onCheckResultListener.needInitPassword(true);
        }
    }

    class ItemIcon {

        private Point point;
        private String code;
        private boolean isPressed = false;

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public boolean isPressed() {
            return isPressed;
        }

        public void setPressed(boolean pressed) {
            isPressed = pressed;
        }
    }

    public interface OnCheckResultListener {
        //是否需要设置密码，如果存储的为空，则需要设置，用于修改界面提示语
        void needInitPassword(boolean need);

        //验证成功
        void checkSucc();

        //验证失败
        void checkFail();
    }

    public void setOnCheckResultListener(OnCheckResultListener onCheckResultListener) {
        this.onCheckResultListener = onCheckResultListener;
        if (TextUtils.isEmpty(sp.getString(TAG, null))) {
            if (onCheckResultListener != null) {
                onCheckResultListener.needInitPassword(true);
            }
        } else {
            if (onCheckResultListener != null) {
                onCheckResultListener.needInitPassword(false);
            }
            passWord = sp.getString(TAG, null);
        }
    }

    public static String getMd5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

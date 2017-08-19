package com.mj.customview.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mj.customview.R;

/**
 * Created by MengJie on 2017/8/8.
 */

public class CustomView3 extends View {

    private static final int DEF_FIRST_COLOR = 0xffFF4081;
    private static final int DEF_SECOND_COLOR = 0xff303F9F;
    private static final float DEF_STROKE_WIDTH_DIP = 1;
    private static final int DEF_SPEED = 20;

    private int mFirstColor;
    private int mSecondColor;
    private int mStrokeWidth;
    private int mSpeed;
    private Paint mPaint;
    private float mProgress;
    private boolean isNext;
    private RectF oval;

    public CustomView3(Context context) {
        this(context, null, 0);
    }

    public CustomView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取TypeArray
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView3, defStyleAttr, 0);

        try {
            mFirstColor = ta.getColor(R.styleable.CustomView3_firstColor, DEF_FIRST_COLOR);
            mSecondColor = ta.getColor(R.styleable.CustomView3_secondColor, DEF_SECOND_COLOR);
            int defValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_STROKE_WIDTH_DIP, getResources().getDisplayMetrics());
            mStrokeWidth = ta.getDimensionPixelSize(R.styleable.CustomView3_strokeWidth, defValue);
            mSpeed = ta.getInt(R.styleable.CustomView3_speed, DEF_SPEED);
        } finally {
            ta.recycle();
        }

        //初始化
        init(context);

    }

    private void init(Context context) {
        mPaint = new Paint();
        oval = new RectF();

        new Thread(){
            @Override
            public void run() {
                while (true) {
                    mProgress ++;
                    if (mProgress >= 360) {
                        mProgress = 0;
                        isNext = !isNext;
                    }
                    // 刷新view
                    postInvalidate();

                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int radius = cx - mStrokeWidth /2;
        mPaint.setStrokeWidth(mStrokeWidth);// 设置圆环的宽度
        mPaint.setAntiAlias(true);// 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);// 圆环样式

        // 圆环
        oval.left = cx - radius;
        oval.top = cy - radius;
        oval.right = cx + radius;
        oval.bottom = cy + radius;

        if (isNext) {
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(cx, cy, radius, mPaint);// 画圆环
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);// 画弧线
        } else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(cx, cy, radius, mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }
    }
}

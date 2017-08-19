package com.mj.customview.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mj.customview.R;

/**
 * Created by MengJie on 2017/7/31.
 */

public class CustomView1 extends View {

    private String mText = "hello";
    private float mTextSize = 16;
    private int mTextColor = 0xff757575;
    private Paint mPaint;
    private Rect mBounds;

    public CustomView1(Context context) {
        super(context);
        init(context);
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView1, 0, 0);

        try {
            mText = ta.getString(R.styleable.CustomView1_text);
            mTextSize = ta.getDimension(R.styleable.CustomView1_textSize, 16f);
            mTextColor = ta.getColor(R.styleable.CustomView1_textColor, 0xff757575);
        } finally {
            ta.recycle();
        }

        init(context);
    }

    private void init(Context context) {
        //paint
        mPaint = new Paint();

        //bounds
        mBounds = new Rect();

        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mText, 0, mText.length(), mBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if (wMode == MeasureSpec.EXACTLY) {
            width = w;
        }else{
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBounds);
            int textWidth = mBounds.width();
            width = getPaddingLeft() + textWidth + getPaddingRight();
        }

        if (hMode == MeasureSpec.EXACTLY) {
            height = h;
        }else{
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBounds);
            int textHeight = mBounds.height();
            height = getPaddingTop() + textHeight + getPaddingBottom();
        }

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTextColor);
        canvas.drawText(mText, getWidth() / 2 - mBounds.width() / 2, getHeight() / 2 + mBounds.height() / 2, mPaint);

    }

}

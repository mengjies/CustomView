package com.mj.customview.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.mj.customview.R;

/**
 * Created by MengJie on 2017/8/8.
 */

public class CustomView4 extends View {
    private static final int DEF_FIRST_COLOR = 0xff757575;
    private static final int DEF_SECOND_COLOR = 0xffFF4081;
    private static final float DEF_STROKE_WIDTH_DIP = 5.0f;
    private static final float DEF_DOT_SPACE = 10;
    private static final int DEF_DOT_COUNT = 12;
    private int mFirstColor;
    private int mSecondColor;
    private float mStrokeWidth;
    private int mDotSpace;
    private int mDotCount;
    private Bitmap mImage;
    private Paint mPaint;
    private RectF mOval;
    private Rect mRect;
    private int mCurrentCount = 6;

    public CustomView4(Context context) {
        this(context, null, 0);
    }

    public CustomView4(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TypeArray
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView4, defStyleAttr, 0);

        try {
            mFirstColor = ta.getColor(R.styleable.CustomView4_firstColor, DEF_FIRST_COLOR);
            mSecondColor = ta.getColor(R.styleable.CustomView4_secondColor, DEF_SECOND_COLOR);
            int defStrokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_STROKE_WIDTH_DIP, getResources().getDisplayMetrics());
            mStrokeWidth = ta.getDimensionPixelSize(R.styleable.CustomView4_strokeWidth, defStrokeWidth);
            mDotSpace = ta.getInt(R.styleable.CustomView4_dotSpace, (int) DEF_DOT_SPACE);
            mDotCount = ta.getInt(R.styleable.CustomView4_dotCount, DEF_DOT_COUNT);
            mImage = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.CustomView4_bg, 0));
        } finally {
            ta.recycle();
        }

        init(context);
    }

    private void init(Context context) {
        //paint
        mPaint = new Paint();
        //rect drawable
        mRect = new Rect();
        //oval arc
        mOval = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //drawArc
        int itemSize = 360 / mDotCount;
        float dotSize = itemSize - mDotSpace;

        int cx = getWidth()/2;
        int cy = getHeight()/2;
        int radius = (int) (cx - mStrokeWidth/2);

        mOval.left = cx - radius;
        mOval.top = cy - radius;
        mOval.right = cx + radius;
        mOval.bottom = cy + radius;

        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        mPaint.setColor(mFirstColor);
        for(int i = 0; i < mDotCount; i++) {
            canvas.drawArc(mOval, -90 + mDotSpace/2 + i*itemSize, dotSize, false, mPaint);
        }

        mPaint.setColor(mSecondColor);
        for (int j = 0; j < mCurrentCount; j++) {
            canvas.drawArc(mOval, -90 + mDotSpace/2 + j*itemSize, dotSize, false, mPaint);
        }

        //drawBitmap
        int rectRadius = (int) ((radius - mStrokeWidth) * Math.sqrt(2)/2);
        mRect.left = radius - rectRadius;
        mRect.top = radius - rectRadius;
        mRect.right = radius + rectRadius;
        mRect.bottom = radius + rectRadius;

        if (mImage != null) {
            int imageRadius = mImage.getWidth();
            if (imageRadius < rectRadius) {
                mRect.left = radius - imageRadius;
                mRect.top = radius - imageRadius;
                mRect.right = radius + imageRadius;
                mRect.bottom = radius + imageRadius;
            }
            canvas.drawBitmap(mImage, null, mRect, mPaint);
        }

    }

    private int yDown, yUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                yUp = (int) event.getY();
                if (yUp > yDown) {
                    onDown();
                }else{
                    onUp();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void onUp() {
        if (mCurrentCount > 0) {
            mCurrentCount --;
        }
        postInvalidate();
    }

    private void onDown() {
        if (mCurrentCount <= mDotCount) {
            mCurrentCount ++;
        }
        postInvalidate();
    }
}

package com.mj.customview.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mj.customview.R;

/**
 * Created by MengJie on 2017/8/1.
 */

public class CustomView2 extends View {

    private static final int IMAGE_SCALE_FITXY = 0;
    //params
    private String mText = "";
    private float mTextSize = 16;
    private int mTextColor = 0xff757575;
    private Bitmap mImage;
    //
    private Rect rect;
    private Paint mPaint;
    private Rect mTextBounds;
    //width and height
    private int mWidth;
    private int mHeight;
    private int mImageScale;

    public CustomView2(Context context) {
        this(context, null, 0);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView2, defStyleAttr, 0);
        try {
            mText = ta.getString(R.styleable.CustomView2_text);
            mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
            mTextSize = ta.getDimensionPixelSize(R.styleable.CustomView2_textSize, (int) mTextSize);
            mTextColor = ta.getColor(R.styleable.CustomView2_textColor, mTextColor);
            mImage = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.CustomView2_image, 0));
            mImageScale = ta.getInt(R.styleable.CustomView2_imageScaleType, 0);
        } finally {
            ta.recycle();
        }

        init(context);
    }

    private void init(Context context) {
        rect = new Rect();
        mPaint = new Paint();
        mTextBounds = new Rect();

        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int wImage = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
        int wText = getPaddingLeft() + getPaddingRight() + mTextBounds.width();
        int width = Math.max(wImage, wText);

        switch (wMode) {
            case MeasureSpec.EXACTLY:
                mWidth = w;
                break;
            case MeasureSpec.AT_MOST:
                mWidth = Math.min(width, w);
                break;
            case MeasureSpec.UNSPECIFIED:
                mWidth = width;
                break;
            default:
                break;
        }

        int height = getPaddingTop() + getPaddingBottom() + mTextBounds.height() + mImage.getHeight();

        switch (hMode) {
            case MeasureSpec.EXACTLY:
                mHeight = h;
                break;
            case MeasureSpec.AT_MOST:
                mHeight = Math.min(height, h);
                break;
            case MeasureSpec.UNSPECIFIED:
                mHeight = height;
                break;
            default:
                break;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        //drawRect
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(),mPaint);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        //drawText
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        if (mTextBounds.width() > mWidth) {
            // 末尾使用...
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mText, paint, mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg,getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        }else{
            // 字体居中
            canvas.drawText(mText, mWidth/2 - mTextBounds.width()/2, mHeight - getPaddingBottom(), mPaint);
        }

        //drawDrawable
        //减去字体高度
        rect.bottom -= mTextBounds.height();
        if (mImageScale == IMAGE_SCALE_FITXY) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }else{
            //计算居中的矩形范围
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mTextBounds.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mTextBounds.height()) / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, rect, mPaint);
        }

    }
}

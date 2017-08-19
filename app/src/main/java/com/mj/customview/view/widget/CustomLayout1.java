package com.mj.customview.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by MengJie on 2017/8/9.
 */

public class CustomLayout1 extends ViewGroup {
    public CustomLayout1(Context context) {
        this(context, null, 0);
    }

    public CustomLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高
     *
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;

        //计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //child count
        int cCount = getChildCount();
        int cw = 0;
        int ch = 0;
        MarginLayoutParams cParams = null;

        //计算出child中的最大值
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();
            cParams = (MarginLayoutParams) child.getLayoutParams();

            cWidth = cWidth + cParams.leftMargin + cParams.rightMargin;
            cHeight = cHeight + cParams.topMargin + cParams.bottomMargin;

            cw = Math.max(cw, cWidth);
            ch = Math.max(ch, cHeight);
        }

        //wMode
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                width = w;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(w, cw);
                break;
            case MeasureSpec.UNSPECIFIED:
                width = cw;
                break;
            default:
                break;
        }

        //hMode
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                height = h;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(h, ch);
                break;
            case MeasureSpec.UNSPECIFIED:
                height = ch;
                break;
            default:
                break;
        }

        //setMeasureDimension
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (!b) {
            return;
        }

        int cCount = getChildCount();
        MarginLayoutParams cParams;
        int cWidth = 0;
        int cHeight = 0;
        int cl = 0, ct = 0, cr = 0, cb = 0;

        for (int j = 0; j < cCount; j++) {
            View child = getChildAt(j);
            cWidth = child.getMeasuredWidth();
            cHeight = child.getMeasuredHeight();
            cParams = (MarginLayoutParams) child.getLayoutParams();

            cl = cParams.leftMargin;
            ct = cParams.topMargin;
            cr = cl + cWidth;
            cb = ct + cHeight;

            child.layout(cl, ct, cr, cb);
        }
    }
}

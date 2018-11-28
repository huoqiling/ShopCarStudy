package com.zhangxin.study.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.zhangxin.study.utils.CommonUtils;

/**
 * 设置最高显示的recyclerView
 */
public class MaxHeightRecyclerView extends RecyclerView {

    private int maxHeight = 350;

    public MaxHeightRecyclerView(Context context) {
        this(context, null);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        maxHeight = CommonUtils.dip2px(context, maxHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() > 0) {
            int height;
            View child = getChildAt(0);
            RecyclerView.LayoutParams params = (LayoutParams) child.getLayoutParams();
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int max = getAdapter().getItemCount() * (child.getMeasuredHeight() + getPaddingTop() + getPaddingBottom() + params.topMargin + params
                    .bottomMargin);
            height = Math.min(max, maxHeight);
            setMeasuredDimension(widthMeasureSpec, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

package com.zhangxin.study.base;


import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author: ZhangXin
 * @time: 2017/4/7 17:33
 * @description: 自定义view基类
 */
public class BaseFrameLayout extends FrameLayout {

    public Context mContext;

    public BaseFrameLayout(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    public void showView(@NonNull View view) {
        view.setVisibility(View.VISIBLE);
    }

    public void goneView(@NonNull View view) {
        view.setVisibility(View.GONE);
    }

    public void invisibleView(@NonNull View view) {
        view.setVisibility(View.INVISIBLE);
    }

    /**
     * 获取str资源
     *
     * @param strResId
     * @return
     */
    public String getStringResources(@StringRes int strResId) {
        try {
            return mContext.getResources().getString(strResId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取颜色
     *
     * @param colorResId
     * @return
     */
    public int getColorResources(@ColorRes int colorResId) {
        return mContext.getResources().getColor(colorResId);
    }

    public void setTextViewData(TextView textView, @NonNull String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                textView.setText(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTextViewHtmlData(TextView textView, @NonNull String htmlStr) {
        try {
            if (!TextUtils.isEmpty(htmlStr)) {
                textView.setText(Html.fromHtml(htmlStr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给EditText赋值
     *
     * @param editText
     * @param str
     */
    public void setEditTextData(EditText editText, @NonNull String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                editText.setText(str);
                editText.setSelection(str.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

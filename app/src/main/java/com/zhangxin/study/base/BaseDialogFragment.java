package com.zhangxin.study.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zhangxin.study.R;

/**
 * @Author zhangxin
 * @date 2017/6/30 10:55
 * @description DialogFragment基类
 * 用于显示背景透明的DialogFragment
 **/

public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        Window window = dialog == null ? null : dialog.getWindow();
        if (null != dialog && null != window) {
            window.setLayout(-1, -2);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Dialog);  //具有阴影效果
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(getLayoutId(), null);
        initDialog();

        return mView;
    }

    private void initDialog() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //去除标题栏
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setCancelable(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterCreate(savedInstanceState);
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    /**
     * 添加在onStart()
     */
    public void gravityBottom(){
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

}

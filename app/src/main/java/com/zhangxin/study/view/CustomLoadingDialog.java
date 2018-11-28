package com.zhangxin.study.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @date 2017/12/11
 * @author zhangxin
 * @description 自定义加载框
 */
public class CustomLoadingDialog extends BaseDialogFragment {

    @BindView(R.id.loading)
    AVLoadingIndicatorView loading;

    Unbinder unbinder;

    private boolean cancellable;

    public CustomLoadingDialog setIsCancellable(boolean cancellable) {
        this.cancellable = cancellable;
        return this;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_loading;
    }

    private void initDialog() {
        getDialog().setCanceledOnTouchOutside(cancellable);
        getDialog().setCancelable(cancellable);
    }

    private void initView() {
        loading.smoothToShow();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnLoadingDialogCancelListener != null){
            mOnLoadingDialogCancelListener.onCancel();
        }
        try {
            loading.smoothToHide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initDialog();
        initView();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private OnLoadingDialogCancelListener mOnLoadingDialogCancelListener;

    public void setOnLoadingDialogCancelListener(OnLoadingDialogCancelListener onLoadingDialogCancelListener) {
        mOnLoadingDialogCancelListener = onLoadingDialogCancelListener;
    }

    public interface OnLoadingDialogCancelListener{
        void onCancel();
    }

}

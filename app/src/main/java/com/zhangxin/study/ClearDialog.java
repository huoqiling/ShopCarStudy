package com.zhangxin.study;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 清除弹窗
 */
public class ClearDialog extends BaseDialogFragment {

    @BindView(R.id.btnCancel)
    TextView btnCancel;

    @BindView(R.id.btnClear)
    TextView btnClear;

    Unbinder unbinder;

    private onClearCarListener clearCarListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_clear;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    public ClearDialog setClearCarListener(onClearCarListener clearCarListener) {
        this.clearCarListener = clearCarListener;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnCancel, R.id.btnClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnClear:
                if(null != clearCarListener){
                    clearCarListener.clearCar();
                    dismiss();
                }
                break;
        }
    }

    public interface onClearCarListener{
        void clearCar();
    }
}

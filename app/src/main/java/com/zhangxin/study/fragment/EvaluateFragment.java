package com.zhangxin.study.fragment;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseLazyFragment;

public class EvaluateFragment extends BaseLazyFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_evaluate;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean useEventBus() {
        return false;
    }
}

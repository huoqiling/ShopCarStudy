package com.zhangxin.study.activity;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.loading.LoadingView;

import butterknife.BindView;

public class ShapeLoadingActivity extends BaseActivity {


    @BindView(R.id.loadView)
    LoadingView loadView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shape_loading;
    }

    @Override
    protected void initView() {

    }

}

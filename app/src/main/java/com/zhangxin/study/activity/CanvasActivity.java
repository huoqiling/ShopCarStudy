package com.zhangxin.study.activity;

import android.os.Bundle;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.view.CanvasView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhangxin
 * @date 2019/5/27
 * @desc 画布
 **/
public class CanvasActivity extends BaseActivity {
    @BindView(R.id.canvasView)
    CanvasView canvasView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_canvas;
    }

    @Override
    protected void initView() {
        canvasView.startAnim();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}

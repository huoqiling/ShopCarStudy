package com.zhangxin.study.activity;

import android.support.v7.widget.RecyclerView;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;

import butterknife.BindView;

/**
 * @author zhangxin
 * @date 2019/8/15
 * @desc app版本更新
 **/
public class AppUpdateActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_update;
    }

    @Override
    protected void initView() {

    }


}

package com.zhangxin.study.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhangxin.study.MyApplication;
import com.zhangxin.study.R;
import com.zhangxin.study.adapter.EvaluateAdapter;
import com.zhangxin.study.base.BaseLazyFragment;
import com.zhangxin.study.bean.EvaluateUserBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EvaluateFragment extends BaseLazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Unbinder unbinder;

    private EvaluateAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_evaluate;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new EvaluateAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void lazyData() {
        super.lazyData();
        adapter.setNewData(getData());
    }

    private List<EvaluateUserBean> getData(){
        List<EvaluateUserBean> userBeanList = new ArrayList<>();
        for(int i=0;i<10;i++){
            int resId = MyApplication.getInstance().getResources().getIdentifier("food" + new Random().nextInt(8), "mipmap", "com.zhangxin.study");
            EvaluateUserBean userBean = new EvaluateUserBean("匿名用户"+i,resId);
            userBeanList.add(userBean);
        }
        return userBeanList;
    }

    @Override
    protected boolean useEventBus() {
        return false;
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
}

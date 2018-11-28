package com.zhangxin.study.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhangxin.study.MyApplication;
import com.zhangxin.study.R;
import com.zhangxin.study.activity.MainActivity;
import com.zhangxin.study.adapter.FoodAdapter;
import com.zhangxin.study.base.BaseEvent;
import com.zhangxin.study.base.BaseLazyFragment;
import com.zhangxin.study.bean.FoodBean;
import com.zhangxin.study.event.FoodEvent;
import com.zhangxin.study.utils.CommonUtils;
import com.zhangxin.study.view.AddWidget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author zhangxin
 * @date 2018/11/28
 * @desc 商品
 **/
public class FoodFragment extends BaseLazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Unbinder unbinder;

    private FoodAdapter foodAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_food;
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        recyclerView.setNestedScrollingEnabled(false);
        foodAdapter = new FoodAdapter(new AddWidget.OnAddClickListener() {
            @Override
            public void add(View view, FoodBean foodBean) {
                postEvent("addFood",new FoodEvent(view,foodBean));
            }

            @Override
            public void sub(FoodBean foodBean) {
                postEvent("subFood",new FoodEvent(null,foodBean));
            }
        });
        recyclerView.setAdapter(foodAdapter);
        foodAdapter.setNewData(CommonUtils.getFoodBeanList(MyApplication.getInstance()));
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        if(event.getEventName().equals("foodFragment")){
            FoodEvent foodEvent = (FoodEvent) event.getObject();
            updateSelectFoodCount(foodEvent.getFoodBean());
        }

        if(event.getEventName().equals("clearSelect")){
            for (int i = 0; i < foodAdapter.getData().size(); i++) {
                FoodBean foodBean = foodAdapter.getItem(i);
                foodBean.setSelectCount(0);
                foodAdapter.setData(i, foodBean);
            }
        }
    }

    /**
     * 更改主页商品的选中数量
     *
     * @param foodBean
     */
    private void updateSelectFoodCount(FoodBean foodBean) {
        for (int i = 0; i < foodAdapter.getData().size(); i++) {
            FoodBean fb = foodAdapter.getItem(i);
            if (fb.getId().equals(foodBean.getId())) {
                fb.setSelectCount(foodBean.getSelectCount());
                foodAdapter.setData(i, fb);
            }
        }
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

package com.zhangxin.study.event;


import android.view.View;

import com.zhangxin.study.bean.FoodBean;

public class FoodEvent  {

    private View view;
    private FoodBean foodBean;

    public FoodEvent(View view, FoodBean foodBean) {
        this.view = view;
        this.foodBean = foodBean;
    }

    public View getView() {
        return view;
    }

    public FoodBean getFoodBean() {
        return foodBean;
    }
}

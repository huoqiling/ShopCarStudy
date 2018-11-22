package com.zhangxin.study;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author zhangxin
 * @desc
 * @date 2018/11/20
 **/
public class FoodPopAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {


    private AddWidget.OnAddClickListener addClickListener;

    public FoodPopAdapter(AddWidget.OnAddClickListener addClickListener) {
        super(R.layout.item_food_pop);
        this.addClickListener = addClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodBean item) {
        helper.setText(R.id.tvFoodName, item.getName());
        helper.setText(R.id.tvTotalPrice, item.getFoodPrice());
        AddWidget addWidget = helper.getView(R.id.addWidget);
        addWidget.init(addClickListener, item);
    }
}

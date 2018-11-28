package com.zhangxin.study.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhangxin.study.R;
import com.zhangxin.study.bean.FoodBean;
import com.zhangxin.study.view.AddWidget;

/**
 * @author zhangxin
 * @desc
 * @date 2018/11/20
 **/
public class PopCarAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {


    private AddWidget.OnAddClickListener addClickListener;

    public PopCarAdapter(AddWidget.OnAddClickListener addClickListener) {
        super(R.layout.item_pop_car);
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

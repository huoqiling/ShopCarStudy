package com.zhangxin.study.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhangxin.study.R;
import com.zhangxin.study.bean.FoodBean;
import com.zhangxin.study.view.AddWidget;

/**
 * @author zhangxin
 * @date 2018/11/20
 * @desc 首页适配器
 **/
public class FoodAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {


    private AddWidget.OnAddClickListener addClickListener;

    public FoodAdapter(AddWidget.OnAddClickListener addClickListener) {
        super(R.layout.item_food);
        this.addClickListener = addClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodBean item) {
        helper.setImageResource(R.id.ivFood, item.getIconId());
        helper.setText(R.id.tvFoodName, item.getName());
        helper.setText(R.id.tvFoodScale, item.getScale());
        helper.setText(R.id.tvFoodPrice, item.getFoodPrice());
        AddWidget addWidget = helper.getView(R.id.addWidget);
        addWidget.init(addClickListener, item);
    }


}

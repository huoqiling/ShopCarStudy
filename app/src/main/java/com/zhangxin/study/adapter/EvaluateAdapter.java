package com.zhangxin.study.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhangxin.study.R;
import com.zhangxin.study.bean.EvaluateUserBean;

/**
 *
 @date 2018/11/29
 @author zhangxin
 @desc 评论
 *
 **/
public class EvaluateAdapter extends BaseQuickAdapter<EvaluateUserBean,BaseViewHolder> {


    public EvaluateAdapter() {
        super(R.layout.item_evaluate);
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateUserBean item) {
        helper.setImageResource(R.id.ivUserHead,item.getHeadRes());
        helper.setText(R.id.tvUserName,item.getUserName());
    }
}

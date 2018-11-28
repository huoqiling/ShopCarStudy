package com.zhangxin.study.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.zhangxin.study.bean.FoodBean;
import com.zhangxin.study.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddWidget extends FrameLayout {

    @BindView(R.id.btnSub)
    ImageView btnSub;

    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.btnAdd)
    ImageView btnAdd;

    private OnAddClickListener clickListener;
    private FoodBean foodBean;
    private long count = 0;
    private Context context;

    public AddWidget(@NonNull Context context) {
        this(context, null);
    }

    public AddWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddWidget(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = inflate(context, R.layout.view_add_widget, this);
        ButterKnife.bind(view);
    }


    public void init(OnAddClickListener clickListener, FoodBean foodBean) {
        this.foodBean = foodBean;
        this.clickListener = clickListener;
        count = foodBean.getSelectCount();
        Log.w("AddWidget","count--"+count);
        if (count == 0) {
            btnSub.setVisibility(INVISIBLE);
            tvCount.setVisibility(INVISIBLE);
        } else {
            btnSub.setVisibility(VISIBLE);
            tvCount.setVisibility(VISIBLE);
            tvCount.setText(count + "");
        }
    }

    @OnClick({R.id.btnSub, R.id.btnAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSub:
                if (count == 0) {
                    return;
                }

                if (count == 1) {
                    btnSub.setVisibility(INVISIBLE);
                    tvCount.setVisibility(INVISIBLE);
                }

                count--;
                tvCount.setText(count == 0 ? "1" : count + "");
                foodBean.setSelectCount(count);
                if (null != clickListener) {
                    clickListener.sub(foodBean);
                }
                break;
            case R.id.btnAdd:
                if (count == 0) {
                    addAnim();
                }
                btnSub.setVisibility(VISIBLE);
                tvCount.setVisibility(VISIBLE);
                count++;
                tvCount.setText(count + "");
                foodBean.setSelectCount(count);
                if (null != clickListener) {
                    clickListener.add(btnAdd, foodBean);
                }
                break;
        }
    }

    /**
     * 减法得动画
     */
    private void subAnim() {
        ViewAnimator.animate(btnSub)
                .translationX(0, btnAdd.getLeft() - btnSub.getLeft())
                .rotation(-360)
                .alpha(255, 0)
                .duration(300)
                .interpolator(new AccelerateInterpolator())
                .andAnimate(tvCount)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {

                    }
                })
                .translationX(0, btnAdd.getLeft() - tvCount.getLeft())
                .rotation(-360)
                .alpha(255, 0)
                .interpolator(new AccelerateInterpolator())
                .duration(300)
                .start();
    }

    /**
     * 添加得动画
     */
    private void addAnim() {
        ViewAnimator.animate(btnSub)
                .translationX(btnAdd.getLeft() - btnSub.getLeft(), 0)
                .rotation(360)
                .alpha(0, 255)
                .duration(300)
                .interpolator(new DecelerateInterpolator())
                .andAnimate(tvCount)
                .translationX(btnAdd.getLeft() - tvCount.getLeft(), 0)
                .rotation(360)
                .alpha(0, 255)
                .interpolator(new DecelerateInterpolator())
                .duration(300)
                .start();
    }

    public interface OnAddClickListener {

        void add(View view, FoodBean foodBean);

        void sub(FoodBean foodBean);
    }
}

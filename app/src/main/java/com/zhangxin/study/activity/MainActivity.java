package com.zhangxin.study.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhangxin.study.R;
import com.zhangxin.study.adapter.PopCarAdapter;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.base.BaseEvent;
import com.zhangxin.study.bean.FoodBean;
import com.zhangxin.study.event.FoodEvent;
import com.zhangxin.study.fragment.EvaluateFragment;
import com.zhangxin.study.fragment.FoodFragment;
import com.zhangxin.study.utils.CommonUtils;
import com.zhangxin.study.view.AddWidget;
import com.zhangxin.study.view.ClearDialog;
import com.zhangxin.study.view.MaxHeightRecyclerView;
import com.zhangxin.study.view.ShopCarView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btnClear)
    TextView btnClear;

    @BindView(R.id.popContainer)
    LinearLayout popContainer;

    @BindView(R.id.blackView)
    View blackView;

    @BindView(R.id.popRecyclerView)
    MaxHeightRecyclerView popRecyclerView;

    @BindView(R.id.appBar)
    AppBarLayout appBar;

    @BindView(R.id.tvCarNonSelect)
    TextView tvCarNonSelect;

    @BindView(R.id.tvAmount)
    TextView tvAmount;

    @BindView(R.id.llAmountContainer)
    LinearLayout llAmountContainer;

    @BindView(R.id.tvCarLimit)
    TextView tvCarLimit;

    @BindView(R.id.rlCar)
    RelativeLayout rlCar;

    @BindView(R.id.ivShopCar)
    ImageView ivShopCar;

    @BindView(R.id.tvCarBadge)
    TextView tvCarBadge;
    @BindView(R.id.flCar)
    ShopCarView flCar;

    @BindView(R.id.rootView)
    CoordinatorLayout rootView;

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.tabLayout)
    CommonTabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private BottomSheetBehavior behavior;
    private PopCarAdapter popAdapter;
    private int expendedTag = 2;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titleList = new String[]{"商品", "评价"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initToolbar();
        initBehavior();
        initViewPager();
        initPopRecyclerView();

    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    private void initToolbar() {
        toolbar.setTitle("底部弹出Behavior");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbar.setTitle("CollapsingToolbar");
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            //verticalOffset是当前appbarLayout的高度与最开始appbarlayout高度的差，向上滑动的话是负数
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //通过日志得出活动启动是两次，由于之前有setExpanded所以三次
                Log.d("启动活动调用监听次数", "几次");
                if (getSupportActionBar().getHeight() - appBarLayout.getHeight() == verticalOffset) {
                    //折叠监听
                    collapsingToolbar.setTitleEnabled(false);
                }
                if (expendedTag == 2 && verticalOffset == 0) {
                    //展开监听
                    collapsingToolbar.setTitleEnabled(true);
                }
                if (expendedTag != 2 && verticalOffset == 0) {
                    expendedTag++;
                }
            }
        });
    }

    private void initViewPager() {
        fragmentList.add(new FoodFragment());
        fragmentList.add(new EvaluateFragment());
        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
        for (final String title : titleList) {
            mTabEntities.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return title;
                }

                @Override
                public int getTabSelectedIcon() {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon() {
                    return 0;
                }
            });
        }
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initBehavior() {
        behavior = BottomSheetBehavior.from(popContainer);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    blackView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                blackView.setVisibility(View.VISIBLE);
                ViewCompat.setAlpha(blackView, slideOffset);
            }
        });
    }

    private void initPopRecyclerView() {
        popRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        popAdapter = new PopCarAdapter(new AddWidget.OnAddClickListener() {
            @Override
            public void add(View view, FoodBean foodBean) {
                calculateCar(foodBean);
                postEvent("foodFragment", new FoodEvent(view, foodBean));
            }

            @Override
            public void sub(FoodBean foodBean) {
                calculateCar(foodBean);
                postEvent("foodFragment", new FoodEvent(null, foodBean));
            }
        });
        popRecyclerView.setAdapter(popAdapter);
    }


    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        if (event.getEventName().equals("addFood")) {
            FoodEvent foodEvent = (FoodEvent) event.getObject();
            calculateCar(foodEvent.getFoodBean());
            CommonUtils.addFoodToCarAnim(foodEvent.getView(), ivShopCar, MainActivity.this, rootView);
        }

        if (event.getEventName().equals("subFood")) {
            FoodEvent foodEvent = (FoodEvent) event.getObject();
            calculateCar(foodEvent.getFoodBean());
        }
    }

    @Override
    public boolean doubleExitAppEnable() {
        return true;
    }

    /**
     * 計算購物車
     *
     * @param foodBean
     */
    private void calculateCar(FoodBean foodBean) {
        long totalCount = 0;
        boolean hasFood = false;
        int p = -1;
        BigDecimal amount = new BigDecimal(0.0);
        for (int i = 0; i < popAdapter.getData().size(); i++) {
            FoodBean fb = popAdapter.getItem(i);
            if (fb.getId().equals(foodBean.getId())) {
                fb = foodBean;
                hasFood = true;
                if (foodBean.getSelectCount() == 0) {
                    p = i;
                } else {
                    popAdapter.setData(i, foodBean);
                }
            }
            totalCount += fb.getSelectCount();
            amount = amount.add(CommonUtils.mul(Double.parseDouble(fb.getPrice() + ""), Double.valueOf(fb.getSelectCount() + "")));
        }

        if (p >= 0) {
            popAdapter.remove(p);
        } else if (!hasFood && foodBean.getSelectCount() > 0) {
            popAdapter.addData(foodBean);
            totalCount += foodBean.getSelectCount();
            amount = amount.add(CommonUtils.mul(Double.parseDouble(foodBean.getPrice() + ""), Double.valueOf(foodBean.getSelectCount() + "")));
        }

        Log.e("AddWidget", "amount--" + amount);

        updateAmount(amount);
        if (totalCount > 0) {
            tvCarBadge.setVisibility(View.VISIBLE);
            tvCarBadge.setText(totalCount + "");
        } else {
            tvCarBadge.setVisibility(View.GONE);
        }
    }

    private void updateAmount(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0.0)) == 0) {
            tvCarLimit.setText("¥20 起送");
            tvCarLimit.setTextColor(getResources().getColor(R.color.gray_a8a8a8));
            tvCarLimit.setBackgroundColor(getResources().getColor(R.color.gray_535353));
            ivShopCar.setImageResource(R.mipmap.shop_car_empty);
            tvCarNonSelect.setVisibility(View.VISIBLE);
            llAmountContainer.setVisibility(View.GONE);
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (amount.compareTo(new BigDecimal(20.0)) < 0) {
            tvCarLimit.setText("还差 ¥" + new BigDecimal(20.0).subtract(amount) + " 起送");
            tvCarLimit.setTextColor(getResources().getColor(R.color.gray_a8a8a8));
            tvCarLimit.setBackgroundColor(getResources().getColor(R.color.gray_535353));
            ivShopCar.setImageResource(R.mipmap.shop_car);
            tvCarNonSelect.setVisibility(View.GONE);
            llAmountContainer.setVisibility(View.VISIBLE);
        } else {
            tvCarLimit.setText("  去结算  ");
            tvCarLimit.setTextColor(getResources().getColor(R.color.white));
            tvCarLimit.setBackgroundColor(getResources().getColor(R.color.green_59d178));
            ivShopCar.setImageResource(R.mipmap.shop_car);
            tvCarNonSelect.setVisibility(View.GONE);
            llAmountContainer.setVisibility(View.VISIBLE);
        }
        tvAmount.setText("¥" + amount);
    }


    @OnClick({R.id.flCar, R.id.blackView, R.id.btnClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.flCar:
                if (popAdapter.getItemCount() == 0) {
                    return;
                }

                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.blackView:
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.btnClear:
                ClearDialog clearDialog = new ClearDialog();
                clearDialog.setClearCarListener(new ClearDialog.onClearCarListener() {
                    @Override
                    public void clearCar() {
                        updateAmount(new BigDecimal(0));
                        tvCarBadge.setVisibility(View.GONE);
                        popAdapter.getData().clear();
                        popAdapter.notifyDataSetChanged();
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        postEvent("clearSelect");
                    }
                }).show(getSupportFragmentManager(), "clearDialog");
                break;
        }
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {


        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragmentList.get(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }
}

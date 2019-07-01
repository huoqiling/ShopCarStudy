package com.zhangxin.study.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.zhangxin.study.BuildConfig;
import com.zhangxin.study.R;
import com.zhangxin.study.bean.FoodBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 公共工具类
 */
public class CommonUtils {

    /**
     * @param context
     * @param d
     * @return
     */
    public static int dip2px(Context context, double d) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (d * scale + 05.f);
    }


    /**
     * 加法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    /**
     * 减法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    /**
     * 乘法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }


    /**
     * 除法
     *
     * @param v1
     * @param v2
     * @param scale 精确范围 精确度不能小于0
     * @return
     */
    public static BigDecimal divide(double v1, double v2, int scale) {
        if (scale > 0) {
            //如果精确范围小于0，抛出异常信息
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, BigDecimal.ROUND_DOWN);
        }
        return new BigDecimal(0);
    }

    /**
     *  添加食品到购物车中
     * @param fromView 从哪里来
     * @param toView   往哪里去
     * @param context
     * @param rootView 根部view
     */
    public static void addFoodToCarAnim(View fromView, View toView, Context context, final CoordinatorLayout rootView) {
        int[] fromLoc = new int[2];
        fromView.getLocationInWindow(fromLoc);

        int[] toLoc = new int[2];
        toView.getLocationInWindow(toLoc);

        Path path = new Path();
        path.moveTo(fromLoc[0], fromLoc[1]-50);
        path.quadTo(toLoc[0], fromLoc[1]-200, toLoc[0]+80, toLoc[1]);

        final TextView textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.circle_blue);
        textView.setText("1");
        textView.setTextSize(10f);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams((int)(fromView.getWidth()*0.6), (int)(fromView.getHeight()*0.6));
        rootView.addView(textView, lp);
        ViewAnimator.animate(textView).path(path).accelerate().duration(700).onStop(new AnimationListener.Stop() {
            @Override
            public void onStop() {
                rootView.removeView(textView);
            }
        }).start();
    }

    public static boolean isDebug(){
        return BuildConfig.DEBUG;
    }

}

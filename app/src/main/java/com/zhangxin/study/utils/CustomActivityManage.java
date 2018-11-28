package com.zhangxin.study.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zhangxin
 * @date 2017/12/11
 * @description activity 管理
 */
public class CustomActivityManage {

    private List<Activity> activities;

    public CustomActivityManage() {
        activities = new LinkedList<>();
    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
        for (Activity a : activities) {
            LogUtil.e("addActivity-->>", a.getClass().getName());
        }
    }

    /**
     * 删除
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
        }
        for (Activity a : activities) {
            LogUtil.e("removeActivity-->>", a.getClass().getName());
        }


    }

    /**
     * 删除所有activity
     */
    public void finishAllActivity() {
        for (final Activity activity : activities) {
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.finish();
                    }
                });
            } else {
                activities.remove(activity);
            }
        }
    }
}

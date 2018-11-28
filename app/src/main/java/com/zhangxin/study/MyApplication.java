package com.zhangxin.study;

import android.app.Activity;
import android.app.Application;

import com.zhangxin.study.utils.CustomActivityManage;

/**
 *
 @date 2018/11/28
 @author zhangxin
 @desc 自定义application
 *
 **/
public class MyApplication extends Application {

    private static MyApplication instance;
    private CustomActivityManage activityManage;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        instance = this;
        activityManage = new CustomActivityManage();
    }



    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityManage.addActivity(activity);
    }

    /**
     * 删除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityManage.removeActivity(activity);
    }

    /**
     * 删除所有activity
     */
    public void finishAllActivity() {
        activityManage.finishAllActivity();

    }
}

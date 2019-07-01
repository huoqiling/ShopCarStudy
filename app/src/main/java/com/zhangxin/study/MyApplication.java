package com.zhangxin.study;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.zhangxin.study.net.OkGoUtil;
import com.zhangxin.study.utils.CommonUtils;
import com.zhangxin.study.utils.CustomActivityManage;

/**
 * @author zhangxin
 * @date 2018/11/28
 * @desc 自定义application
 **/
public class MyApplication extends Application {

    private static MyApplication instance;
    private CustomActivityManage activityManage;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        OkGoUtil.init(this, CommonUtils.isDebug());
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

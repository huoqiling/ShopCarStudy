package com.zhangxin.study;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.zhangxin.study.utils.CustomActivityManage;
import com.zhangxin.study.utils.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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
        initOkGo();
    }

    private void init() {
        instance = this;
        activityManage = new CustomActivityManage();
    }


    /**
     * 初始化okgo
     */
    private void initOkGo() {

        HttpHeaders headers = new HttpHeaders();
        headers.put("Charset", "UTF-8");
        headers.put("Connection", "Keep-Alive");
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        LoggerInterceptor loggingInterceptor = new LoggerInterceptor("OkGo",BuildConfig.DEBUG);
        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));

        OkGo.getInstance()
                .init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(0)
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .addCommonHeaders(headers);
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

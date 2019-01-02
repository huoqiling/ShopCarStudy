package com.zhangxin.study.net;

import android.support.annotation.NonNull;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.HttpParams;

/**
 * @Author zhangxin
 * @date 2017/7/1 15:57
 * @description OkGo帮助类
 **/
public class OkGoUtil {

    /**
     * post方法 不带参数
     *
     * @param url 地址
     * @param tag 标志
     * @param <T> 实体类
     */
    public static <T> void doPost(@NonNull String url, Object tag, AbsCallback<T> callback) {
        doPost(url, tag, null, callback);
    }

    /**
     * post方法 带参数
     *
     * @param url        地址
     * @param tag        标志
     * @param httpParams 参数
     * @param <T>        实体类
     */
    public static <T> void doPost(@NonNull String url, Object tag, HttpParams httpParams, AbsCallback<T> callback) {
        if (null != httpParams) {
            OkGo.post(url).tag(tag).params(httpParams).execute((Callback<Object>) callback);
        } else {
            OkGo.post(url).tag(tag).execute((Callback<Object>) callback);
        }
    }

    /**
     * 不带参数的get方法
     *
     * @param url 地址
     * @param tag 标志
     * @param <T> 实体类
     */
    public static <T> void doGet(@NonNull String url, Object tag, AbsCallback<T> callback) {
        doGet(url, tag, null, callback);
    }

    /**
     * 待参数的get方法
     *
     * @param url        地址
     * @param tag        标志
     * @param httpParams 参数
     * @param <T>        实体类
     */
    public static <T> void doGet(@NonNull String url, Object tag, HttpParams httpParams, AbsCallback<T> callback) {
        if (null != httpParams) {
            OkGo.get(url).tag(tag).params(httpParams).execute((Callback<Object>) callback);
        } else {
            OkGo.get(url).tag(tag).execute((Callback<Object>) callback);
        }
    }

}



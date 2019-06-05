package com.zhangxin.study.net;

import com.lzy.okgo.model.HttpParams;
import com.zhangxin.study.bean.UserBean;
import com.zhangxin.study.net.callback.JsonCallback;

public class Business {

    private final static String BASE_URL = "http://192.168.2.132:8080/";
    private final static String REGISTER_URL = BASE_URL + "user/register";
    private final static String LOGIN_URL = BASE_URL + "user/login";

    public static void register(String name, String password, String payPassword, String phone, JsonCallback<UserBean> callback) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("name", name);
        httpParams.put("password", password);
        httpParams.put("payPassword", payPassword);
        httpParams.put("phone", phone);
        OkGoUtil.doPost(REGISTER_URL, "register", httpParams, callback);
    }

    public static void login(String name, String password,JsonCallback<UserBean> callback){
        HttpParams httpParams = new HttpParams();
        httpParams.put("name", name);
        httpParams.put("password", password);
        OkGoUtil.doPost(LOGIN_URL, "login", httpParams, callback);
    }
}

package com.zhangxin.study.cache;

/**
 * Created by zyfx_ on 2017/7/4.
 */

public class PreferencesKey {
    // 用户信息
    public interface User {
        String NAME = "User";
        String ID = "id";
        String LOGIN_STATE = "LoginState";    //boolean，是否已登录
    }
}

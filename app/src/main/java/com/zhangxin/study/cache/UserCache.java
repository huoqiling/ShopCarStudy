package com.zhangxin.study.cache;

import com.zhangxin.study.MyApplication;
import com.zhangxin.study.bean.UserBean;

/**
 * @Author zhangxin
 * @date 2017/7/5 14:21
 * @description 用户数据保存
 **/
public class UserCache {

    // 安全存储对象
    public static SecurityStorage storage = new SecurityStorage(MyApplication.getInstance(), PreferencesKey.User.ID);

    public static void clear() {
        storage.clear();
    }

    /**
     * @return 是否已登录
     */
    public static boolean isLogin() {
        return storage.getBoolean(PreferencesKey.User.LOGIN_STATE, false);
    }

    public static void setLoginState(boolean loginState) {
        storage.put(PreferencesKey.User.LOGIN_STATE, loginState);
    }

    public static void saveUserInfo(UserBean userBean){
        storage.put(PreferencesKey.User.NAME,userBean);
    }

}

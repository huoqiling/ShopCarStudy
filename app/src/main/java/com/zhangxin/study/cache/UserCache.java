package com.zhangxin.study.cache;

import com.zhangxin.study.bean.UserBean;
import com.zhangxin.study.utils.SharedPreferencesUtil;

/**
 * @Author zhangxin
 * @date 2017/7/5 14:21
 * @description 用户数据保存
 **/
public class UserCache {


    /**
     * @return 是否已登录
     */
    public static boolean isLogin() {
        return (boolean) SharedPreferencesUtil.get(PreferencesKey.User.LOGIN_STATE, false);
    }

    public static void setLoginState(boolean loginState) {
        SharedPreferencesUtil.put(PreferencesKey.User.LOGIN_STATE, loginState);
    }

    public static void saveUserInfo(UserBean userBean) {
        SharedPreferencesUtil.put(PreferencesKey.User.NAME, userBean);
    }

}

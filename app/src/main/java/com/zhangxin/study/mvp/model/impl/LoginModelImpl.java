package com.zhangxin.study.mvp.model.impl;

import android.os.Handler;
import android.text.TextUtils;

import com.zhangxin.study.mvp.model.LoginModel;
import com.zhangxin.study.mvp.model.entities.User;
import com.zhangxin.study.mvp.presenter.OnLoginFinishedListener;

/**
 * @author zhangxin
 * @date 2020/4/13
 * @desc 接口实现类
 **/
public class LoginModelImpl implements LoginModel {

    private static final String USERNAME = "zhangxin";
    private static final String PASSWORD = "123456";

    @Override
    public void login(User user, final OnLoginFinishedListener listener) {
        final String username = user.getUsername();
        final String password = user.getPassword();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(username) || !TextUtils.equals(username, USERNAME)) {
                    listener.onUsernameError();
                    return;
                }
                if (TextUtils.isEmpty(password) || !TextUtils.equals(password,PASSWORD)) {
                    listener.onPasswordError();
                    return;
                }

                if (TextUtils.equals(username, USERNAME) && TextUtils.equals(password, PASSWORD)) {
                    listener.onSuccess();
                }
            }
        }, 2000);
    }
}

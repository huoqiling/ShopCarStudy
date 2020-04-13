package com.zhangxin.study.mvp.view;

/**
 * @author zhangxin
 * @date 2020/4/13
 * @desc
 **/
public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void showSuccess();
}

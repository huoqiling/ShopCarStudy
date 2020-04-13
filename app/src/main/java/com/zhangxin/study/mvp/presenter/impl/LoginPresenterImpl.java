package com.zhangxin.study.mvp.presenter.impl;

import com.zhangxin.study.mvp.model.LoginModel;
import com.zhangxin.study.mvp.model.entities.User;
import com.zhangxin.study.mvp.model.impl.LoginModelImpl;
import com.zhangxin.study.mvp.presenter.LoginPresenter;
import com.zhangxin.study.mvp.presenter.OnLoginFinishedListener;
import com.zhangxin.study.mvp.view.LoginView;

/**
 * @author zhangxin
 * @date 2020/4/13
 * @desc 由于presenter完成二者的交互，那么肯定需要二者的实现类（通过传入参数，或者new）。
 * presenter里面有个OnLoginFinishedListener， 其在Presenter层实现，给Model层回调，更改View层的状态， 确保 Model层不直接操作View层。
 **/
public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishedListener {

    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModelImpl();
    }

    @Override
    public void validateCredentials(User user) {
        if (null != loginView) {
            loginView.showProgress();
        }
        loginModel.login(user, this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onUsernameError() {
        if (null != loginView) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (null != loginView) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (null != loginView) {
            loginView.showSuccess();
        }
    }
}

package com.zhangxin.study.mvp.presenter;

import com.zhangxin.study.mvp.model.entities.User;

/**
 * @author zhangxin
 * @date 2020/4/13
 * @desc 登陆的Presenter 的接口，实现类为LoginPresenterImpl，完成登陆的验证，以及销毁当前view。
 **/
public interface LoginPresenter {

    void validateCredentials(User user);

    void onDestroy();

}

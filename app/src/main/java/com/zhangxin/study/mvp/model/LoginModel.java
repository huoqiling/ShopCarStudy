package com.zhangxin.study.mvp.model;

import com.zhangxin.study.mvp.model.entities.User;
import com.zhangxin.study.mvp.presenter.OnLoginFinishedListener;

/**
 * @author zhangxin
 * @date 2020/4/13
 * @desc 方便实现回调presenter，通知presenter业务逻辑的返回结果，具体在presenter层介绍
 **/
public interface LoginModel {

    void login(User user, OnLoginFinishedListener listener);
}

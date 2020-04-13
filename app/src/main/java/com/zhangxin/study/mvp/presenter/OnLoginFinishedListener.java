package com.zhangxin.study.mvp.presenter;

/**
 * @author zhangxin
 * @date 2020/4/13
 * @desc 当Model层得到请求的结果，需要回调Presenter层，让Presenter层调用View层的接口方法。
 **/
public interface OnLoginFinishedListener {

    void onUsernameError();

    void onPasswordError();

    void onSuccess();
}

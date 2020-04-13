package com.zhangxin.study.mvp.ui;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.mvp.model.entities.User;
import com.zhangxin.study.mvp.presenter.LoginPresenter;
import com.zhangxin.study.mvp.presenter.impl.LoginPresenterImpl;
import com.zhangxin.study.mvp.view.LoginView;
import com.zhangxin.study.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MVPLoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.etUserName)
    EditText etUserName;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btnLogin)
    TextView btnLogin;

    private LoginPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvplogin;
    }

    @Override
    protected void initView() {
        presenter = new LoginPresenterImpl(this);
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        User user = new User();
        user.setUsername(etUserName.getText().toString());
        user.setPassword(etPassword.getText().toString());
        presenter.validateCredentials(user);

    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        dismissProgressDialog();
    }

    @Override
    public void setUsernameError() {
        ToastUtil.showTextToast("用户名错误");
    }

    @Override
    public void setPasswordError() {
        ToastUtil.showTextToast("密码错误");
    }

    @Override
    public void showSuccess() {
        dismissProgressDialog();
        ToastUtil.showTextToast("登录成功");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}

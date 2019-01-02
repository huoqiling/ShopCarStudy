package com.zhangxin.study.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.model.Response;
import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.bean.UserBean;
import com.zhangxin.study.cache.UserCache;
import com.zhangxin.study.net.Bussiness;
import com.zhangxin.study.net.callback.JsonCallback;
import com.zhangxin.study.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhangxin
 * @date 2018/12/7
 * @desc 登录
 **/
public class LoginActivity extends BaseActivity {

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btnLogin)
    TextView btnLogin;

    @BindView(R.id.btnRegister)
    TextView btnRegister;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        initToolbar();
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnRegister:
                startActivityForResult(new Intent(this, RegisterActivity.class), 1);
                break;
        }
    }

    private void login() {
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showTextToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showTextToast("请输入密码");
            return;
        }
        showProgressDialog();
        Bussiness.login(name, password, new JsonCallback<UserBean>() {
            @Override
            public void onSuccess(Response<UserBean> response) {
                super.onSuccess(response);
                try {
                    UserBean userBean = response.body();
                    if (userBean.isSuccess()) {
                        UserCache.setLoginState(true);
                        ToastUtil.showTextToast("登录成功");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        ToastUtil.showTextToast(userBean.errorMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<UserBean> response) {
                super.onError(response);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                String name = data.getStringExtra("name");
                if (!TextUtils.isEmpty(name)) {
                    etName.setText(name);
                    etName.setSelection(name.length());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

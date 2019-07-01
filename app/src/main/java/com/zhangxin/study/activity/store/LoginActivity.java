package com.zhangxin.study.activity.store;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.bean.UserBean;
import com.zhangxin.study.cache.UserCache;
import com.zhangxin.study.net.Business;
import com.zhangxin.study.net.callback.JsonCallback;
import com.zhangxin.study.utils.ToastUtil;
import com.zhangxin.study.view.CustomTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

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

    @BindView(R.id.titleBar)
    CustomTitleBar titleBar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(titleBar.toolbar);
        titleBar.toolbar.setTitle("");
        titleBar.setOnCustomTitleBarListener(new CustomTitleBar.OnCustomTitleBarListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }

            @Override
            public void onTitleClick() {

            }
        });
    }


    @OnClick({R.id.btnLogin, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                startIntent(MainActivity.class);
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
        Business.login(name, password, new JsonCallback<UserBean>() {
            @Override
            public void onSuccess(UserBean userBean, Call call, Response response) {
                dismissProgressDialog();
                try {
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
            public void onError(Call call, okhttp3.Response response, Exception e) {
                super.onError(call, response, e);
                dismissProgressDialog();
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
        ButterKnife.bind(this);
    }
}

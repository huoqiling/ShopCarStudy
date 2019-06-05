package com.zhangxin.study.activity.store;

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
import com.zhangxin.study.net.Business;
import com.zhangxin.study.net.callback.JsonCallback;
import com.zhangxin.study.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author zhangxin
 * @date 2018/12/7
 * @desc 注册
 **/
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.etPasswordConfirm)
    EditText etPasswordConfirm;

    @BindView(R.id.etPayPassword)
    EditText etPayPassword;

    @BindView(R.id.etPayPasswordConfirm)
    EditText etPayPasswordConfirm;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.btnRegister)
    TextView btnRegister;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick(R.id.btnRegister)
    public void onViewClicked() {
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etPasswordConfirm.getText().toString().trim();
        String payPassword = etPayPassword.getText().toString().trim();
        String confirmPayPassword = etPayPasswordConfirm.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            ToastUtil.showTextToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showTextToast("请输入密码");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            ToastUtil.showTextToast("请输入确认密码");
            return;
        }
        if (!password.equals(confirmPassword)) {
            ToastUtil.showTextToast("密码不一致，请重新输入");
            return;
        }


        if (TextUtils.isEmpty(payPassword)) {
            ToastUtil.showTextToast("请输入支付密码");
            return;
        }

        if (TextUtils.isEmpty(confirmPayPassword)) {
            ToastUtil.showTextToast("请输入确认支付密码");
            return;
        }
        if (!payPassword.equals(confirmPayPassword)) {
            ToastUtil.showTextToast("支付密码不一致，请重新输入");
            return;
        }

        if (confirmPassword.equals(confirmPayPassword)) {
            ToastUtil.showTextToast("登录密码不能和支付密码相同");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showTextToast("请输入电话号码");
            return;
        }

        showProgressDialog();
        Business.register(name, confirmPassword, confirmPayPassword, phone, new JsonCallback<UserBean>() {
            @Override
            public void onSuccess(Response<UserBean> response) {
                super.onSuccess(response);
                dismissProgressDialog();
                try {
                    UserBean userBean = response.body();
                    if (userBean.isSuccess()) {
                        ToastUtil.showTextToast("注册成功");
                        Intent intent = new Intent();
                        intent.putExtra("name", userBean.data.name);
                        setResult(RESULT_OK,intent);
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
                dismissProgressDialog();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.zhangxin.study.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhangxin.study.MyApplication;
import com.zhangxin.study.R;
import com.zhangxin.study.view.CustomLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author zhangxin
 * @date 2017/12/11
 * @description 基类activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private long exitTime = 0; //双击退出函数变量
    private CustomLoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {//修改重复回到前一个activity
            finish();
            return;
        }
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(this);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        initView();
        // 所有子类都将继承这些相同的属性,请在设置界面之后设置
        ImmersionBar.with(this).init();
    }


    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract boolean useEventBus();

    public void postEvent(String eventName) {
        postEvent(eventName, null);
    }

    public void postEvent(String eventName, Object object) {
        EventBus.getDefault().post(new BaseEvent(eventName, object));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event) {

    }

    @Subscribe
    public void onEventBackground(BaseEvent event) {

    }

    @Subscribe
    public void onEventMainThread(BaseEvent event) {

    }


    /**
     * 显示弹窗
     */
    public void showProgressDialog() {
        showProgressHud(true);
    }

    /***
     * 控制弹窗是否可已取消
     *
     * @param cancellable
     */
    public void showProgressHud(boolean cancellable) {
        try {
            loadingDialog = new CustomLoadingDialog();
            loadingDialog.setIsCancellable(cancellable)
                    .show(getSupportFragmentManager(), "loadingDialog");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 隐藏弹窗
     */
    public void dismissProgressDialog() {
        try {
            if (null != loadingDialog) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleExitAppEnable()) {
            exitAppDoubleClick();
        } else {
            super.onBackPressed();
        }
    }


    public boolean doubleExitAppEnable() {
        return false;
    }

    /**
     * 双击退出APP
     */
    private void exitAppDoubleClick() {

        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, R.string.exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            exitApp();
        }
    }

    /**
     * 退出APP
     */
    private void exitApp() {

        super.onBackPressed();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 如果你的app可以横竖屏切换，并且适配4.4或者emui3手机请务必在onConfigurationChanged方法里添加这句话
        ImmersionBar.with(this).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        // 必须调用该方法，防止内存泄漏
        ImmersionBar.with(this).destroy();
    }

}

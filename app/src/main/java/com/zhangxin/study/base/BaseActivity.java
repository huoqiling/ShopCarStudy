package com.zhangxin.study.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zhangxin.study.MyApplication;
import com.zhangxin.study.R;

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
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

}

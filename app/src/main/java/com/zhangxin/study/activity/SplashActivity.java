package com.zhangxin.study.activity;

import android.content.Intent;
import android.os.Handler;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;
import com.zhangxin.study.cache.UserCache;

public class SplashActivity extends BaseActivity {

    /**
     * 延迟两秒
     */
    private static final int APP_DISPLAY_LENGTH = 2000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpActivity();
            }
        }, APP_DISPLAY_LENGTH);
    }

    /**
     * 跳转
     */
    private void jumpActivity() {
        if (UserCache.isLogin()) {
            startActivity(new Intent(this,MainActivity.class));
        } else {
            startActivity(new Intent(this,LoginActivity.class));
        }
        this.finish();
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }
}

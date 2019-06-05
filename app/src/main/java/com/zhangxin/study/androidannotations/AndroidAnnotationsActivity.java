package com.zhangxin.study.androidannotations;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import com.zhangxin.study.R;
import com.zhangxin.study.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhangxin
 * @date 2019/5/6
 * @desc
 **/
public class AndroidAnnotationsActivity extends BaseActivity {


    @BindView(R.id.btnSend)
    TextView btnSend;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annotations;
    }

    @Override
    protected void initView() {

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSend)
    public void onViewClicked() {
//        Intent shortCutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//        Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher);
//        Uri uri = Uri.parse("http://blog.csdn.net/wanggsx918");
//        Intent pendingIntent = new Intent(Intent.ACTION_VIEW, uri);
//        //桌面快捷方式图标
//        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);
//        //桌面快捷方式标题
//        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
//        //桌面快捷方式动作:点击图标时的动作
//        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, pendingIntent);
//        sendBroadcast(shortCutIntent);
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://blog.csdn.net/wanggsx918");
        intent.setData(content_url);
        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
        startActivity(intent);

    }
}

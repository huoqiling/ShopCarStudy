package com.zhangxin.study.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangxin.study.MyApplication;
import com.zhangxin.study.view.CustomLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author zhangxin
 * @date 2017/6/30 16:15
 * @description fragment基类
 **/
public abstract class BaseFragment extends Fragment{

    protected View mView;//用户设置的根布局
    private Unbinder unbinder;
    private CustomLoadingDialog loadingDialog;
    private boolean mIsVisibleToUser; // 是否可见
    protected final String baseTag = UUID.randomUUID().toString(); // 用getBaseTag()获取

    private Activity activity;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            if (getLayoutId() != 0) {
                mView = inflater.inflate(getLayoutId(), container, false);
            }
        }
        unbinder = ButterKnife.bind(this, mView);
        initView();
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        return mView;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }


    public void startIntent(Class<? extends Activity> activityClass) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        this.startActivity(intent);
    }

    public void startIntent(Class<? extends Activity> activityClass, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        this.startActivityForResult(intent, requestCode);
    }

    public void startIntent(Intent i, int requestCode) {
        this.startActivityForResult(i, requestCode);
    }

    public void startIntent(Class<? extends Activity> activityClass, BaseIntent baseIntent) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        baseIntent.setIntent(intent);
        this.startActivity(intent);
    }

    public void startIntent(Class<? extends Activity> activityClass, BaseIntent baseIntent, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        baseIntent.setIntent(intent);
        this.startActivityForResult(intent, requestCode);
    }

    public interface BaseIntent {
        void setIntent(Intent intent);
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void initView();

    /**
     * EventBus
     */
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
                    .show(getChildFragmentManager(), "loadingDialog");
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


    /**
     * 给TextView 赋值
     *
     * @param textView
     * @param str
     */
    public static void setTextViewData(TextView textView, @NonNull String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                textView.setText(str);
            }else{
                textView.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给TextView 赋值
     *
     * @param textView
     * @param htmlStr
     */
    public static void setTextViewHtmlData(TextView textView, @NonNull String htmlStr) {
        try {
            if (!TextUtils.isEmpty(htmlStr)) {
                textView.setText(Html.fromHtml(htmlStr));
            }else{
                textView.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给带单位的TextView
     *
     * @param textView
     * @param str
     * @param colorResId
     * @param colorStr
     */
    public static void setTextViewHtmlData( TextView textView, @NonNull String str, @ColorRes int colorResId, @NonNull String colorStr) {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(colorStr)) {
                textView.setText(Html.fromHtml(str + "<font color=" + getColorResources(colorResId) + ">" + colorStr + "</font>"));
            }else{
                textView.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给EditText赋值
     *
     * @param editText
     * @param str
     */
    public static void setEditTextData(@NonNull EditText editText, @NonNull String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                editText.setText(str);
                editText.setSelection(str.length());
            }else{
                editText.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取当前页面的tag
     * @return
     */
    public String getBaseTag() {
        return baseTag;
    }


    /**
     * 获取str资源
     *
     * @param strResId
     * @return
     */
    public static String getStringResources(@StringRes int strResId) {
        try {
            return MyApplication.getInstance().getResources().getString(strResId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取颜色
     *
     * @param colorResId
     * @return
     */
    public static int getColorResources(@ColorRes int colorResId) {
        return MyApplication.getInstance().getResources().getColor(colorResId);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        try {
            if (null != unbinder) {
                unbinder.unbind();
            }
            if (useEventBus()) {
                EventBus.getDefault().unregister(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroyView();
    }



}

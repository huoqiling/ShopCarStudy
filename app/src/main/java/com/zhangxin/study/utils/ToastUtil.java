package com.zhangxin.study.utils;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

import com.zhangxin.study.MyApplication;

/**
 * @Author zhangxin
 * @date 2017/7/3 15:33
 * @description 自定义toast
 **/
public class ToastUtil {

    private static Toast mToast = null;

    /**
     * 非延时toast
     *
     * @param
     * @param msg
     */
    public static void showTextToast(@NonNull String msg) {
        try {
            if (!TextUtils.isEmpty(msg) && !msg.equals("null")) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(MyApplication.getInstance(), msg + "", Toast.LENGTH_LONG);
                mToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showTextToast(@StringRes int stringId) {
        try {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(MyApplication.getInstance(), stringId + "", Toast.LENGTH_LONG);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(final String toast, final Context context)
    {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }
}

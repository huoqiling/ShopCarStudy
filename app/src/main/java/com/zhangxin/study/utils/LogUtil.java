package com.zhangxin.study.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import com.zhangxin.study.BuildConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date 2018/11/28
 * @author zhangxin
 * @description 自定义log
 */
public class LogUtil {
    /**
     * 调试模式：是否开启调试；false:关闭调试；true:打开调试
     */
    public static final boolean DEBUG = BuildConfig.DEBUG;
    /**
     * 标记调试信息字段,开发人员可以设置便于调试
     */
    public static String TAG = "debug";

    public static void i(String msg) {
        if (DEBUG) {
            try {
                Log.i(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void i(String msg, Throwable tr) {
        if (DEBUG) {
            try {
                Log.i(TAG, msg, tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            try {
                Log.e(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void e(String msg, Throwable tr) {
        if (DEBUG) {
            try {
                Log.e(TAG, msg, tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            try {
                Log.v(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void v(String msg, Throwable tr) {
        if (DEBUG) {
            try {
                Log.v(TAG, msg, tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            try {
                Log.w(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void w(String msg, Throwable tr) {
        if (DEBUG) {
            try {
                Log.w(TAG, msg, tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            try {
                Log.w(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void d(String msg, Throwable tr) {
        if (DEBUG) {
            try {
                Log.d(TAG, msg, tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void zhangx(String msg) {
        i("zhangXin", msg);
    }

    public static void i(String TAG, String msg) {
        if (DEBUG) {
            try {
                Log.i(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void e(String TAG, String msg) {
        if (DEBUG) {
            try {
                Log.e(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void w(String TAG, String msg) {
        if (DEBUG) {
            try {
                Log.w(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void d(String TAG, String msg) {
        if (DEBUG) {
            try {
                Log.d(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void v(String TAG, String msg) {
        if (DEBUG) {
            try {
                Log.v(TAG, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static void saveLog(String content) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        try {
            FileWriter fileWriter = new FileWriter(Environment.getExternalStorageDirectory() + File.separator
                    + "log.txt", true);
            fileWriter.write(df.format(new Date()) + content);
            fileWriter.write(13);
            fileWriter.write(10);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

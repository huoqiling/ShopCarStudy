package com.zhangxin.study.net;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.zhangxin.study.utils.DownloadFileListener;
import com.zhangxin.study.utils.LoggerInterceptor;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author zhangxin
 * @date 2017/7/1 15:57
 * @description OkGo帮助类
 **/
public class OkGoUtil {

    private static Application application;
    private static boolean isDebug;

    public static void init(Application application, boolean isDebug) {
        OkGoUtil.application = application;
        OkGoUtil.isDebug = isDebug;
        HttpHeaders headers = new HttpHeaders();
        headers.put("Charset", "UTF-8");
        headers.put("Connection", "Keep-Alive");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        OkGo.init(application);
        OkGo.getInstance()
                .addInterceptor(new LoggerInterceptor("OkGoDebug", isDebug)) //建议设置OkHttpClient，不设置会使用默认的
                .setConnectTimeout(30 * 1000)  //全局的连接超时时间
                .setReadTimeOut(30 * 1000)     //全局的读取超时时间
                .setWriteTimeOut(30 * 1000)    //全局的写入超时时间
                //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(0)
                .setCookieStore(new MemoryCookieStore())
                .addCommonHeaders(headers);
    }

    public static Application getApplication() {
        return application;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    /**
     * post方法 带参数
     *
     * @param url        地址
     * @param tag        标志
     * @param httpParams 参数
     * @param <T>        实体类
     */
    public static <T> void doPost(@NonNull String url, Object tag, HttpParams httpParams, AbsCallback<T> callback) {
        if (null != httpParams) {
            OkGo.post(url).tag(tag).params(httpParams).execute(callback);
        } else {
            OkGo.post(url).tag(tag).execute(callback);
        }
    }

    /**
     * 待参数的get方法
     *
     * @param url        地址
     * @param tag        标志
     * @param httpParams 参数
     * @param <T>        实体类
     */
    public static <T> void doGet(@NonNull String url, Object tag, HttpParams httpParams, AbsCallback<T> callback) {
        if (null != httpParams) {
            OkGo.get(url).tag(tag).params(httpParams).execute(callback);
        } else {
            OkGo.get(url).tag(tag).execute(callback);
        }
    }

    /**
     * 下载文件
     *
     * @param url          下载地址
     * @param destFileDir  保存文件路径
     * @param destFileName 保存文件名
     */
    public static void downloadFile(String url, String destFileDir, String destFileName, @Nullable Object tag, final DownloadFileListener fileListener) {
        OkGo.get(url).tag(tag).execute(new FileCallback(destFileDir, destFileName) {
            @Override
            public void onSuccess(File file, Call call, Response response) {
                fileListener.onSuccess(file);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                fileListener.onFail();
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                fileListener.onProgress(currentSize, totalSize, progress);
            }
        });
    }

    /**
     * 单个上传
     *
     * @param url        上传路径
     * @param httpParams 上传参数
     * @param tag
     * @param <T>
     */
    public static <T> void uploadFile(String url, @Nullable HttpParams httpParams, @Nullable Object tag, @NonNull AbsCallback<T> callback) {
        OkGo.post(url).isMultipart(true).params(httpParams).tag(tag).execute(callback);
    }

    /**
     * 多个文件上传
     *
     * @param url        上传路径
     * @param httpParams 上传参数
     * @param tag
     * @param <T>
     */
    public static <T> void uploadFileList(String url, List<File> fileList, @Nullable HttpParams httpParams, @Nullable Object tag, @NonNull AbsCallback<T> callback) {
        OkGo.post(url).isMultipart(true).params(httpParams).addFileParams("myfile", fileList).tag(tag).execute(callback);
    }

}



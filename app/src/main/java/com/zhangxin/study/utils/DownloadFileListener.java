package com.zhangxin.study.utils;

import java.io.File;

/**
 *
 @date 2019/6/19
 @author zhangxin
 @desc 下载文件监听
 *
 **/
public interface DownloadFileListener {

    void onSuccess(File file);
    void onFail();
    void onProgress(long currentSize, long totalSize, float progress);
}

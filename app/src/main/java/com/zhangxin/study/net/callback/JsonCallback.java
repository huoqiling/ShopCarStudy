/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhangxin.study.net.callback;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.zhangxin.study.R;
import com.zhangxin.study.utils.LogUtil;
import com.zhangxin.study.utils.ToastUtil;

import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * 修订历史：
 * ================================================
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    private HttpUrl httpUrl = null;

    private String jsonString = "";

    public String getJsonString() {
        return jsonString;
    }

    private boolean isLogin = false;

    public JsonCallback() {
    }

    public JsonCallback(boolean isLogin) {
        this.isLogin = isLogin;
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        Type genType = getClass().getGenericSuperclass();
        //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        //我们的示例代码中，只有一个泛型，所以取出第一个，得到如下结果
        //com.lzy.demo.model.Login
        Type type = params[0];

        //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
        //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
        Reader reader = response.body().charStream();
        JsonReader jsonReader = new JsonReader(reader);
        //有数据类型，表示有data
        T data = Convert.fromJson(jsonReader, type);
        jsonString = Convert.toJson(data);
        JsonObject returnData = new JsonParser().parse(Convert.toJson(data)).getAsJsonObject();
        LogUtil.zhangx("jsonString==>>"+jsonString);
        Gson gs = new Gson();

        response.close();

        return data;
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        URL url = call.request().url().url();
        if (response != null) {
            int code = response.code();
            if (code == 404) {
                LogUtil.e("JsonCallback", "404 当前链接不存在");
            }
        }
        if (e instanceof SocketTimeoutException) {
            LogUtil.e("JsonCallback", "请求超时");
        } else if (e instanceof SocketException) {
            LogUtil.e("JsonCallback", "服务器异常");
            ToastUtil.showTextToast(R.string.serverException);
        } else if (e instanceof JsonParseException) {
            LogUtil.e("JsonCallback", "数据解析错误=" + e.toString());
            ToastUtil.showTextToast(R.string.dataParsingError);
        } else if (e instanceof UnknownHostException) {
            LogUtil.e("JsonCallback", "网络连接不可用，请检查网络");
            ToastUtil.showTextToast(R.string.networkError);
        } else {
            LogUtil.e("JsonCallback", e.getMessage());
        }
        try {
            if (null != e.getCause() && "NOT_LOGGEDIN".equals(e.getCause().getMessage())) {
                ToastUtil.showTextToast(e.getCause().getMessage());
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

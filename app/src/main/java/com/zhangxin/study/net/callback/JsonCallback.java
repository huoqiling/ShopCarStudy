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
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.cookie.store.CookieStore;
import com.lzy.okgo.request.base.Request;
import com.zhangxin.study.cache.UserCache;
import com.zhangxin.study.utils.LogUtil;

import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Cookie;
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
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        try {
            if (UserCache.isLogin()) {
                CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
                httpUrl = HttpUrl.parse("http://192.168.2.132:8080/");
                List<Cookie> cookies = cookieStore.getCookie(httpUrl);
                request.headers("Cookie", cookies.toString().substring(1, cookies.toString().length() - 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {

    }


    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response){
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
}

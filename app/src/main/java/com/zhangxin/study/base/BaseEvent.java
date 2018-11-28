package com.zhangxin.study.base;

import android.support.annotation.NonNull;

/**
 * @date 2017/12/11
 * @author zhangxin
 * @description EventBus 基类
 */
public class BaseEvent {

    private String eventName;
    private Object object;

    public BaseEvent(@NonNull String eventName, Object object) {
        this.eventName = eventName;
        this.object = object;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(@NonNull String eventName) {
        this.eventName = eventName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

package com.zhangxin.study.bean;

import java.io.Serializable;

public class BaseBean implements Serializable {

    public String responseMsg;
    public String errorMessage;

    public boolean isSuccess(){
        return responseMsg.equalsIgnoreCase("success");
    }

}

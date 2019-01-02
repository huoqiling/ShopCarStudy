package com.zhangxin.study.bean;

import java.io.Serializable;

public class UserBean extends BaseBean{

    public DataBean data;

    public static class DataBean implements Serializable {

        public Long id;
        public String name;
        public String phone;
    }
}

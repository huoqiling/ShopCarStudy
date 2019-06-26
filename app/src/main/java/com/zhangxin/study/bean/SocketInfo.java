package com.zhangxin.study.bean;

import java.io.Serializable;

public class SocketInfo implements Serializable {


    public String createTime;
    public FromBean from;
    public String message;
    public ToBean to;

    public static class FromBean implements Serializable {

        public String code;
        public String headImg;
        public long id;
        public String phone;
    }

    public static class ToBean implements Serializable {

        public CaiyongBean caiyong;
        public DaqiBean daqi;


    }
    public static class CaiyongBean {

        public String code;
        public String headImg;
        public long id;
        public String phone;
    }

    public static class DaqiBean {

        public String code;
        public String headImg;
        public long id;
        public String phone;
    }

}

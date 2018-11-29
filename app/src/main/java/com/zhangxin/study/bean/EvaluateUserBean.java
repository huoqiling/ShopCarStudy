package com.zhangxin.study.bean;

/**
 * 評論用戶
 */
public class EvaluateUserBean {

    private String userName;
    private int headRes;

    public EvaluateUserBean(String userName, int headRes) {
        this.userName = userName;
        this.headRes = headRes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHeadRes() {
        return headRes;
    }

    public void setHeadRes(int headRes) {
        this.headRes = headRes;
    }
}

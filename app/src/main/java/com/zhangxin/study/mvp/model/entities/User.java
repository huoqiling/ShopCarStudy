package com.zhangxin.study.mvp.model.entities;

/**
 * @author zhangxin
 * @date 2020/4/13
 * @desc 封装了用户名、密码，方便数据传递。
 **/
public class User {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

package com.leary.littlefairy.beautifulapp.model.Entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/7.
 */

public class UserInfo implements Serializable {

    private String userName;
    private String userNick;
    private int age;
    private String sex;
    private int id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

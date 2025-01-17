package com.zhang.musicrs.entity;

import org.springframework.stereotype.Component;


@Component
public class UserDO {
    // 用户id
    private String uid;
    // 用户名
    private String name;
    // 用户密码
    private String password;
    // 用户性别
    private String gender;
    // 用户年龄
    private int age;
    // 用户所在地区
    private String area;
    // 用户注册时间
    private String registerTime;
    // 用户的自我简介
    private String des;
    public UserDO() {
    }

    public String getUid() {
        return uid;
    }

    public UserDO setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDO setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserDO setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserDO setAge(int age) {
        this.age = age;
        return this;
    }

    public String getArea() {
        return area;
    }

    public UserDO setArea(String area) {
        this.area = area;
        return this;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public UserDO setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
        return this;
    }

    public String getDes() {
        return des;
    }

    public UserDO setDes(String des) {
        this.des = des;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", area='" + area + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}

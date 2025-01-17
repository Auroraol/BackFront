package com.zhang.musicrs.entity;

import org.springframework.stereotype.Component;

@Component
public class AdministratorDO {
    // 管理员账号
    private String name;
    // 管理员密码
    private String password;

    public AdministratorDO() {
    }

    public String getName() {
        return name;
    }

    // 类似链式编程
    public AdministratorDO setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AdministratorDO setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

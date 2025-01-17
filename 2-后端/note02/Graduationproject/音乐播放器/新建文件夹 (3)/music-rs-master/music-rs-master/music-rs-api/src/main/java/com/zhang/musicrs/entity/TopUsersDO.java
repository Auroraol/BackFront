package com.zhang.musicrs.entity;

import org.springframework.stereotype.Component;

@Component
public class TopUsersDO {
    // 用户id
    private String name;
    // 相似好友id集合，中间用逗号隔开
    private String topUsers;
    // private List<User> topusers;

    public TopUsersDO() {
    }

    public String getName() {
        return name;
    }

    public TopUsersDO setName(String name) {
        this.name = name;
        return this;
    }

    public String getTopUsers() {
        return topUsers;
    }

    public TopUsersDO setTopUsers(String topUsers) {
        this.topUsers = topUsers;
        return this;
    }

    @Override
    public String toString() {
        return "TopUsers{" +
                "name='" + name + '\'' +
                ", topUsers='" + topUsers + '\'' +
                '}';
    }
}

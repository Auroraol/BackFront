package com.itheima.pojo;

import java.util.List;

/**
 * �û��־û���
 */
public class Users {
    private Integer id;                 // �û����
    private String username;           // �û�����
    private String address;            // �û���ַ
    private List<Orders> ordersList; //�û������Ķ���

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", address="
                + address + ", ordersList=" + ordersList + "]";
    }
}


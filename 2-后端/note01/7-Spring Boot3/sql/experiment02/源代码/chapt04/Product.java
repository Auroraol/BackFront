package com.itheima.pojo;

import java.util.List;

/**
 * ��Ʒ�־û���
 */
public class Product {
    private Integer id;                //��Ʒid
    private String name;                //��Ʒ����
    private Double price;                //��Ʒ����
    private List<Orders> orders;        //�붩������������

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name
                + ", price=" + price + "]";
    }
}


package com.itheima.pojo;

import java.util.List;

/**
 * �����־û���
 */
public class Orders {
    private Integer id;                //����id
    private String number;            //�������
    //������Ʒ��������
    private List<Product> productList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", productList=" + productList +
                '}';
    }
}


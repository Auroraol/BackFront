package com.itheima.pojo;

/**
 * ���֤�־û���
 */
public class IdCard {
    private Integer id;                 //����id
    private String code;                //���֤����

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "IdCard [id=" + id + ", code=" + code + "]";
    }
}


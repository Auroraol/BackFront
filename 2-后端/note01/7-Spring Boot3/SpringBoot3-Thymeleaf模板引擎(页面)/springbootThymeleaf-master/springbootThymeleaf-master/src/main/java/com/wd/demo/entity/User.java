package com.wd.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data  //自动生成get set方法
@Entity  //自动生成相应的表，表名默认与实体类名相同   (JPA)
@Table(name = "user")
//@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class User {

    @Id  //设置主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自动增长
    private Integer userId;

    // @Column 指定在表中的名字，默认与属性名相同
    private String userName;
    private String userPassword;
   // private Integer userAge;
   // private String userPhone;

}

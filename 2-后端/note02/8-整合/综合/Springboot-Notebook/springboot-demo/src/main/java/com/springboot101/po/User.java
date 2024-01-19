package com.springboot101.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity   //实体类注解，自动建表必须添加
@Table(name = "t_user")  //设置生成的表名,不添加默认表名为实体类名
public class User {

    @Id   //标注主键
    @GeneratedValue(strategy = GenerationType.AUTO)   //id自增策略
    private Long id;

    private String name;

    private Integer age;

    private String email;

    @Column(name = "create_time")  //设置生成的字段名,不添加默认字段为变量名
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}
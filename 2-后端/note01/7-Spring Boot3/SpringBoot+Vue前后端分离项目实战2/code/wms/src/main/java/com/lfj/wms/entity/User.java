package com.lfj.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    @TableField(value = "no")
    private String no;

    /**
     * 名字
     */
    @TableField(value = "name")
    private String name;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 性别
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 角色 0超级管理员，1管理员，2普通账号
     */
    @TableField(value = "role_id")
    private Integer roleId;

    /**
     * 是否有效，Y有效，其他无效
     */
    @TableField(value = "isValid")
    private String isvalid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
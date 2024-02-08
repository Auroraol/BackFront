package com.springboot101.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.springboot101.annotation.EncryptField;
import lombok.Data;

/**
 * 
 * @TableName users
 */
@TableName(value ="users")
@Data
public class Users implements Serializable {
    /**
     * 
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 
     */
    @EncryptField  //标记需要加密的字段或参数
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 
     */
    @EncryptField  //标记需要加密的字段或参数
    @TableField(value = "address")
    private String address;

    /**
     * 
     */
    @TableField(value = "age")
    private String age;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
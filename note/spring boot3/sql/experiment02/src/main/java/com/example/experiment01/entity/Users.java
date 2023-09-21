package com.example.experiment01.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

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
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    /**
     * 
     */
    @TableField(value = "uname")
    private String uname;

    /**
     * 
     */
    @TableField(value = "uage")
    private Integer uage;

    private List<TbOrders> ordersList; //用户关联的订单

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
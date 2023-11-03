package com.example.experiment01.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_product
 */
@TableName(value ="tb_product")
@Data
public class TbProduct implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "NAME")
    private String name;

    /**
     * 
     */
    @TableField(value = "price")
    private Double price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
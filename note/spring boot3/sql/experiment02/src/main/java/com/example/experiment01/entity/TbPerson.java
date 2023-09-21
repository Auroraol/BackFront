package com.example.experiment01.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_person
 */
@TableName(value ="tb_person")
@Data
public class TbPerson implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 
     */
    @TableField(value = "card_id")
    private Integer cardId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
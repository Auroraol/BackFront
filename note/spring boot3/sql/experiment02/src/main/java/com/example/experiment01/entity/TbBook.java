package com.example.experiment01.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_book
 */
@TableName(value ="tb_book")
@Data
public class TbBook implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "bookName")
    private String bookname;

    /**
     * 
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 
     */
    @TableField(value = "author")
    private String author;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
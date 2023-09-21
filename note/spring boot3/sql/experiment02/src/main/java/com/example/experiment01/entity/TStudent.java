package com.example.experiment01.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_student
 */
@TableName(value ="t_student")
@Data
public class TStudent implements Serializable {
    /**
     * 
     */
    @TableId(value = "sid", type = IdType.AUTO)
    private Integer sid;

    /**
     * 
     */
    @TableField(value = "sname")
    private String sname;

    /**
     * 
     */
    @TableField(value = "sage")
    private Integer sage;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
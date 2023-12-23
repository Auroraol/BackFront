package com.lfj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName x_role
 */
@TableName(value ="x_role")
@Data
public class XRole implements Serializable {
    /**
     * 
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    /**
     * 
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 
     */
    @TableField(value = "role_desc")
    private String roleDesc;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package com.lfj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName x_menu
 */
@TableName(value ="x_menu")
@Data
public class XMenu implements Serializable {
    /**
     * 
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    /**
     * 
     */
    @TableField(value = "component")
    private String component;

    /**
     * 
     */
    @TableField(value = "path")
    private String path;

    /**
     * 
     */
    @TableField(value = "redirect")
    private String redirect;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "title")
    private String title;

    /**
     * 
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 
     */
    @TableField(value = "is_leaf")
    private String isLeaf;

    /**
     * 
     */
    @TableField(value = "hidden")
    private Integer hidden;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
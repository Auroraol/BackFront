package com.lfj.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 货名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 仓库
     */
    @TableField(value = "storage")
    private Integer storage;

    /**
     * 分类
     */
    @TableField(value = "goodsType")
    private Integer goodstype;

    /**
     * 数量
     */
    @TableField(value = "count")
    private Integer count;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
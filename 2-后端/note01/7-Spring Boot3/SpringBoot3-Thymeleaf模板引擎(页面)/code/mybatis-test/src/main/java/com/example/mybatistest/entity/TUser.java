package com.example.mybatistest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 用户
 * @TableName t_user
 */
@ApiModel("用户实体")
@TableName(value ="t_user")
@Data
public class TUser implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名（不能重复）
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 昵称（可以重复）
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 0：未删除 其他：已删除
     */
    @TableField(value = "deleted_flag")
    private Long deletedFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
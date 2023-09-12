package cn.edu.guet.entity;

import cn.edu.guet.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @Classname User
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/28 15:56
 * @Created by abner.guo
 */
@Data
@TableName("t_user")
public class User {

    @TableId(value = "uid",type = IdType.AUTO)
    private Long uid;

    @TableField("name")
    private String name;

    private Integer age;

    private String email;

    @TableLogic
    private Integer isDeleted;

    private SexEnum sex;
}
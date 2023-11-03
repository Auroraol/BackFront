package cn.edu.guet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @Classname Product
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/29 11:35
 * @Created by abner.guo
 */
@Data
@TableName("t_product")
public class Product {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer price;

    @Version
    private Integer version;

}

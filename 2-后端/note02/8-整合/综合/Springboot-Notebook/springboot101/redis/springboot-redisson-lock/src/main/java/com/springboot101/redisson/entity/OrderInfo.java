package com.springboot101.redisson.entity;

import lombok.Data;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;
import org.redisson.api.annotation.RIndex;

/**

 * @Description: 订单信息
 */
@REntity   //Redis的基础上实现的
@Data
public class OrderInfo {

    @RId
    private Integer id;

    @RIndex
    private String productId;

    @RIndex
    private String name;

    @RIndex
    private Integer age;

    @RIndex
    private Integer productQuantity;

    @RIndex
    private Integer price = 10;
}

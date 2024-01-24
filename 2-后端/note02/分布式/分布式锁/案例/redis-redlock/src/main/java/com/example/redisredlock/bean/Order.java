package com.example.redisredlock.bean;

import com.example.redisredlock.base.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 订单表
 *
 * @author niceyoo
 */
@Data
@Entity
@DynamicUpdate //在运行时生成 SQL UPDATE 语句，只更新那些发生变化的字段，而不是更新所有字段。
@Table(name = "order2")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 下单用户id
     */
    private String userId;

    /**
     * 产品id
     */
    private String productId;

}
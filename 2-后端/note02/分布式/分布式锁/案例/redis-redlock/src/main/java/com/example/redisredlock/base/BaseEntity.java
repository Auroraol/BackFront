package com.example.redisredlock.base;
import com.example.redisredlock.utils.SnowFlakeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @JsonIgnoreProperties 排除：hibernateLazyInitializer、handler、fieldHandler
 * @EntityListeners 是用在 JPA 中的，自动填充字段：CreatedDate、CreatedBy、LastModifiedDate、LastModifiedBy
 */
@Data
@MappedSuperclass  //JPA 注解，表示该类是一个映射的超类，不会映射到数据库表，但可以被其他实体类继承。
//@EntityListeners(AuditingEntityListener.class)  //JPA 注解，指定实体监听器，用于在实体对象的生命周期中处理回调事件。使用 AuditingEntityListener 监听器，用于自动填充实体的创建时间和更新时间。
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}) //忽略了 Hibernate 中一些特定的属性，如 hibernateLazyInitializer、handler 等。这样在将实体对象转换为 JSON 格式时，可以避免懒加载导致的问题。
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L; //serialVersionUID：这是一个特殊的静态成员变量，用于标识对象的版本号。1L，表示该类的第一个版本

    @Id
    private String id = String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId());  // Snowflake 算法生成的分布式唯一标识符, 作为默认值

//    @CreatedDate  //JPA 注解，用于处理实体的创建时间
    @CreationTimestamp //用于在实体对象被持久化到数据库时，自动填充该字段的值为当前时间。不需要额外的 @EntityListeners 配置。
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

//    @LastModifiedDate  //JPA 注解，用于处理实体的最后修改时间
    @UpdateTimestamp     // Hibernate 注解，用于处理实体的最后修改时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}

package com.springboot101.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**

 * @Description:
 * @date 2021/08/19
 */
@Data
@NoArgsConstructor
@MappedSuperclass  //JPA 注解，表示该类是一个映射的超类，不会映射到数据库表，但可以被其他实体类继承。
//@EntityListeners(AuditingEntityListener.class)  //JPA 注解，指定实体监听器，用于在实体对象的生命周期中处理回调事件。使用 AuditingEntityListener 监听器，用于自动填充实体的创建时间和更新时间。
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}) //忽略了 Hibernate 中一些特定的属性，如 hibernateLazyInitializer、handler 等。这样在将实体对象转换为 JSON 格式时，可以避免懒加载导致的问题。
public abstract class BaseEntity implements Serializable {

    //serialVersionUID：这是一个特殊的静态成员变量，用于标识对象的版本号。1L，表示该类的第一个版本
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间
     */
//    @CreatedDate     //JPA 注解，用于处理实体的创建时间
    @CreationTimestamp //用于在实体对象被持久化到数据库时，自动填充该字段的值为当前时间。不需要额外的 @EntityListeners 配置。
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createDate;

    /**
     * 更新时间
     */
//    @LastModifiedDate //JPA 注解，用于处理实体的最后修改时间
     @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date updateDate;

    /**
     * 0:未删除;1:已删除
     */
    @Column(name = "removed")
    private int removed = 0;
}
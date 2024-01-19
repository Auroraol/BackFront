package com.springboot101.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户
 *

 * @Description: 用户
 * @date 2021/09/15
 */
@Data
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "fire_user")
public class User extends BaseEntity {
    /**
     * 账号
     */
    @Column(name = "account_id")
    private String accountId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 电子邮件
     */
    @Column(name = "email")
    private String email;

    /**
     * 头像
     */
    @Column(name = "avatar_path")
    private String avatarPath;

    /**
     * 是否为管理员
     */
    @Column(name = "is_admin")
    private boolean isAdmin;

    /**
     * 启用状态
     */
    @Column(name = "enabled")
    private boolean enabled;

    /**
     * 密码重置时间
     */
    @Column(name = "pwd_reset_rime")
    private Date pwdResetRime;

    /**
     * 个人简介
     */
    @Column(name = "personal_desc")
    private String personalDesc;

    /**
     * 人脸识别信息  类型是blob
     */
    @Column(name = "detect_faces")
    private byte[] detectFaces;

    /**
     * 未知性别=-1 、男性=0 、女性=1
     */
    @Column(name = "gender")
    private Integer gender = -1;

    /**
     * 是否为第一次登录的新用户
     */
    @Column(name = "first_login")
    private boolean firstLogin;
}

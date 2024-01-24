package com.youzhirong.fbslock.dao;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import tk.mybatis.mapper.annotation.Version;
 
/**
 * [说明]用户表DO
 * @author youzhirong
 * @version 创建时间： 2020-04-08
 */
@Data
@Table(name = "yzr_user")
public class UserDO extends BaseDomain {
     
     
    @Id()
    private Long id; //主键
     
    private String loginId; //登陆用户账号
     
    private String userCode; //用户code
    
    private String userName; //用户名称
    
}
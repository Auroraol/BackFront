package com.youzhirong.fbslock.mapper;

import org.springframework.stereotype.Component;

import com.youzhirong.fbslock.dao.UserDO;

import tk.mybatis.mapper.common.BaseMapper;
 
/**
 * [说明]用户表Mapper
 * @author youzhirong
 * @version 创建时间： 2020-04-08
 */
@Component
public interface UserMapper extends BaseMapper<UserDO> {

}

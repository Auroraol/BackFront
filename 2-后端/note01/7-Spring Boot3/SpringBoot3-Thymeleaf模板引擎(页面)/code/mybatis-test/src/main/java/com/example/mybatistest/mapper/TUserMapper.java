package com.example.mybatistest.mapper;

import com.example.mybatistest.entity.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 16658
* @description 针对表【t_user(用户)】的数据库操作Mapper
* @createDate 2024-01-03 13:24:53
* @Entity com.example.mybatistest.entity.TUser
*/

@Mapper
public interface TUserMapper extends BaseMapper<TUser> {

}





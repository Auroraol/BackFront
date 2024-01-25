package com.example.lesson3test.mapper;

import com.example.lesson3test.entity.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 16658
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2024-01-03 17:55
* @Entity com.example.lesson3test.entity.TUser
*/

@Mapper
public interface TUserMapper extends BaseMapper<TUser> {

}





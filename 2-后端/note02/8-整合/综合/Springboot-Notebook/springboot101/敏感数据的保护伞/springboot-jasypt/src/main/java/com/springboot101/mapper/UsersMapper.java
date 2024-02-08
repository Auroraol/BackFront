package com.springboot101.mapper;

import com.springboot101.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 16658
* @description 针对表【users】的数据库操作Mapper
* @createDate 2024-02-07 22:16:14
* @Entity com.springboot101.entity.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}





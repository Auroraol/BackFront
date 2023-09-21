package com.example.experiment01.mapper;

import com.example.experiment01.entity.TbIdcard;
import com.example.experiment01.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 16658
* @description 针对表【users】的数据库操作Mapper
* @createDate 2023-09-21 08:27:09
* @Entity com.example.experiment01.entity.Users
*/
public interface UsersMapper extends BaseMapper<Users> {
	List<Users> findUserWithOrders(int id);
}





package com.springboot101.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot101.entity.Users;
import com.springboot101.service.UsersService;
import com.springboot101.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
* @author 16658
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-02-07 22:16:14
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

}





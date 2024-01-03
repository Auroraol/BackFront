package com.example.mybatistest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatistest.entity.TUser;
import com.example.mybatistest.service.TUserService;
import com.example.mybatistest.mapper.TUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 16658
* @description 针对表【t_user(用户)】的数据库操作Service实现
* @createDate 2024-01-03 13:24:53
*/
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
    implements TUserService{

}





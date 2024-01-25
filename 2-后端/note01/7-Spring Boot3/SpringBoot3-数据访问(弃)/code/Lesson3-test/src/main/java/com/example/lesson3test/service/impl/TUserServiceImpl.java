package com.example.lesson3test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lesson3test.entity.TUser;
import com.example.lesson3test.service.TUserService;
import com.example.lesson3test.mapper.TUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 16658
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2024-01-03 17:55:39
*/
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
    implements TUserService{

}





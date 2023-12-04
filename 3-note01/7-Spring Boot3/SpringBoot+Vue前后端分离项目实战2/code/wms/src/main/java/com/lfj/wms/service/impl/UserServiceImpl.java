package com.lfj.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfj.wms.entity.User;
import com.lfj.wms.service.UserService;
import com.lfj.wms.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 16658
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-11-27 15:43:27
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}





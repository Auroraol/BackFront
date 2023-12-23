package cn.edu.guet.service;

import cn.edu.guet.entity.User;
import cn.edu.guet.mapper.UserMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Classname UserServiceImpl
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/28 19:06
 * @Created by abner.guo
 */
@Service
@DS("master") //指定操作的数据源，master为user表
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}

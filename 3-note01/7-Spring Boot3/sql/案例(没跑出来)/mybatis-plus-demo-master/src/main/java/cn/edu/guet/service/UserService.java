package cn.edu.guet.service;

import cn.edu.guet.entity.User;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Classname UserService
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/28 19:06
 * @Created by abner.guo
 */
//@DS("master") //指定操作的数据源，master为user表
public interface UserService extends IService<User> {
}

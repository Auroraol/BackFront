package com.atguigu.boot3.ssm.controller;

import com.atguigu.boot3.ssm.enutity.TUser;
import com.atguigu.boot3.ssm.mapper.UserMapper;
import com.atguigu.boot3.ssm.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lfy
 * @Description
 * @create 2023-04-20 17:07
 */
@RestController   //与前端交互
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;


    /**
     * 返回User的json数据, 显示,查找
     */
    @GetMapping("/{id}")
    public TUser getUser(@PathVariable("id") Long id){
        TUser user = userMapper.getUserById(id);
        return user;
    }

    /**
     *新增
     */
    @PostMapping
    public Integer saveUser(@RequestBody TUser user){
        return  userService.save(user);
    }

    /**
     *  删除
     */

    @DeleteMapping("/delete/{id}")
    public Integer delete(@PathVariable("id") Long id){
        return  userMapper.delete(id);
    }
}

package cn.edu.guet;

import cn.edu.guet.entity.User;
import cn.edu.guet.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Classname LambdaUpdateWrapperTest
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/29 11:01
 * @Created by abner.guo
 */

@SpringBootTest
public class LambdaUpdateWrapperTest {


    @Autowired
    private UserMapper userMapper;


    @Test
    public void test12(){
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.like(User::getName, "a")
                .and(i -> i.gt(User::getAge, 20).or().isNull(User::getEmail));
        updateWrapper.set(User::getName, "小黑").set(User::getEmail,"abc@atguigu.com");
        int result = userMapper.update(null, updateWrapper);
        System.out.println("result："+result);
    }
}

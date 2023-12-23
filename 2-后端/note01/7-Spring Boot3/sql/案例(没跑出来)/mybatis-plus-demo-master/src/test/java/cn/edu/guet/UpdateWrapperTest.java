package cn.edu.guet;

import cn.edu.guet.entity.User;
import cn.edu.guet.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Classname UpdateWrapperTest
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/28 20:00
 * @Created by abner.guo
 */
@SpringBootTest
public class UpdateWrapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void test08(){
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("name","a").and( i -> i.gt("age",20).or().isNull("email")).set("email","svip@qq.com");
        int result = userMapper.update(null, updateWrapper);
        System.out.println(result > 0 ? "修改成功！" : "修改失败！");
        System.out.println("受影响的行数为：" + result);
    }
}

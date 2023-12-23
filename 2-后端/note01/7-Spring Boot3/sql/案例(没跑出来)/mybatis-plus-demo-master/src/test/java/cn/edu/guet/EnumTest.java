package cn.edu.guet;

import cn.edu.guet.entity.User;
import cn.edu.guet.enums.SexEnum;
import cn.edu.guet.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;

/**
 * @Classname EnumTest
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/29 13:40
 * @Created by abner.guo
 */
@SpringBootTest
public class EnumTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        User user = new User();
        user.setName("admin");
        user.setAge(33);
        user.setSex(SexEnum.MALE);
        int result = userMapper.insert(user);
        System.out.println("result:"+result);
    }

}

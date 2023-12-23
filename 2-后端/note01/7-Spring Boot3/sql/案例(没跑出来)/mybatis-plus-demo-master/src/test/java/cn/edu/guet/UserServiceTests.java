package cn.edu.guet;

import cn.edu.guet.entity.User;
import cn.edu.guet.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname UserServiceTests
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/28 19:09
 * @Created by abner.guo
 */
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void testGetCount(){
        //查询总记录数
        //执行的SQL为：SELECT COUNT( * ) FROM user
        long count = userService.count();
        System.out.println("总记录数：" + count);
    }

    @Test
    public void test(){
        List<User> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setName("Vz"+i);
            user.setAge(20+i);
            list.add(user);
        }
        boolean b = userService.saveBatch(list);
        System.out.println(b ? "添加成功！" : "添加失败！");
    }


}

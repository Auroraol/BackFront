package cn.edu.guet;

import cn.edu.guet.entity.User;
import cn.edu.guet.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Classname PageTest
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/29 11:09
 * @Created by abner.guo
 */
@SpringBootTest
public class PageTest {


    @Autowired
    private UserMapper userMapper;

    /**
     * Mybatis-Plus自带的分页的方法
     */
    @Test
    public void testPage(){
        //new Page()中的两个参数分别是当前页码，每页显示数量
        Page<User> page = userMapper.selectPage(new Page<>(3, 3), null);
        List<User> users = page.getRecords();
        users.forEach(System.out::println);
    }

    /**
     * 自己定义的方法实现分页
     */
    @Test
    public void testPageVo(){
        Page<User> page = userMapper.selectPageVo(new Page<User>(2,3), 20);
        List<User> users = page.getRecords();
        users.forEach(System.out::println);
    }

}

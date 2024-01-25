package cn.edu.guet;

import cn.edu.guet.entity.Product;
import cn.edu.guet.entity.User;
import cn.edu.guet.service.ProductService;
import cn.edu.guet.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Classname TestDatasourceApplicationTests
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/29 14:03
 * @Created by abner.guo
 */
@SpringBootTest
public class TestDatasourceApplicationTests {

    @Resource
    UserService userService;

    @Resource
    ProductService productService;

    @Test
    void contextLoads() {
        User user = userService.getById(1L);
        Product product = productService.getById(1L);
        System.out.println("User = " + user);
        System.out.println("Product = " + product);
    }

}

package com.shardingsphere_101;

import com.shardingsphere_101.entity.User;
import com.shardingsphere_101.repository.UserRepository;
import com.shardingsphere_101.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
class ShardingSphereApplicationTests {

    @Resource
    private OrderService orderService;

    @Resource
    private UserRepository userRepository;

    /**
     * 插入 t_order 数据验证简单的分库分表示例
     */
    @Test
    void saveOrderSharding() {
        orderService.save();
    }

    /**
     * t_user 未做分库分表的表，插入数据验证示例, 未分片的表默认会使用第一个数据源作为默认数据源，也就是 `datasource.names` 第一个。
     */
    @Test
    void saveDefaultUserSharding() {
        User user = new User();
        user.setUName("金刀");
        user.setUAge(12);
        user.setUAddress("搜索");
        user.setCreateTime(new Date());
        user.setDateTime(new Date());
        userRepository.save(user);
    }
}

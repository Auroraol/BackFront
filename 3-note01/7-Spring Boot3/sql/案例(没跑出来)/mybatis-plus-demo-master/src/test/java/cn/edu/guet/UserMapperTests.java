package cn.edu.guet;

import cn.edu.guet.entity.User;
import cn.edu.guet.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class UserMapperTests {

    @Resource
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 测试插入一条数据
     * MyBatis-Plus在实现插入数据时，会默认基于雪花算法的策略生成id
     */
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("V4");
        user.setAge(21);
        user.setEmail("vz@oz6.cn");
        int result = userMapper.insert(user);
        System.out.println(result > 0 ? "添加成功！" : "添加失败！");
        System.out.println("受影响的行数为：" + result);
        //1527206783590903810（当前 id 为雪花算法自动生成的id）
        System.out.println("id自动获取" + user.getUid());
    }

    /**
     * 测试根据id删除一条数据
     */
    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(5L);
        System.out.println(result);
        System.out.println(result > 0 ? "删除成功！" : "删除失败！");
        System.out.println("受影响的行数为：" + result);
    }


    /**
     * 测试通过id批量删除数据
     */
    @Test
    public void testDeleteBatchIds(){
        List<Long> ids = Arrays.asList(2L,3L,4L);
        int result = userMapper.deleteBatchIds(ids);
        System.out.println(result);
        System.out.println(result > 0 ? "删除成功！" : "删除失败！");
        System.out.println("受影响的行数为：" + result);
    }

    /**
     * 测试根据Map集合中所设置的条件删除数据
     */
    @Test
    public void testDeleteByMap(){
        //当前演示为根据name和age删除数据
        //执行SQL为：DELETE FROM user WHERE name = ? AND age = ?
        Map<String,Object> map = new HashMap<>();
        map.put("name","Vz");
        map.put("age",21);
        int result = userMapper.deleteByMap(map);
        System.out.println(result > 0 ? "删除成功！" : "删除失败！");
        System.out.println("受影响的行数为：" + result);
    }

    /**
     * 测试根据id修改用户信息
     */
    @Test
    public void testUpdateById(){
        //执行SQL为： UPDATE user SET name=?, age=?, email=? WHERE id=?
        User user = new User();
        user.setUid(5L);
        user.setName("VzUpdate");
        user.setAge(18);
        user.setEmail("Vz@sina.com");
        int result = userMapper.updateById(user);
        System.out.println(result > 0 ? "修改成功！" : "修改失败！");
        System.out.println("受影响的行数为：" + result);
    }
    /**
     * 测试根据id查询用户数据
     */
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(5L);
        System.out.println(user);
    }

    /**
     * 根据多个id查询用户数据
     */
    @Test
    public void testSelectBatchIds(){
        //执行SQL为：SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
        List<Long> ids = Arrays.asList(1L,2L,3L);
        List<User> users = userMapper.selectBatchIds(ids);
        users.forEach(System.out::println);
    }

    /**
     * 根据Map所设置的条件查询用户
     */
    @Test
    public void testSelectByMap(){
        //执行SQL为：SELECT id,name,age,email FROM user WHERE age = ?
        Map<String,Object> map = new HashMap<>();
        map.put("age",18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    /**
     * 测试查询所有数据
     */
    @Test
    void testSelectList(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}

package com.atguigu.boot3.ssm.mapper;

import com.atguigu.boot3.ssm.enutity.TUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lfy
 * @Description
 * @create 2023-04-20 16:59
 */

@Mapper
public interface UserMapper {

    /**
     * 1、每个方法都在Mapper文件中有一个sql标签对应。
     * 2、所有参数都应该用@Param进行签名，以后使用指定的名字在SQL中取值
     */
    TUser getUserById(@Param("id") Long id);   //查找id=参数的数据

    @Insert("insert into t_user values (#{id}, #{loginName}, #{nickName}, #{passwd})")   //方法2
    void insert(TUser user);

    void update(TUser user);

    @Delete("delete from t_user where id=#{id)")
    Integer delete(@PathVariable("id")  Long id);
}

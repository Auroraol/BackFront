package com.wd.demo.db1.dao;

import com.wd.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Jpa提供了增删改查的功能
public interface IUserDao extends JpaRepository<User, Integer> {  //第一个泛型为实体类，第二个为实体类的主键类型

    /**
     * 查找是否有这样的用户名和密码用户存在
     * @param name
     * @param password
     * @return
     */
    User findUserByUserNameAndUserPassword(String name, String password);


    /**
     * 查找是否有这样的用户存在
     * @param name
     * @return
     */
    List<User> findUserByUserName(String name);


}

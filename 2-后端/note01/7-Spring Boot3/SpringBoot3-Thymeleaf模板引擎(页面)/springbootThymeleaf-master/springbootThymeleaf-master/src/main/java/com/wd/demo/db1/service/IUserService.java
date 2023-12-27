package com.wd.demo.db1.service;

import com.wd.demo.entity.User;

import java.util.List;

public interface IUserService {

    /**
     * 添加新用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 查找是否有这样的用户名和密码用户存在
     * @param name
     * @param password
     * @return
     */
    User findByNameAndPassword(String name, String password);

    /**
     * 查找是否有这样的用户存在
     * @param name
     * @return
     */
    List<User> findByName(String name);
}

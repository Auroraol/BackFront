package com.wd.demo.db1.service.impl;

import com.wd.demo.db1.dao.IUserDao;
import com.wd.demo.db1.service.IUserService;
import com.wd.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    /**
     * 添加新用户
     * @param user
     */
    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    /**
     * 查找是否有这样的用户名和密码用户存在
     * @param name
     * @param password
     * @return
     */
    @Override
    public User findByNameAndPassword(String name, String password){
        return userDao.findUserByUserNameAndUserPassword(name, password);
    }

    /**
     * 查找是否有这样的用户存在
     * @param name
     * @return
     */
    @Override
    public List<User> findByName(String name){
        return userDao.findUserByUserName(name);
    }
}

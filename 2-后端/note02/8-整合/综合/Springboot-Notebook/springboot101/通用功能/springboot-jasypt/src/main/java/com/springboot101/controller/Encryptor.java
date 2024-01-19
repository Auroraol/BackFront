package com.springboot101.controller;

import com.alibaba.fastjson.JSON;
import com.springboot101.annotation.EncryptField;
import com.springboot101.annotation.EncryptMethod;
import com.springboot101.model.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/encryptor/")
public class Encryptor {

    @Autowired
    private StringEncryptor stringEncryptor;

    public void encrypt(String content) {
        String encryptStr = stringEncryptor.encrypt("公众号：程序员小富");
        System.out.println("加密后的内容：" + encryptStr);
    }

    @EncryptMethod
    @PostMapping(value = "test")
    @ResponseBody
    public Object testEncrypt(@RequestBody UserVo user, @EncryptField String name) {

        return insertUser(user, name);
    }

    private UserVo insertUser(UserVo user, String name) {
        System.out.println("加密后的数据：user" + JSON.toJSONString(user));
        return user;
    }
}

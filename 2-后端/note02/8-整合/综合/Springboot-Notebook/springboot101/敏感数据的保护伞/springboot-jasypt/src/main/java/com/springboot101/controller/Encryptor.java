package com.springboot101.controller;

import com.springboot101.annotation.EncryptField;
import com.springboot101.annotation.EncryptMethod;
import com.springboot101.entity.Users;
import com.springboot101.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.springboot101.enums.EncryptConstant.DECRYPT;

@Slf4j
@RestController
@RequestMapping("/encryptor")
public class Encryptor {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Autowired
    UsersService usersService;

    /**
     * 预先生成的加密值，可以通过stringEncryptor内调用API生成
     * @param content
     */
    @GetMapping("/test1")
    public void encryptTest(String content) {
        String encryptStr = stringEncryptor.encrypt(content);
        String decryptStr = stringEncryptor.decrypt(encryptStr);
        System.out.println("加密后的内容：" + encryptStr);
        System.out.println("解密后的内容：" + decryptStr);
    }

    @EncryptMethod()
    @PostMapping("/dataEnc")
    @ResponseBody
    public Object testEncrypt(@RequestBody Users user, @RequestParam @EncryptField String username) {
        System.out.println("加密后的Users: " + user);
        System.out.println("加密后的username：" + username); // 打印加密后的用户名

        // 保存到数据库  Users(userId=123, mobile=iehuD1gFxRoYq7hJcw/JD+YLfYu6Rb9Z, address=n4ZdBg7VCsEtb37dC0PmzXv11/58qALK, age=25)
        usersService.save(user);

        return user;
    }

    @GetMapping("/dataDec")
    @EncryptMethod(type = DECRYPT)
    public Users testDecrypt() {
        Users user = usersService.getById(123);
        return user;
    }

    @GetMapping("/dataDec2")
    @EncryptMethod(type = DECRYPT)
    public String test2Decrypt(String name) {
        System.out.println(name);
        return name;
    }
}

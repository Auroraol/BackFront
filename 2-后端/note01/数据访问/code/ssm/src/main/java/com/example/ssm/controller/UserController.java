package com.example.ssm.controller;

import com.example.ssm.entity.Product;
import com.example.ssm.entity.User;
import com.example.ssm.service.ProductService;
import com.example.ssm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController   //与前端交互
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;


    @GetMapping("/user/{id}")
    public Integer saveUser(@PathVariable("id") int id){
        log.info("添加id");
        User user = new User(id, "id", "男");
        return userService.save(user);
    }

    @GetMapping("/product/{id}")
    public List<Product> showProduct(@PathVariable("id") int id){
        log.info("显示Product表");
        return productService.showById(id);
    }

}

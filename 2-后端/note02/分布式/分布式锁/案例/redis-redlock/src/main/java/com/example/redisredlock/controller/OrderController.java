package com.example.redisredlock.controller;

import com.example.redisredlock.bean.Order;
import com.example.redisredlock.server.OrderServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author niceyoo
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderServer orderServer;

    @PostMapping("/createOrder1")
    public boolean createOrder(Order order) {
        return orderServer.createOrder(order.getUserId(), order.getProductId());
    }

    @PostMapping("/createOrder2")
    public boolean createOrder(@RequestParam String userId,@RequestParam String productId) {
        return orderServer.createOrder(userId, productId);
    }
}
package com.example.redisredlock.server.impl;

import com.example.redisredlock.bean.Order;
import com.example.redisredlock.dao.OrderDao;
import com.example.redisredlock.server.OrderServer;
import com.example.redisredlock.server.StockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
public class OrderServerImpl implements OrderServer {

    /**
     * 库存service
     */
    @Resource
    private StockService stockService;

    /**
     * 订单order dao
     */
    @Resource
    private OrderDao orderDao;

    @Override
    public OrderDao getRepository() {
        return orderDao;
    }

    @Resource
    private RedissonClient redissonClient;

    @Override
    public boolean createOrder(String userId, String productId) {

        //  如果不加锁，必然超卖
        RLock lock = redissonClient.getLock("stock:" + productId);

        try {
            // 2.尝试加锁，最多等待10秒，上锁后10秒自动解锁
            boolean locked = lock.tryLock(10,10, TimeUnit.SECONDS);

            if (locked) {
                //lock.lock(10, TimeUnit.SECONDS);
                // 3.获取锁成功，执行需要加锁保护的业务逻辑
                int stock = stockService.get(productId).getStockNum();
                log.info("剩余库存：{}", stock);

                if (stock > 0 && stockService.decrease(productId)) {
                    String orderNo = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                    Order order = new Order();
                    order.setUserId(userId);
                    order.setProductId(productId);
                    order.setOrderNo(orderNo);
                    Date now = new Date();
                    order.setCreateTime(now);
                    order.setUpdateTime(now);
                    orderDao.save(order);
                    return true;
                }
            } else {
                // 获取锁失败，处理获取锁失败的逻辑
                // ...
            }

        } catch (InterruptedException ex) {
            log.error("下单失败", ex);
        } finally {
            // 4.释放自己的锁
            if(lock != null && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }

        return false;
    }

}

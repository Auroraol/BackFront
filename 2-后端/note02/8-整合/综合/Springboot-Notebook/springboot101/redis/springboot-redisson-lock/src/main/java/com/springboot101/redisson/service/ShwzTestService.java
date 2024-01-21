package com.springboot101.redisson.service;

import com.springboot101.redisson.entity.OrderInfo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @Author: LFJ
 * @ddescription: 砍价接口
 * @Date: 2024-01-21 15:08
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ShwzTestService {

	@Autowired
	private RedissonClient redissonClient;

	public String decreasePrice(String productId, Integer productQuantity) throws InterruptedException {
		//  1.设置分布式锁
		String key = "dec_store_lock_" + productId;
		RLock lock = redissonClient.getLock(key);

		// 创建 OrderInfo 对象
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setProductId(productId);
		orderInfo.setProductQuantity(productQuantity);
		System.out.println(orderInfo);

		try
		{
			// 2.尝试加锁，最多等待3秒，上锁后10秒自动解锁
			boolean locked = lock.tryLock(3, 10, TimeUnit.SECONDS);

			if (locked) {
				// 3.获取锁成功，执行需要加锁保护的业务逻辑
				int currentPrice = orderInfo.getPrice();
				if (currentPrice == 0) {
					return "当前价格为0，不能在减了！！";
				}
				orderInfo.setPrice(currentPrice - 1);
			}
		} catch (InterruptedException e) {
			// 处理异常情况
			// ...
		} finally {
			// 4.释放自己的锁
			if(lock != null && lock.isHeldByCurrentThread()){
				lock.unlock();
			}
		}
		String result = "当前价格：" + orderInfo.getPrice();
		return result;
	}
}

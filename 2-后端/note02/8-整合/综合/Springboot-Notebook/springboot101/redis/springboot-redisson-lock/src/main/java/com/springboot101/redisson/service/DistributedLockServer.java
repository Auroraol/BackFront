package com.springboot101.redisson.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: LFJ
 * @Date: 2024-01-21 15:02
 */
@Service
public class DistributedLockServer {
	@Resource
	private RedissonClient redissonClient;

	public void doSomethingWithLock() {
		// 1.设置分布式锁
		RLock lock = redissonClient.getLock("myLock");

		try {
			// 2.尝试加锁，最多等待3秒，上锁后10秒自动解锁
			boolean locked = lock.tryLock(3, 10, TimeUnit.SECONDS);

			if (locked) {
				// 3.获取锁成功，执行需要加锁保护的业务逻辑
				// ...
			} else {
				// 获取锁失败，处理获取锁失败的逻辑
				// ...
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
	}
}

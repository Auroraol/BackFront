package com.example.springbootredissontest1.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Janwes
 * @version 1.0.0
 * @package com.janwes.utils
 * @date 2022/4/11 16:57
 * @description
 */
@Component
public class RedisLockUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockUtil.class);

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 加锁
     *
     * @param lockName
     * @return
     */
    public boolean lock(String lockName) {
        if (checkRedissonClient()) return false;
        try {
            // 获取锁对象
            RLock lock = redissonClient.getLock(lockName);
            // 上锁
            lock.lock();
            LOGGER.info("===> Thread [{}] redis lock [{}] success...", Thread.currentThread().getName(), lockName);
            return true;
        } catch (Exception e) {
            LOGGER.error("===> redis lock [{}] failure...", lockName, e);
            return false;
        }
    }

    /**
     * 释放锁
     *
     * @param lockName
     * @return
     */
    public boolean unlock(String lockName) {
        if (checkRedissonClient()) return false;
        try {
            // 获取锁对象
            RLock lock = redissonClient.getLock(lockName);
            // 判断要解锁的key释放被锁定、当前线程是否持有此锁定
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                // 释放锁
                lock.unlock();
            }
            LOGGER.info("===> Thread [{}] redis unlock [{}] success...", Thread.currentThread().getName(), lockName);
            return true;
        } catch (Exception e) {
            LOGGER.error("===> redis unlock [{}] failure...", lockName, e);
            return false;
        }
    }

    private boolean checkRedissonClient() {
        return redissonClient == null;
    }
}

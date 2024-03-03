package com.github.xiaolyuh.stats;

import com.alibaba.fastjson.JSON;
import com.github.xiaolyuh.cache.Cache;
import com.github.xiaolyuh.cache.LayeringCache;
import com.github.xiaolyuh.manager.AbstractCacheManager;
import com.github.xiaolyuh.manager.CacheManager;
import com.github.xiaolyuh.redis.clinet.RedisClient;
import com.github.xiaolyuh.redis.serializer.SerializationException;
import com.github.xiaolyuh.setting.LayeringCacheSetting;
import com.github.xiaolyuh.support.LayeringCacheRedisLock;
import com.github.xiaolyuh.util.GlobalConfig;
import com.github.xiaolyuh.util.NamedThreadFactory;
import com.github.xiaolyuh.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 统计服务
 *
 * @author yuhao.wang3
 */
public class StatsService {
    private static Logger logger = LoggerFactory.getLogger(StatsService.class);

    /**
     * 缓存统计数据前缀
     */
    public static final String CACHE_STATS_KEY_PREFIX = "layering-cache:cache_stats_info:";

    /**
     * 定时任务线程池
     */
    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("layering-cache-stat"));

    /**
     * {@link AbstractCacheManager }
     */
    private AbstractCacheManager cacheManager;

    /**
     * 获取缓存统计list
     *
     * @param cacheNameParam 缓存名称
     * @return List&lt;CacheStatsInfo&gt;
     */
    public List<CacheStatsInfo> listCacheStats(String cacheNameParam) {
        logger.debug("获取缓存统计数据");

        Set<String> layeringCacheKeys = cacheManager.getRedisClient().scan(CACHE_STATS_KEY_PREFIX + "*");
        if (CollectionUtils.isEmpty(layeringCacheKeys)) {
            return Collections.emptyList();
        }
        // 遍历找出对应统计数据
        List<CacheStatsInfo> statsList = new ArrayList<>();
        for (String key : layeringCacheKeys) {
            if (StringUtils.isNotBlank(cacheNameParam) && !key.startsWith(CACHE_STATS_KEY_PREFIX + cacheNameParam)) {
                continue;
            }

            CacheStatsInfo cacheStats = null;
            try {
                cacheStats = cacheManager.getRedisClient().get(key, CacheStatsInfo.class, GlobalConfig.GLOBAL_REDIS_SERIALIZER);
            } catch (SerializationException e) {
                cacheManager.getRedisClient().delete(key);
            }
            if (!Objects.isNull(cacheStats)) {
                statsList.add(cacheStats);
            }
        }

        return statsList.stream().sorted(Comparator.comparing(CacheStatsInfo::getHitRate)).collect(Collectors.toList());
    }

    /**
     * 同步缓存统计list
     */
    public void syncCacheStats() {
        RedisClient redisClient = cacheManager.getRedisClient();
        // 清空统计数据
        resetCacheStat();
        executor.scheduleWithFixedDelay(() -> {
            logger.debug("执行缓存统计数据采集定时任务");
            Set<AbstractCacheManager> cacheManagers = AbstractCacheManager.getCacheManager();
            List<CacheStatsInfo> cacheStatsInfos = new LinkedList<>();
            for (AbstractCacheManager abstractCacheManager : cacheManagers) {
                // 获取CacheManager
                CacheManager cacheManager = abstractCacheManager;
                Collection<String> cacheNames = cacheManager.getCacheNames();
                for (String cacheName : cacheNames) {
                    // 获取Cache
                    Collection<Cache> caches = cacheManager.getCache(cacheName);
                    for (Cache cache : caches) {
                        LayeringCache layeringCache = (LayeringCache) cache;
                        LayeringCacheSetting layeringCacheSetting = layeringCache.getLayeringCacheSetting();
                        // 加锁并增量缓存统计数据，缓存key=固定前缀 +缓存名称加 + 内部缓存名
                        String redisKey = CACHE_STATS_KEY_PREFIX + cacheName + layeringCacheSetting.getInternalKey();
                        LayeringCacheRedisLock lock = new LayeringCacheRedisLock(redisClient, redisKey, 60, 5000);
                        try {
                            if (lock.tryLock()) {
                                CacheStatsInfo cacheStats = null;
                                try {
                                    cacheStats = redisClient.get(redisKey, CacheStatsInfo.class, GlobalConfig.GLOBAL_REDIS_SERIALIZER);
                                } catch (SerializationException e) {
                                    redisClient.delete(redisKey);
                                }
                                if (Objects.isNull(cacheStats)) {
                                    cacheStats = new CacheStatsInfo();
                                }

                                // 设置缓存唯一标示
                                cacheStats.setNameSpace(GlobalConfig.NAMESPACE);
                                cacheStats.setCacheName(cacheName);
                                cacheStats.setInternalKey(layeringCacheSetting.getInternalKey());

                                cacheStats.setDepict(layeringCacheSetting.getDepict());
                                // 设置缓存配置信息
                                cacheStats.setLayeringCacheSetting(layeringCacheSetting);

                                // 设置缓存统计数据
                                CacheStats layeringCacheStats = layeringCache.getCacheStats();
                                CacheStats firstCacheStats = layeringCache.getFirstCache().getCacheStats();
                                CacheStats secondCacheStats = layeringCache.getSecondCache().getCacheStats();

                                // 清空加载缓存时间
                                firstCacheStats.getAndResetCachedMethodRequestTime();
                                secondCacheStats.getAndResetCachedMethodRequestTime();

                                cacheStats.setRequestCount(cacheStats.getRequestCount() + layeringCacheStats.getAndResetCacheRequestCount());
                                cacheStats.setMissCount(cacheStats.getMissCount() + layeringCacheStats.getAndResetCachedMethodRequestCount());
                                cacheStats.setTotalLoadTime(cacheStats.getTotalLoadTime() + layeringCacheStats.getAndResetCachedMethodRequestTime());
                                cacheStats.setHitRate((cacheStats.getRequestCount() - cacheStats.getMissCount()) / (double) cacheStats.getRequestCount() * 100);

                                cacheStats.setFirstCacheSize(layeringCache.getFirstCache().estimatedSize());
                                cacheStats.setFirstCacheRequestCount(cacheStats.getFirstCacheRequestCount() + firstCacheStats.getAndResetCacheRequestCount());
                                cacheStats.setFirstCacheMissCount(cacheStats.getFirstCacheMissCount() + firstCacheStats.getAndResetCachedMethodRequestCount());

                                cacheStats.setSecondCacheRequestCount(cacheStats.getSecondCacheRequestCount() + secondCacheStats.getAndResetCacheRequestCount());
                                cacheStats.setSecondCacheMissCount(cacheStats.getSecondCacheMissCount() + secondCacheStats.getAndResetCachedMethodRequestCount());

                                // 将缓存统计数据写到redis
                                redisClient.set(redisKey, cacheStats, 24, TimeUnit.HOURS, GlobalConfig.GLOBAL_REDIS_SERIALIZER);
                                cacheStatsInfos.add(cacheStats);
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            }
            logger.info("Layering Cache 统计信息：{}", JSON.toJSONString(cacheStatsInfos));
            try {
                // 上报缓存统计信息
                this.cacheManager.getCacheStatsReportService().reportCacheStats(cacheStatsInfos);
            } catch (Throwable e) {
                logger.error("上报缓存统计信息失败 {}", e.getMessage(), e);
            }

            //  初始时间间隔是1分
        }, 1, 1, TimeUnit.MINUTES);
    }

    /**
     * 关闭线程池
     */
    public void shutdownExecutor() {
        executor.shutdown();
    }

    /**
     * 重置缓存统计数据
     */
    public void resetCacheStat() {
        RedisClient redisClient = cacheManager.getRedisClient();
        Set<String> layeringCacheKeys = redisClient.scan(CACHE_STATS_KEY_PREFIX + "*");

        for (String key : layeringCacheKeys) {
            resetCacheStat(key);
        }
    }

    /**
     * 重置缓存统计数据
     *
     * @param redisKey redisKey
     */
    public void resetCacheStat(String redisKey) {
        RedisClient redisClient = cacheManager.getRedisClient();
        try {
            CacheStatsInfo cacheStats = null;
            try {
                cacheStats = cacheManager.getRedisClient().get(redisKey, CacheStatsInfo.class, GlobalConfig.GLOBAL_REDIS_SERIALIZER);
            } catch (SerializationException e) {
                cacheManager.getRedisClient().delete(redisKey);
            }
            if (Objects.nonNull(cacheStats)) {
                cacheStats.clearStatsInfo();
                // 将缓存统计数据写到redis
                redisClient.set(redisKey, cacheStats, 24, TimeUnit.HOURS);
            }
        } catch (Exception e) {
            redisClient.delete(redisKey);
        }
    }

    public void setCacheManager(AbstractCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}

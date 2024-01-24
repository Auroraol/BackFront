package com.example.redisredlock.server;

import com.example.redisredlock.base.BaseService;
import com.example.redisredlock.bean.Stock;

/**
 * 库存server
 * 库存实现类，就一个接口，完成对库存的-1操作。
 */
public interface StockService extends BaseService<Stock, String> {

    /**
     * 减库存
     * @param productId
     * @return
     */
    public boolean decrease(String productId);
}

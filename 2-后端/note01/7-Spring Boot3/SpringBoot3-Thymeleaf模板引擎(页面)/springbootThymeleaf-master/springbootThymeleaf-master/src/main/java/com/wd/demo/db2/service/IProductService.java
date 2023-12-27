package com.wd.demo.db2.service;

import com.wd.demo.entity.Product;

import java.util.List;

public interface IProductService {

    /**
     * 添加一个商品
     * @param product
     */
    void saveProduct(Product product);

    /**
     * 查询所有的商品
     * @return
     */
    List<Product> findAllProducts();
}

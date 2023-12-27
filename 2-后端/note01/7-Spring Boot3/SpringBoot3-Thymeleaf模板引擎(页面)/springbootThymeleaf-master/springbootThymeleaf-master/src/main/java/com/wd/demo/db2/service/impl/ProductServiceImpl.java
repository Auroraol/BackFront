package com.wd.demo.db2.service.impl;

import com.wd.demo.db2.mapper.ProductMapper;
import com.wd.demo.db2.service.IProductService;
import com.wd.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService
{
    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加一个商品
     * @param product
     */
    @Override
   // @Transactional(transactionManager = "db2TransactionManager")
    public void saveProduct(Product product) {
        productMapper.addProduct(product.getProductName(), product.getProductPrice());
    }

    /**
     * 查询所有的商品
     * @return
     */
    @Override
    public List<Product> findAllProducts() {
        return productMapper.findAll();
    }
}

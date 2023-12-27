package com.wd.demo.db2.mapper;

import com.wd.demo.entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Qualifier("db2SqlSessionFactory")
@Mapper
@Component
public interface ProductMapper {

    /**
     * 添加一个商品
     * @param name
     * @param price
     */
    @Insert("insert into product(productName, productPrice) values(#{productName}, #{productPrice})")
    void addProduct(@Param("productName") String name, @Param("productPrice") Double price);

    /**
     * 查询所有的商品
     * @return
     */
    @Select("select * from product")
    List<Product> findAll();

}

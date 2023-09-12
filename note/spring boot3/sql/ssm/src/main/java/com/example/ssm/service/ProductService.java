package com.example.ssm.service;

import com.example.ssm.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
//import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 16658
* @description 针对表【product】的数据库操作Service
* @createDate 2023-09-12 15:27:54
*/
//extends IService<Product>
public interface ProductService {
	List<Product> showById(@Param("id") Integer id);
}

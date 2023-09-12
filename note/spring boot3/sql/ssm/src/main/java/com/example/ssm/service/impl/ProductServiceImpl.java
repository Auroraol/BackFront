package com.example.ssm.service.impl;

import com.example.ssm.entity.Product;
//import com.example.ssm.service.ProductService;
import com.example.ssm.mapper.ProductMapper;
import com.example.ssm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 16658
* @description 针对表【product】的数据库操作Service实现
* @createDate 2023-09-12 15:27:54
*/
//extends ServiceImpl<ProductMapper, Product>
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductMapper productMapper;

	@Override
	public List<Product> showById(Integer id) {
		return productMapper.findAllById(id);
	}
}





package cn.edu.guet.service;

import cn.edu.guet.entity.Product;
import cn.edu.guet.mapper.ProductMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Classname ProductServiceImpl
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/29 14:05
 * @Created by abner.guo
 */
//@DS("slave_1")
@Service
@DS("slave_1")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


}

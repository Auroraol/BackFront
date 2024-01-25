package cn.edu.guet.mapper;

import cn.edu.guet.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Classname ProductMapper
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/7/29 11:35
 * @Created by abner.guo
 */
public interface ProductMapper extends BaseMapper<Product> {

    int insertSelectiveInt(Product product);

}
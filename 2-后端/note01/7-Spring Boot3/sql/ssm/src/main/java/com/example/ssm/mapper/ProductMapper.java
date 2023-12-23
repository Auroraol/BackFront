package com.example.ssm.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.ssm.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 16658
* @description 针对表【product】的数据库操作Mapper
* @createDate 2023-09-12 15:27:54
* @Entity com.example.ssm.entity.Product
*/
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
	List<Product> findAllById(@Param("id") Integer id);
}





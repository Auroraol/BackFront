package com.example.experiment01.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.example.experiment01.entity.TbProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author 16658
* @description 针对表【tb_product】的数据库操作Mapper
* @createDate 2023-09-21 08:26:27
* @Entity com.example.experiment01.entity.TbProduct
*/
public interface TbProductMapper extends BaseMapper<TbProduct> {
//
//	@Select("SELECT * from tb_product where id IN(SELECT product_id FROM tb_ordersitem  WHERE orders_id = #{id}")
	List<TbProduct> findProductById(int id);
}





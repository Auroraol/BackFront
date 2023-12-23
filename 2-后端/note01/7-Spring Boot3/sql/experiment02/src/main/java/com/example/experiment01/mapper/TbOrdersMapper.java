package com.example.experiment01.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.experiment01.entity.TbOrders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 16658
* @description 针对表【tb_orders】的数据库操作Mapper
* @createDate 2023-09-21 08:26:13
* @Entity com.example.experiment01.entity.TbOrders
*/
@Mapper
public interface TbOrdersMapper extends BaseMapper<TbOrders> {
	// 多对多嵌套查询：通过执行另外一条SQL映射语句来返回预期的特殊类型
	List<TbOrders> findOrdersWithP1(@Param("id") Integer id);
	// 多对多嵌套结果查询：查询某订单及其关联的商品详情
	List<TbOrders> findOrdersWithP2(@Param("id") Integer id);

}





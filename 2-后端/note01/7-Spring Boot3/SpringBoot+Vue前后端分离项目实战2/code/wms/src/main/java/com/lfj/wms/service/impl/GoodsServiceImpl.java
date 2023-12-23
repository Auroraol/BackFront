package com.lfj.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfj.wms.entity.Goods;
import com.lfj.wms.service.GoodsService;
import com.lfj.wms.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

/**
* @author 16658
* @description 针对表【goods】的数据库操作Service实现
* @createDate 2023-11-22 22:55:07
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService{

}





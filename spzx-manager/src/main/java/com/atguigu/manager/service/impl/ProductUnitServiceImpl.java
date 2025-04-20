package com.atguigu.manager.service.impl;

import com.atguigu.manager.mapper.ProductUnitMapper;
import com.atguigu.manager.service.IProductUnitService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductUnitServiceImpl extends ServiceImpl<ProductUnitMapper, ProductUnit> implements IProductUnitService {
}

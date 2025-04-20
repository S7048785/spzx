package com.atguigu.manager.service.impl;

import com.atguigu.manager.mapper.ProductSpecMapper;
import com.atguigu.manager.service.IProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec> implements IProductSpecService {


	@Override
	public Page<ProductSpec> findByPage(Integer page, Integer limit) {
		Page<ProductSpec> pageInfo = new Page<>(page, limit);
		page(pageInfo, Wrappers.lambdaQuery(ProductSpec.class).eq(ProductSpec::getIsDeleted, 0).orderByDesc(ProductSpec::getId));
		return pageInfo;
	}
}

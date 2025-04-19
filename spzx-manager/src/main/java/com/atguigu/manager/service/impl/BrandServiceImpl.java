package com.atguigu.manager.service.impl;

import com.atguigu.manager.mapper.BrandMapper;
import com.atguigu.manager.service.IBrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {
	@Override
	public Page<Brand> findByPage(Integer page, Integer limit) {
		Page<Brand> brandPage = new Page<>(page, limit);

		page(brandPage,Wrappers.lambdaQuery(Brand.class).eq(Brand::getIsDeleted, 0));
		return brandPage;
	}
}

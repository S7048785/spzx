package com.atguigu.manager.service.impl;

import com.atguigu.manager.mapper.CategoryBrandMapper;
import com.atguigu.manager.service.ICategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements ICategoryBrandService {
	@Autowired
	private CategoryBrandMapper categoryBrandMapper;
	@Override
	public Page<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
		// 参数校验
		if (page == null || page <= 0) {
			page = 1;
		}
		if (limit == null || limit <= 0) {
			limit = 10;
		}
		Page<CategoryBrand> cbPage = new Page<>(page, limit);
		categoryBrandMapper.findByPage(cbPage, categoryBrandDto);
		return cbPage;
	}

	@Override
	public List<Brand> findBrandByCategoryId(Long categoryId) {
		List<Brand> brandList = categoryBrandMapper.findBrandByCategoryId(categoryId);
		return brandList;
	}
}

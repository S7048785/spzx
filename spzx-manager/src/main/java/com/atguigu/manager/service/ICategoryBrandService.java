package com.atguigu.manager.service;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ICategoryBrandService extends IService<CategoryBrand> {
	Page<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

	List<Brand> findBrandByCategoryId(Long categoryId);
}

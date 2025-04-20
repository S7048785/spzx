package com.atguigu.manager.mapper;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryBrandMapper extends BaseMapper <CategoryBrand> {
	Page<CategoryBrand> findByPage(Page<CategoryBrand> cBpage, CategoryBrandDto categoryBrandDto);

	List<Brand> findBrandByCategoryId(Long categoryId);
}

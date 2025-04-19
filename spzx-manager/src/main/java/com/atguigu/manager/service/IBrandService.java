package com.atguigu.manager.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IBrandService extends IService<Brand> {
	Page<Brand> findByPage(Integer page, Integer limit);
}

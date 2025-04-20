package com.atguigu.manager.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IProductSpecService extends IService<ProductSpec> {
	Page<ProductSpec> findByPage(Integer page, Integer limit);
}

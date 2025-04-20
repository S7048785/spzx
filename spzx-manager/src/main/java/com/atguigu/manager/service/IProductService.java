package com.atguigu.manager.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IProductService extends IService<Product> {
	Page<Product> findByPage(Integer page, Integer limit, ProductDto productDto);

	void saveProduct(Product product);

	Product getProductById(Long id);

	void updateProductById(Product product);

	void deleteproductById(Long id);

	void updateAuditStatus(Long id, Integer auditStatus);
}

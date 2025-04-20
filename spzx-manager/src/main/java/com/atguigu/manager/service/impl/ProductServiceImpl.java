package com.atguigu.manager.service.impl;

import com.atguigu.manager.mapper.ProductDetailsMapper;
import com.atguigu.manager.mapper.ProductMapper;
import com.atguigu.manager.mapper.ProductSkuMapper;
import com.atguigu.manager.service.IProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YYJYP
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductSkuMapper productSkuMapper;
	@Autowired
	private ProductDetailsMapper productDetailsMapper;

	@Override
	public Page<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
		Page<Product> productPage = new Page<>(page, limit);
		productMapper.findByPage(productPage, productDto);
		return productPage;
	}

	@Transactional
	@Override
	public void saveProduct(Product product) {
		// 保存商品数据
		product.setStatus(0);              // 设置上架状态为0
		product.setAuditStatus(0);         // 设置审核状态为0
		productMapper.insert(product);

		// 保存商品sku数据
		List<ProductSku> productSkuList = product.getProductSkuList();
		for(int i=0,size=productSkuList.size(); i<size; i++) {

			// 获取ProductSku对象
			ProductSku productSku = productSkuList.get(i);
			productSku.setSkuCode(product.getId() + "_" + i);       // 构建skuCode

			productSku.setProductId(product.getId());               // 设置商品id
			productSku.setSkuName(product.getName() + productSku.getSkuSpec());
			productSku.setSaleNum(0);                               // 设置销量
			productSku.setStatus(0);
			productSkuMapper.insert(productSku);                    // 保存数据

		}
	}

	@Override
	public Product getProductById(Long id) {
		// 根据id查询商品数据
		Product product = productMapper.selectById(id);

		// 根据商品的id查询sku数据
		List<ProductSku> productSkuList = productSkuMapper.selectList(Wrappers.lambdaQuery(ProductSku.class).eq(ProductSku::getProductId, id).eq(ProductSku::getProductId, id).orderByDesc(ProductSku::getId));
		product.setProductSkuList(productSkuList);

		// 根据商品的id查询商品详情数据
		ProductDetails productDetails = productDetailsMapper.selectOne(Wrappers.lambdaQuery(ProductDetails.class).eq(ProductDetails::getProductId, id));
		product.setDetailsImageUrls(productDetails.getImageUrls());

		// 返回数据
		return product;
	}

	@Transactional
	@Override
	public void updateProductById(Product product) {
		// 修改商品基本数据
		productMapper.updateById(product);

		// 修改商品的sku数据
		List<ProductSku> productSkuList = product.getProductSkuList();
		productSkuList.forEach(productSku -> {
			productSkuMapper.updateById(productSku);
		});

		// 修改商品的详情数据
		ProductDetails productDetails = productDetailsMapper.selectOne(Wrappers.lambdaQuery(ProductDetails.class).eq(ProductDetails::getProductId, product.getId()));
		productDetails.setImageUrls(product.getDetailsImageUrls());
		productDetailsMapper.updateById(productDetails);
	}

	@Override
	public void deleteproductById(Long id) {

		productMapper.update(Wrappers.lambdaUpdate(Product.class).eq(Product::getId, id).set(Product::getIsDeleted, 1));
		productSkuMapper.update(Wrappers.lambdaUpdate(ProductSku.class).eq(ProductSku::getProductId, id).set(ProductSku::getIsDeleted, 1));
		productDetailsMapper.update(Wrappers.lambdaUpdate(ProductDetails.class).eq(ProductDetails::getProductId, id).set(ProductDetails::getIsDeleted, 1));

	}

	@Override
	public void updateAuditStatus(Long id, Integer auditStatus) {
		Product product = new Product();
		product.setId(id);
		if(auditStatus == 1) {
			product.setAuditStatus(1);
			product.setAuditMessage("审批通过");
		} else {
			product.setAuditStatus(-1);
			product.setAuditMessage("审批不通过");
		}
		productMapper.updateById(product);
	}
}

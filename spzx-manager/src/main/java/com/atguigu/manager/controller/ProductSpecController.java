package com.atguigu.manager.controller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.manager.service.IProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private IProductSpecService productSpecService ;

    @GetMapping("/{page}/{limit}")
    public Result<Page<ProductSpec>> findByPage(@PathVariable Integer page, @PathVariable Integer limit) {
        Page<ProductSpec> pageInfo = productSpecService.findByPage(page, limit);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    @PostMapping("save")
    public Result save(@RequestBody ProductSpec productSpec) {
        productSpec.setCreateTime(DateUtil.now());
        productSpec.setUpdateTime(DateUtil.now());
        productSpec.setIsDeleted(0);
        productSpecService.save(productSpec);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @PutMapping("updateById")
    public Result updateById(@RequestBody ProductSpec productSpec) {
        productSpec.setUpdateTime(DateUtil.now());
        productSpecService.updateById(productSpec);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @DeleteMapping("/deleteById/{id}")
    public Result removeById(@PathVariable Long id) {
        productSpecService.update(Wrappers.lambdaUpdate(ProductSpec.class).eq(ProductSpec::getId, id).set(ProductSpec::getUpdateTime, DateUtil.now()).set(ProductSpec::getIsDeleted, 1));
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping("findAll")
    public Result findAll() {
        List<ProductSpec> list = productSpecService.list(Wrappers.lambdaQuery(ProductSpec.class).orderByDesc(ProductSpec::getId));
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }
}
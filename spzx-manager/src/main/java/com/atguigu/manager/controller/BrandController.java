package com.atguigu.manager.controller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.manager.service.IBrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/admin/product/brand")
public class BrandController {

    @Autowired
    private IBrandService brandService ;

    @GetMapping("/{page}/{limit}")
    public Result<Page<Brand>> findByPage(@PathVariable Integer page, @PathVariable Integer limit) {
        Page<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }
    @PostMapping("save")
    public Result save(@RequestBody Brand brand) {
        String now = DateUtil.now();
        brand.setCreateTime(now);
        brand.setUpdateTime(now);
        brand.setIsDeleted(0);
        brandService.save(brand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }


}
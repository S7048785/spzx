package com.atguigu.manager.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.atguigu.manager.Listen.CategoryExcelListener;
import com.atguigu.manager.mapper.CategoryMapper;
import com.atguigu.manager.service.ICategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spzx.common.exception.GuiguException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YYJYP
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

	@Autowired
	private CategoryMapper categoryMapper;
	@Override
	public List<Category> findByParentId(Long parentId) {

		List<Category> categoryList = categoryMapper.selectByParentId(parentId);
		if(!CollUtil.isEmpty(categoryList)) {

			// 遍历分类的集合，获取每一个分类数据
			categoryList.forEach(item -> {

				// 查询该分类下子分类的数量
				int count = Math.toIntExact(categoryMapper.selectCount(Wrappers.lambdaQuery(Category.class).eq(Category::getParentId, item.getId()).eq(Category::getIsDeleted, 0)));

				if(count > 0) {
					item.setHasChildren(true);
				} else {
					item.setHasChildren(false);
				}

			});
		}
		return categoryList;
	}

	@Override
	public void exportData(HttpServletResponse response) {
		try {

			// 设置响应结果类型
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("utf-8");

			// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
			String fileName = URLEncoder.encode("分类数据", "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			//response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

			// 查询数据库中的数据
			List<Category> categoryList = list(Wrappers.lambdaQuery(Category.class).eq(Category::getIsDeleted, 0).orderBy(true,true, Category::getId));
			List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size());

			// 将从数据库中查询到的Category对象转换成CategoryExcelVo对象
			for(Category category : categoryList) {
				CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
				BeanUtils.copyProperties(category, categoryExcelVo, CategoryExcelVo.class);
				categoryExcelVoList.add(categoryExcelVo);
			}

			// 写出数据到浏览器端
			EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).sheet("分类数据").doWrite(categoryExcelVoList);

		} catch (Exception e) {
			e.printStackTrace();
			throw new GuiguException(500, "数据导出失败");
		}
	}

	@Override
	public void importData(MultipartFile file) {
		try {
			CategoryExcelListener<CategoryExcelVo> excelListener = new CategoryExcelListener<>(categoryMapper);
			// 调用read方法读取excel数据
			EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelListener).sheet().doRead();
		} catch (IOException e) {
			throw new GuiguException(500, "数据导入失败");
		}
	}
}

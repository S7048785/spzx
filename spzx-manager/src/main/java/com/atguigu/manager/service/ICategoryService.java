package com.atguigu.manager.service;

import com.atguigu.spzx.model.entity.product.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoryService extends IService<Category> {
	List<Category> findByParentId(Long parentId);

	void exportData(HttpServletResponse response);

	void importData(MultipartFile file);
}

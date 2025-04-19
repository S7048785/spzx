package com.atguigu.manager.Listen;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.img.ColorUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.manager.mapper.CategoryMapper;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;

import java.util.List;

/**
 * @author YYJYP
 */
public class CategoryExcelListener<T> extends AnalysisEventListener<T> {

	/**
	 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
	 */
	private static final int BATCH_COUNT = 100;
	/**
	 * 缓存的数据
	 */
	private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

	//获取mapper对象
	private CategoryMapper categoryMapper;
	public CategoryExcelListener(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}

	// 每解析一行数据就会调用一次该方法
	@Override
	public void invoke(T o, AnalysisContext analysisContext) {
		cachedDataList.add(o);
		// 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
		if (cachedDataList.size() >= BATCH_COUNT) {
			saveData();
			// 存储完成清理 list
			cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		// excel解析完毕以后需要执行的代码
		// 这里也要保存数据，确保最后遗留的数据也存储到数据库
		saveData();
	}

	private void saveData() {
		List<Category> map = CollUtil.map(cachedDataList, item -> BeanUtil.copyProperties(item, Category.class), true);
		categoryMapper.insertCategoryBatch(map);
	}
}
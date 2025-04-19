package com.atguigu.manager;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义一个监听器，监听解析到的数据
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {

	//可以通过实例获取该值
	private List<T> datas = new ArrayList<>();
	/**
	 * 监听到每一条数据都会执行此方法
	 * @param o
	 * @param analysisContext
	 */
	@Override
	public void invoke(T o, AnalysisContext analysisContext) {
		//数据存储到list，供批量处理，或后续自己业务逻辑处理。
		datas.add(o);
	}

	public List<T> getDatas() {
		return datas;
	}

	/**
	 * 解析完成之后执行此方法
	 * @param analysisContext
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}
}

package spzx.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;
import spzx.common.Listener.ExcelListener;

import java.util.List;

/**
 * @author YYJYP
 */
public class EasyExcelUtil {

	public static <T> List<T> read(String filePath, Class<T> clazz) {
		// 创建监听器对象
		ExcelListener<T> excelListener = new ExcelListener<>();
		// 执行excel读取操作
		try {
			EasyExcel.read(filePath, clazz, excelListener)
					// 读取第0个sheet
					.sheet()
					// 执行读取操作 (同步读取)
					.doRead();
		} catch (ExcelDataConvertException e) {
			throw new RuntimeException("文件读取失败");
		}
		//获取解析到的数据
		return excelListener.getDatas();
	}

	public static <T> void write(String filePath, List<T> list) {
		// 执行excel写入操作
		EasyExcel.write(filePath, CategoryExcelVo.class)
				// 读取第0个sheet
				.sheet()
				// 执行读取操作 (同步读取)
				.doWrite(list);
	}
}

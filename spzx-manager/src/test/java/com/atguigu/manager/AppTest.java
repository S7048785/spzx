package com.atguigu.manager;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.atguigu.manager.service.ISysMenuService;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spzx.common.utils.EasyExcelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
@Slf4j
//@SpringBootTest
public class AppTest {

//	@Autowired
//	private MinioUtil minioUtil;
//	@Autowired
//	private MinioConfig minioConfig;
	@Autowired
	private ISysMenuService sysMenuService;

//	@Test
//	public void testUUID() {
//
//		String string = UUID.randomUUID().toString(true);
//		log.info("{}", string);
//	}
//
	@Test
	public void testFindNodes() {
//		sysMenuService.findUserMenuList()
	}

//	@Test
//	public void testMinio() throws IOException {
//		File file = new File("F:\\resource\\image\\127773888.jpg");
//		MultipartFile multipartFile = new MockMultipartFile(
//				"file",               // 表单字段名
//				"127773888.jpg",       // 文件名
//				MediaType.IMAGE_JPEG_VALUE,  // 内容类型（可选）
//				new FileInputStream(file)    // 文件流
//		);
//		// 上传文件
//		minioUtil.uploadFile(minioConfig.bucketFiles, multipartFile);
//
//		String fileUrl = minioUtil.getFileUrl("spzx-bucket", "2025/04/16/05597eea-6372-40e3-8184-fbefee0628a7.jpg");
//		log.info("{}", fileUrl);
//	}

	@Test
	public void testEsayExcel() {
		// 指定文件路径
		String filePath = "F:\\resource\\excel\\category.xlsx";
		// 创建监听器对象
		ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>();
		// 执行excel读取操作
		EasyExcel.read(filePath, CategoryExcelVo.class, excelListener)
				// 读取第0个sheet
				.sheet()
				// 执行读取操作 (同步读取)
				.doRead();
		//获取解析到的数据
		List<CategoryExcelVo> excelVoList = excelListener.getDatas();
		excelVoList.forEach(System.out::println);
	}

	@Test
	public void testEasyExcelUtil() {
		List<CategoryExcelVo> read = EasyExcelUtil.read("F:\\resource\\excel\\category.xlsx", CategoryExcelVo.class);
		read.forEach(System.out::println);
	}

	@Test
	public void testEasyExcelWrite() {
		List<CategoryExcelVo> list = new ArrayList<>() ;
		list.add(new CategoryExcelVo(1L , "数码办公" , "",0L, 1, 1)) ;
		list.add(new CategoryExcelVo(11L , "华为手机" , "",1L, 1, 2)) ;
		EasyExcelUtil.write("F:\\resource\\excel\\category.xlsx", list);

//		EasyExcel.write("F:\\resource\\excel\\category.xlsx" , CategoryExcelVo.class).sheet().doWrite(list);

	}

	@Test
	public void testDateUtil() {
		String now = DateUtil.now();
		System.out.println(now);
	}

}

package com.atguigu.manager.service.impl;

import com.atguigu.manager.config.MinioConfig;
import com.atguigu.manager.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spzx.common.utils.MinioUtil;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private MinioUtil minioUtil;
	@Autowired
	private MinioConfig minioConfig;
	@Override
	public String fileUpload(MultipartFile multipartFile) {
		return "http://192.168.142.129:9000/" + minioConfig.bucketFiles + "/" + minioUtil.uploadFile(minioConfig.bucketFiles, multipartFile);
	}
}

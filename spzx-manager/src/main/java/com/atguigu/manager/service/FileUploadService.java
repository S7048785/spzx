package com.atguigu.manager.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

	String fileUpload(MultipartFile multipartFile);
}

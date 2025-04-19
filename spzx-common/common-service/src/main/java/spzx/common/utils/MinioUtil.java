package spzx.common.utils;

import cn.hutool.core.lang.UUID;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import spzx.common.exception.GuiguException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class MinioUtil {
	@Autowired
	private MinioClient minioClient;

	/**
	 * 判断文件是否存在
	 * @param bucketName 桶名
	 * @param objectName 文件名
	 * @return 存在返回true
	 */
	public boolean existFile(String bucketName, String objectName) {
		try {
			minioClient.statObject(
					StatObjectArgs.builder()
							.bucket(bucketName)
							.object(objectName)
							.build()
			);
			// 文件存在
			return true;
		} catch (Exception e) {
			if (e instanceof ErrorResponseException) {
				// 文件不存在
				return false;
			}
			// 其他异常
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 上传文件
	 *
	 * @param bucketName 桶名
	 * @param file       文件
	 * @return 文件全路径名 上传失败返回null
	 */
	public String uploadFile(String bucketName, MultipartFile file) {
		// 获取当前年月
		String dir1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String objectName = file.getOriginalFilename();
		// 文件名
		try {
			String fileName = dir1 + "/" + UUID.randomUUID() + objectName.substring(objectName.lastIndexOf("."));
			minioClient.putObject(
					PutObjectArgs.builder()
							// 存储桶名称
							.bucket(bucketName)
							//文件在MinIO中的名称
							.object(fileName)
							.stream(file.getInputStream(), file.getSize(), -1)
							.contentType(file.getContentType())
							.build());
			log.info("上传文件成功");
			return fileName;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("上传文件失败");
//			throw new CommonException(MessageConstant.UPLOAD_ERROR);
		}
		return null;
	}


	/**
	 * 获取文件url
	 * @param bucketName
	 * @param objectName
	 * @return
	 */
	public String getFileUrl(String bucketName, String objectName) {
		// 获取文件url前判断是否存在
		boolean b = existFile(bucketName, objectName);
		if (!b) {
			// 文件不存在
			throw new GuiguException(404,"文件不存在");
		}
		try {
			return minioClient.getPresignedObjectUrl(
					GetPresignedObjectUrlArgs.builder()
							.bucket(bucketName)
							.method(Method.GET)
							.object(objectName)
							.expiry(60 * 60 * 24)
							.build()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除文件
	 */
	public void removeFile(String bucketName, String objectName) {
		try {
			minioClient.removeObject(
					RemoveObjectArgs.builder()
							.bucket(bucketName)
							.object(objectName)
							.build()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.atguigu.manager.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
  @Value("${minio.endpoint}")
  String endpoint;
  @Value("${minio.access-key}")
  String accessKey;
  @Value("${minio.secret-key}")
  String secretKey;
  @Value("${minio.bucket.files}")
  public String bucketFiles;

  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder()
      .endpoint(endpoint)
      .credentials(accessKey, secretKey)
      .build();
  }
}
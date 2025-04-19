package com.atguigu.manager;

import com.atguigu.manager.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Hello world!
 *
 */
@EnableConfigurationProperties(value = {UserAuthProperties.class})
@SpringBootApplication
public class ManagerApplication {
    // 启动springboot项目
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
package com.atguigu.manager.config;

import com.atguigu.manager.properties.UserAuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.atguigu.manager.interceptor.LoginAuthInterceptor;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private LoginAuthInterceptor loginAuthInterceptor;

	@Autowired
	private UserAuthProperties userAuthProperties;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginAuthInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns(
						"swagger-ui/**",
						"/swagger-resources/**",
						"/v3/**",
						"/webjars/**",
						"/doc.html"
				).excludePathPatterns(userAuthProperties.getNoAuthUrls());
	}

	/**
	 * 设置静态资源映射
	 *
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/systemPictures/**")
				.addResourceLocations("file:" + System.getProperty("user.dir") + File.separator + "uploadFile" + File.separator + "systemPictures" + File.separator);
		registry.addResourceHandler("/uploadFile/pluginFiles/logo/**")
				.addResourceLocations("file:" + System.getProperty("user.dir") + File.separator + "uploadFile" + File.separator + "pluginFiles" + File.separator + "logo" + File.separator);

		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}

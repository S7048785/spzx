package spzx.common.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
public class GlobalCorsConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // 允许所有路径
						.allowedOrigins("http://localhost:3001") // 明确列出允许的 origins (请求地址)
						.allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
						.allowedHeaders("*") // 允许的请求头
						.allowCredentials(true); // 允许携带凭证 (origins为*才是false)
			}
		};
	}
}
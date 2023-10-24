package com.smartfactory.apiserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @FileName : ServletConfig.java
 * @author : dooho
 * @Class 설명 : Sevlet 설정을 위한 클래스
 */
@EnableWebMvc
@Configuration
public class ServletConfig implements WebMvcConfigurer {

	ServletConfig() {
		super();
	}
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
				.allowedMethods(
						HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name(), HttpMethod.PUT.name(),
						HttpMethod.HEAD.name(), HttpMethod.PATCH.name(), HttpMethod.OPTIONS.name()
				);
    }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

}

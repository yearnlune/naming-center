package yearnlune.lab.namingcenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import yearnlune.lab.namingcenter.service.LogService;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.16
 * DESCRIPTION :
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	final LogService logService;

	public WebMvcConfig(LogService logService) {
		this.logService = logService;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new WebInterceptor(logService));
	}
}

package com.ntkd.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ntkd.interceptor.MyInterceptor;

//spring mvc 配置文件，只扫描Controller
@ComponentScan(value = { "com.ntkd" }, 
			   includeFilters = {@Filter(type = FilterType.ANNOTATION, classes = { Controller.class }) }, 
			    useDefaultFilters = false)
@EnableWebMvc
public class SpringMvcConfig extends WebMvcConfigurerAdapter {
//	定制

	// 视图解析器
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	// 静态资源访问
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// 相当于<mvc:default-servlet-handler/>
		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// /** 任意路径任意请求
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
	}

}

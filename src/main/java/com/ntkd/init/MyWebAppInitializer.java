package com.ntkd.init;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.ntkd.config.SpringConfig;
import com.ntkd.config.SpringMvcConfig;

public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	//获取根容器的配置类 spring容器配置文件
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {SpringConfig.class};
	}

	//获取web容器的配置类 spring mvc 容器配置文件
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {SpringMvcConfig.class};
	}

	//获取servlet的dispatchservlet的mapping
	// /:拦截所有静态资源，不包括*.jsp
	// /*:拦截所有，包括*.jsp
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[]{characterEncodingFilter};
	}

}

### 基于注解的Springmvc项目

只有一个log的xml，其他的都用注解代替

原理：

1. Servlet 3.0有一个Shared libraries / runtimes pluggability特性，

   setvlet容器启动时，会扫描每个jar包里面的`/META-INF/services/javax.servlet.ServletContainerInitializer`，文件的内容指向ServletContainerInitializer的实现类（全类名），然后运行这个实现类里面的方法。

2. 我们看到spring-web-4.3.25.RELEASE.jar包下面有`/META-INF/services/javax.servlet.ServletContainerInitializer`，打开文件，里面有`org.springframework.web.SpringServletContainerInitializer`，我们找到包里面的这个类

   ```java
   package org.springframework.web;
   
   //容器启动就会运行这个类里面的方法
   //@HandlesTypes()里指定的这个类型下面的子类（实现类，子接口等），指定类本身得不到
   //这里扫描到任何WebApplicationInitializer的实现类和子接口
   //我们写一个他的实现类就行了
   @HandlesTypes(WebApplicationInitializer.class)
   public class SpringServletContainerInitializer implements ServletContainerInitializer {
   
   }
   ```

   ```java
   package com.ntkd.init;
   
   import javax.servlet.Filter;
   import org.springframework.web.filter.CharacterEncodingFilter;
   import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
   import com.ntkd.config.SpringConfig;
   import com.ntkd.config.SpringMvcConfig;
   
   //我们要使用注解方式，那么继承AbstractAnnotationConfigDispatcherServletInitializer类
   //AbstractAnnotationConfigDispatcherServletInitializer实现了WebApplicationInitializer
   //所以会被上面的@HandlesTypes(WebApplicationInitializer.class)扫描到，然后容器启动时加载
   //这个文件相当于以前的web.xml
   public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
   
   	//获取根容器的配置类 spring容器配置文件
       //我们定义为SpringConfig.class，相当于以前的applicationContext.xml
   	@Override
   	protected Class<?>[] getRootConfigClasses() {
   		return new Class<?>[] {SpringConfig.class};
   	}
   
   	//获取web容器的配置类 spring mvc 容器配置文件
       //我们定义为SpringMvcConfig.class，相当于springmvc.xml
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
   
   ```

   剩下的就看SpringConfig和SpringMvcConfig配置文件就行了

   其他的文件和原来的都一样

   


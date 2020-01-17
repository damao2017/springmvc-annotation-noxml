package com.ntkd.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;


//spring配置文件，扫描所有包，除了controller
//根容器
@ComponentScan(value= {"com.ntkd"},excludeFilters = {
		@Filter(type=FilterType.ANNOTATION,classes = {Controller.class})
})
//@PropertySource(value= {"classpath:mysql.properties"},encoding = "UTF-8")
//开启事务
@EnableTransactionManagement
//扫描mapper的接口
@MapperScan(basePackages = "com.ntkd.mapper") 
public class SpringConfig {
	
	@Bean
	public DataSource dataSource() {
		
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		druidDataSource.setUrl("jdbc:mysql://192.168.14.131:13306/permission");
		druidDataSource.setUsername("ntkd");
		druidDataSource.setPassword("ntkd");
		druidDataSource.setMaxActive(5);
		druidDataSource.setMinIdle(2);
		druidDataSource.setMaxWait(60000);
		druidDataSource.setDefaultAutoCommit(true);
		druidDataSource.setRemoveAbandoned(true);
		druidDataSource.setRemoveAbandonedTimeout(60);
		
		return druidDataSource;
	}
	
	
	//mybatis的配置文件
	@Bean
	public Configuration configuration() {
		Configuration configuration = new Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		return configuration;
		
	}
 	
	//mybatis SqlSessionFactoryBean 配置
	@Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource,Configuration configuration) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfiguration(configuration);
        
        //启用分页 插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("helperDialect", "mysql");
        pageInterceptor.setProperties(properties);
        
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});
   
        return sqlSessionFactoryBean;
    }
	
	//事务管理器
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) throws PropertyVetoException {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);
		return dataSourceTransactionManager;
	}


}

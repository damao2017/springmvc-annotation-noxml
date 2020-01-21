package com.ntkd.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.event.support.SingleArgumentMethodEventListener;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;

import com.ntkd.shiro.MyAuthorizingRealm;
import com.ntkd.shiro.MyCredentialsMatcher;


public class SpringShiro {
	
	@Autowired
	private EhCacheManagerFactoryBean ehCacheManagerFactoryBean;
	
	//自定义验证器
	@Bean
	public MyCredentialsMatcher myCredentialsMatcher() {
		return new MyCredentialsMatcher();
	}
	
	//自定义realm
	@Bean
	public MyAuthorizingRealm myAuthorizingRealm() {
		MyAuthorizingRealm myAuthorizingRealm = new MyAuthorizingRealm();
		myAuthorizingRealm.setCredentialsMatcher(myCredentialsMatcher());
		return myAuthorizingRealm;
	}


	
	//增加ehcache缓存
	@Bean
	public EhCacheManager MyEhCacheManager() {

        EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManager(ehCacheManagerFactoryBean.getObject());
		return ehCacheManager; 
	}
	
	//session管理器
	@Bean
	public DefaultWebSessionManager defaultWebSessionManager() {
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		//默认30分钟
		defaultWebSessionManager.setGlobalSessionTimeout(3*24*60*60*1000);
		//删除失效的session
		defaultWebSessionManager.setDeleteInvalidSessions(true);
		return null;
	}
	
	//用户表必须实现 序列化接口 Serializable
	//rememberMe:设置cookie
	@Bean
	public SimpleCookie simpleCookie() {
		SimpleCookie simpleCookie = new SimpleCookie();
		simpleCookie.setMaxAge(7*24*60*60);
		simpleCookie.setName("myshrio");
		return simpleCookie;
	}
	
	//rememberMe:记住我
	@Bean
	public CookieRememberMeManager cookieRememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(simpleCookie());
		return cookieRememberMeManager;
	}
	
	@Bean
	public DefaultWebSecurityManager defaultWebSecurityManager() {
	        
	        DefaultWebSecurityManager securityManager = 
	            new DefaultWebSecurityManager();
	        // 关联realm.
	        securityManager.setRealm(myAuthorizingRealm());
	        //设置ehcache缓存
	        securityManager.setCacheManager(MyEhCacheManager());
	        //rememberMe:设置rememberMe
	        securityManager.setRememberMeManager(cookieRememberMeManager());
	        return securityManager;
	    }
	
	//这个类传递给MyWebAppInitializer的DelegatingFilterProxy代理类
	@Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置 SecurityManager
        bean.setSecurityManager(defaultWebSecurityManager());
		// 设置登录成功页面
        bean.setSuccessUrl("/main");
        // 设置登录页面
        bean.setLoginUrl("/login");
        // 设置未授权提示页面
        bean.setUnauthorizedUrl("/unAuth");
        
        /**
         * anon：匿名用户可访问
         * authc：认证用户可访问
         * user：使用rememberMe可访问
         * perms：对应权限可访问
         * role：对应角色权限可访问
         **/
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/assets/**","anon");
        filterMap.put("/login","anon");
        filterMap.put("/userLogin","anon");
        filterMap.put("/logout", "logoutFilter");
        
        //rememberMe:user的remember能查看
        filterMap.put("/main","user");
        
        filterMap.put("/**","authc");
        
        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }
	
	//自定义filterMap.put("/logout", "logout");的跳转页面，默认是/
	@Bean
	public LogoutFilter logoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setRedirectUrl("/login");
		return logoutFilter;
	}
	
	//权限注解的advisor
	//需要在springmvc中开启aop支持 
	//@EnableAspectJAutoProxy(proxyTargetClass=true)
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager());
		return authorizationAttributeSourceAdvisor;
	}

	




}

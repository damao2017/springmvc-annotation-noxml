package com.ntkd.test;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ntkd.config.SpringConfig;
import com.ntkd.entity.User;
import com.ntkd.mapper.UserMapper;

/**
 *  @description 
 *	@author NTDM
 *	@date 2020年1月14日 下午4:16:15
 *
 */
public class MainTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringConfig.class);

		
		System.out.println("========================");
		String[] names = applicationContext.getBeanDefinitionNames();

		for (String name : names)
			System.out.println("==>"+name);
		System.out.println("========================");
		
		UserMapper userMapper = applicationContext.getBean(UserMapper.class);
		List<User> userList = userMapper.getUserList();
		for (User user : userList) {
			System.out.println(user);
		}
		applicationContext.close();
	}

}

package com.ntkd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ntkd.service.HelloService;

@Controller
public class HelloController {

	@Autowired
	HelloService helloService;

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		System.out.println("===========");
		return helloService.sayHello("tomcat");
	}

	@RequestMapping("/success")
	public String success() {

		return "success";
	}
}

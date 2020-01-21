package com.ntkd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {


	@ResponseBody
	@RequestMapping("/main")
	public String main() {
		return "main";
	}

	@ResponseBody
	@RequestMapping("/login")
	public String login() {

		return "login";
	}

	@ResponseBody
	@RequestMapping("/unAuth")
	public String unAuth() {

		return "unAuth";
	}
	

	
}

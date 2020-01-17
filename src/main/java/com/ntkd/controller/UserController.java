package com.ntkd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ntkd.common.Page;
import com.ntkd.entity.User;
import com.ntkd.service.UserService;

/**
 *  @description 
 *	@author NTDM
 *	@date 2020年1月14日 上午9:26:38
 *
 */

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@ResponseBody
	@RequestMapping("/userAdd")
	public String userAdd() {
		User user = new User(111,"asd",23);
		userService.insertUser(user);
		return "OK";
	}
	
	@ResponseBody
	@RequestMapping("/userListAll")
	public Object userListAll(@RequestParam(required = false, defaultValue = "0") int start,
			@RequestParam(required = false, defaultValue = "2") int limit) {
		
		Page retultPage = new Page();

		PageHelper.startPage(start / limit + 1, limit);
		List<User> all = userService.userListAll();

		PageInfo<User> page = new PageInfo<User>(all);

		retultPage.setList(all);
		retultPage.setTotal(page.getTotal());

		return retultPage;
		
	}

}

package com.ntkd.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
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
	
	//自己写loginin
	@RequestMapping("/userLogin")
	public String userLogin(String name,String password,boolean remenber) {
		UsernamePasswordToken token = new UsernamePasswordToken(name, password); 
		if(remenber)
			token.setRememberMe(true);
		
		Subject currentUser = SecurityUtils.getSubject(); 
		currentUser.login(token);  
		
	
		
		
        System.out.println("->登录成功！currentUser.getPrincipal()： [" + currentUser.getPrincipal() + "] ");
        
        System.out.println("->是都有user:list权限："+currentUser.isPermitted("user:list"));
		return "redirect:/main";
	}
	//自己写logout
//	@RequestMapping("/userLogout")
//    public String userLogout(){   
//        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息  
//        SecurityUtils.getSubject().logout();      
//        return "redirect:/login";  
//    }   
	
	
	@ResponseBody
	@RequestMapping("/userAdd")
	public String userAdd() {
		User user = new User(111,"asd",23,"ssss");
		userService.insertUser(user);
		return "OK";
	}
	
	@RequiresPermissions("user:list")
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

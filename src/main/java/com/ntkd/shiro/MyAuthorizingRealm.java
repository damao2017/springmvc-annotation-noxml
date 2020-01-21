package com.ntkd.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.ntkd.entity.User;

/**
 *  @description 
 *	@author NTDM
 *	@date 2020-01-16 15:14:07
 *
 */

//AuthorizingRealm包含了AuthenticatingRealm的功能
public class MyAuthorizingRealm extends AuthorizingRealm{

	public void setName() {
		super.setName("permissionRealm");
	}
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		
		System.out.println("--->执行授权");
		
		User user = (User)principals.getPrimaryPrincipal();
		
		System.out.println("--->得到用户："+user);
		//根据用户信息，从数据库中查询
		//模拟数据
		List<String> permissions = new ArrayList<String>();
		permissions.add("user:add");
		permissions.add("user:list");
		permissions.add("user:update");

		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		info.addStringPermissions(permissions);
		
		return info;
	}

	//认证
	//如果成功，返回token
	//如果失败，返回null
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		System.out.println("--->执行认证");
		//得到用户名
		String username = (String)token.getPrincipal();
		System.out.println("--->得到用户名token.getPrincipal()："+token.getPrincipal().toString());
		
//		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
//		System.out.println("--->UsernamePasswordToken："+upToken);
//		
//		System.out.println("--->UsernamePasswordToken.getUsername："+upToken.getUsername());
//		System.out.println("--->UsernamePasswordToken.getPassword："+new String(upToken.getPassword()));
//		//得到用户名
//		String username = upToken.getUsername();
		
		
		if(!StringUtils.isEmpty(username)){
			
			if(username.equals("aa")) {
				//这里写取数据区查询的代码，通过username查询得到用户
				
				User user = new User(12000,"aa",11,"11");
				//5c9f9903627f3b3b55d776a055189e44 11加盐aa，一次散列得到
				//User user = new User(12000,"aa","5c9f9903627f3b3b55d776a055189e44");
				System.out.println("--->得到用户，进入验证");
				return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
				//return new SimpleAuthenticationInfo(user, user.getPassword(),ByteSource.Util.bytes(username), getName());
			}
        }

		return null;
	}
	
	

}

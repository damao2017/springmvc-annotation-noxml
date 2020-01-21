package com.ntkd.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @description
 * @author NTDM
 * @date 2020-01-19 16:12:28
 *
 */
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

//		Object tokenCredentials = getCredentials(token);
//      Object accountCredentials = getCredentials(info);
		
		System.out.println("----->进入MyCredentialsMatcher！");
		
		//用户输入的
		Object tokenCredentials = getCredentials(token);
		
		//转化之后可以查看到具体的账号密码
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		System.out.println("----->tokenCredentials:"+new String(upToken.getUsername()));
		System.out.println("----->tokenCredentials:"+new String(upToken.getPassword()));
		
		//数据库查询到的密码
		Object accountCredentials = getCredentials(info);
		System.out.println("----->accountCredentials:"+accountCredentials);
		
		//两个得到之后，随便怎么比较，最后return true或者false就行了
		
		//这里还是默认
		return equals(tokenCredentials, accountCredentials);
	}

}

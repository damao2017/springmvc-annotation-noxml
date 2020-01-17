package com.ntkd.mapper;

import java.util.List;

import com.ntkd.entity.User;

/**
 *  @description 
 *	@author NTDM
 *	@date 2020年1月14日 下午4:04:39
 *
 */
//@Mapper
public interface UserMapper {
	
//	@Select("SELECT * FROM sys_user")
    List<User> getUserList();

	int insertUser(User user);

}

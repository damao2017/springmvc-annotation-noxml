package com.ntkd.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ntkd.entity.User;
import com.ntkd.mapper.UserMapper;

@Service
public class UserService {

	
	@Autowired
	private UserMapper userMapper;


	public List<User> userListAll() {
		return userMapper.getUserList();
		
	}


	public Object insertUser(User user) {
		return userMapper.insertUser(user);
		
	}
	
	
	
	
}
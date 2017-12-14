package com.sf.iguess.survey.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sf.iguess.survey.domain.User;
import com.sf.iguess.survey.mapper.UserDao;
import com.sf.iguess.survey.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;

	@Override
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	
	

}

package com.sf.iguess.survey.service;

import com.sf.iguess.survey.domain.User;

public interface UserService {
	
	public User findByUserName(String userName);

}

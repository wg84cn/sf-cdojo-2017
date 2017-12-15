package com.sf.iguess.survey.service;

import javax.servlet.http.HttpSession;

import com.sf.iguess.survey.domain.User;

public interface UserService {
	
	public User findByUserName(String userName);

    public String getUserEncodePassword(String password, String userSalt, HttpSession session)throws Exception;

}

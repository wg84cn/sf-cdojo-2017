package com.sf.iguess.survey.service.impl;

import java.security.interfaces.RSAPrivateKey;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.sf.iguess.survey.domain.User;
import com.sf.iguess.survey.mapper.UserDao;
import com.sf.iguess.survey.service.UserService;
import com.smart.platform.constant.HttpConstant;
import com.smart.platform.formative.RSACryptor;
import com.smart.platform.encrypt.UserDataEncrypt;


@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;

	@Override
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	
	public String getUserEncodePassword(String password, String userSalt, HttpSession session) throws Exception {
        RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute(HttpConstant.RSA_PRIVATE_KEY);
        String userDecodePassword = RSACryptor.decrypttoStr(privateKey, password);
        return UserDataEncrypt.getUserEncrypter().encryptSensitive(userDecodePassword, userSalt);
	}
}

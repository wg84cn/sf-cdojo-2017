package com.sf.iguess.survey.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sf.iguess.survey.domain.ExpressDelivery;
import com.sf.iguess.survey.domain.User;
import com.sf.iguess.survey.mapper.UserDao;
import com.sf.iguess.survey.service.UserService;

/**
 * ClassName: ExpressDeliveryServiceImpl <br/>
 * Function: 用户业务实现类. <br/>
 * date: 2017年12月15日 下午3:05:25 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	private static final String PIC_URL = 
			"http://sfsrc.sf-express.com/common/images/personal_add/portrait_c_120.png";
	
	/**
	 * 新增用户.
	 * @see com.sf.iguess.survey.service.UserService#saveUserByExpressDelivery(com.sf.iguess.survey.domain.ExpressDelivery)
	 */
	@Override
	public void saveUserByExpressDelivery(ExpressDelivery ed) {
		String userId = ed.getPhoneNumber();
		if(userDao.selectByPrimaryKey(userId) == null){
			User user = new User();
			user.setUserId(userId);
			user.setUserName(ed.getUserName());
			user.setPicUrl(PIC_URL);
			user.setCreateTime(new Date());
			userDao.insert(user);
		}
	}
}


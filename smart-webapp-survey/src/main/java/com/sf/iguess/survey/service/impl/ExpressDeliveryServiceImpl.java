package com.sf.iguess.survey.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sf.iguess.survey.domain.ExpressDelivery;
import com.sf.iguess.survey.domain.StoreGoods;
import com.sf.iguess.survey.domain.User;
import com.sf.iguess.survey.mapper.ExpressDeliveryDao;
import com.sf.iguess.survey.mapper.StoreGoodsDao;
import com.sf.iguess.survey.mapper.UserDao;
import com.sf.iguess.survey.service.AreaService;
import com.sf.iguess.survey.service.ExpressDeliveryService;
import com.sf.iguess.survey.service.StroreService;
import com.smart.platform.toolkit.PropertyPlaceholderConfigurer;
import com.smart.platform.toolkit.RegularMatcher;


@Service
public class ExpressDeliveryServiceImpl implements ExpressDeliveryService {
	
	@Resource
	private ExpressDeliveryDao expressDeliveryDao;
	@Resource
	private StoreGoodsDao storeGoodsDao;
	@Resource
	private UserDao userDao;
	@Resource
	private AreaService areaService;
	@Resource
	private StroreService stroreService;
	
	private static final int MAX_DAY_EXPRESS_NUMBER = 20;
	private static final String STORE_GOOD_DETAIL_URL = "store.good.detail.url";

	/**
	 * 保存寄件人信息，同时加入团.
	 * @see com.sf.iguess.survey.service.ExpressDeliveryService#save(com.sf.iguess.survey.domain.ExpressDelivery)
	 */
	@Override
	public StoreGoods save(String storeId,ExpressDelivery expressDelivery) {
		StoreGoods storeGood = storeGoodsDao.selectByPrimaryKey(storeId);
		//判断参数是否合法
		if(storeGood!=null && checkSendMsg(expressDelivery,storeGood)){
			//更新集货信息
			storeId = stroreService.updateStoreGroupStatus(storeGood);
			//保存storeId
			expressDelivery.setStoreId(storeId);
			expressDeliveryDao.insert(expressDelivery);
			//新增用户
			String userId = expressDelivery.getPhoneNumber();
			if(userDao.selectByPrimaryKey(userId) != null){
				User user = new User();
				user.setUserId(userId);
				user.setUserName(expressDelivery.getUserName());
				userDao.insert(user);
			}
			//加入返回链接
			String url = PropertyPlaceholderConfigurer
					.getConfigValue(STORE_GOOD_DETAIL_URL) + storeId;
			StoreGoods newStore = storeGoodsDao.selectByPrimaryKey(storeId);
			newStore.setUrl(url);
			return newStore;
		}
		return null;
	}

	@Override
	public boolean checkSendMsg(ExpressDelivery expressDelivery,StoreGoods storeGood){
		return this.checkDetailAddress(expressDelivery.getDetailAddress())
			&& this.checkUserName(expressDelivery.getUserName()) 
			&& this.checkAvgWeight(expressDelivery.getAvgWeight(),
					storeGood.getWeightMin(),storeGood.getWeightMax())
			&& this.checkPhoneNumber(expressDelivery.getPhoneNumber())
			&& this.checkUserArea(expressDelivery.getUserArea())
			&& this.checkDayExpressNumber(expressDelivery.getDayExpressNumber());
	}

	
	private boolean checkDayExpressNumber(Integer dayExpressNumber) {
		return (dayExpressNumber != null 
			&& dayExpressNumber.intValue() < MAX_DAY_EXPRESS_NUMBER);
	}

	private boolean checkUserArea(String userArea) {
		return areaService.isExist(userArea);
	}
	
	private boolean checkPhoneNumber(String phoneNumber) {
		return RegularMatcher.checkMobileNumber(phoneNumber);
	}

	private boolean checkAvgWeight(Double avgWeight, Double min, Double max) {
		return avgWeight!=null 
				&& avgWeight.doubleValue() >= min.doubleValue()
				&& avgWeight.doubleValue() <= max.doubleValue();
	}

	private boolean checkUserName(String userName) {
		return StringUtils.isNotBlank(userName)
				&& userName.length() < 100;
	}

	private boolean checkDetailAddress(String detailAddress) {
		return StringUtils.isNotBlank(detailAddress)
				&& detailAddress.length() < 2000;
	}
	
}

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

/**
 * ClassName: ExpressDeliveryServiceImpl <br/>
 * Function: 寄件服务实现类. <br/>
 * date: 2017年12月15日 下午3:05:25 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
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
			String newStoreId = stroreService.updateStoreGroupStatus(storeGood);
			//保存storeId
			expressDelivery.setStoreId(newStoreId);
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
					.getConfigValue(STORE_GOOD_DETAIL_URL) + newStoreId;
			StoreGoods newStore = storeGoodsDao.selectByPrimaryKey(newStoreId);
			newStore.setUrl(url);
			return newStore;
		}
		return null;
	}

	/**
	 * 寄件信息检查
	 * @see com.sf.iguess.survey.service.ExpressDeliveryService#checkSendMsg(com.sf.iguess.survey.domain.ExpressDelivery, com.sf.iguess.survey.domain.StoreGoods)
	 */
	@Override
	public boolean checkSendMsg(ExpressDelivery expressDelivery,StoreGoods storeGood){
		return this.checkUserInfo(expressDelivery) 
				&& this.checkAddress(expressDelivery)
				&& this.checkCargo(expressDelivery, storeGood);
	}
	
	/**
	 * checkCargo:.检查货物相关的信息 <br/>
	 *
	 * @author 618721
	 * @param expressDelivery
	 * @param storeGood
	 * @return
	 * @since JDK 1.8
	 */
	private boolean checkCargo(ExpressDelivery expressDelivery,StoreGoods storeGood){
		return this.checkAvgWeight(expressDelivery.getAvgWeight(),
				storeGood.getWeightMin(),storeGood.getWeightMax())
				&& this.checkDayExpressNumber(expressDelivery.getDayExpressNumber());
	}
	
	/**
	 * checkUserInfo:检查用户信息. <br/>
	 *
	 * @author 618721
	 * @param expressDelivery
	 * @return
	 * @since JDK 1.8
	 */
	public boolean checkUserInfo(ExpressDelivery expressDelivery){
		return this.checkUserName(expressDelivery.getUserName()) 
				&& this.checkPhoneNumber(expressDelivery.getPhoneNumber());
	}
	
	/**
	 * checkAddress:检查地址相关信息. <br/>
	 *
	 * @author 618721
	 * @param expressDelivery
	 * @return
	 * @since JDK 1.8
	 */
	public boolean checkAddress(ExpressDelivery expressDelivery){
		return this.checkDetailAddress(expressDelivery.getDetailAddress())
				&& this.checkUserArea(expressDelivery.getUserArea());
	}
	
	/**
	 * checkDayExpressNumber:检查每天件量. <br/>
	 *
	 * @author 618721
	 * @param dayExpressNumber
	 * @return
	 * @since JDK 1.8
	 */
	private boolean checkDayExpressNumber(Integer dayExpressNumber) {
		return (dayExpressNumber != null 
			&& dayExpressNumber.intValue() < MAX_DAY_EXPRESS_NUMBER);
	}

	/**
	 * checkUserArea:用户地区校验. <br/>
	 *
	 * @author 618721
	 * @param userArea
	 * @return
	 * @since JDK 1.8
	 */
	private boolean checkUserArea(String userArea) {
		return areaService.isExist(userArea);
	}
	
	/**
	 * checkPhoneNumber:检查用户电话号码. <br/>
	 *
	 * @author 618721
	 * @param phoneNumber
	 * @return
	 * @since JDK 1.8
	 */
	private boolean checkPhoneNumber(String phoneNumber) {
		return RegularMatcher.checkMobileNumber(phoneNumber);
	}

	/**
	 * checkAvgWeight:校验平均重量. <br/>
	 *
	 * @author 618721
	 * @param avgWeight
	 * @param min
	 * @param max
	 * @return
	 * @since JDK 1.8
	 */
	private boolean checkAvgWeight(Double avgWeight, Double min, Double max) {
		return avgWeight!=null 
				&& avgWeight.doubleValue() >= min.doubleValue()//最小
				&& avgWeight.doubleValue() <= max.doubleValue();//最大
	}

	/**
	 * checkUserName:校验用户名. <br/>
	 *
	 * @author 618721
	 * @param userName
	 * @return
	 * @since JDK 1.8
	 */
	private boolean checkUserName(String userName) {
		return StringUtils.isNotBlank(userName)
				&& userName.length() < 100;
	}

	/**
	 * checkDetailAddress:校验详细地址. <br/>
	 *
	 * @author 618721
	 * @param detailAddress
	 * @return
	 * @since JDK 1.8
	 */
	private boolean checkDetailAddress(String detailAddress) {
		return StringUtils.isNotBlank(detailAddress)
				&& detailAddress.length() < 2000;
	}
	
}

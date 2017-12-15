package com.sf.iguess.survey.service;

import java.util.List;

import com.sf.iguess.survey.domain.ExpressDelivery;
import com.sf.iguess.survey.domain.StoreGoods;
import com.sf.iguess.survey.domain.User;

/**
 * ClassName: ExpressDeliveryService <br/>
 * Function: 寄件业务类. <br/>
 * date: 2017年12月15日 下午3:18:23 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
public interface ExpressDeliveryService {
	
	/**
	 * save:保存寄件人信息，同时加入团.<br/>
	 *
	 * @author 618721
	 * @param storeId
	 * @param expressDelivery
	 * @return
	 * @since JDK 1.8
	 */
	public StoreGoods save(String storeId, ExpressDelivery expressDelivery);
	
	/**
	 * checkSendMsg:寄件信息检查. <br/>
	 *
	 * @author 618721
	 * @param expressDelivery
	 * @param storeGood
	 * @return
	 * @since JDK 1.8
	 */
	public boolean checkSendMsg(ExpressDelivery expressDelivery,StoreGoods storeGood);
	
	/**
	 * getExpresssUserList:根据集货团得到用户. <br/>
	 *
	 * @author 618721
	 * @param storeId
	 * @return
	 * @since JDK 1.8
	 */
	public List<User> getExpresssUserList(String storeId);
}

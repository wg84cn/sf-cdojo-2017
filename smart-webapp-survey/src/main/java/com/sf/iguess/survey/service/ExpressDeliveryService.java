package com.sf.iguess.survey.service;

import com.sf.iguess.survey.domain.ExpressDelivery;
import com.sf.iguess.survey.domain.StoreGoods;

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

	public StoreGoods save(String storeId, ExpressDelivery expressDelivery);
	
	public boolean checkSendMsg(ExpressDelivery expressDelivery,StoreGoods storeGood);
	
}

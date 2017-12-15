package com.sf.iguess.survey.service;

import com.sf.iguess.survey.domain.ExpressDelivery;
import com.sf.iguess.survey.domain.StoreGoods;

public interface ExpressDeliveryService {
	

	public boolean save(String storeId, ExpressDelivery expressDelivery);
	
	public boolean checkSendMsg(ExpressDelivery expressDelivery,StoreGoods storeGood);
	
}

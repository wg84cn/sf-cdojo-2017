package com.sf.iguess.survey.service;

import java.util.List;

import com.sf.iguess.survey.domain.StoreGoods;

public interface StroreService {
	
	List<StoreGoods> selectActiveStoreList();
	
	StoreGoods selectStoreGood(String stroeId);

	void checkStoreGoodsStatus();

	void autoCreateStoreGoods();
	
	String updateStoreGroupStatus(StoreGoods goods);
}

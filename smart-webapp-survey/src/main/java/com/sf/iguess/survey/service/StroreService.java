package com.sf.iguess.survey.service;

import java.util.List;

import com.sf.iguess.survey.domain.StoreGoods;

public interface StroreService {
	
	List<StoreGoods> selectStoreList();
	
	StoreGoods selectStoreGood(Long stroeId);

	void checkStoreGoodsStatus();

	void autoCreateStoreGoods();
}

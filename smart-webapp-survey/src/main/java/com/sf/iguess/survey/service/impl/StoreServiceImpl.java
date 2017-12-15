package com.sf.iguess.survey.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.sf.iguess.survey.domain.StoreGoods;
import com.sf.iguess.survey.mapper.StoreGoodsDao;
import com.sf.iguess.survey.service.StroreService;

@Service
public class StoreServiceImpl implements StroreService {
	
	@Resource
	private StoreGoodsDao storeGoodsDao;

	@Override
	public List<StoreGoods> selectStoreList() {
		return storeGoodsDao.selectStoreList();
	}

	@Override
	public StoreGoods selectStoreGood(Long stroeId) {
		return storeGoodsDao.selectByPrimaryKey(stroeId);
	}

}

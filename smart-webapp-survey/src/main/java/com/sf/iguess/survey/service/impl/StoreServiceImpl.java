package com.sf.iguess.survey.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sf.iguess.survey.domain.MarketBasicInfo;
import com.sf.iguess.survey.domain.StoreGoods;
import com.sf.iguess.survey.mapper.MarketBasicInfoDao;
import com.sf.iguess.survey.mapper.StoreGoodsDao;
import com.sf.iguess.survey.service.StroreService;
import com.smart.platform.toolkit.UuidUtil;

@Service
public class StoreServiceImpl implements StroreService {
	
	@Resource
	private StoreGoodsDao storeGoodsDao;
	
	@Resource
	private MarketBasicInfoDao marketBasicInfoDao;
	
	private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

	@Override
	public List<StoreGoods> selectStoreList() {
		return storeGoodsDao.selectStoreList();
	}

	@Override
	public StoreGoods selectStoreGood(String stroeId) {
		return storeGoodsDao.selectByPrimaryKey(stroeId);
	}
	
	@Override
	public String updateStoreGroupStatus(StoreGoods goods) {
		MarketBasicInfo marketInfo = marketBasicInfoDao.selectByPrimaryKey(goods.getMarketId());
		Integer updateNumber = storeGoodsDao.updateStoreGroupStatus(marketInfo.getGroupLimit());
		String storeId = goods.getStoreId();
		if(updateNumber == null || updateNumber == 0){
			logger.warn("update store group error as group is above limit.");
			storeId = UuidUtil.get32UUID();
			storeGoodsDao.insertSelective(new StoreGoods(storeId, goods.getMarketId(), 1));
		}
		return storeId;
	}
	
	@Override
	public void checkStoreGoodsStatus() {
		
	}

	@Override
	public void autoCreateStoreGoods() {
		List<MarketBasicInfo> maketBasicList = marketBasicInfoDao.selectMaketBasicList();
		for(MarketBasicInfo marketBasic:maketBasicList){
			List<StoreGoods>  storeGoods = storeGoodsDao.selectStoreByMarketId(marketBasic.getMktId());
			if(storeGoods != null && !storeGoods.isEmpty()){
				continue;
			}
			storeGoodsDao.insertSelective(new StoreGoods(UuidUtil.get32UUID(), marketBasic.getMktId() ,0));
		}
	}
}

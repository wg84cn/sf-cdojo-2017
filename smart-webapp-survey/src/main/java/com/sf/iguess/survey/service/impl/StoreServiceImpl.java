package com.sf.iguess.survey.service.impl;

import java.util.List;
import javax.annotation.Resource;

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

	@Override
	public List<StoreGoods> selectStoreList() {
		return storeGoodsDao.selectStoreList();
	}

	@Override
	public StoreGoods selectStoreGood(Long stroeId) {
		return storeGoodsDao.selectByPrimaryKey(stroeId);
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
			storeGoodsDao.insertSelective(new StoreGoods(UuidUtil.get32UUID(), marketBasic.getMktId()));
		}
	}
}

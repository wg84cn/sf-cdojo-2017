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
	
	/* 
	 * 1. 更新集货信息
	 * 2. 更新失败，查找是否新增集货，更新成功直接返回 (获取已满)
	 * 3. 如果有新增集货的信息，按照新的集货标记更新
	 * 4. 如果没有新增的集货物信息，生成新的集货标记进行更新
	 * 5. 返回新的集货ID
	 */
	@Override
	public String updateStoreGroupStatus(StoreGoods goods) {
		MarketBasicInfo marketInfo = marketBasicInfoDao.selectByPrimaryKey(goods.getMarketId());
		Integer updateNumber = storeGoodsDao.updateStoreGroupStatus(goods.getStoreId(), marketInfo.getGroupLimit());
		if (updateNumber != null && updateNumber > 0) {
			return goods.getStoreId();
		}
		logger.warn("update store group error as group is above limit, system will add new store goods record");
		// 判断新的集货物是否已经被新增
		List<StoreGoods> newGoodsList = storeGoodsDao.selectActiveStoreGoods(goods.getMarketId(), marketInfo.getGroupLimit());
		StoreGoods newGoods = newGoodsList.get(0);
		String storeId = null;
		if (newGoods == null) {
			storeId = UuidUtil.get32UUID();
			storeGoodsDao.insertSelective(new StoreGoods(storeId, goods.getMarketId(), 1));
		} else {
			storeId = newGoods.getStoreId();
			storeGoodsDao.updateStoreGroupStatus(storeId, marketInfo.getGroupLimit());
		}
		return storeId;
	}
	
	/*  
	 * 1. 集货信息过期更新
	 */
	@Override
	public void checkStoreGoodsStatus() {
		// 获取状态为活跃状态集货记录
		List<MarketBasicInfo> maketBasicList = marketBasicInfoDao.selectMaketBasicList();
		for(MarketBasicInfo marketBasic:maketBasicList){
			List<StoreGoods>  storeGoodsList = storeGoodsDao.selectActiveStoreGoods(marketBasic.getMktId(), null);
			if(storeGoodsList != null && !storeGoodsList.isEmpty()){
				continue;
			}
			byte duration = marketBasic.getGroupDuration();
			//过期
			for(StoreGoods storeGoods:storeGoodsList){
				if(storeGoods.initMinuteDuration() >= duration){
					storeGoods.setStatus(StoreGoods.OVERDUE_STATUS);
					storeGoodsDao.updateByPrimaryKey(storeGoods);
				}
			}
		}
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

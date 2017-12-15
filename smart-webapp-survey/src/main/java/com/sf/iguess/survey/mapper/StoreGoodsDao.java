package com.sf.iguess.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sf.iguess.survey.domain.StoreGoods;

@Mapper
public interface StoreGoodsDao {

    int deleteByPrimaryKey(Long storeId);

    int insert(StoreGoods record);

    int insertSelective(StoreGoods record);

    StoreGoods selectByPrimaryKey(@Param("storeId")String storeId);

    int updateByPrimaryKeySelective(StoreGoods record);

	List<StoreGoods> selectStoreByMarketId(@Param("mktId")String mktId);

	Integer updateStoreGroupStatus(@Param("storeId")String storeId, @Param("goupLimit")Short goupLimit);

	List<StoreGoods> selectActiveStoreGoods(@Param("marketId")String marketId, @Param("groupLimit")Short groupLimit);

	void updateStoreFullStatus(@Param("storeId")String storeId, @Param("goupLimit")Short groupLimit, @Param("fullStatus")Short fullStatus);
}
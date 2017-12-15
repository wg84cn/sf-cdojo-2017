package com.sf.iguess.survey.mapper;

import java.util.List;

import com.sf.iguess.survey.domain.MarketBasicInfo;

public interface MarketBasicInfoDao {

    int deleteByPrimaryKey(String mktId);

    int insert(MarketBasicInfo record);

    int insertSelective(MarketBasicInfo record);

    MarketBasicInfo selectByPrimaryKey(String mktId);

    int updateByPrimaryKeySelective(MarketBasicInfo record);

    int updateByPrimaryKeyWithBLOBs(MarketBasicInfo record);

    int updateByPrimaryKey(MarketBasicInfo record);

	List<MarketBasicInfo> selectMaketBasicList();
}
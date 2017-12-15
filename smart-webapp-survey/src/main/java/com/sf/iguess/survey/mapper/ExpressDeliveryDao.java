package com.sf.iguess.survey.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sf.iguess.survey.domain.ExpressDelivery;

@Mapper
public interface ExpressDeliveryDao {

    int insert(ExpressDelivery record);

    int insertSelective(ExpressDelivery record);
}
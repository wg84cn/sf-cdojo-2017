package com.sf.iguess.survey.mapper;

import com.sf.iguess.survey.domain.ExpressDelivery;

public interface ExpressDeliveryDao {

    int insert(ExpressDelivery record);

    int insertSelective(ExpressDelivery record);
}
package com.sf.iguess.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sf.iguess.survey.domain.ExpressDelivery;
import com.sf.iguess.survey.domain.StoreGoods;
import com.sf.iguess.survey.domain.User;

@Mapper
public interface ExpressDeliveryDao {

    int insert(ExpressDelivery record);

    int insertSelective(ExpressDelivery record,StoreGoods storeGood);

	List<User> getExpressUserList(@Param("storeId")String storeId);
}
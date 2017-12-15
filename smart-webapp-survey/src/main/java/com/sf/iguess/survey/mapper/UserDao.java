package com.sf.iguess.survey.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sf.iguess.survey.domain.User;

@Mapper
public interface UserDao {
	
    User findByUserName(String userName);
    
    User selectByPrimaryKey(String userId);
    
    int insert(User record);
    
}
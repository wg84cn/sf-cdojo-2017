package com.sf.iguess.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sf.iguess.survey.domain.Page;
import com.sf.iguess.survey.domain.UserScore;

@Mapper
public interface UserScoreDao {
	public List<UserScore> findAll();
	
	public UserScore findById(String scoreId);
	
	public boolean save(UserScore userScore);
	
	public boolean update(UserScore userScore);
	
	public Integer count();
	
	public List<UserScore> findByPage(Page page);
}
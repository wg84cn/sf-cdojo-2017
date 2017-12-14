package com.sf.iguess.survey.service;

import java.util.List;

import com.sf.iguess.survey.domain.Page;
import com.sf.iguess.survey.domain.UserScore;

public interface UserScoreService {
	
	public UserScore findById(String scoreId);

	public List<UserScore> findAll();
	
	public boolean save(UserScore userScore);
	
	public boolean update(UserScore userScore);
	
	public Integer count();
	
	public List<UserScore> findByPage(Page page);
	
	public void pageInit(Page page);
}

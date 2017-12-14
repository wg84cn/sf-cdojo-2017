package com.sf.iguess.survey.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sf.iguess.survey.domain.Page;
import com.sf.iguess.survey.domain.UserScore;
import com.sf.iguess.survey.mapper.UserScoreDao;
import com.sf.iguess.survey.service.UserScoreService;

@Service
public class UserScoreServiceImpl implements UserScoreService {
	
	@Resource
	private UserScoreDao userScoreDao;

	@Override
	public UserScore findById(String scoreId) {
		return userScoreDao.findById(scoreId);
	}

	@Override
	public List<UserScore> findAll() {
		return userScoreDao.findAll();
	}

	@Override
	public boolean save(UserScore userScore) {
		String scoreId = userScore.getScoreId();
		if(null == scoreId){
			userScore.setScoreId(UUID.randomUUID().toString());
			return userScoreDao.save(userScore);
		}else{
			return userScoreDao.update(userScore);
		}
	}
	
	@Override
	public boolean update(UserScore userScore) {
		return userScoreDao.update(userScore);
	}

	@Override
	public Integer count() {
		return userScoreDao.count();
	}

	@Override
	public List<UserScore> findByPage(Page page) {
		return userScoreDao.findByPage(page);
	}
	
	
	public void pageInit(Page page){
		int pageIndex = page.getPageIndex();
		int pageSize = page.getPageSize();
		if(pageSize == 0){
			pageSize = Page.DEFAULT_PAGESIZE;
		}
		page.setTotalRecord(count());
		page.setTotalPage((page.getTotalRecord()/pageSize) +1);
		page.setLimitBegin((pageIndex-1)*pageSize);
	}

}

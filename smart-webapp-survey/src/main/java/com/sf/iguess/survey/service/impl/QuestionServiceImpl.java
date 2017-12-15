package com.sf.iguess.survey.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sf.iguess.survey.domain.Question;
import com.sf.iguess.survey.mapper.QuestionDao;
import com.sf.iguess.survey.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Resource
	private QuestionDao questionDao;

	@Override
	public List<Question> findBySurveyType(Integer surveyType) {
		return questionDao.findBySurveyType(surveyType);
	}

	@Override
	public String findPageQuestions() {
		PageHelper.startPage(1, 10);
		List<Question> questList = questionDao.findPageQuestions();
		PageInfo<Question> appsPageInfo = new PageInfo<>(questList);
		return JSON.toJSONString(appsPageInfo);
	}

}

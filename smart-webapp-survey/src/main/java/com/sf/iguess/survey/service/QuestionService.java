package com.sf.iguess.survey.service;

import java.util.List;

import com.sf.iguess.survey.domain.Question;

public interface QuestionService {
	
	public List<Question> findBySurveyType(Integer surveyType);
	
	public String findPageQuestions();

}

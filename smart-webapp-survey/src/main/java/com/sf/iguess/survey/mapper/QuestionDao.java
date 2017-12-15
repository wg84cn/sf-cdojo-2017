package com.sf.iguess.survey.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sf.iguess.survey.domain.Question;

@Mapper
public interface QuestionDao {
    public List<Question> findBySurveyType(Integer surveyType);

	public List<Question> findPageQuestions();
}
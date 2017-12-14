package com.sf.iguess.survey.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.iguess.survey.domain.Question;
import com.sf.iguess.survey.service.QuestionService;

/**
 * @author 80002286
 * @date 2017年12月4日
 * @time 下午8:53:31
 * @description 
 */
@RequestMapping("question")
@Controller
public class QuestionConstroller {
	
	@Resource
	private QuestionService questionService;
	
	@RequestMapping("findBySurveyType")
	@ResponseBody
	public List<Question> findBySurveyType(Integer surveyType){
		return questionService.findBySurveyType(surveyType);
	}

}

package com.sf.iguess.survey.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sf.iguess.response.JsonResult;
import com.sf.iguess.response.ResponseCode;
import com.sf.iguess.survey.service.QuestionService;

@RestController
@RequestMapping({ "/notice" })
public class NoticeController {
	
	@Resource
	private QuestionService questionService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult postAnswer() {
		return new JsonResult();
	}
	
	@RequestMapping(value = "/questionsView", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult questionsView(
			@RequestParam(value = "surveyType", required = true) 
			Integer surveyType) {
		return new JsonResult(ResponseCode.SUCCESS, 
				"", 
				questionService.findQuestionsBySurveyType(surveyType));
	}
}

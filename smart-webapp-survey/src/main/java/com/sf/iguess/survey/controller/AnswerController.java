package com.sf.iguess.survey.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sf.iguess.email.service.IMsgSendService;
import com.sf.iguess.email.template.SuveryEmailTemplate;
import com.sf.iguess.response.JsonResult;
import com.sf.iguess.response.ResponseCode;
import com.sf.iguess.survey.constant.Constant;
import com.sf.iguess.survey.domain.Answer;
import com.sf.iguess.survey.domain.User;
import com.sf.iguess.survey.domain.UserScore;
import com.sf.iguess.survey.service.AnswerService;
import com.sf.iguess.survey.service.UserScoreService;

/**
 * @author 80002286
 * @date 2017年12月4日
 * @time 下午8:53:31
 * @description 
 */
@RequestMapping("answer")
@Controller
public class AnswerController{
	
	@Resource
	private AnswerService answerService;
	
	@Resource
	private UserScoreService userScoreService;
	
	@Resource
	private IMsgSendService msgSendService;
	
	private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);
	
	@PostMapping("/save")
	@ResponseBody
	public JsonResult save(String answerJson, UserScore userScore,
			@RequestParam(value = "files", required = false) MultipartFile[] files, HttpSession session){
		User userInfo = (User) session.getAttribute(Constant.USER_INFO);
		if(null == userInfo){
		    logger.warn("检查到用户未登录状态提交问卷，提交失败！");
		    return new JsonResult(ResponseCode.NOT_LOGIN);
		}
		
		if(!validateFileSize(files)) {
		    return new JsonResult(ResponseCode.TOO_LARGEFILES);
		}
		
		if(!validateSubmitTimes(session)) {
            return new JsonResult(ResponseCode.TOO_FREQUENT);
        }
		
		List<Answer> answerList = JSON.parseArray(answerJson,Answer.class);
		answerService.saveAnswerList(userScore, answerList,files);
		
        JSONObject parms = SuveryEmailTemplate.getClickLinkUrl(userScore.getScoreId());
		if(!userInfo.isCurrentManage()) {
            msgSendService.sendEmail(userScore.getRelationerEmail(), parms);
		}
		return new JsonResult(ResponseCode.SUCCESS,"", null);
	}

	private boolean validateFileSize(MultipartFile[] files)
    {
	    if(files == null || files.length == 0) {
	        return true;
	    }
	    long fileTotalSize = 0;
	    for(MultipartFile file :files) {
	        if(file == null) {
	            continue;
	        }
	        fileTotalSize += file.getSize();
	    }
	    return fileTotalSize <= Constant.UPLOAD_FILE_LIMIT;
    }

	
	@RequestMapping("/findAnswerInfo")
	@ResponseBody
	public JsonResult findAnswerInfo(String scoreId){
		List<Answer> answerList = answerService.findByScoreId(scoreId);
		return new JsonResult(ResponseCode.SUCCESS,"",answerList);
	}
	
	/**
	 * 校验是否填写了必填项目
	 * @return
	 */
    private boolean validateSubmitTimes(HttpSession session)
    {
        Long lastSubmitTime = (Long)session.getAttribute(Constant.USER_SUBMIT_TIMES);
        long currentTime = System.currentTimeMillis();
        if (lastSubmitTime == null)
        {
            session.setAttribute(Constant.USER_SUBMIT_TIMES, currentTime);
            return true;
        }
        boolean isAllowSubmit = (currentTime - lastSubmitTime >= Constant.USER_SUBMIT_DURATION);
        if (isAllowSubmit)
        {
            session.setAttribute(Constant.USER_SUBMIT_TIMES, currentTime);
        }
        return isAllowSubmit;
    }
}

package com.sf.iguess.survey.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.iguess.response.JsonResult;
import com.sf.iguess.response.ResponseCode;
import com.sf.iguess.survey.constant.Constant;
import com.sf.iguess.survey.domain.Page;
import com.sf.iguess.survey.domain.UserScore;
import com.sf.iguess.survey.service.UserScoreService;

/**
 * @author 80002286
 * @date 2017年12月4日
 * @time 下午11:06:42
 * @description 
 */

@RequestMapping("userScore")
@Controller
public class UserScoreConstroller {
	
	@Resource
	private UserScoreService userScoreService;
	
	@RequestMapping("findByScoreId")
	@ResponseBody
	public JsonResult findByScoreId(String scoreId){
		UserScore userScoreInfo = userScoreService.findById(scoreId);
		return new JsonResult(ResponseCode.SUCCESS, "1", userScoreInfo);
	}
	
	/**
	 * 前台只需要给pageIndex就足够了，如果需要改变pageSize则给出pageSize
	 * @param page
	 * @return
	 */
	@RequestMapping("findByPage")
	@ResponseBody
	public JsonResult findByPage(Page page){
		userScoreService.pageInit(page);
		List<UserScore> userScoreList = userScoreService.findByPage(page);
		if(userScoreList.isEmpty()){
			return new JsonResult(ResponseCode.NOT_DATA,"",null);
		}
		Map<String, Object> map = new HashMap<>();
		map.put(Constant.PAGE_DATA, userScoreList);
		page.setCondition(map);
		return new JsonResult(ResponseCode.SUCCESS,"",page);
	}

}

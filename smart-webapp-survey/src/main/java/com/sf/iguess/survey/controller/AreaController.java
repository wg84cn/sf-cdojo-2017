/**
 * Project Name:webapp-survey
 * File Name:UserController.java
 * Package Name:com.sf.iguess.survey.controller
 * Date:2017年12月14日下午9:21:39
 * Copyright (c) 2017, tlw All Rights Reserved.
 *
*/

package com.sf.iguess.survey.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.iguess.response.JsonResult;
import com.sf.iguess.response.ResponseCode;
import com.sf.iguess.survey.domain.ExpressDelivery;
import com.sf.iguess.survey.service.AreaService;

/**
 * ClassName:UserController <br/>
 * Function: 寄件信息控制类 <br/>
 * Date:     2017年12月14日 下午9:21:39 <br/>
 * @author   618721
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RequestMapping("/area")
@RestController
public class AreaController {
	
	@Resource
	private AreaService areaService;

	/**
	 * saving:查询地区字典. <br/>
	 *
	 * @author 618721
	 * @param expressDelivery
	 * @return
	 * @since JDK 1.8
	 */
	@GetMapping("/list")
	public JsonResult saving(ExpressDelivery expressDelivery){
		return new JsonResult(ResponseCode.SUCCESS, "",areaService.readAreaJson());
	}
	
}


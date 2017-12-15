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

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.iguess.response.JsonResult;
import com.sf.iguess.response.ResponseCode;
import com.sf.iguess.survey.domain.ExpressDelivery;
import com.sf.iguess.survey.domain.StoreGoods;
import com.sf.iguess.survey.service.ExpressDeliveryService;

/**
 * ClassName:UserController <br/>
 * Function: 寄件信息控制类 <br/>
 * Date:     2017年12月14日 下午9:21:39 <br/>
 * @author   618721
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RequestMapping("/expressDelivery")
@RestController
public class ExpressDeliveryController {
	
	@Resource
	private ExpressDeliveryService expressDeliveryService;

	/**
	 * saving:保存寄件信息. <br/>
	 *
	 * @author 618721
	 * @param storeId
	 * @param expressDelivery
	 * @return
	 * @since JDK 1.8
	 */
	@PostMapping("/StoreGoods/{storeId}/saving")
	public JsonResult saving(
			@PathVariable("storeId")String storeId,
			ExpressDelivery expressDelivery){
		//保存并寄件信息
		StoreGoods storeGood = expressDeliveryService.save(storeId,expressDelivery);
		if(storeGood != null){
			return new JsonResult(ResponseCode.SUCCESS,"", storeGood);
		}
		return new JsonResult(ResponseCode.PARAMS_ERROR, "参数错误");
	}
}


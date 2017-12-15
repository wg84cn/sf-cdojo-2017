package com.sf.iguess.survey.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.iguess.response.JsonResult;
import com.sf.iguess.response.ResponseCode;
import com.sf.iguess.survey.domain.StoreGoods;
import com.sf.iguess.survey.domain.User;
import com.sf.iguess.survey.service.ExpressDeliveryService;
import com.sf.iguess.survey.service.StroreService;

/**
 * @author 80002286
 * @date 2017年12月4日
 * @time 下午8:53:31
 * @description
 */
@RequestMapping("storeGoods")
@RestController
public class StoreGoodsController {

	@Resource
	private StroreService storeGoodsService;
	
	@Resource
	private ExpressDeliveryService expressDeliveryService;

	@RequestMapping("/getStore/{goodsId}")
	public JsonResult getStoreGood(@PathVariable("goodsId") String goodsId) {
		StoreGoods storeGoods = storeGoodsService.selectStoreGood(goodsId);
		return new JsonResult(ResponseCode.SUCCESS, "", storeGoods);
	}

	@RequestMapping("loadActiveStoreList")
	public JsonResult loadActiveStoreList() {
		List<StoreGoods> storeGoodsList = storeGoodsService.selectActiveStoreList();
		return new JsonResult(ResponseCode.SUCCESS, "", storeGoodsList);
	}
	
	@RequestMapping("loadStoreUserList/{goodsId}")
	public JsonResult loadStoreUserList(@PathVariable("goodsId") String goodsId) {
		List<User> storeGoodsList = expressDeliveryService.getExpresssUserList(goodsId);
		return new JsonResult(ResponseCode.SUCCESS, "", storeGoodsList);
	}
}

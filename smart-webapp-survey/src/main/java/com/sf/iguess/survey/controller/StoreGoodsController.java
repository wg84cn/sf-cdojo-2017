package com.sf.iguess.survey.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sf.iguess.response.JsonResult;
import com.sf.iguess.response.ResponseCode;
import com.sf.iguess.survey.domain.StoreGoods;
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

	@RequestMapping("/getStore/{goodsId}")
	public JsonResult getStoreGood(@PathVariable("goodsId") Long goodsId) {
		StoreGoods storeGoods = storeGoodsService.selectStoreGood(goodsId);
		return new JsonResult(ResponseCode.SUCCESS, "", storeGoods);
	}

	@RequestMapping("loadStoreList")
	public JsonResult loadStoreList() {
		List<StoreGoods> storeGoodsList = storeGoodsService.selectStoreList();
		return new JsonResult(ResponseCode.SUCCESS, "", storeGoodsList);
	}
}

package com.sf.iguess.survey.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sf.iguess.survey.service.StroreService;

@Component
public class StoreGoodsScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(StoreGoodsScheduler.class);
	
	private StroreService stroreService;
	/**
	 * 定时检查集货的完成状态
	 */
	@Scheduled(cron = "* */20 * * * ?")
	public void checkStoreGoodsStatus() {
		try {
			//stroreService.checkStoreGoodsStatus();
		} catch (Exception e) {
			logger.error("Execute nessus bugs file synchronized task error:{}", e);
		}
		logger.info("Execute nessus bugs file synchronized task end");
	}
	
	/**
	 * 定时检查集货的完成状态
	 */
	@Scheduled(cron = "*/50 * * * * ?")
	public void autoCreateStoreGoods() {
		try {
			//stroreService.autoCreateStoreGoods();
		} catch (Exception e) {
			logger.error("Execute nessus bugs file synchronized task error:{}", e);
		}
		logger.info("Execute nessus bugs file synchronized task end");
	}

}

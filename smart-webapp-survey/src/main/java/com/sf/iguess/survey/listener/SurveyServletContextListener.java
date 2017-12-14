package com.sf.iguess.survey.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class SurveyServletContextListener implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(SurveyServletContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("ServletContex初始化:{}",sce.getServletContext().getServerInfo());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("ServletContex销毁");
	}

}
package com.sf.iguess.survey.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class SurveyHttpSessionListener implements HttpSessionListener {

	private static final Logger loger = LoggerFactory.getLogger(SurveyHttpSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    	loger.info("Session 被创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	HttpSession session = se.getSession();
    	session.invalidate();	
    	loger.info("ServletContex 被销毁");
    }
}
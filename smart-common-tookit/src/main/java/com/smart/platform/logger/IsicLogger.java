package com.smart.platform.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 说明：日志处理
 * 
 * @author xutao9@sf-express.com 修改时间：2014年9月20日
 * @version
 */
public final class IsicLogger {

	private static final Logger logger = LoggerFactory.getLogger(IsicLogger.class);
	
	private static String SYSTEM_NAME = null;
	
	private static IsicLogger isicLogger;

	private IsicLogger() {

	}

	public static Logger getLogger(String systemName) {
		SYSTEM_NAME = systemName;
		return logger;
	}
	
	public static IsicLogger getIsicLogger()
	{
		if(isicLogger == null)
		{
			isicLogger = new IsicLogger();
		}
		return isicLogger;
	}
	
	public void warn(String format, Object... arguments)
	{
		logger.warn(format, SYSTEM_NAME ,arguments);
	}
	
	public void error(String format, Object... arguments)
	{
		logger.warn(format, SYSTEM_NAME, arguments);
	}
	
	public void info(String format, Object... arguments)
	{
		logger.warn(format,SYSTEM_NAME, arguments);
	}
}

package com.smart.platform.logger;
/**
 * Project Name:isiccollecter
 * File Name:CollectInfoLogger.java
 * Package Name:
 * Date:2016年9月10日下午8:08:57
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:CollectInfoLogger <br/>
 * Date:     2016年9月10日 下午8:08:57 <br/>
 * @author   01135912
 * @since    JDK 1.6
 * @see 	 
 */
public class CollectLogger
{
    private static final Logger LOG = LoggerFactory.getLogger(CollectLogger.class);
    
    public static Logger logger()
    {
        return LOG;
    }
    public static void error(String printInfo, Object... arguments)
    {
        LOG.error(printInfo, arguments);
    }
    
    public static void info(String printInfo, Object... arguments)
    {
        LOG.info(printInfo, arguments);
    }
    
    public static void info(AuditLogger auditLog)
    {
        LOG.info(auditLog.toString());
    }
    
    public static void debug(String printInfo, Object... arguments)
    {
        LOG.debug(printInfo, arguments);
    }
}


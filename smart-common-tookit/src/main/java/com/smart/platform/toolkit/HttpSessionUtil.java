/**
 * Project Name:iatp-basic-common
 * File Name:HttpSessionUtil.java
 * Package Name:com.smart.platform.toolkit
 * Date:2017年4月10日下午3:38:40
 * Copyright (c) 2017, xutao9@sf-express.com All Rights Reserved.
 *
*/

package com.smart.platform.toolkit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:HttpSessionUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年4月10日 下午3:38:40 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
/**
 * ClassName: HttpSessionUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年4月10日 下午3:38:40 <br/>
 *
 * @author 01135912
 * @version 
 * @since JDK 1.7
 */
public class HttpSessionUtil
{
    private static final Logger LOG = LoggerFactory.getLogger(HttpSessionUtil.class);
    
    public static Integer getSessionIntValue(HttpServletRequest request, String attrName)
    {
        if (request == null)
        {
            LOG.warn("request session {} is invalid.", attrName);
            return null;
        }
        Object requetObj = request.getSession().getAttribute(attrName);
        if (StringUtil.isEmpty(requetObj))
        {
            LOG.warn("request session {} is invalid.", attrName);
            return null;
        }
        return (Integer)requetObj;
    }

    /**
     * getAndReset:(这里用一句话描述这个方法的作用). <br/>
     * @author 01135912
     * @param kaptchaSessionKey
     * @param string
     * @return
     * @since JDK 1.6
     */
    public static String getAndReset(HttpSession session,String kaptchaSessionKey)
    {
        String validateCode = (String)session.getAttribute(kaptchaSessionKey);
        session.setAttribute(kaptchaSessionKey, "");
        return validateCode;
    }
}


/**
 * Project Name:iatp-basic-common
 * File Name:DateTest.java
 * Package Name:com.smart.platform.date
 * Date:2016年11月8日下午2:36:07
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.date;

import java.util.Date;

import org.junit.Test;

import com.smart.platform.toolkit.DateStyle;
import com.smart.platform.toolkit.DateUtil;

/**
 * ClassName:DateTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年11月8日 下午2:36:07 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class DateTest
{
    /**
     * CSV导出
     * @throws Exception
     */
    @Test
    public void dateMonthTest()
    {
        String currTimeStr = DateUtil.DateToString(new Date(), DateStyle.YYYYMMDDHHMMSS);
        System.out.println(currTimeStr);
        
        Date date = DateUtil.StringToDate("20151008030208",DateStyle.YYYYMMDDHHMMSS);
        System.out.println(DateUtil.getIntervalMonth(new Date(),date));
    }
}


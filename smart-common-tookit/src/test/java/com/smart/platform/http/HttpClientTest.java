/**
 * Project Name:basicplatform
 * File Name:HttpClientTest.java
 * Package Name:com.smart.platform.http
 * Date:2016年8月5日下午3:35:07
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.http;

import java.io.IOException;

import org.junit.Test;

import com.smart.platform.toolkit.HttpClientHelper;

/**
 * ClassName:HttpClientTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年8月5日 下午3:35:07 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class HttpClientTest
{
    @Test
    public void getTest()
    {
        String url = "http://10.202.34.211:53823";
        try
        {
            System.out.println(HttpClientHelper.getPageContent(url));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void postTest()
    {
        
    }
}


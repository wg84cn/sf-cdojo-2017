/**
 * Project Name:webapp-survey
 * File Name:JdbcEncodeTool.java
 * Package Name:com.sf.iguess.survey
 * Date:2017年12月14日下午9:56:01
 * Copyright (c) 2017, tlw All Rights Reserved.
 *
*/

package com.sf.iguess.survey;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * ClassName:JdbcEncodeTool <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017年12月14日 下午9:56:01 <br/>
 * @author   Wayen
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class JdbcEncodeTool {

	public static void main(String[] args) throws Exception {
		String pwd = ConfigTools.encrypt("cdojo_dev");
		System.out.println(pwd);
	}

}


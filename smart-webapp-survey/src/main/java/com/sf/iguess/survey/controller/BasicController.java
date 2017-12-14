/**
 * Project Name:webapp-survey
 * File Name:BasicController.java
 * Package Name:com.sf.iguess.survey.controller
 * Date:2017年12月12日上午9:57:41
 * Copyright (c) 2017, 01135912 All Rights Reserved.
 *
*/

package com.sf.iguess.survey.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.smart.platform.toolkit.DateStyle;

/**
 * ClassName:BasicController <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason:	 ADD REASON. <br/>
 * Date:     2017年12月12日 上午9:57:41 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class BasicController
{
    @InitBinder   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }  
}


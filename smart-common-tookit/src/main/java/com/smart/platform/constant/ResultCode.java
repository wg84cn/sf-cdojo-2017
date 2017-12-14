/**
 * Project Name:basicplatform
 * File Name:ResultCode.java
 * Package Name:com.smart.platform.constant
 * Date:2016年7月26日下午11:16:02
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.constant;

/**
 * ClassName:ResultCode <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年7月26日 下午11:16:02 <br/>
 * 
 * @author 01135912
 * @version
 * @since JDK 1.6
 * @see
 */
public final class ResultCode
{
    public static final int SUCCESS = 0;
    
    public static final int ERROR_INNER = 13201;
    
    public static final int ERROR_NO_EXIST = 13202;
    
    public static final int ERROR_INVALID_ACTION = 13203;
    
    public static final int ERROR_NO_RIGHT = 13204;
    
    public static final int INVALIDE_PARAM = 13205;
    
    public static final int INVALID_ACCESS = 13206;
    
    public static final int NO_LOGIN_USER = 13207;
    // 禁用用户
    public static final int DISABLE_USER = 13208;
    // 会话过期
    public static final int SESSION_EXPIRED = 13209;
    // 手机号己注册
    public static final int PHONE_REGISTERED = 13210;
    // 昵称己注册
    public static final int NICKNAME_REGISTERED = 13211;
}

package com.smart.platform.annotation;

import java.lang.annotation.*;

import com.smart.platform.constant.SystemConstant;

/**
 * ClassName: CurrentUser <br/>
 * Function: 绑定当前登陆用户. <br/>
 * date: 2017年12月15日 上午9:10:28 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
	/**
	 * 当前用户在request中的名字
	 * 
	 * @return
	 */
	String value() default SystemConstant.CURRENT_USER;

}

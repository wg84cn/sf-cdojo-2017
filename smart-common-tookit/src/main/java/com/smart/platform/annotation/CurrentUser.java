package com.smart.platform.annotation;

import java.lang.annotation.*;

import com.smart.platform.constant.SystemConstant;

/**
 * <p>
 * 绑定当前登录的用户
 * </p>
 * <p>
 * 不同于@ModelAttribute
 * </p>
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

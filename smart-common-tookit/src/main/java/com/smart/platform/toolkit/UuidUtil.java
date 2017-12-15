package com.smart.platform.toolkit;

import java.util.UUID;

/**
 * ClassName: UuidUtil <br/>
 * Function: 生成唯一编码. <br/>
 * date: 2017年12月15日 下午5:21:50 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
public class UuidUtil {

	private UuidUtil() {}

	public static String get32UUID() {
		String uuidStr = UUID.randomUUID().toString();
		return uuidStr.replaceAll("-", "");
	}
}

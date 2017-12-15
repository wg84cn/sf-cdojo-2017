package com.smart.platform.toolkit;

import java.util.UUID;

public class UuidUtil {

	private UuidUtil() {

	}

	public static String get32UUID() {
		String uuidStr = UUID.randomUUID().toString();
		return uuidStr.replaceAll("-", "");
	}
}

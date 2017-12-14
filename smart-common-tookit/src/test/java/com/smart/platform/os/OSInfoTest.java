package com.smart.platform.os;

import org.junit.Test;
public class OSInfoTest {
	
	@Test
	public void osTest() {
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("os.version"));
		System.out.println(System.getProperty("os.arch"));
	}
}

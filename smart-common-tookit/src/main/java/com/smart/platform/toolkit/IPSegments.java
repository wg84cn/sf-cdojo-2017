package com.smart.platform.toolkit;

import java.util.ArrayList;
import java.util.List;

/**
 * With defined IP record as following , we encode the IP message to the class IPSegments
 * @author 01135912
 */
public final class IPSegments {

	private static List<IPFields> ipFieldList = new ArrayList<IPFields>(16);

	/*
	 * 1 0.0.0.0 0.255.255.255 A RESERVED FALSE 2 1.0.0.0 9.255.255.255 A PUBLIC
	 * TRUE 3 10.0.0.0 10.255.255.255 A PRIVATE TRUE 4 11.0.0.0 126.255.255.255
	 * A PUBLIC TRUE 5 127.0.0.0 127.255.255.255 A RESERVED TRUE 6 128.0.0.0
	 * 169.253.255.255 B PUBLIC TRUE 7 169.254.0.0 169.254.255.255 B RESERVED
	 * TRUE 8 169.255.0.0 172.15.255.255 B PUBLIC TRUE 9 172.16.0.0
	 * 172.31.255.255 B PRIVATE TRUE 10 172.32.0.0 191.255.255.255 B PUBLIC TRUE
	 * 11 192.0.0.0 192.167.255.255 C PUBLIC TRUE 12 192.168.0.0 192.168.255.255
	 * C PRIVATE TRUE 13 192.169.0.0 223.255.255.255 C PUBLIC TRUE 14 224.0.0.0
	 * 239.255.255.255 D RESERVED TRUE 15 240.0.0.0 255.255.255.254 E RESERVED
	 * TRUE 16 255.255.255.255 255.255.255.255 E RESERVED TRUE
	 */

	static {
		ipFieldList.add(new IPFields(0L, 16777215L, "A", "RESERVED", false));
		ipFieldList.add(new IPFields(16777216L, 167772159L, "A", "PUBLIC", true));
		ipFieldList.add(new IPFields(167772160L, 184549375L, "A", "PRIVATE", true));
		ipFieldList.add(new IPFields(184549376L, 2130706431L, "A", "PUBLIC", true));
		ipFieldList.add(new IPFields(2130706432L, 2147483647L, "A", "RESERVED", true));
		ipFieldList.add(new IPFields(2147483648L, 2851995647L, "B", "PUBLIC", true));
		ipFieldList.add(new IPFields(2851995648L, 2852061183L, "B", "RESERVED", true));
		ipFieldList.add(new IPFields(2852061184L, 2886729727L, "B", "PUBLIC", true));
		ipFieldList.add(new IPFields(2886729728L, 2887778303L, "B", "PRIVATE", true));
		ipFieldList.add(new IPFields(2887778304L, 3221225471L, "B", "PUBLIC", true));
		ipFieldList.add(new IPFields(3221225472L, 3232235519L, "C", "PUBLIC", true));
		ipFieldList.add(new IPFields(3232235520L, 3232301055L, "C", "PRIVATE", true));
		ipFieldList.add(new IPFields(3232301056L, 3758096383L, "C", "PUBLIC", true));
		ipFieldList.add(new IPFields(3758096384L, 4026531839L, "D", "RESERVED", true));
		ipFieldList.add(new IPFields(4026531840L, 4294967294L, "E", "RESERVED", true));
		ipFieldList.add(new IPFields(4294967295L, 4294967295L, "E", "RESERVED", true));
	}

	public static String getIpNetType(String ipAddress) {
		Long ipLongValue = IPSourceUtil.getIpLongFromStr(ipAddress);
		for (IPFields ipFields : ipFieldList) {
			if (ipLongValue >= ipFields.getStartIp() && ipLongValue <= ipFields.getEndIp()) {
				return ipFields.getNetType();
			}
		}
		return null;
	}

	static class IPFields {

		private Integer number;
		private Long startIp;
		private Long endIp;
		private String classify;
		private String netType;
		private boolean isValid;

		public IPFields(Long startIp, Long endIp, String classify, String netType, boolean isValid) {
			this.setStartIp(startIp);
			this.setEndIp(endIp);
			this.setClassify(classify);
			this.setNetType(netType);
			this.setValid(isValid);
		}

		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
			this.number = number;
		}

		public Long getStartIp() {
			return startIp;
		}

		public void setStartIp(Long startIp) {
			this.startIp = startIp;
		}

		public Long getEndIp() {
			return endIp;
		}

		public void setEndIp(Long endIp) {
			this.endIp = endIp;
		}

		public String getClassify() {
			return classify;
		}

		public void setClassify(String classify) {
			this.classify = classify;
		}

		public String getNetType() {
			return netType;
		}

		public void setNetType(String netType) {
			this.netType = netType;
		}

		public boolean isValid() {
			return isValid;
		}

		public void setValid(boolean isValid) {
			this.isValid = isValid;
		}
	}
}

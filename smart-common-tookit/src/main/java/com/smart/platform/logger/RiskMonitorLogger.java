package com.smart.platform.logger;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.smart.platform.toolkit.DateUtil;
import com.smart.platform.toolkit.PropertyPlaceholderConfigurer;


/**
  *@version 创建时间：2017年5月12日 下午6:10:25
*/
public class RiskMonitorLogger {

	private static final String SYSTEM_CONFIG_NAME = "system.name";

	private static String destinationApp = PropertyPlaceholderConfigurer.getConfigValue(SYSTEM_CONFIG_NAME);

	private static String USERID;
	private String dst_ip;
	private String src_ip;
	private String Computer_name;
	private String src_mac;
	private String oper_name;
	private String oper_result;
	private String fail_reason;
	private String destination_app;
	private String source_app;
	
	

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}

	public String getDst_ip() {
		return dst_ip;
	}

	public void setDst_ip(String dst_ip) {
		this.dst_ip = dst_ip;
	}

	public String getSrc_ip() {
		return src_ip;
	}

	public void setSrc_ip(String src_ip) {
		this.src_ip = src_ip;
	}

	public String getComputer_name() {
		return Computer_name;
	}

	public void setComputer_name(String computer_name) {
		Computer_name = computer_name;
	}

	public String getSrc_mac() {
		return src_mac;
	}

	public void setSrc_mac(String src_mac) {
		this.src_mac = src_mac;
	}

	public String getOper_name() {
		return oper_name;
	}

	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}

	public String getOper_result() {
		return oper_result;
	}

	public void setOper_result(String oper_result) {
		this.oper_result = oper_result;
	}

	public String getFail_reason() {
		return fail_reason;
	}

	public void setFail_reason(String fail_reason) {
		this.fail_reason = fail_reason;
	}

	public String getDestination_app() {
		return destination_app;
	}

	public void setDestination_app(String destination_app) {
		this.destination_app = destination_app;
	}

	public String getSource_app() {
		return source_app;
	}

	public void setSource_app(String source_app) {
		this.source_app = source_app;
	}

	
	public static JSONObject getUserId(String user){
		JSONObject object = new JSONObject();
		object.put("USERID", user);
		return object;
	}
	
	public static JSONObject getRiskMonitorLogger(String user, String dst_ip,
			String src_ip,String Computer_name,String src_mac, String oper_name,
			String oper_result,String fail_reason){
		JSONObject object = new JSONObject();
		object.put("USERID", user);
		object.put("source_app",destinationApp );
		object.put("destination_app",destinationApp );
		object.put("dst_ip", dst_ip);
		object.put("src_ip",src_ip );
		object.put("Computer_name", Computer_name);
		object.put("src_mac", src_mac);
		object.put("oper_name", oper_name);
		object.put("oper_result", oper_result);
		object.put("fail_reason", fail_reason);
		return object;
	}
	
	
	public static String getRiskReturnLogic(String disposal) {
		if (disposal.equals("PS01")) {
			return "放行";
		} else if (disposal.equals("PS02")) {
			return "页面提醒";
		} else if (disposal.equals("PS03")) {
			return "阻断";
		} else if (disposal.equals("PS11")) {
			return "一级预警";
		} else if (disposal.equals("PS12")) {
			return "二级预警";
		} else if (disposal.equals("PS13")) {
			return "三级预警";
		}
		return null;
	}
	
	
	
	
	/**风险评估*/
	public static JSONObject getRiskAssessment(String transType, String channelType,String ipAddress,JSONObject extInfo){
		JSONObject object = new JSONObject();
		object.put("transTime", DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		object.put("transType", transType);
		object.put("channelType", channelType);
		object.put("ipAddress", ipAddress);
		object.put("extInfo", extInfo);
		return object;
	}
	
	
	/**风险确认*/
	public static JSONObject getRiskIdentification(String transCode,String transType, 
			String transStatus, String channelType,String ipAddress,JSONObject extInfo){
		JSONObject object = new JSONObject();
		object.put("transCode", transCode);
		object.put("transTime", DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		object.put("transType", transType);
		object.put("transStatus", transStatus);
		object.put("channelType", channelType);
		object.put("ipAddress", ipAddress);
		object.put("extInfo", extInfo);
		return object;
	}
	
	
}

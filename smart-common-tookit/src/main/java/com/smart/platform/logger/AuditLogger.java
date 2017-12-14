package com.smart.platform.logger;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.smart.platform.toolkit.DateUtil;
import com.smart.platform.toolkit.IPSourceUtil;
import com.smart.platform.toolkit.PropertyPlaceholderConfigurer;

/**
 * @author 80002458
 * @version 创建时间：2017年4月13日 上午11:32:14
 */
public class AuditLogger {

	private static final String SYSTEM_CONFIG_NAME = "system.name";

	private static String destinationApp = PropertyPlaceholderConfigurer.getConfigValue(SYSTEM_CONFIG_NAME);

	private static String sourceApp = destinationApp;

	private Integer logId;
	private Integer versionId;
	private String userName;
	private String srcIp;
	private String computerName;
	private String mac;
	private String hostName;
	private String operCode;
	private String operResult;
	private String failReason;
	private String fromUser;
	

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOperResult() {
		return operResult;
	}

	public void setOperResult(String operResult) {
		this.operResult = operResult;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public static String getDestinationApp() {
		return destinationApp;
	}

	public static void setDestinationApp(String destinationApp) {
		AuditLogger.destinationApp = destinationApp;
	}

	public static String getSourceApp() {
		return sourceApp;
	}

	public static void setSourceApp(String sourceApp) {
		AuditLogger.sourceApp = sourceApp;
	}

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(logId);
		builder.append("\u0000");
		builder.append(versionId);
		builder.append("\u0000");
		builder.append(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		builder.append("\u0000");
		builder.append(userName);
		builder.append("\u0000");
		builder.append(sourceApp);
		builder.append("\u0000");
		builder.append(destinationApp);
		builder.append("\u0000");
		builder.append(hostName);
		builder.append("\u0000");
		builder.append(srcIp);
		builder.append("\u0000");
		builder.append("");
		builder.append("\u0000");
		builder.append("");
		builder.append("\u0000");
		builder.append(operCode);
		builder.append("\u0000");
		builder.append(operResult);
		builder.append("\u0000");
		builder.append(failReason);
		builder.append("\u0000");
		return builder.toString();
	}

	public AuditLogger() {
		super();
	}

	public AuditLogger(Integer logId, Integer versionId, String userName, HttpServletRequest request,
			String hostName,String operCode,String operResult, String failReason) {
		this.logId = logId;
		this.versionId = versionId;
		this.userName = userName;
		this.srcIp = IPSourceUtil.getIpAddr(request);
		this.hostName = hostName;
		this.operCode=operCode;
		this.operResult = operResult;
		this.failReason=failReason;
	}
	
	public AuditLogger(Integer logId, Integer versionId, String userName, String ip,
			String hostName,String operCode,String operResult, String failReason) {
		this.logId = logId;
		this.versionId = versionId;
		this.userName = userName;
		this.srcIp = ip;
		this.hostName = hostName;
		this.operCode=operCode;
		this.operResult = operResult;
		this.failReason=failReason;
	}

	public static String  userOper(Integer logId, Integer versionId,String userName,
			String fromUser,String hostName,String operCode,String operResult){
		StringBuilder builder = new StringBuilder();
		builder.append(logId);
		builder.append("\u0000");
		builder.append(versionId);
		builder.append("\u0000");
		builder.append(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		builder.append("\u0000");
		builder.append(userName);
		builder.append("\u0000");
		builder.append(fromUser);
		builder.append("\u0000");
		builder.append(sourceApp);
		builder.append("\u0000");
		builder.append(destinationApp);
		builder.append("\u0000");
		builder.append(hostName);
		builder.append("\u0000");
		builder.append(operCode);
		builder.append("\u0000");
		builder.append(operResult);
		builder.append("\u0000");
		return builder.toString();
	}
}

package com.sf.iguess.response;

/**
 * ClassName: ResponseCode <br/>
 * Function: 返回 code 定义类. <br/>
 * date: 2017年12月14日 下午9:04:45 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
public enum ResponseCode {
	/** 成功 */
	SUCCESS("200", "成功"),
	
	NOT_DATA("300", "没有数据"),

	/** 没有登录 */
	NOT_LOGIN("400", "没有登录"),

	/** 发生异常 */
	EXCEPTION("401", "发生异常"),

	/** 系统错误 */
	SYS_ERROR("402", "系统错误"),

	/** 参数错误 */
	PARAMS_ERROR("403", "参数错误 "),

	/** 不支持或已经废弃 */
	NOT_SUPPORTED("410", "不支持或已经废弃"),

	/** AuthCode错误 */
	INVALID_AUTHCODE("444", "无效的AuthCode"),

	/** 太频繁的调用 */
	TOO_FREQUENT("445", "太频繁的调用"),
	
	/** 太频繁的调用 */
    TOO_LARGEFILES("446", "请求文件超过限制大小！"),

	/** 未知的错误 */
	UNKNOWN_ERROR("499", "未知错误");

	private String val;
	
	private String msg;
	
	private ResponseCode(String value, String msg) {
		this.val = value;
		this.msg = msg;
	}

	public String val() {
		return val;
	}

	public String msg() {
		return msg;
	}
}
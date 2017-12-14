package com.smart.platform.toolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegularMatcher {

	public static boolean isRegularMatch(String inputStr, String regexStr) {
		if (inputStr == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}

	public static boolean isRegularAllMatch(String inputStr, String regexStr) {
		if (inputStr == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}

	/**
	 * replaceAll:(多行文本进行替换). <br/>
	 * 
	 * @author 01135912
	 * @since JDK 1.6
	 */
	public static String replaceAll(String moreLineStr, String regulerStr, String replaceStr) {
		Pattern patter = Pattern.compile(regulerStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher mather = patter.matcher(moreLineStr);
		return mather.replaceAll(replaceStr);
	}

	public static String getMatchStr(String inputStr, String regexStr) {
		return getMatchStrWithGroup(inputStr, regexStr, 1);
	}

	public static String getMatchStrWithGroup(String inputStr, String regexStr, int group) {
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		String matcherStr = null;
		if (matcher.find()) {
			matcherStr = matcher.group(group);
		}
		return matcherStr;
	}

	/**
	 * Each input string just match one group,but the group is not sure,just
	 * include in groups.
	 **/
	public static List<String> getMatchStrWithGroups(String inputStr, String regexStr, List<Integer> groups) {
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (!matcher.find()) {
			return null;
		}
		List<String> matchSet = new ArrayList<String>();
		for (int group : groups) {
			String matchStr = matcher.group(group);
			if (matchStr != null && !matchStr.trim().isEmpty()) {
				matchSet.add(matchStr);
			}
		}
		return matchSet;
	}

	/**
	 * Each input string just match one group,but the group is not sure,just
	 * include in groups.
	 **/
	public static List<String> getMatchStrWithNullGroups(String inputStr, String regexStr, List<Integer> groups) {
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (!matcher.find()) {
			return null;
		}
		List<String> matchSet = new ArrayList<String>();
		for (int group : groups) {
			String matchStr = matcher.group(group);
			matchSet.add(matchStr);
		}
		return matchSet;
	}

	/**
	 * Each input string just match one group,but the group is not sure,just
	 * include in groups.
	 **/
	public static void getMatchStrWithGroups(String inputStr, String regexStr, List<Integer> groups,
			List<List<String>> resultsList) {
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		String matcherStr = null;
		if (!matcher.find()) {
			return;
		}

		for (int group : groups) {
			matcherStr = matcher.group(group);
			if (matcherStr != null) {
				resultsList.get(group - 1).add(matcherStr);
				break;
			}
		}
	}

	public static List<String> getMatchStrList(String inputStr, String regexStr) {
		Pattern pattern = Pattern.compile(regexStr);
		Matcher matcher = pattern.matcher(inputStr);
		List<String> matcherStrList = new ArrayList<String>();
		while (matcher.find()) {
			matcherStrList.add(matcher.group());
		}
		return matcherStrList;
	}

	public static boolean startCheck(String reg, String string) {
		boolean tem = false;

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string);

		tem = matcher.matches();
		return tem;
	}

	/**
	 * 检验整数,适用于正整数、负整数、0，负整数不能以-0开头, 正整数不能以0开头
	 * 
	 */
	public static boolean checkNr(String nr) {
		String reg = "^(-?)[1-9]+\\d*|0";
		return startCheck(reg, nr);
	}

	/**
	 * 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数
	 */
	public static boolean checkCellPhone(String cellPhoneNr) {
		String reg = "^[1][\\d]{10}";
		return startCheck(reg, cellPhoneNr);
	}

	/**
	 * 检验空白符
	 */
	public static boolean checkWhiteLine(String line) {
		String regex = "(\\s|\\t|\\r)+";

		return startCheck(regex, line);
	}

	/**
	 * 检查EMAIL地址 用户名和网站名称必须>=1位字符
	 * 地址结尾必须是以com|cn|com|cn|net|org|gov|gov.cn|edu|edu.cn结尾
	 */
	public static boolean checkEmailWithSuffix(String email) {
		String regex = "\\w+\\@\\w+\\.(com|cn|com.cn|net|org|gov|gov.cn|edu|edu.cn)";

		return startCheck(regex, email);
	}

	/**
	 * 检查邮政编码(中国),6位，第一位必须是非0开头，其他5位数字为0-9
	 */
	public static boolean checkPostcode(String postCode) {
		String regex = "^[1-9]\\d{5}";
		return startCheck(regex, postCode);
	}

	/**
	 * 检验用户名 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾 用户名有最小长度和最大长度限制，比如用户名必须是4-20位
	 */
	public static boolean checkUsername(String username, int min, int max) {
		String regex = "[\\w\u4e00-\u9fa5]{" + min + "," + max + "}(?<!_)";
		return startCheck(regex, username);
	}

	/**
	 * 检验用户名 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾 有最小位数限制的用户名，比如：用户名最少为4位字符
	 */
	public static boolean checkUsername(String username, int min) {
		// [\\w\u4e00-\u9fa5]{2,}?
		String regex = "[\\w\u4e00-\u9fa5]{" + min + ",}(?<!_)";

		return startCheck(regex, username);
	}

	/**
	 * 检验用户名 取值范围为a-z,A-Z,0-9,"_",汉字 最少一位字符，最大字符位数无限制，不能以"_"结尾
	 */
	public static boolean checkUsername(String username) {
		String regex = "[\\w\u4e00-\u9fa5]+(?<!_)";
		return startCheck(regex, username);
	}

	/**
	 * 查看IP地址是否合法
	 */
	public static boolean checkIP(String ipAddress) {
		String regex = "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\." + "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\."
				+ "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\." + "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])";

		return startCheck(regex, ipAddress);
	}

	/**
	 * 查看IP地址是否合法
	 */
	public static boolean checkIPSegment(String inputIpStr) {
		String regex = "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\." + "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\."
				+ "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\." + "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])/[0-3]{0,1}[0-9]";
		return startCheck(regex, inputIpStr);
	}

	/**
	 * 验证注册密码 格式：字母数字，长度8-20位
	 */
	public static boolean checkPassword(String password) {
		String regex = "^(?![0-9A-Z]+$)(?![0-9a-z]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
		return startCheck(regex, password);
	}

	/**
	 * 验证国内电话号码 格式：010-67676767，区号长度3-4位，必须以"0"开头，号码是7-8位
	 */
	public static boolean checkPhoneNr(String phoneNr) {
		String regex = "^[0]\\d{2,3}\\-\\d{7,8}";
		return startCheck(regex, phoneNr);
	}

	/**
	 * 验证国内电话号码 格式：6767676, 号码位数必须是7-8位,头一位不能是"0"
	 */
	public static boolean checkPhoneNrWithoutCode(String phoneNr) {
		String reg = "^[1-9]\\d{6,7}";

		return startCheck(reg, phoneNr);
	}

	/**
	 * 验证国内电话号码 格式：0106767676，共11位或者12位，必须是0开头
	 */
	public static boolean checkPhoneNrWithoutLine(String phoneNr) {
		String reg = "^[0]\\d{10,11}";
		return startCheck(reg, phoneNr);
	}

	/**
	 * 验证国内身份证号码：15或18位，由数字组成，不能以0开头
	 */
	public static boolean checkIdCard(String idNr) {
		String reg = "^[1-9](\\d{14}|\\d{17})";
		return startCheck(reg, idNr);
	}

	/**
	 * 网址验证<br>
	 * 符合类型：<br>
	 * http://www.test.com<br>
	 * http://163.com
	 */
	public static boolean checkWebSite(String url) {
		// http://www.163.com
		String reg = "^(http)\\://(\\w+\\.\\w+\\.\\w+|\\w+\\.\\w+)";
		return startCheck(reg, url);
	}

	/**
	 * POST请求过滤
	 * 
	 * @param sql
	 * @return
	 */
	public static boolean checkSQL(String sql) {
		String reg = "\\b(and|or)\\b.{1,6}?(=|>|<|\\bin\\b|\\blike\\b)|\\/\\*.+?\\*\\/|<\\s*script\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)";
		return startCheck(reg, sql);
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	
	/**
	 * 校验昵称由字母和数字组成,长度6-15位
	 * @param nickName
	 * @return
	 */
	public static boolean checkNickName(String nickName){
		String reg = "^(?=\\S*?[A-Za-z])(?=\\S*?[0-9])\\S{6,15}$";
		return startCheck(reg, nickName);
	}
}

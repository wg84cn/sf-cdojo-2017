package com.smart.platform.toolkit;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

public final class StringUtil {
	public static final String SPLIT_DATE_MARK = "-";

	public static final String EMPTY_STRING = "";

	public static final String QUOTATION_STRING = "\"";

	public static final String SPLIT_TIME_MARK = ":";

	public static final char SINGLE_QUOTES_MARK = '\'';

	public static final String DOUBLE_QUOTES_MARK = "\"";

	private static final String STRING_APPEND_MARK = ",";

	public static final String ENCODE = "UTF-8";

	private static final String CDATA_START = "![CDATA[";
	private static final String CDATA_END = "]]";

	private static final String NUMBER_ARRAY_STR = "1234567890";

	private static final String VALID_CHAR_STR = "1234567890abcdefghijkmnpqrstuvwxyz";

	/**
	 * subFirstWithSpace:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param input
	 * @param subString
	 * @return
	 * @since JDK 1.6
	 */
	public final static String subFirstWithSpace(String input, String subString) {
		String restString = input.substring(subString.length());
		return restString.trim().split(" ")[0];
	}

	/**
	 * splitWithSpace:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param inputStr
	 * @return
	 * @since JDK 1.6
	 */
	public final static String[] splitWithSpace(String inputStr) {
		if (inputStr == null || inputStr.trim().isEmpty()) {
			return new String[] {};
		}
		return inputStr.trim().split(" ");
	}

	/**
	 * getLastStrSplitSpace:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param inputStr
	 * @return
	 * @since JDK 1.6
	 */
	public final static String getLastStrSplitSpace(String inputStr) {
		String[] strArray = splitWithSpace(inputStr);
		if (strArray == null || strArray.length == 0) {
			return null;
		}
		return strArray[strArray.length - 1].trim();
	}

	/**
	 * isBettwenMark:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param inputText
	 * @param markStr
	 * @return
	 * @since JDK 1.6
	 */
	public final static boolean isBettwenMark(String inputText, String markStr) {
		if (inputText == null || inputText.isEmpty()) {
			return false;
		}

		if (inputText.startsWith(markStr) && inputText.endsWith(markStr)) {
			return true;
		}
		return false;
	}

	/**
	 * builderStrWithCurrentTime:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param inputArray
	 * @return
	 * @since JDK 1.6
	 */
	public final static String builderStrWithCurrentTime(String... inputArray) {
		if (inputArray == null || inputArray.length == 0) {
			return null;
		}
		StringBuilder sbder = new StringBuilder();
		for (String inputStr : inputArray) {
			if (inputStr == null) {
				continue;
			}
			sbder.append(inputStr);
		}
		sbder.append("_").append(DateUtil.getDateTimeStr()).append(UUID.randomUUID());
		return sbder.toString();
	}

	/**
	 * listToStrWithMark:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param intList
	 * @return
	 * @since JDK 1.6
	 */
	public final static String listToStrWithMark(List<Integer> intList) {
		if (intList == null || intList.isEmpty()) {
			return null;
		}
		StringBuilder sbder = new StringBuilder();
		for (Integer intValue : intList) {
			if (intValue == null) {
				continue;
			}
			sbder.append(intValue).append(STRING_APPEND_MARK);
		}
		if (sbder.indexOf(STRING_APPEND_MARK) > -1) {
			return sbder.substring(0, sbder.lastIndexOf(STRING_APPEND_MARK));
		}
		return null;
	}

	/**
	 * strToListWithMark:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param inputStr
	 * @return
	 * @since JDK 1.6
	 */
	public static String[] splitWithMark(String inputStr) {
		if (inputStr == null || inputStr.trim().isEmpty()) {
			return null;
		}
		return inputStr.split(STRING_APPEND_MARK);
	}

	public static boolean isEmpty(String string) {
		return string == null || string.trim().length() == 0;
	}

	public static boolean isEmpty(Object inputObj) {
		if (inputObj == null) {
			return true;
		}
		String inputObjStr = String.valueOf(inputObj);
		return StringUtil.isEmpty(inputObjStr);
	}

	/**
	 * @author 01135912
	 * @param strArray
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean hasBlankStr(String... strArray) {
		for (String str : strArray) {
			if (str == null || str.trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * getStrNoQuatation:(去除首尾引号). <br/>
	 * 
	 * @author 01135912
	 * @return
	 * @since JDK 1.6
	 */
	public static String getStrNoQuatation(String inputOrgStr) {
		return (String) JSON.parse(inputOrgStr);
	}

	/**
	 * getStrNoQuatation:(去除首尾CDATA标志). <br/>
	 * 
	 * @author 01135912
	 * @return
	 * @since JDK 1.6
	 */
	public static String getStrNoCData(String inputOrgStr) {
		if (inputOrgStr == null || !inputOrgStr.startsWith(CDATA_START)) {
			throw new IllegalArgumentException("Invalid data with cdata flag : " + inputOrgStr);
		}
		inputOrgStr = inputOrgStr.substring(CDATA_START.length() + 1);
		return inputOrgStr.substring(0, inputOrgStr.lastIndexOf(CDATA_END));
	}

	public static boolean isContains(String inputStr, String matchStr) {
		String[] matchs = matchStr.split(" ");
		for (String match : matchs) {
			if (match.trim().isEmpty()) {
				continue;
			}
			if (!inputStr.contains(match.trim())) {
				return false;
			}
		}
		return true;
	}

	public static String getArrayStrSizeStr(String inputValue) {
		String[] leftStrArray = StringUtil.splitWithMark(inputValue);
		if (leftStrArray == null || leftStrArray.length == 0) {
			return null;
		}

		StringBuilder sizeSbder = new StringBuilder();
		for (String leftStr : leftStrArray) {
			sizeSbder.append(leftStr == null ? 0 : leftStr.length()).append(",");
		}
		return sizeSbder.substring(0, sizeSbder.length() - 1);
	}

	/**
	 * appendArrayToString:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param inputIpSources
	 * @param ipSegmentStr
	 * @return
	 * @since JDK 1.6
	 */
	public static String appendArrayToString(String[] inputSourceStrs, String ipSegmentStr) {
		if (inputSourceStrs == null || inputSourceStrs.length == 0) {
			return EMPTY_STRING;
		}
		List<String> inputSourceList = Arrays.asList(inputSourceStrs);
		return appendListToString(inputSourceList, ipSegmentStr);
	}

	/**
	 * appendArrayToString:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param inputIpSources
	 * @param ipSegmentStr
	 * @return
	 * @since JDK 1.6
	 */
	public static String appendListToString(List<String> inputSourceStrs, String ipSegmentStr) {
		if (inputSourceStrs == null || inputSourceStrs.isEmpty()) {
			return EMPTY_STRING;
		}
		StringBuilder sdber = new StringBuilder();
		for (int idx = 0; idx < inputSourceStrs.size(); idx++) {
			String eachStr = inputSourceStrs.get(idx);
			if (isEmpty(eachStr)) {
				continue;
			}
			sdber.append(eachStr);
			if (idx < inputSourceStrs.size() - 1) {
				sdber.append(ipSegmentStr);
			}
		}
		return sdber.toString();
	}

	/**
	 * appendStrings:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param moreStrs
	 * @return
	 * @since JDK 1.6
	 */
	public static String appendStrings(String... moreStrs) {
		if (moreStrs == null || moreStrs.length == 0) {
			return null;
		}

		StringBuilder sbder = new StringBuilder();
		for (String inputStr : moreStrs) {
			inputStr = isEmpty(inputStr) ? "" : inputStr;
			sbder.append(inputStr).append(SPLIT_DATE_MARK);
		}
		return sbder.toString();
	}

	/**
	 * trimWithChar:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author 01135912
	 * @param quotationString
	 * @return
	 * @since JDK 1.6
	 */
	public static String trimWithChar(String inputStr, String formateStr) {
		if (isEmpty(inputStr)) {
			return inputStr;
		}
		inputStr = inputStr.trim();
		if (inputStr.startsWith(formateStr)) {
			inputStr = inputStr.substring(formateStr.length());
		}

		if (inputStr.endsWith(formateStr)) {
			inputStr = inputStr.substring(0, inputStr.length() - formateStr.length());
		}
		return inputStr;
	}

	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? NUMBER_ARRAY_STR : VALID_CHAR_STR;
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * 
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr) {
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
		valStr = valStr + ",";
		while (valStr.indexOf(',') > 0) {
			returnStr[i] = valStr.substring(0, valStr.indexOf(','));
			valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

			i++;
		}
		return returnStr;
	}

	/**
	 * 获取字符串编码
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isCompleteEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notCompleteEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isCompleteEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	
	public static String getRightTypeData(String headUrl, String dataType) {
		if(isEmpty(headUrl) || !headUrl.endsWith(dataType)){
			return null;
		}
		return headUrl;
	}
}
package com.smart.platform.toolkit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public final class XssWhiteListUtil {

	private static final String XSS_WHITE_LIST = "xssWhiteList";

	private static final Logger LOG = LoggerFactory.getLogger(XssWhiteListUtil.class);

	private static Whitelist whitelist = null;

	private static Map<String, String> regexMap = null;

	/**
	 * 采用jsoup白名单方式过滤非法的html字符。 原理： 1.首先通过白名单过滤掉非法的html标签，即只允许输出白名单内的标签
	 * 2.对特殊的属性（主要是style）用正则过滤，只允许安全的属性值存在
	 * @param htmlStr
	 *            原始的html片段（用户通过富文本编辑器提交的html代码）
	 * @return 过滤后的安全的html片段
	 * @return
	 */
	public static String whiteListXss(String htmlStr) {
		Document doc = Jsoup.parseBodyFragment(htmlStr);
		OutputSettings outSet = new OutputSettings();
		outSet.prettyPrint(false);
		outSet.outline(false);
		doc.outputSettings(outSet);
		Map<String, String> regexMap = initRegexMap();
		if (regexMap != null) {
			for (Map.Entry<String, String> entiy : regexMap.entrySet()) {
				String key = entiy.getKey();
				Elements els = doc.select(key);
				for (Element el : els) {
					String attribute = key.substring(key.indexOf("[") + 1, key.indexOf("]"));
					String attributeValue = el.attr(attribute);
					Matcher valueMatcher = Pattern.compile(entiy.getValue()).matcher(attributeValue);
					if (valueMatcher.find()) {
						String safeValue = valueMatcher.group();
						el.attr(attribute, safeValue);
					}
				}
			}
		}
		Whitelist whitelist = initWhiteList();
		return Jsoup.clean(doc.html(), "", whitelist);
	}
	
	private static Whitelist initWhiteList() {
		if (whitelist != null) {
			return whitelist;
		}
		synchronized (new Object()) {
			whitelist = new Whitelist();
			Map<String, Map<String, String>> whitelistmap = readWhiteJsonData();
			for (Entry<String, Map<String, String>> entiy : whitelistmap.entrySet()) {
				String tag = entiy.getKey();
				whitelist.addTags(tag);
				for (Entry<String, String> entiy2 : entiy.getValue().entrySet()) {
					String attribute = entiy2.getKey();
					whitelist.addAttributes(tag, attribute);
				}
			}
		}
		return whitelist;
	}

	private static Map<String, String> initRegexMap() {
		if (regexMap != null && !regexMap.isEmpty()) {
			return regexMap;
		}
		synchronized (new Object()) {
			regexMap = new HashMap<String, String>();
			Map<String, Map<String, String>> whitelistmap = readWhiteJsonData();
			for (Map.Entry<String, Map<String, String>> entiy : whitelistmap.entrySet()) {
				String tag = entiy.getKey();
				for (Map.Entry<String, String> entiy2 : entiy.getValue().entrySet()) {
					String attribute = entiy2.getKey();
					String attributeValue = entiy2.getValue();
					if (attributeValue != null && attributeValue.trim().length() > 0) {
						regexMap.put(tag + "[" + attribute + "]", attributeValue);
					}
				}
			}
		}
		return regexMap;
	}

	private static Map<String, Map<String, String>> readWhiteJsonData() {
		Resource resource = new ClassPathResource("sysconstants.json");
		try {
			File file = resource.getFile();
			String jsonString = FileUtils.readFileToString(file, "UTF-8");
			JSONObject jsonObject = JSON.parseObject(jsonString);
			JSONObject whitelistjson = jsonObject.getJSONObject(XSS_WHITE_LIST);
			return JSON.parseObject(whitelistjson.toString(), HashMap.class);
		} catch (Exception e) {
			LOG.error("init system xss white list error:{}", e.toString());
			return null;
		}
	}
}
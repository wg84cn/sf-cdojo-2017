package com.sf.iguess.survey.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sf.iguess.survey.service.AreaService;

/**
 * ClassName: AreaServiceImpl <br/>
 * Function: 地区业务类. <br/>
 * date: 2017年12月15日 下午3:18:56 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
@Service
public class AreaServiceImpl implements AreaService {
	//日志类
	private static final Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
	//地区字典  json 文件名
	private static final String AREA_JSON_NAME = "area.json";
	//classPath地址
	private static final String FILE_DIR = 
			Thread.currentThread().getContextClassLoader().getResource("").getPath();
	//地区分隔符
	private static final String AREA_SEPERATE = "-";
	//文本关键字text
	private static final String AREA_PARAM_TEXT = "text";
	//文本关键字children
	private static final String AREA_PARAM_CHILDREN = "children";
	
	@Override
	public String readAreaJson() {
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader bf = new BufferedReader(
				new FileReader(FILE_DIR + AREA_JSON_NAME))){
			String line;
			while ((line = bf.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			logger.error("城市地区编码错误",e);
		}
		return stringBuilder.toString();
	}

	/**
	 * 约定字符判断是否存在该地区
	 * @see com.sf.iguess.survey.service.AreaService#isExist(java.lang.String)
	 */
	@Override
	public boolean isExist(String area) {
		if(StringUtils.isNotBlank(area)){
			String[] areas = area.split(AREA_SEPERATE);
			int flagLevel = 0;
			JSONArray jsonArr = JSONArray.parseArray(this.readAreaJson());
			return this.isExist(jsonArr,flagLevel,areas);
		}
		return false;
	}
	
	/**
	 * isExist:递归调用判断是否存在，并且给到最底层<br/>
	 *
	 * @author 618721
	 * @param jsonArr
	 * @param flagLevel
	 * @param areas
	 * @return
	 * @since JDK 1.8
	 */
	private boolean isExist(JSONArray jsonArr,int flagLevel,String[] areas){
		if(flagLevel < areas.length){
			for (Object obj : jsonArr) {
				JSONObject json = (JSONObject)obj;
				if(json.getString(AREA_PARAM_TEXT).equals(areas[flagLevel])){
					flagLevel++;
					return isExist(json.getJSONArray(AREA_PARAM_CHILDREN),flagLevel,areas);
				}
			}
			return false;
		}
		return jsonArr == null;
	}
	
}

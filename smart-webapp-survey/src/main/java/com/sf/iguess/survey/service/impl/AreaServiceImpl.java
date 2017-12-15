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

@Service
public class AreaServiceImpl implements AreaService {
	
	private static final Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
	
	private static final String AREA_JSON_NAME = "area.json";
	private static final String FILE_DIR = 
			Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final String AREA_SEPERATE = "-";
	
	private static final String AREA_PARAM_TEST = "text";
	private static final String AREA_PARAM_CHILDREN = "children";
	
	@Override
	public String listArea() {
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

	@Override
	public boolean isExist(String area) {
		if(StringUtils.isNotBlank(area)){
			String[] areas = area.split(AREA_SEPERATE);
			int flagLevel = 0;
			JSONArray jsonArr = JSONArray.parseArray(this.listArea());
			return this.isExist(jsonArr,flagLevel,areas);
		}
		return false;
	}
	
	
	private boolean isExist(JSONArray jsonArr,int flagLevel,String[] areas){
		if(flagLevel < areas.length){
			for (Object obj : jsonArr) {
				JSONObject json = (JSONObject)obj;
				if(json.getString(AREA_PARAM_TEST).equals(areas[flagLevel])){
					flagLevel++;
					return isExist(json.getJSONArray(AREA_PARAM_CHILDREN),flagLevel,areas);
				}
			}
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		AreaService areaService = new AreaServiceImpl();
		System.out.println(areaService.isExist("北京市-北京市"));
	}

}

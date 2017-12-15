package com.sf.iguess.survey.service;

/**
 * ClassName: AreaService <br/>
 * Function: 地区业务类. <br/>
 * date: 2017年12月15日 下午3:18:07 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
public interface AreaService {
	/**
	 * readAreaJson:读取地区字典文件. <br/>
	 *
	 * @author 618721
	 * @return
	 * @since JDK 1.8
	 */
	public String readAreaJson();
	
	/**
	 * isExist:是否存在该地区. <br/>
	 *
	 * @author 618721
	 * @param area
	 * @return
	 * @since JDK 1.8
	 */
	public boolean isExist(String area); 
	
}

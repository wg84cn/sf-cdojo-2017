package com.sf.iguess.survey.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author 80002286
 * @date 2017年12月5日
 * @time 下午4:23:46
 * @description
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page{
	/**默认显示数据量*/
	public static int DEFAULT_PAGESIZE = 10;	
	private int pageIndex;          //当前页码
	private int pageSize;        //每页行数
	private int totalRecord;      //总记录数
	private int totalPage;        //总页数
	private int limitBegin;		//mysql 分页起始点 mybatis不支持sql计算 直接计算传入
	private Map<String,Object> condition;
	
	public Page() 
	{
		pageIndex = 1;
		pageSize = DEFAULT_PAGESIZE;
		totalRecord = 0;
		totalPage = 0;
	}
		   
	public static Page newBuilder(int pageIndex, int pageSize,int totalRecord){
	    Page page = new Page();
	    page.setPageIndex(pageIndex);
	    page.setPageSize(pageSize);
	    page.setLimitBegin(0);
	    page.setTotalPage(totalRecord/pageSize+1);
	    page.setTotalRecord(totalRecord);
	    return page;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	public Map<String, Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}

	public int getLimitBegin() {
		return limitBegin;
	}

	public void setLimitBegin(int limitBegin) {
		this.limitBegin = limitBegin;
	}
	
	
}

package com.sf.iguess.survey.domain;

import java.util.Date;

public class UserScore {
    private String scoreId;

    private String userName;			//用户名

    private String supplierName;        //供应商全称

    private String projectName;         //合作项目名称

    private String relationerName;      //联系人名称

    private String relationerEmail;     //联系人邮箱

    private String buyerName;           //采购人名称

    private String buyerEmail;          //采购人邮箱

    private Date startTime;             //作答开始时间

    private Date endTime;               //作答结束时间

    private Integer score;              //总分
    
    private Integer surveyType;			//问卷类型

    private Integer status;             //是否及格，1：及格，0：不及格

    private Date createTime;

    private Date modifyTime;

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRelationerName() {
        return relationerName;
    }

    public void setRelationerName(String relationerName) {
        this.relationerName = relationerName;
    }

    public String getRelationerEmail() {
        return relationerEmail;
    }

    public void setRelationerEmail(String relationerEmail) {
        this.relationerEmail = relationerEmail;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSurveyType() {
		return surveyType;
	}

	public void setSurveyType(Integer surveyType) {
		this.surveyType = surveyType;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
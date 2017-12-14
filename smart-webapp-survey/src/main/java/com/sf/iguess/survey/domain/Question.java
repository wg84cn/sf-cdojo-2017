package com.sf.iguess.survey.domain;

import java.util.Date;

public class Question {
    private String id;                 	//使用UUID

    private Integer surveyType;			//问卷调查类型

    private Integer serialNumber;      	//问题的序列号

    private String questionName;       	//题目

    private String classes;            	//分类

    private String chidren;            	//子类
    
    private Byte replyRequired;       	//是否为必须回复项
    
    private String reply;				//回复
    
    private Byte dataRequired;			//是否为必填资料证明

    private String dataProve;			//资料证明

    private Date createTime;

    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(Integer surveyType) {
        this.surveyType = surveyType;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getChidren() {
        return chidren;
    }

    public void setChidren(String chidren) {
        this.chidren = chidren;
    }

    public Byte getReplyRequired() {
		return replyRequired;
	}

	public void setReplyRequired(Byte replyRequired) {
		this.replyRequired = replyRequired;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Byte getDataRequired() {
		return dataRequired;
	}

	public void setDataRequired(Byte dataRequired) {
		this.dataRequired = dataRequired;
	}

	public String getDataProve() {
		return dataProve;
	}

	public void setDataProve(String dataProve) {
		this.dataProve = dataProve;
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
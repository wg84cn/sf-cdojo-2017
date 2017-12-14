package com.sf.iguess.survey.domain;

import java.util.Date;

public class Answer {
    private String id;                	//使用UUID

    private String scoreId;          	//得分情况表ID
    
    private Integer serialNumber;       //问题表序号

    private Integer choseValue;       	//所选值

    private String reply;				//回复

    private String fileLink;			//文件位置(根路径到文件的路径)
    
    private String fileName;            //文件位置(根路径到文件的路径)

    private Date createTime;

    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScoreId() {
		return scoreId;
	}

	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}

	public Integer getChoseValue() {
        return choseValue;
    }

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setChoseValue(Integer choseValue) {
        this.choseValue = choseValue;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
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

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
}
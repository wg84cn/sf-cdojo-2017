package com.sf.iguess.survey.domain;

/**
 * ClassName: ExpressDelivery <br/>
 * Function: 寄件信息实体类. <br/>
 * date: 2017年12月15日 上午11:05:35 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
public class ExpressDelivery {
	/** id */
    private Long id;
    /** storeId 外键 */
    private String storeId;
    /** 用户地区 */
    private String userArea;
    /** 用户名称 */
    private String userName;
    /** 电话号码 */
    private String phoneNumber;
    /** 每日件量 */
    private Integer dayExpressNumber;
    /** 每件平均重量 */
    private Double avgWeight;
    /** 详细地址 */
    private String detailAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea == null ? null : userArea.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Integer getDayExpressNumber() {
        return dayExpressNumber;
    }

    public void setDayExpressNumber(Integer dayExpressNumber) {
        this.dayExpressNumber = dayExpressNumber;
    }

    public Double getAvgWeight() {
		return avgWeight;
	}

	public void setAvgWeight(Double avgWeight) {
		this.avgWeight = avgWeight;
	}

	public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
    
}
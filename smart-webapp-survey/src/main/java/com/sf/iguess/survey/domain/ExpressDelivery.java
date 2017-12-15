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
	
    private Long id;
    
    private String storeId;

    private Long userId;

    private String userArea;

    private String userName;

    private String phoneNumber;

    private Integer dayExpressNumber;

    private Double avgWeight;

    private String detailAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
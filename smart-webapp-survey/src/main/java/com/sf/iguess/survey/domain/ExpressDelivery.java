package com.sf.iguess.survey.domain;

public class ExpressDelivery {
    private Long id;

    private Long userId;

    private String userArea;

    private String userName;

    private String phoneNumber;

    private Integer dayExpressNumber;

    private Float avgWeight;

    private byte[] detailAddress;

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

    public Float getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(Float avgWeight) {
        this.avgWeight = avgWeight;
    }

    public byte[] getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(byte[] detailAddress) {
        this.detailAddress = detailAddress;
    }
}
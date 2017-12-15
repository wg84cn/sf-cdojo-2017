package com.sf.iguess.survey.domain;

public class MarketBasicInfo {
    private String mktId;

    private String mktNameShow;

    private Short dailyMinPackages;

    private Double weightMin;

    private Double weightMax;

    private Double basePrice;

    private Double baseWeight;

    private Short groupLimit;

    private Byte groupDuration;

    private String userRequire;

    public String getMktId() {
        return mktId;
    }

    public void setMktId(String mktId) {
        this.mktId = mktId == null ? null : mktId.trim();
    }

    public String getMktNameShow() {
        return mktNameShow;
    }

    public void setMktNameShow(String mktNameShow) {
        this.mktNameShow = mktNameShow == null ? null : mktNameShow.trim();
    }

    public Short getDailyMinPackages() {
        return dailyMinPackages;
    }

    public void setDailyMinPackages(Short dailyMinPackages) {
        this.dailyMinPackages = dailyMinPackages;
    }

    public Double getWeightMin() {
        return weightMin;
    }

    public void setWeightMin(Double weightMin) {
        this.weightMin = weightMin;
    }

    public Double getWeightMax() {
        return weightMax;
    }

    public void setWeightMax(Double weightMax) {
        this.weightMax = weightMax;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getBaseWeight() {
        return baseWeight;
    }

    public void setBaseWeight(Double baseWeight) {
        this.baseWeight = baseWeight;
    }

    public Short getGroupLimit() {
        return groupLimit;
    }

    public void setGroupLimit(Short groupLimit) {
        this.groupLimit = groupLimit;
    }

    public Byte getGroupDuration() {
        return groupDuration;
    }

    public void setGroupDuration(Byte groupDuration) {
        this.groupDuration = groupDuration;
    }

    public String getUserRequire() {
        return userRequire;
    }

    public void setUserRequire(String userRequire) {
        this.userRequire = userRequire == null ? null : userRequire.trim();
    }
}
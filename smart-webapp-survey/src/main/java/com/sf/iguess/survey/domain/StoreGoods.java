package com.sf.iguess.survey.domain;

import java.util.Date;

public class StoreGoods {

	private String storeId;

	private String storeMarketPic;

	private Integer storedNumber;

	private String marketId;

	private String mktNameShow;

	private Short dailyMinPackages;

	private Double weightMin;

	private Double weightMax;

	private Double basePrice;

	private Double baseWeight;

	private Short groupLimit;

	private Byte groupDuration;

	private String userRequire;

	public StoreGoods() {
	}

	public StoreGoods(String storeId, String marketId, Integer storedNumber) {
		this.storeId = storeId;
		this.marketId = marketId;
		this.storedNumber = storedNumber;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getMktNameShow() {
		return mktNameShow;
	}

	public void setMktNameShow(String mktNameShow) {
		this.mktNameShow = mktNameShow;
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
		this.userRequire = userRequire;
	}

	private Date createTime;

	private Date modifyTime;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreMarketPic() {
		return storeMarketPic;
	}

	public void setStoreMarketPic(String storeMarketPic) {
		this.storeMarketPic = storeMarketPic == null ? null : storeMarketPic.trim();
	}

	public Integer getStoredNumber() {
		return storedNumber;
	}

	public void setStoredNumber(Integer storedNumber) {
		this.storedNumber = storedNumber;
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
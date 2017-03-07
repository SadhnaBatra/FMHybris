package com.fmo.wom.domain.enums;

public enum ShippingMethodCategory {

	EMERGENCY(false,"EMERGENCY", ""),
	ENGEXPRESS(true,"ENGEXPRESS", "Eng Express"),
	FREEFREIGHT(false,"FREEFREIGHT", ""),
	REGULAR(false,"REGULAR", ""),
	STOCK(false,"STOCK", "");
	
	private boolean promotion;
	private String categoryCode;
	private String locationCode;
	
	private ShippingMethodCategory(boolean promotion, String categoryCode, String locationCode) {
	    this.promotion = promotion;
	    this.categoryCode = categoryCode;
	    this.locationCode = locationCode;
	}

	public boolean isPromotion() {
		return promotion;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public String getLocationCode() {
		return locationCode;
	}

	
}

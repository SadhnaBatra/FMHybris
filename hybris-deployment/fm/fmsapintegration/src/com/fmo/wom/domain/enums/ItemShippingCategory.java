package com.fmo.wom.domain.enums;
/**
 * Used to identify the category of the items in a shipping package. 
 * The item category is related to a ShippingMethodCategory which is used to select the shipping methods that a user can select from.
 * 
 */
public enum ItemShippingCategory {
	ENGEXPRESS(true,"Eng Express", "express.gif",ShippingMethodCategory.ENGEXPRESS),
	REGULAR(false, "", "_blank.png", ShippingMethodCategory.REGULAR);
	
	private boolean promotion;
	private String categoryName;
	private String imageName;
	private ShippingMethodCategory shippingMethodCategory;
	
	private ItemShippingCategory(boolean promotion, String categoryName, String imageName, ShippingMethodCategory shippingMethodCategory) {
		this.promotion = promotion;
		this.categoryName = categoryName;
		this.imageName = imageName;
		this.shippingMethodCategory = shippingMethodCategory;
	}

	public boolean isPromotion() {
		return promotion;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public ShippingMethodCategory getShippingMethodCategory() {
		return shippingMethodCategory;
	}

	public String getImageName() {
		return imageName;
	}
	
}

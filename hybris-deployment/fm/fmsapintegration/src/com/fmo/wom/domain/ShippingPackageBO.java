package com.fmo.wom.domain;

import java.math.BigDecimal;
import java.util.List;

//
//

import com.fmo.wom.domain.enums.ItemShippingCategory;

public class ShippingPackageBO {
	//private static Logger logger = Logger.getLogger(ShippingPackageBO.class);
	
	private DistributionCenterBO    	distributionCenterBO;  	//Source Distribution Center for this Shipping Package
	private String						locationIdentifier; 	//Identifies this Shipping Package - uniquely identifies mackage for user
	private BigDecimal  				packageWeight;			//Weight of this shipping package
	private BigDecimal					packagePrice;			//Price of this shipping package
	private BigDecimal					packageShipping;			//Estimated shipping cost of this package
	private List<ShippingMethodBO>	    shippingMethodList;     //List of allowable shipping methods for this package
	private List<ItemBO>				packageItems;           //Items from order that are assigned to this package
	private ItemShippingCategory		itemShippingCategory;	//e.g. REGULAR ENGEXPRESS
	
	public ShippingPackageBO() {}
	
	// Accessor methods
	
	public DistributionCenterBO getDistributionCenterBO() {
		return distributionCenterBO;
	}
	public void setDistributionCenterBO(DistributionCenterBO distributionCenterBO) {
		this.distributionCenterBO = distributionCenterBO;
	}
	public String getLocationIdentifier() {
		//logger.debug("getLocationIdentifier() ==> {}", locationIdentifier);
		return locationIdentifier;
	}
	public void setLocationIdentifier(String locationIdentifier) {
		this.locationIdentifier = locationIdentifier;
	}
	public BigDecimal getPackagePrice() {
		return packagePrice;
	}
	public void setPackagePrice(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}
	public BigDecimal getPackageShipping() {
		return packageShipping;
	}
	public void setPackageShipping(BigDecimal packageShipping) {
		this.packageShipping = packageShipping;
	}
	public BigDecimal getPackageWeight() {
		return packageWeight;
	}
	public void setPackageWeight(BigDecimal packageWeight) {
		this.packageWeight = packageWeight;
	}
	public boolean isPromotion() {
		return getItemShippingCategory().isPromotion();
	}
	public String getPromotionImageName() {
		return getItemShippingCategory().getImageName();
	}
	public ItemShippingCategory getItemShippingCategory() {
		return itemShippingCategory;
	}
	public void setItemShippingCategory(ItemShippingCategory itemShippingCategory) {
		this.itemShippingCategory = itemShippingCategory;
	}
	public List<ShippingMethodBO> getShippingMethodList() {
		return shippingMethodList;
	}
	public void setShippingMethodList(List<ShippingMethodBO> shippingMethodList) {
		this.shippingMethodList = shippingMethodList;
	}
	public List<ItemBO> getPackageItems() {
		return packageItems;
	}
	public void setPackageItems(List<ItemBO> packageItems) {
		this.packageItems = packageItems;
	}

	@Override
	public String toString() {
		return "ShippingPackageBO [distributionCenterID=" + (distributionCenterBO == null ? -1 : distributionCenterBO.getDistCenterId()) 
				+ ", locationIdentifier=" + locationIdentifier 
				+ ", packageWeight=" + packageWeight
				+ ", shippingMethodListSize=" + (shippingMethodList == null ? 0 : shippingMethodList.size())
				+ ", packageItemsSize=" + (packageItems == null ? 0 : packageItems.size()) 
				+ ", itemShippingCategory="	+ itemShippingCategory 
				+ "]";
	}

}

package com.federalmogul.storefront.forms;

import de.hybris.platform.commercefacades.user.data.AddressData;

public class UPSForm {
	
	private String dcCode=null;
	private AddressData shipToAddress;
	private AddressData shipFromAddress;
	private Double itemWeight;
	private String shippingMethod;
	private String dcName;
	
	public String getDcName() {
		return dcName;
	}
	public void setDcName(String dcName) {
		this.dcName = dcName;
	}
	public String getDcCode() {
		return dcCode;
	}
	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}
	public AddressData getShipToAddress() {
		return shipToAddress;
	}
	public void setShipToAddress(AddressData shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public AddressData getShipFromAddress() {
		return shipFromAddress;
	}
	public void setShipFromAddress(AddressData shipFromAddress) {
		this.shipFromAddress = shipFromAddress;
	}
	public Double getItemWeight() {
		return itemWeight;
	}
	public void setItemWeight(Double itemWeight) {
		this.itemWeight = itemWeight;
	}
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}


}

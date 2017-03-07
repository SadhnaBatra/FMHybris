package com.fmo.wom.domain;

import java.io.Serializable;

/**
 * Entity implementation class for Entity: DcShippingMethodCategoryBO
 *
 */
public class DcShippingMethodCategoryBO implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	private long dcCarrierShippingMethodId;   

	private long smCategoryId;

	private DcShippingMethodBO dcShippingMethod;

	private ShippingMethodCategoryBO shippingMethodCategory;

	
	public DcShippingMethodCategoryBO() {
		super();
	}   
	public long getDcCarrierShippingMethodId() {
		return this.dcCarrierShippingMethodId;
	}
	public void setDcCarrierShippingMethodId(long dcCarrierShippingMethodId) {
		this.dcCarrierShippingMethodId = dcCarrierShippingMethodId;
	}   
	public long getSmCategoryId() {
		return this.smCategoryId;
	}
	public void setSmCategoryId(long smCategoryId) {
		this.smCategoryId = smCategoryId;
	}
	public DcShippingMethodBO getDcShippingMethod() {
		return dcShippingMethod;
	}
	public void setDcShippingMethod(DcShippingMethodBO dcShippingMethod) {
		this.dcShippingMethod = dcShippingMethod;
	}
	public ShippingMethodCategoryBO getShippingMethodCategory() {
		return shippingMethodCategory;
	}
	public void setShippingMethodCategory(
			ShippingMethodCategoryBO shippingMethodCategory) {
		this.shippingMethodCategory = shippingMethodCategory;
	}
   
}

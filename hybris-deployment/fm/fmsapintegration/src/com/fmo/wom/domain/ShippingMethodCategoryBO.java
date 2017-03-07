package com.fmo.wom.domain;

import java.io.Serializable;
//import javax.persistence.*;

import com.fmo.wom.domain.enums.ShippingMethodCategory;

/**
 * Entity implementation class for Entity: ShippingMethodCategoryBO
 *
 */
//Entity
//Table(name="SHIPPING_METHOD_CATEGORY")
public class ShippingMethodCategoryBO implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	//Id
	//Column(name = "SM_CATEGORY_ID")
	private long smCategoryId;

	//Column(name = "SM_CATEGORY_NAME")
	//Enumerated(EnumType.STRING)
	private ShippingMethodCategory shippingMethodCategoryName;

	public ShippingMethodCategoryBO() {
		super();
	}   
	public long getSmCategoryId() {
		return this.smCategoryId;
	}
	public void setSmCategoryId(long smCategoryId) {
		this.smCategoryId = smCategoryId;
	}
	public ShippingMethodCategory getShippingMethodCategoryName() {
		return shippingMethodCategoryName;
	}
	public void setShippingMethodCategoryType(
			ShippingMethodCategory shippingMethodCategoryType) {
	}   
   
}

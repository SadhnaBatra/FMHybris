package com.fmo.wom.domain.ecc;

import java.io.Serializable;

import com.fmo.wom.domain.WOMBaseBO;

public class ShippedItemBO extends WOMBaseBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int orderedQuantity;
	
	private int shippedQuantity;
	
	private String unit;
	
	private String partNumber;
	
	private String description;
	
	private double unitPrice;
	
	private double totalPrice;

	public ShippedItemBO() {
		super();
	}

	public int getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(int orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public int getShippedQuantity() {
		return shippedQuantity;
	}

	public void setShippedQuantity(int shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Shipped Items [orderedQuantity=" + orderedQuantity
				+ ", shippedQuantity=" + shippedQuantity 
				+ ", unit=" + unit
				+ ", partNumber=" + partNumber 
				+ ", description=" + description
				+ ", unitPrice=" + unitPrice 
				+ ", totalPrice=" + totalPrice
				+ "]";
	}

}

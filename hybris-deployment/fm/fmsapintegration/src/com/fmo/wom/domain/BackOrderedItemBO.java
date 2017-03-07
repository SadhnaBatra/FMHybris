package com.fmo.wom.domain;

import java.util.Date;


public class BackOrderedItemBO {

	private String partNumber;
	private int originalOrderQty;
	private int backOrderQty;
	private Date availabilityDate;
	
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	public int getOriginalOrderQty() {
		return originalOrderQty;
	}
	public void setOriginalOrderQty(int originalOrderQty) {
		this.originalOrderQty = originalOrderQty;
	}
	
	public int getBackOrderQty() {
		return backOrderQty;
	}
	public void setBackOrderQty(int backOrderQty) {
		this.backOrderQty = backOrderQty;
	}
	
	public Date getAvailabilityDate() {
		return availabilityDate;
	}
	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}
	
	@Override
	public String toString() {
		return "BackOrderedItemBO [partNumber=" + partNumber
				+ ", originalOrderQty=" + originalOrderQty + ", backOrderQty="
				+ backOrderQty + ", availabilityDate=" + availabilityDate + "]";
	}

}

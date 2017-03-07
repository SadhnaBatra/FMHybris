package com.fmo.wom.domain;

import java.util.Date;
import java.util.List;

import com.fmo.wom.domain.enums.ExternalSystem;

public class BackOrderUnitBO {

	private String orderNumber;
	private ExternalSystem externalSystem;
	private Date orderDate;
	private DistributionCenterBO distributionCenter;
	private List<BackOrderedItemBO> backOrderedItems;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public ExternalSystem getExternalSystem() {
		return externalSystem;
	}
	public void setExternalSystem(ExternalSystem externalSystem) {
		this.externalSystem = externalSystem;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public DistributionCenterBO getDistributionCenter() {
		return distributionCenter;
	}
	public void setDistributionCenter(DistributionCenterBO distributionCenter) {
		this.distributionCenter = distributionCenter;
	}
	
	public List<BackOrderedItemBO> getBackOrderedItems() {
		return backOrderedItems;
	}
	public void setBackOrderedItems(List<BackOrderedItemBO> backOrderedItems) {
		this.backOrderedItems = backOrderedItems;
	}
	
	@Override
	public String toString() {
		return "BackOrderUnitBO [orderNumber=" + orderNumber
				+ ", externalSystem=" + externalSystem + ", orderDate="
				+ orderDate + ", distributionCenter=" + distributionCenter
				+ ", backOrderedItems=" + backOrderedItems + "]";
	}

}

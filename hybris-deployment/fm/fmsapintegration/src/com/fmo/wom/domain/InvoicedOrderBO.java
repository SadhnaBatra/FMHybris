package com.fmo.wom.domain;

public class InvoicedOrderBO {

	private String orderNumber;
	private String customerPurchaseOrderNumber;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getCustomerPurchaseOrderNumber() {
		return customerPurchaseOrderNumber;
	}
	
	public void setCustomerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
		this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
	}

	@Override
	public String toString() {
		return "InvoicedOrderBO [orderNumber=" + orderNumber
				+ ", customerPurchaseOrderNumber="
				+ customerPurchaseOrderNumber + "]";
	}
	
}

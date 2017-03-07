package com.fmo.wom.domain;

import java.util.List;

public class BackOrderBO {

	private AccountBO billToAccount;
	private AccountBO shipToAccount;
	private String masterOrderNumber;
	private String customerPurchaseOrderNumber;
	private List<BackOrderUnitBO> backOrderUnits;
	
	public AccountBO getBillToAccount() {
		return billToAccount;
	}
	public void setBillToAccount(AccountBO billToAccount) {
		this.billToAccount = billToAccount;
	}
	
	public AccountBO getShipToAccount() {
		return shipToAccount;
	}
	public void setShipToAccount(AccountBO shipToAccount) {
		this.shipToAccount = shipToAccount;
	}
	
	public String getMasterOrderNumber() {
		return masterOrderNumber;
	}
	public void setMasterOrderNumber(String masterOrderNumber) {
		this.masterOrderNumber = masterOrderNumber;
	}
	
	public String getCustomerPurchaseOrderNumber() {
		return customerPurchaseOrderNumber;
	}
	public void setCustomerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
		this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
	}
	
	public List<BackOrderUnitBO> getBackOrderUnits() {
		return backOrderUnits;
	}
	public void setBackOrderUnits(List<BackOrderUnitBO> backOrderUnits) {
		this.backOrderUnits = backOrderUnits;
	}
	
	@Override
	public String toString() {
		return "BackOrderBO [billToAccount=" + billToAccount
				+ ", shipToAccount=" + shipToAccount + ", masterOrderNumber="
				+ masterOrderNumber + ", customerPurchaseOrderNumber="
				+ customerPurchaseOrderNumber + ", backOrderUnits="
				+ backOrderUnits + "]";
	}
	
}

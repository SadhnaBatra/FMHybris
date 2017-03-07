package com.fmo.wom.business.util.backorder;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.DistributionCenterBO;

@SuppressWarnings("serial")
public class BackOrderSearchValues implements Serializable {

	private static Logger logger = Logger.getLogger(BackOrderSearchValues.class);
	
	private AccountBO billToAccount;
	private String partNumber = "";
	private AccountBO shipToAccount;
	private String masterOrderNumber = "";
	private String orderNumber;
	private String customerPONumber = "";
	private Date orderDate = null;
	private DistributionCenterBO distCntr = null;
	private int originalOrderQty = 0;
	private int backOrderQty = 0;
	private Date availabilityDate = null;
	
	public BackOrderSearchValues() {
	}

	public AccountBO getBillToAccount() {
		return billToAccount;
	}

	public void setBillToAccount(AccountBO billToAccount) {
		this.billToAccount = billToAccount;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCustomerPONumber() {
		return customerPONumber;
	}

	public void setCustomerPONumber(String customerPONumber) {
		this.customerPONumber = customerPONumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public DistributionCenterBO getDistCntr() {
		return distCntr;
	}

	public void setDistCntr(DistributionCenterBO distCntr) {
		this.distCntr = distCntr;
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

}

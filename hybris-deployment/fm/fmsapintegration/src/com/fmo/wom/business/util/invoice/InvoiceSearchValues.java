package com.fmo.wom.business.util.invoice;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.enums.InvoiceStatus;
import com.fmo.wom.domain.enums.InvoiceType;

public class InvoiceSearchValues implements Serializable {

	private static Logger logger = Logger.getLogger(InvoiceSearchValues.class);
	
	private AccountBO billToAccount;	
	private AccountBO shipToAccount;
	private String invoiceNumber;
	private Date invoiceDate;
	private PriceBO invoiceAmount;
	private String orderNumber;
	private String customerPurchaseOrderNumber;
	private String applicationCode;
	private InvoiceStatus invoiceStatus;
	private InvoiceType invoiceType;
	
	public InvoiceSearchValues() {

	}

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

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public PriceBO getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(PriceBO invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

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

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Override
	public String toString() {
		return "InvoiceSearchValues [billToAcct=" + billToAccount
				+ ", shipToAcct=" + shipToAccount + ", invoiceNumber="
				+ invoiceNumber + ", invoiceDate=" + invoiceDate
				+ ", invoiceAmount=" + invoiceAmount + ", orderNumber="
				+ orderNumber + ", customerPurchaseOrderNumber="
				+ customerPurchaseOrderNumber + ", applicationCode="
				+ applicationCode + ", invoiceStatus=" + invoiceStatus.getCode()
				+ ", invoiceType=" + invoiceType.getCode() + "]";
	}

}

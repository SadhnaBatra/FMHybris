package com.fmo.wom.domain;

import java.util.Date;

import com.fmo.wom.domain.enums.InvoiceStatus;
import com.fmo.wom.domain.enums.InvoiceType;

public class InvoiceBO {

	private AccountBO billToAccount;
	private AccountBO shipToAccount;
	private String invoiceNumber;
	private Date invoiceDate;
	private PriceBO invoiceAmount;
	//private List<InvoicedOrderBO> invoicedOrders;
	private InvoicedOrderBO invoiceOrderBO;
	private String applicationCode;
	private InvoiceStatus invoiceStatus;
	private InvoiceType invoiceType;
	
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
	
	/*public List<InvoicedOrderBO> getInvoicedOrders() {
		return invoicedOrders;
	}

	public void setInvoicedOrders(List<InvoicedOrderBO> invoicedOrders) {
		this.invoicedOrders = invoicedOrders;
	}*/

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
	

	public InvoicedOrderBO getInvoiceOrderBO() {
		return invoiceOrderBO;
	}

	public void setInvoiceOrderBO(InvoicedOrderBO invoiceOrderBO) {
		this.invoiceOrderBO = invoiceOrderBO;
	}

	@Override
	public String toString() {
		return "InvoiceBO [billToAccount=" + billToAccount + ", shipToAccount="
				+ shipToAccount + ", invoiceNumber=" + invoiceNumber
				+ ", invoiceDate=" + invoiceDate + ", invoiceAmount="
				+ invoiceAmount + ", invoicedOrder=" + invoiceOrderBO
				+ ", applicationCode=" + applicationCode + ", invoiceStatus="
				+ invoiceStatus + ", invoiceType=" + invoiceType + "]";
	}

}

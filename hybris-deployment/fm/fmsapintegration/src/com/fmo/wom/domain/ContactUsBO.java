package com.fmo.wom.domain;

import java.util.Date;

public class ContactUsBO {
	
	private String name;
	
	private String phoneNbr;
	
	private String purchaseOrderNbr;
	
	private String orderNbr;
	
	private Date orderDate;
	
	private String invoiceNbr;
	
	private Date invoiceDate;
	
	private String comments;

	public ContactUsBO() {
		this.name = "";
		this.phoneNbr = "";
		this.purchaseOrderNbr = "";
		this.orderNbr = "";
		this.orderDate = null;
		this.invoiceNbr = "";
		this.invoiceDate = null;
		this.comments = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNbr() {
		return phoneNbr;
	}

	public void setPhoneNbr(String phoneNbr) {
		this.phoneNbr = phoneNbr;
	}

	public String getPurchaseOrderNbr() {
		return purchaseOrderNbr;
	}

	public void setPurchaseOrderNbr(String purchaseOrderNbr) {
		this.purchaseOrderNbr = purchaseOrderNbr;
	}

	public String getOrderNbr() {
		return orderNbr;
	}

	public void setOrderNbr(String orderNbr) {
		this.orderNbr = orderNbr;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getInvoiceNbr() {
		return invoiceNbr;
	}

	public void setInvoiceNbr(String invoiceNbr) {
		this.invoiceNbr = invoiceNbr;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "ContactUsBO [name=" + name + ", phoneNbr=" + phoneNbr
				+ ", purchaseOrderNbr=" + purchaseOrderNbr + ", orderNbr="
				+ orderNbr + ", orderDate=" + orderDate + ", invoiceNbr="
				+ invoiceNbr + ", invoiceDate=" + invoiceDate + ", comments="
				+ comments + "]";
	}
	
}
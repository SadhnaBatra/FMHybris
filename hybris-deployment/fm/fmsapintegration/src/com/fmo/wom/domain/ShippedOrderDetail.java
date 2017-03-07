package com.fmo.wom.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;


public class ShippedOrderDetail extends ShippedOrder {

	private static final long serialVersionUID = 1L;
	
	private String orderedBy;
	
	private AccountBO billToAccount;
	
	private AccountBO shipToAccount;
	
	private String returnOrderReason;
	
	private List<String> commentsList;
	
	private Date invoiceDate;
	
	private Date cancelledDate;
	
	private Date requestedDeliveryDate;
	
	//Items Shipped from different dc's or from Same DC under different packing
	private List<ShippingUnitBO> shippingUnitBOList;

	public String getOrderedBy() {
		return orderedBy ==null ?"":orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
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

	public String getReturnOrderReason() {
		return returnOrderReason;
	}

	public void setReturnOrderReason(String returnOrderReason) {
		this.returnOrderReason = returnOrderReason;
	}

	public List<String> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<String> commentsList) {
		this.commentsList = commentsList;
	}
	
	public void addComment(String aComment) {
		if (commentsList == null) {
			commentsList = new ArrayList<String>();
		}
		if (GenericValidator.isBlankOrNull(aComment) == false) {
			commentsList.add(aComment);
		}
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}
	
	public String getInvoiceDateString() {
		String result = "";
		if (this.invoiceDate != null) {
			result = sdf.format(invoiceDate);
		}
		return result;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getCancelledDate() {
		return cancelledDate;
	}

	public String getCancelledDateString() {
		String result = "";
		if (this.cancelledDate != null) {
			result = sdf.format(cancelledDate);
		}
		return result;
	}
	
	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	public Date getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}
	
	public String getRequestedDeliveryDateString() {
		String result = "";
		if (this.requestedDeliveryDate != null) {
			result = sdf.format(requestedDeliveryDate);
		}
		return result;
	}

	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}

	public List<ShippingUnitBO> getShippingUnitBOList() {
		return shippingUnitBOList;
	}

	public void setShippingUnitBOList(List<ShippingUnitBO> shippingUnitBOList) {
		this.shippingUnitBOList = shippingUnitBOList;
	}

	@Override
	public String toString() {
		return "ShippedOrderDetail [orderedBy=" + orderedBy
				+ ", billToAccount=" + billToAccount + ", shipToAccount="
				+ shipToAccount + ", returnOrderReason=" + returnOrderReason
				+ ", commentsList=" + commentsList + ", invoiceDate="
				+ invoiceDate + ", cancelledDate=" + cancelledDate
				+ ", requestedDeliveryDate=" + requestedDeliveryDate
				+ ", shippingUnitBOList=" + shippingUnitBOList + ", orderType="
				+ orderType + ", poNumber=" + poNumber + ", masterOrderNumber="
				+ masterOrderNumber + ", orderNumber=" + orderNumber
				+ ", packingSlipNumber=" + packingSlipNumber + ", shippingDC="
				+ shippingDC + ", shipDate=" + shipDate + ", trackingNumber="
				+ trackingNumber + ", orderDate=" + orderDate
				+ ", orderStatus=" + orderStatus + ", orderSource="
				+ orderSource + ", externalSystem=" + externalSystem + "]";
	}
	
}

package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;

import com.fmo.wom.domain.enums.ExternalSystem;

/*
 * Holds All the Values returned by ZOTC_HYBRIS_ORD_SEARCH (Order Header Function)
 */
public class ECCShippedOrder implements Serializable{

	private static final long serialVersionUID = 1L;

	private ExternalSystem externalSystem;
	
	private String accountCode = null;
	
	private String orderNumber = null;
	
	private String masterOrderNumber = null;
	
	private String poNumber = null;
	
	private Date orderDate = null;
	
	private String deliveryNumber = null;
	
	private Date shipDate = null;
	
	private String orderSource = null;
	
	private String carrierCode = null;
	
	private String carrierName = null;
	
	private String trackingNumber = null;
	
	private String trackingStatus = null;
	
	protected DistributionCenterBO shippingDC;
	
	private String returnOrderReason = null;

	public ECCShippedOrder() {
		super();
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getMasterOrderNumber() {
		return masterOrderNumber;
	}

	public void setMasterOrderNumber(String masterOrderNumber) {
		this.masterOrderNumber = masterOrderNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}


	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrier) {
		this.carrierCode = carrier;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getTrackingStatus() {
		return trackingStatus;
	}

	public void setTrackingStatus(String trackingStatus) {
		this.trackingStatus = trackingStatus;
	}

	public ExternalSystem getExternalSystem() {
		return externalSystem;
	}

	public void setExternalSystem(ExternalSystem externalSystem) {
		this.externalSystem = externalSystem;
	}

	public DistributionCenterBO getShippingDC() {
		return shippingDC;
	}

	public void setShippingDC(DistributionCenterBO shippingDC) {
		this.shippingDC = shippingDC;
	}

	public String getReturnOrderReason() {
		return returnOrderReason;
	}

	public void setReturnOrderReason(String returnOrderReason) {
		this.returnOrderReason = returnOrderReason;
	}

	@Override
	public String toString() {
		return "ECCShippedOrder [accountCode=" + accountCode 
				+ ", masterOrderNumber=" + masterOrderNumber
				+ ", poNumber=" + poNumber
				+", orderNumber="+ orderNumber 
				+ ", orderDate=" + orderDate
				+ ", deliveryNumber=" + deliveryNumber 
				+ ", shipDate="+ shipDate 
				+ ", orderSource=" + orderSource 
				+ ", carrierCode="+ carrierCode 
				+ ", carrierName=" + carrierName
				+ ", trackingNumber=" + trackingNumber 
				+ ", trackingStatus="+ trackingStatus
				+ ", Shipping DC=" + shippingDC
				+", externalSystem= "+externalSystem
				+ ", returnOrderReason="+ returnOrderReason + "]";
	}

}

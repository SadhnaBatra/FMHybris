package com.fmo.wom.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.OrderType;

public class ShippedOrder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected static final String DATE_PATTERN = "MM/dd/yyyy";
	
	protected static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

	protected OrderType orderType;
	
	protected String poNumber;
	
	protected String masterOrderNumber;
	
	protected String orderNumber;
	
	protected String packingSlipNumber;
	
	protected DistributionCenterBO shippingDC;
	
	protected Date shipDate;
	
	protected String trackingNumber;
	
	protected String trackingURL;
	
	private String carrierName;
    
    private String carrierCode;
    
	protected Date orderDate;
	
	protected String orderStatus;
	
	protected String orderSource;
	
	protected ExternalSystem externalSystem;

	private String returnOrderReason;
	
	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public String getPoNumber() {
		return poNumber == null?"":poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getMasterOrderNumber() {
		return masterOrderNumber == null ?"":masterOrderNumber;
	}

	public void setMasterOrderNumber(String masterOrderNumber) {
		this.masterOrderNumber = masterOrderNumber;
	}

	public String getOrderNumber() {
		return orderNumber == null?"":orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPackingSlipNumber() {
		return packingSlipNumber == null?"":packingSlipNumber;
	}

	public void setPackingSlipNumber(String packingSlipNumber) {
		this.packingSlipNumber = packingSlipNumber;
	}

	public DistributionCenterBO getShippingDC() {
		return shippingDC;
	}
	
	public String getShippingDCName(){
		return shippingDC == null?"":shippingDC.getName().equalsIgnoreCase(DistributionCenterBO.NOT_FOUND)?"":shippingDC.getName();
	}

	public void setShippingDC(DistributionCenterBO shippingDC) {
		this.shippingDC = shippingDC;
	}

	public Date getShipDate() {
		return shipDate;
	}
	
	public String getShipDateString() {
		String result = "";
		if (this.shipDate != null) {
			result = sdf.format(shipDate);
		}
		return result;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public String getTrackingNumber() {
		return trackingNumber == null?"":trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}
	
	public String getOrderDateString() {
		String result = "";
		if (this.orderDate != null) {
			result = sdf.format(orderDate);
		}
		return result;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus == null?"":orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderSource() {
		return orderSource == null?"ECOM":orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public ExternalSystem getExternalSystem() {
		return externalSystem;
	}

	public void setExternalSystem(ExternalSystem externalSystem) {
		this.externalSystem = externalSystem;
	}
	
	public String getReturnOrderReason() {
		return returnOrderReason;
	}

	public void setReturnOrderReason(String returnOrderReason) {
		this.returnOrderReason = returnOrderReason;
	}
	
	public String getTrackingURL() {
		return trackingURL;
	}

	public void setTrackingURL(String trackingURL) {
		this.trackingURL = trackingURL;
	}

	public String getCarrierName() {
		return carrierName == null?"":carrierName.trim();
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getCarrierCode() {
		return carrierCode == null?"":carrierCode.trim();
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	@Override
	public String toString() {
		return "ShippedOrder [Order Type= "+ orderType+", poNumber=" + poNumber
				+ ", masterOrderNumber=" + masterOrderNumber + ", orderNumber="
				+ orderNumber + ", packingSlipNumber=" + packingSlipNumber
				+ ", shippingDC=" + shippingDC + ", shipDate=" + shipDate
				+ ", trackingNumber=" + trackingNumber + ", orderDate="
				+ orderDate + ", orderStatus=" + orderStatus + ", orderSource="
				+ orderSource + ", externalSystem=" + externalSystem 
				+", returnOrderReason "+returnOrderReason 
				+", carrierName "+carrierName
				+", carrierCode "+carrierCode
				+", trackingURL "+trackingURL+"]" ;
	}
	
}

package com.fmo.wom.business.orderstatus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderStatusResult {

	private static final String DATE_PATTERN = "MM/dd/yyyy";
	private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

	/* ShippedOrder instance used to create this result */
	private ShippedOrder shippedOrder;				//
	/* OrderShippingUnit instance used to create this result */
	private OrderShippingUnit orderShippingUnit; 
	
	private String purchaseOrderNumber;
	private String confirmationNumber;		//AKA Master Order Number
	private String orderNumber;
    private String packingSlipNumber;
	private Date   orderDate;
    private String shippingDC;				//Distribution center name
    private String status;					//Message key for status
    private Date   shipDate;
    private String trackingNumber;			//Carrier tracking number
    private String trackingURL;             //Carrier URL
    private String source;					//Order source (e.g. WEB)
	public OrderStatusResult(
			String purchaseOrderNumber,
			String confirmationNumber,
			String orderNumber,
			String packingSlipNumber,
			Date   orderDate,
			String shippingDC,
			String status,
			Date   shipDate,
			String trackingNumber,
			String trackingURL,
			String source,
			ShippedOrder shippedOrder,
			OrderShippingUnit orderShippingUnit
			) {
		super();
		this.setPurchaseOrderNumber(purchaseOrderNumber);
		this.setConfirmationNumber(confirmationNumber);
		this.setOrderNumber(orderNumber);
		this.setPackingSlipNumber(packingSlipNumber);
		this.setOrderDate(orderDate);
		this.setShippingDC(shippingDC);
		this.setStatus(status);
		this.setShipDate(shipDate);
		this.setTrackingNumber(trackingNumber);
		this.setTrackingURL(trackingURL);
		this.setSource(source);
		this.setShippedOrder(shippedOrder);
		this.setOrderShippingUnit(orderShippingUnit);
	}
	

	// ACCESSORS 
	public String getConfirmationNumber() {
		return confirmationNumber == null ? "" : confirmationNumber;
	}
	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderDateString() {
		String result = "";
		if (this.orderDate != null) {
			result = sdf.format(orderDate);
		}
		return result;
	}
	public String getOrderNumber() {
		return orderNumber == null ? "" : orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getPackingSlipNumber() {
		return packingSlipNumber == null ? "" : packingSlipNumber;
	}
	public void setPackingSlipNumber(String packingSlipNumber) {
		this.packingSlipNumber = packingSlipNumber;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber == null ? "" : purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public Date getShipDate() {
		return shipDate;
	}
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
	public String getShipDateString() {
		String result = "";
		if (this.shipDate != null) {
			result = sdf.format(shipDate);
		}
		return result;
	}
	public String getShippingDC() {
		return shippingDC == null ? "" : shippingDC;
	}
	public void setShippingDC(String shippingDC) {
		this.shippingDC = shippingDC;
	}
	public String getSource() {
		return source == null ? "" : source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStatus() {
		return status == null ? "" : status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTrackingNumber() {
		return trackingNumber == null ? "" : trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	
	public ShippedOrder getShippedOrder() {
		return shippedOrder;
	}

	public void setShippedOrder(ShippedOrder shippedOrder) {
		this.shippedOrder = shippedOrder;
	}

	public OrderShippingUnit getOrderShippingUnit() {
		return orderShippingUnit;
	}

	public void setOrderShippingUnit(OrderShippingUnit orderShippingUnit) {
		this.orderShippingUnit = orderShippingUnit;
	}
	
	public String getTrackingURL() {
		return trackingURL == null ? "" : trackingURL;
	}

	public void setTrackingURL(String trackingURL) {
		this.trackingURL = trackingURL;
	}


	public boolean isOpenOrder() {
		return true;
	}
	public boolean isClosedOrder() {
		return true;
	}
	@Override
	public String toString() {
		return "OrderStatusResult [purchaseOrderNumber="
				+ purchaseOrderNumber + ", confirmationNumber="
				+ confirmationNumber + ", orderNumber=" + orderNumber
				+ ", packingSlipNumber=" + packingSlipNumber + ", orderDate="
				+ getOrderDateString() + ", shippingDC="
				+ shippingDC + ", status="
				+ status + ", shipDate=" + getShipDateString()
				+ ", trackingNumber=" + trackingNumber + ", source=" + source
				+ "]";
	}
    
}

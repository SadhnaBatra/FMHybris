package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fmo.wom.domain.ecc.ShippedItemBO;

public class InvoiceDetailsBO extends WOMBaseBO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BillToAcctBO invoiceToAccount;
		
	private ShipToAcctBO deliverToAccount;
	
	private String invoiceNumber;
	
	private String packingSlip;
	
	private Date shippingDate;
    
	private String shippedVia;
	
	private String trackingNumber;
	
	private Date invoiceDate;
	
	private String confirmationNumer;
	
	private String paymentMethod;
	
	private List<ShippedItemBO> orderDetails;

	private double subTotal;
	
	//private String unitExt;
	
	private String deliveryCharges;
	
	private double tax;
	
	private double total;
	
	public InvoiceDetailsBO() {
		super();
	}

	public BillToAcctBO getInvoiceToAccount() {
		return invoiceToAccount;
	}

	public void setInvoiceToAccount(BillToAcctBO invoiceToAccount) {
		this.invoiceToAccount = invoiceToAccount;
	}

	public ShipToAcctBO getDeliverToAccount() {
		return deliverToAccount;
	}

	public void setDeliverToAccount(ShipToAcctBO deliverToAccount) {
		this.deliverToAccount = deliverToAccount;
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

	public String getConfirmationNumer() {
		return confirmationNumer;
	}

	public void setConfirmationNumer(String confirmationNumer) {
		this.confirmationNumer = confirmationNumer;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getPackingSlip() {
		return packingSlip;
	}

	public void setPackingSlip(String packingSlip) {
		this.packingSlip = packingSlip;
	}

	public String getShippedVia() {
		return shippedVia;
	}

	public void setShippedVia(String shippedVia) {
		this.shippedVia = shippedVia;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	/**************************************/
	 // Shipped Items
	/*************************************/
	
	public List<ShippedItemBO> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<ShippedItemBO> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	
	/**************************************/
	 // Invoice Footer
	/*************************************/
	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	/*public String getUnitExt() {
		return unitExt;
	}

	public void setUnitExt(String unitExt) {
		this.unitExt = unitExt;
	}*/

	public String getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(String deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Invoice Details [ invoiceNumber = "+invoiceNumber
				+", Invoiced To = "+invoiceToAccount.getAccountCode()
				+", Invoice To Address = "+invoiceToAccount.getAddress()
				+", Shipped To = "+deliverToAccount.getAccountCode()
				+", Shipped To Adress = "+deliverToAccount.getAddress()
				+", Packing Slip = "+packingSlip
				+", Shippng Date = "+shippingDate
				+", Shipped Via = "+shippedVia
				+", Tracking NUmber = "+trackingNumber
				+", Invoice Date = "+invoiceDate
				+", Payement Method = "+paymentMethod
				+", Order Details "+orderDetails
				+", Sub Total = "+subTotal
				//+", unitExt "+unitExt
				+", Delivery Charges = "+deliveryCharges
				+", Taxes = "+tax
				+", Total = "+total
				+" ]";
		
	}

}

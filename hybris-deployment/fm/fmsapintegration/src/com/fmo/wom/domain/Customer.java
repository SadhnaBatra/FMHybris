package com.fmo.wom.domain;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CUSTOMER database table.
 * 
 */

public class Customer extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long customerId;

	private String accountCode;

	private String billToFlag;

	private String buyingAffiliationCode;

	private String buyingGroupCode;

	private String channelCode;

	private Date createdDate;

	private String creditCardFlag;

	private String customerAddress1;

	private String customerAddress2;

	private String customerCity;

	private String customerName;

	private String customerStateProv;

	private String customerZipPostal;

	private String externalSystemCode;

	private String isoCountryCode;

	private BigDecimal kitExpressDistCntrId;

	private BigDecimal minFreeFreightAmt;

	private Date modifiedDate;

	private String regionCode;

	private String serviceTerritoryCode;

	private String shipToLimitFlag;

	private String shippingCode;

	private String territoryCode;

	private String viewInvoiceFlag;

	//bi-directional many-to-one association to CustomerComment
	private List<CustomerComment> customerComments;

    public Customer() {
    }

	public long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getAccountCode() {
		return this.accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getBillToFlag() {
		return this.billToFlag;
	}

	public void setBillToFlag(String billToFlag) {
		this.billToFlag = billToFlag;
	}

	public String getBuyingAffiliationCode() {
		return this.buyingAffiliationCode;
	}

	public void setBuyingAffiliationCode(String buyingAffiliationCode) {
		this.buyingAffiliationCode = buyingAffiliationCode;
	}

	public String getBuyingGroupCode() {
		return this.buyingGroupCode;
	}

	public void setBuyingGroupCode(String buyingGroupCode) {
		this.buyingGroupCode = buyingGroupCode;
	}

	public String getChannelCode() {
		return this.channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreditCardFlag() {
		return this.creditCardFlag;
	}

	public void setCreditCardFlag(String creditCardFlag) {
		this.creditCardFlag = creditCardFlag;
	}

	public String getCustomerAddress1() {
		return this.customerAddress1;
	}

	public void setCustomerAddress1(String customerAddress1) {
		this.customerAddress1 = customerAddress1;
	}

	public String getCustomerAddress2() {
		return this.customerAddress2;
	}

	public void setCustomerAddress2(String customerAddress2) {
		this.customerAddress2 = customerAddress2;
	}

	public String getCustomerCity() {
		return this.customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerStateProv() {
		return this.customerStateProv;
	}

	public void setCustomerStateProv(String customerStateProv) {
		this.customerStateProv = customerStateProv;
	}

	public String getCustomerZipPostal() {
		return this.customerZipPostal;
	}

	public void setCustomerZipPostal(String customerZipPostal) {
		this.customerZipPostal = customerZipPostal;
	}

	public String getExternalSystemCode() {
		return this.externalSystemCode;
	}

	public void setExternalSystemCode(String externalSystemCode) {
		this.externalSystemCode = externalSystemCode;
	}

	public String getIsoCountryCode() {
		return this.isoCountryCode;
	}

	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	public BigDecimal getKitExpressDistCntrId() {
		return this.kitExpressDistCntrId;
	}

	public void setKitExpressDistCntrId(BigDecimal kitExpressDistCntrId) {
		this.kitExpressDistCntrId = kitExpressDistCntrId;
	}

	public BigDecimal getMinFreeFreightAmt() {
		return this.minFreeFreightAmt;
	}

	public void setMinFreeFreightAmt(BigDecimal minFreeFreightAmt) {
		this.minFreeFreightAmt = minFreeFreightAmt;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getServiceTerritoryCode() {
		return this.serviceTerritoryCode;
	}

	public void setServiceTerritoryCode(String serviceTerritoryCode) {
		this.serviceTerritoryCode = serviceTerritoryCode;
	}

	public String getShipToLimitFlag() {
		return this.shipToLimitFlag;
	}

	public void setShipToLimitFlag(String shipToLimitFlag) {
		this.shipToLimitFlag = shipToLimitFlag;
	}

	public String getShippingCode() {
		return this.shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public String getTerritoryCode() {
		return this.territoryCode;
	}

	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}

	public String getViewInvoiceFlag() {
		return this.viewInvoiceFlag;
	}

	public void setViewInvoiceFlag(String viewInvoiceFlag) {
		this.viewInvoiceFlag = viewInvoiceFlag;
	}

	public List<CustomerComment> getCustomerComments() {
		return this.customerComments;
	}

	public void setCustomerComments(List<CustomerComment> customerComments) {
		this.customerComments = customerComments;
	}
	
}
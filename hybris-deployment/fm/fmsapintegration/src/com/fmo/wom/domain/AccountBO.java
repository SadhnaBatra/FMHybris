/*
 * Created on Jun 1, 2011
 *
 */
package com.fmo.wom.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the CUSTOMER database table.
 * 
 * NOTE: The 'createUserId' and 'updateUserId' have been mapped to arbitrary columns of 'String' type
 * 			since the underlying 'CUSTOMER' table does not have corresponding columns AND currently 
 * 			there is no way in JPA to suppress extra attributes passed on from the '@MappedSuperclass'. 
 * 			Absence of these mappings will result in those columns being included in SQL statements 
 * 			being sent to the database, resulting in errors. 
 * 
 * @author vishws74
 */
public class AccountBO extends WOMBaseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlTransient
	private long customerId;

	private String accountCode;

	@XmlTransient
	private SapAcctBO sapAccount;

	private String accountName;

	@XmlTransient
	private String viewInvoiceCode;

	@XmlTransient
	private SalesChannelBO salesChannel;

	@XmlTransient
	private boolean creditRestrictFlag;

	@XmlTransient
	private boolean exemptFromEmergencyFlag;
	
	@XmlTransient
	private boolean manualShipToOverrideAllowed;

	@XmlTransient
	private DistributionCenterBO homeDc;

	@XmlTransient
	private String backorderPolicyType;

	@XmlTransient
	private String shipVia;

	@XmlTransient
	private String crossBorderInd;
	
	private boolean creditCardCustomer;
	
	@XmlTransient
	private CreditCardBO creditCard;

	private AddressBO address;

	@XmlTransient
	private String internalComments1;

	@XmlTransient
	private String internalComments2;

	// NOTE: CountryBO has been made transient here since it is present in AddressBO.
	@XmlTransient
	private CountryBO country;

	@XmlTransient
	private String shippingCode;

	@XmlTransient
	private DistributionCenterBO kitExpressDc;

	@XmlTransient
	private String shipToLimitCode;

	@XmlTransient
	private BigDecimal minFreeFreightAmt;
	
	@XmlTransient
	private boolean freeFreightAllowed;

	@XmlTransient
	private String ownerAffiliation;

	@XmlTransient
	private String buyerAffiliation;

	@XmlTransient
	private String regionCode;
	
	@XmlTransient
	private String territoryCode;

	@XmlTransient
	private String serviceTerritoryCode;

	//bi-directional many-to-one association to AccountCommentBO
	@XmlTransient
	private List<AccountCommentBO> accountComments;

	//bi-directional many-to-one association to AccountRelationshipBO
	@XmlTransient
	private List<AccountRelationshipBO> accountRelationships;

	/** Extra fields than in the original AccountBO: **/
	@XmlTransient
	private boolean billToCustomer;

	@XmlTransient
	private String buyingGroupCode;

	@XmlTransient
	private String channelCode;

	
	@XmlTransient
	private String externalSystemCode;

	@XmlTransient
	private String freightForwarderFlag;

	@XmlTransient
	private BigDecimal kitExpressDistCntrId;

	@XmlTransient
	private boolean freightForwarderAllowed;
	
	@XmlTransient
	private boolean futureOrderAllowed;
	
	@XmlTransient
	private String customerServiceEmailAddress;
	
	// Indicates whether the Customer is allowed to see a Part's price.
	@XmlTransient
	private boolean allowedToSeePartPrice;

	// Indicates whether the Customer is allowed to see a Part's description/name.
	@XmlTransient
	private boolean allowedToSeePartDescription;



	public AccountBO() {
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

	public SapAcctBO getSapAccount() {
		return sapAccount;
	}

	public void setSapAccount(SapAcctBO sapAccount) {
		this.sapAccount = sapAccount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getViewInvoiceCode() {
		return viewInvoiceCode;
	}

	public void setViewInvoiceCode(String viewInvoiceCode) {
		this.viewInvoiceCode = viewInvoiceCode;
	}

	public SalesChannelBO getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(SalesChannelBO salesChannel) {
		this.salesChannel = salesChannel;
	}

	public boolean isCreditRestrictFlag() {
		return creditRestrictFlag;
	}

	public void setCreditRestrictFlag(boolean creditRestrictFlag) {
		this.creditRestrictFlag = creditRestrictFlag;
	}

	public boolean isExemptFromEmergencyFlag() {
		return exemptFromEmergencyFlag;
	}

	public void setExemptFromEmergencyFlag(boolean exemptFromEmergencyFlag) {
		this.exemptFromEmergencyFlag = exemptFromEmergencyFlag;
	}

	public DistributionCenterBO getHomeDc() {
		return homeDc;
	}

	public void setHomeDc(DistributionCenterBO homeDc) {
		this.homeDc = homeDc;
	}

	public String getBackorderPolicyType() {
		return backorderPolicyType;
	}

	public void setBackorderPolicyType(String backorderPolicyType) {
		this.backorderPolicyType = backorderPolicyType;
	}

	public String getShipVia() {
		return shipVia;
	}

	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}

	public String getCrossBorderInd() {
		return crossBorderInd;
	}

	public void setCrossBorderInd(String crossBorderInd) {
		this.crossBorderInd = crossBorderInd;
	}
	@XmlTransient
	public boolean isCreditCardCustomer() {
		return this.creditCardCustomer;
		
	}

	public void setCreditCardCustomer(boolean creditCardCustomer) {
		this.creditCardCustomer = creditCardCustomer;
	}

	public CreditCardBO getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCardBO creditCard) {
		this.creditCard = creditCard;
	}

	public AddressBO getAddress() {
		return address;
	}

	public void setAddress(AddressBO address) {
		this.address = address;
	}

	public String getInternalComments1() {
		return internalComments1;
	}

	public void setInternalComments1(String internalComments1) {
		this.internalComments1 = internalComments1;
	}

	public String getInternalComments2() {
		return internalComments2;
	}

	public void setInternalComments2(String internalComments2) {
		this.internalComments2 = internalComments2;
	}

	public CountryBO getCountry() {
		return country;
	}

	public void setCountry(CountryBO country) {
		this.country = country;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public DistributionCenterBO getKitExpressDc() {
		return kitExpressDc;
	}

	public void setKitExpressDc(DistributionCenterBO kitExpressDc) {
		this.kitExpressDc = kitExpressDc;
	}

	public String getShipToLimitCode() {
		return shipToLimitCode;
	}

	public void setShipToLimitCode(String shipToLimitCode) {
		this.shipToLimitCode = shipToLimitCode;
	}

	public BigDecimal getMinFreeFreightAmt() {
		return minFreeFreightAmt;
	}

	public void setMinFreeFreightAmt(BigDecimal minFreeFreightAmt) {
		this.minFreeFreightAmt = minFreeFreightAmt;
	}

	public String getOwnerAffiliation() {
		return ownerAffiliation;
	}

	public void setOwnerAffiliation(String ownerAffiliation) {
		this.ownerAffiliation = ownerAffiliation;
	}

	public String getBuyerAffiliation() {
		return buyerAffiliation;
	}

	public void setBuyerAffiliation(String buyerAffiliation) {
		this.buyerAffiliation = buyerAffiliation;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getTerritoryCode() {
		return territoryCode;
	}

	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}

	public String getServiceTerritoryCode() {
		return serviceTerritoryCode;
	}

	public void setServiceTerritoryCode(String serviceTerritoryCode) {
		this.serviceTerritoryCode = serviceTerritoryCode;
	}


	public List<AccountCommentBO> getAccountComments() {
		return accountComments;
	}

	@XmlTransient
	public void setAccountComments(List<AccountCommentBO> accountComments) {
		this.accountComments = accountComments;
	}


	public List<AccountRelationshipBO> getAccountRelationships() {
		return accountRelationships;
	}
	
	@XmlTransient
	public void setAccountRelationships(
			List<AccountRelationshipBO> accountRelationships) {
		this.accountRelationships = accountRelationships;
	}

	@XmlTransient
	public boolean isBillToCustomer() {
		return billToCustomer;
	}

	public void setBillToCustomer(boolean billToCustomer) {
		this.billToCustomer = billToCustomer;
	}

	public String getBuyingGroupCode() {
		return buyingGroupCode;
	}

	public void setBuyingGroupCode(String buyingGroupCode) {
		this.buyingGroupCode = buyingGroupCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getExternalSystemCode() {
		return externalSystemCode;
	}

	public void setExternalSystemCode(String externalSystemCode) {
		this.externalSystemCode = externalSystemCode;
	}

	public String getFreightForwarderFlag() {
		return freightForwarderFlag;
	}

	public void setFreightForwarderFlag(String freightForwarderFlag) {
		this.freightForwarderFlag = freightForwarderFlag;
	}

	public boolean isFreightForwarder() {
	    return "Y".equals(getFreightForwarderFlag());
	}
	
	public BigDecimal getKitExpressDistCntrId() {
		return kitExpressDistCntrId;
	}

	public void setKitExpressDistCntrId(BigDecimal kitExpressDistCntrId) {
		this.kitExpressDistCntrId = kitExpressDistCntrId;
	}
	
	public boolean isManualShipToOverrideAllowed() {
		return manualShipToOverrideAllowed;
	}

	public void setManualShipToOverrideAllowed(boolean manualShipToOverrideAllowed) {
		this.manualShipToOverrideAllowed = manualShipToOverrideAllowed;
	}

    public boolean isFreightForwarderAllowed() {
		return freightForwarderAllowed;
	}

	public void setFreightForwarderAllowed(boolean freightForwarderAllowed) {
		this.freightForwarderAllowed = freightForwarderAllowed;
	}
	
	public boolean isFreeFreightAllowed() {
		if ((this.getMinFreeFreightAmt()!= null) 
				&& (this.getMinFreeFreightAmt().intValue() > 0))
				return true;
		else
			return false;
	}
	
	public boolean isFutureOrderAllowed() {
		return futureOrderAllowed;
	}

	public void setFutureOrderAllowed(boolean futureOrderAllowed) {
		this.futureOrderAllowed = futureOrderAllowed;
	}

	public String getCustomerServiceEmailAddress() {
		return customerServiceEmailAddress;
	}

	public void setCustomerServiceEmailAddress(String customerServiceEmailAddress) {
		this.customerServiceEmailAddress = customerServiceEmailAddress;
	}

	public boolean isAllowedToSeePartPrice() {
		return allowedToSeePartPrice;
	}

	public void setAllowedToSeePartPrice(boolean allowedToSeePartPrice) {
		this.allowedToSeePartPrice = allowedToSeePartPrice;
	}

	public boolean isAllowedToSeePartDescription() {
		return allowedToSeePartDescription;
	}

	public void setAllowedToSeePartDescription(boolean allowedToSeePartDescription) {
		this.allowedToSeePartDescription = allowedToSeePartDescription;
	}

	@Override
	public String toString() {
		return "AccountBO [customerId=" + customerId + ", accountCode="
				+ accountCode + ", sapAccount=" + sapAccount + ", accountName=" + accountName
				+ ", viewInvoiceCode=" + viewInvoiceCode + ", salesChannel="
				+ salesChannel + ", creditRestrictFlag=" + creditRestrictFlag
				+ ", exemptFromEmergencyFlag=" + exemptFromEmergencyFlag
				+ ", homeDc=" + homeDc + ", backorderPolicyType="
				+ backorderPolicyType + ", shipVia=" + shipVia
				+ ", crossBorderInd=" + crossBorderInd
				+ ", creditCardCustomer=" + creditCardCustomer
				+ ", creditCard=" + creditCard + ", address=" + address
				+ ", internalComments1=" + internalComments1
				+ ", internalComments2=" + internalComments2 + ", country="
				+ country + ", shippingCode=" + shippingCode
				+ ", kitExpressDc=" + kitExpressDc + ", shipToLimitCode="
				+ shipToLimitCode + ", minFreeFreightAmt=" + minFreeFreightAmt
				+ ", ownerAffiliation=" + ownerAffiliation
				+ ", buyerAffiliation=" + buyerAffiliation + ", regionCode="
				+ regionCode + ", territoryCode=" + territoryCode
				+ ", serviceTerritoryCode=" + serviceTerritoryCode
				+ ", billToCustomer=" + billToCustomer + ", buyingGroupCode="
				+ buyingGroupCode + ", channelCode=" + channelCode
				+ ", externalSystemCode=" + externalSystemCode
				+ ", kitExpressDistCntrId=" + kitExpressDistCntrId
				+ ", customerServiceEmailAddress=" + customerServiceEmailAddress
				+ ", allowedToSeePartPrice=" + allowedToSeePartPrice
				+ ", allowedToSeePartDescription=" + allowedToSeePartDescription
				+ ", createdDate=" + createTimestamp + ", modifiedDate="
				+ updateTimestamp 
				+ "]";
	}
	
}

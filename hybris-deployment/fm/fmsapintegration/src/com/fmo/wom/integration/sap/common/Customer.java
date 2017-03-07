package com.fmo.wom.integration.sap.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.domain.AddressBO;

public class Customer {

    private String accountCode = "";
    private String salesOrgCode = "";; 
    private String distributionChannel = "";; 
    private String division = "";;
  
    private Map<String, AddressBO> shipToAddressMap;
    private AddressBO billToAddress;
    private String invoiceName = "";
    
    private String language = "";;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getSalesOrgCode() {
        return salesOrgCode;
    }

    public void setSalesOrgCode(String salesOrgCode) {
        this.salesOrgCode = salesOrgCode;
    }

    public String getDistributionChannel() {
        return distributionChannel;
    }

    public void setDistributionChannel(String distributionChannel) {
        this.distributionChannel = distributionChannel;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Map<String, AddressBO> getShipToAddressMap() {
        return shipToAddressMap;
    }

    public void setShipToAddressMap(Map<String, AddressBO> shipToAddressMap) {
        this.shipToAddressMap = shipToAddressMap;
    }

    public void addShipToAddress(String key, AddressBO anAddress) {
        
        if (GenericValidator.isBlankOrNull(key)) {
            return;
        }
        
        if (anAddress == null) return;
        
        if (shipToAddressMap == null) {
            shipToAddressMap = new HashMap<String, AddressBO>();
        }
        shipToAddressMap.put(key,anAddress);
    }

    public AddressBO getBillToAddress() {
        return billToAddress;
    }

    public void setBillToAddress(AddressBO billToAddress) {
        this.billToAddress = billToAddress;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }
}

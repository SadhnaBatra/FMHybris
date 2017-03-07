package com.fmo.wom.domain;

import java.util.List;

public class SapAcctBO extends WOMBaseBO {
	
    private String sapAccountCode;
    private List<CustomerSalesOrgBO> customerSalesOrgs;
    private CustomerSalesOrgBO customerSalesOrganization;
    private String shipToCode;
     
	public String getSapAccountCode() {
		return sapAccountCode;
	}
	public void setSapAccountCode(String sapAccountCode) {
		this.sapAccountCode = sapAccountCode;
	}
	public String getShipToCode() {
		return shipToCode;
	}
	public void setShipToCode(String shipToCode) {
		this.shipToCode = shipToCode;
	}
    public List<CustomerSalesOrgBO> getCustSalesOrgs() {
        return customerSalesOrgs;
    }
    public void setCustSalesOrgs(List<CustomerSalesOrgBO> salesOrgs) {
        this.customerSalesOrgs = salesOrgs;
    }
    
    public CustomerSalesOrgBO getCustomerSalesOrganization() {
        return customerSalesOrganization;
    }
    
    public void setCustomerSalesOrganization(CustomerSalesOrgBO aCustomerSalesOrg) {
        this.customerSalesOrganization = aCustomerSalesOrg;
    }
	@Override
	public String toString() {
		return "SapAcctBO [sapAccountCode=" + sapAccountCode
				+ ", customerSalesOrgs=" + customerSalesOrgs
				+ ", customerSalesOrganization=" + customerSalesOrganization
				+ ", shipToCode=" + shipToCode + "]";
	}
    
}

package com.fmo.wom.domain;

import java.io.Serializable;

/**
 * The primary key class for the CUSTOMER_SALES_INFO database table.
 * 
 * @author vishws74
 */

public class CustomerSalesOrgPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long salesOrgId;

	private long customerId;

    public CustomerSalesOrgPK() {
    }
	public long getSalesOrgId() {
		return this.salesOrgId;
	}
	public void setSalesOrgId(long salesOrgId) {
		this.salesOrgId = salesOrgId;
	}
	public long getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CustomerSalesOrgPK)) {
			return false;
		}
		CustomerSalesOrgPK castOther = (CustomerSalesOrgPK)other;
		return 
			(this.salesOrgId == castOther.salesOrgId)
			&& (this.customerId == castOther.customerId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.salesOrgId ^ (this.salesOrgId >>> 32)));
		hash = hash * prime + ((int) (this.customerId ^ (this.customerId >>> 32)));
		
		return hash;
    }
}
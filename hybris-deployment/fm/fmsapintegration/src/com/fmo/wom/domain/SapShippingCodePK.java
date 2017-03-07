package com.fmo.wom.domain;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the SAP_SHIPPING_CODE database table.
 * 
 */
//Embeddable
public class SapShippingCodePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	//Column(name="DC_CARRIER_SHIPMTHD_ID")
	private long dcShippingMethodId;

	//Column(name="CARRIER_SHIPMTHD_CNTRY_ID")
	private long shippingMethodCountryId;

    public SapShippingCodePK() {
    }

	public long getDcShippingMethodId() {
		return dcShippingMethodId;
	}

	public void setDcShippingMethodId(long dcShippingMethodId) {
		this.dcShippingMethodId = dcShippingMethodId;
	}

	public long getShippingMethodCountryId() {
		return shippingMethodCountryId;
	}

	public void setShippingMethodCountryId(long shippingMethodCountryId) {
		this.shippingMethodCountryId = shippingMethodCountryId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SapShippingCodePK)) {
			return false;
		}
		SapShippingCodePK castOther = (SapShippingCodePK)other;
		return 
			(this.dcShippingMethodId == castOther.dcShippingMethodId)
			&& (this.shippingMethodCountryId == castOther.shippingMethodCountryId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.dcShippingMethodId ^ (this.dcShippingMethodId >>> 32)));
		hash = hash * prime + ((int) (this.shippingMethodCountryId ^ (this.shippingMethodCountryId >>> 32)));
		
		return hash;
    }
}
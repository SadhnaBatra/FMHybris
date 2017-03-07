package com.fmo.wom.domain;

import java.io.Serializable;

/**
 * ID class for entity: DcShippingMethodCategory
 *
 */ 
public class DcShippingMethodCategoryPK  implements Serializable {   
   
	         
	private long dcCarrierShippingMethodId;         
	private long smCategoryId;
	private static final long serialVersionUID = 1L;

	public DcShippingMethodCategoryPK() {}

	

	public long getDcCarrierShippingMethodId() {
		return this.dcCarrierShippingMethodId;
	}

	public void setDcCarrierShippingMethodId(long dcCarrierShippingMethodId) {
		this.dcCarrierShippingMethodId = dcCarrierShippingMethodId;
	}
	

	public long getSmCategoryId() {
		return this.smCategoryId;
	}

	public void setSmCategoryId(long smCategoryId) {
		this.smCategoryId = smCategoryId;
	}
	
   
	/*
	 * @see java.lang.Object#equals(Object)
	 */	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof DcShippingMethodCategoryPK)) {
			return false;
		}
		DcShippingMethodCategoryPK other = (DcShippingMethodCategoryPK) o;
		return true
			&& getDcCarrierShippingMethodId() == other.getDcCarrierShippingMethodId()
			&& getSmCategoryId() == other.getSmCategoryId();
	}
	
	/*	 
	 * @see java.lang.Object#hashCode()
	 */	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((int) (getDcCarrierShippingMethodId() ^ (getDcCarrierShippingMethodId() >>> 32)));
		result = prime * result + ((int) (getSmCategoryId() ^ (getSmCategoryId() >>> 32)));
		return result;
	}
   
   
}

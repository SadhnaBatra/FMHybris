package com.fmo.wom.domain;

import java.io.Serializable;

import com.fmo.wom.domain.enums.OrderType;

/**
 * ID class for entity: DcOrderTypeRestriction
 *
 */ 
public class DcOrderTypeRestrictionPK  implements Serializable {   
   
	         
	private long distributionCenterId;
	private OrderType orderType;
	private static final long serialVersionUID = 1L;

	public DcOrderTypeRestrictionPK() {}

	public long getDistributionCenterId() {
		return this.distributionCenterId;
	}

	public void setDistributionCenterId(long distributionCenterId) {
		this.distributionCenterId = distributionCenterId;
	}

	public OrderType getOrderType() {
		return this.orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
   
	/*
	 * @see java.lang.Object#equals(Object)
	 */	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof DcOrderTypeRestrictionPK)) {
			return false;
		}
		DcOrderTypeRestrictionPK other = (DcOrderTypeRestrictionPK) o;
		return true
			&& getDistributionCenterId() == other.getDistributionCenterId()
			&& (getOrderType() == null ? other.getOrderType() == null : getOrderType().equals(other.getOrderType()));
	}
	
	/*	 
	 * @see java.lang.Object#hashCode()
	 */	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((int) (getDistributionCenterId() ^ (getDistributionCenterId() >>> 32)));
		result = prime * result + (getOrderType() == null ? 0 : getOrderType().hashCode());
		return result;
	}
   
}

package com.fmo.wom.domain;

import java.io.Serializable;

import com.fmo.wom.domain.enums.OrderType;

/**
 * Entity implementation class for Entity: DcOrderTypeRestriction
 *
 */

public class DcOrderTypeRestrictionBO implements Serializable {
	   
	private long distributionCenterId;   

	private OrderType orderType;

	private static final long serialVersionUID = 1L;

	public DcOrderTypeRestrictionBO() {
		super();
	}   
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
   
}

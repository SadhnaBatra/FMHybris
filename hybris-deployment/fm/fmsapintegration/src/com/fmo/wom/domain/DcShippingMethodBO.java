package com.fmo.wom.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DISTCNTR_CARRIER_SHIPMTHD database table.
 * 
 */
public class DcShippingMethodBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long dcShippingMethodId;

	private boolean active;

	private Date inactiveFromDate;

	private BigDecimal sortOrder;

	//bi-directional many-to-one association to CarrierShippingMethod
	private ShippingMethodBO shippingMethod;

	//bi-directional many-to-one association to DistributionCenter
	private DistributionCenterBO distributionCenter;

    public DcShippingMethodBO() {
    }

	public long getDcShippingMethodId() {
		return dcShippingMethodId;
	}

	public void setDcShippingMethodId(long dcShippingMethodId) {
		this.dcShippingMethodId = dcShippingMethodId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getInactiveFromDate() {
		return inactiveFromDate;
	}

	public void setInactiveFromDate(Date inactiveFromDate) {
		this.inactiveFromDate = inactiveFromDate;
	}

	public ShippingMethodBO getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethodBO shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public DistributionCenterBO getDistributionCenter() {
		return distributionCenter;
	}

	public void setDistributionCenter(DistributionCenterBO distributionCenter) {
		this.distributionCenter = distributionCenter;
	}

	public BigDecimal getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(BigDecimal sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return "DcShippingMethodBO [dcShippingMethodId=" + dcShippingMethodId
				+ ", active=" + active + ", sortOrder=" + sortOrder
				+ "]";
	}

}
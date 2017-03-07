package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the BPCS_SHIPPING_CODE database table.
 * 
 */

public class BpcsShippingCodeBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;


	private BpcsShippingCodePK id;

	private boolean active;

	private String emergencyShippingCode;

	private String immediateStockShippingCode;

	private Date inactivFromDate;

	private String stockShippingCode;

    public BpcsShippingCodeBO() {
    }

	public BpcsShippingCodePK getId() {
		return this.id;
	}

	public void setId(BpcsShippingCodePK id) {
		this.id = id;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmergencyShippingCode() {
		return emergencyShippingCode;
	}

	public void setEmergencyShippingCode(String emergencyShippingCode) {
		this.emergencyShippingCode = emergencyShippingCode;
	}

	public String getImmediateStockShippingCode() {
		return immediateStockShippingCode;
	}

	public void setImmediateStockShippingCode(String immediateStockShippingCode) {
		this.immediateStockShippingCode = immediateStockShippingCode;
	}

	public Date getInactivFromDate() {
		return this.inactivFromDate;
	}

	public void setInactivFromDate(Date inactivFromDate) {
		this.inactivFromDate = inactivFromDate;
	}

	public String getStockShippingCode() {
		return stockShippingCode;
	}

	public void setStockShippingCode(String stockShippingCode) {
		this.stockShippingCode = stockShippingCode;
	}

	@Override
	public String toString() {
		return "BpcsShippingCodeBO [id=" + id + ", active=" + active
				+ ", emergencyShippingCode=" + emergencyShippingCode
				+ ", immediateStockShippingCode=" + immediateStockShippingCode
				+ ", stockShippingCode=" + stockShippingCode + "]";
	}

}
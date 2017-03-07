package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the CARRIER database table.
 * 
 */
public class CarrierBO extends WOMBaseBO implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private long carrierId;

	private boolean active;

	private String carrierName;
	
	private String carrierCode;

	private Date inactiveFromDate;

    public CarrierBO() {
    }

	public long getCarrierId() {
		return this.carrierId;
	}

	public void setCarrierId(long carrierId) {
		this.carrierId = carrierId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCarrierName() {
		return this.carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public Date getInactiveFromDate() {
		return inactiveFromDate;
	}

	public void setInactiveFromDate(Date inactiveFromDate) {
		this.inactiveFromDate = inactiveFromDate;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	@Override
	public String toString() {
		return "CarrierBO [carrierId=" + carrierId + ", active=" + active
				+ ", carrierName=" + carrierName + ", carrierCode="
				+ carrierCode + "]";
	}

}
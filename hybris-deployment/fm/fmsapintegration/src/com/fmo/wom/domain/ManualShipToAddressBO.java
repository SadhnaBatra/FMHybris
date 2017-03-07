package com.fmo.wom.domain;

import java.io.Serializable;

/**
 * The persistent class for the MANUAL_SHIPTO_ADDRESS database table.
 * 
 */
public class ManualShipToAddressBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long manualShipToAddrId;

	private AddressBO address;
	
	private String manualShiptoAcctCd;

	private String name;

    public ManualShipToAddressBO() {
    }

	public long getManualShipToAddrId() {
		return manualShipToAddrId;
	}

	public void setManualShipToAddrId(long manualShipToAddrId) {
		this.manualShipToAddrId = manualShipToAddrId;
	}

	public AddressBO getAddress() {
		return address;
	}

	public void setAddress(AddressBO address) {
		this.address = address;
	}

	public String getManualShiptoAcctCd() {
		return this.manualShiptoAcctCd;
	}

	public void setManualShiptoAcctCd(String manualShiptoAcctCd) {
		this.manualShiptoAcctCd = manualShiptoAcctCd;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ManualShipToAddressBO [manualShipToAddrId="
				+ manualShipToAddrId + ", address=" + address
				+ ", manualShiptoAcctCd=" + manualShiptoAcctCd + ", name="
				+ name + "]";
	}


	
}
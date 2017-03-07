/*
 * Created on Jun 1, 2011
 */
package com.fmo.wom.domain;

/**
 * Business object to hold Credit Card information.
 * 
 * @author vishws74
 */
public class CreditCardBO extends WOMBaseBO{
	private String ccName;
	private String ccType;
	private String ccNumber;
	private String ccExpiration;
	private String ccCVV;
	private String ccOwner;

	/**
	 * @return Returns the ccCVV.
	 */
	public String getCcCVV() {
		return ccCVV;
	}
	/**
	 * @param ccCVV The ccCVV to set.
	 */
	public void setCcCVV(String ccCVV) {
		this.ccCVV = ccCVV;
	}
	/**
	 * @return Returns the ccExpiration.
	 */
	public String getCcExpiration() {
		return ccExpiration;
	}
	/**
	 * @param ccExpiration The ccExpiration to set.
	 */
	public void setCcExpiration(String ccExpiration) {
		this.ccExpiration = ccExpiration;
	}
	/**
	 * @return Returns the ccName.
	 */
	public String getCcName() {
		return ccName;
	}
	/**
	 * @param ccName The ccName to set.
	 */
	public void setCcName(String ccName) {
		this.ccName = ccName;
	}
	/**
	 * @return Returns the ccNumber.
	 */
	public String getCcNumber() {
		return ccNumber;
	}
	/**
	 * @param ccNumber The ccNumber to set.
	 */
	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}
	/**
	 * @return Returns the ccOwner.
	 */
	public String getCcOwner() {
		return ccOwner;
	}
	/**
	 * @param ccOwner The ccOwner to set.
	 */
	public void setCcOwner(String ccOwner) {
		this.ccOwner = ccOwner;
	}
	/**
	 * @return Returns the ccType.
	 */
	public String getCcType() {
		return ccType;
	}
	/**
	 * @param ccType The ccType to set.
	 */
	public void setCcType(String ccType) {
		this.ccType = ccType;
	}
}

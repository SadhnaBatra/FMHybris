/*
 * Created on Jun 1, 2011
 *
 */
package com.fmo.wom.domain;


/**
 * Business object to hold Country information.
 * 
 * @author vishws74
 */

public class CountryBO {
	
	private String countryName;
	
	private String countryAbbreviation;
	
	private String isoCountryCode;

	/**
	 * @return Returns the countryAbbreviation.
	 */
	public String getCountryAbbreviation() {
		return countryAbbreviation;
	}
	/**
	 * @param countryAbbreviation The countryAbbreviation to set.
	 */
	public void setCountryAbbreviation(String countryAbbreviation) {
		this.countryAbbreviation = countryAbbreviation;
	}
	/**
	 * @return Returns the countryName.
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @param countryName The countryName to set.
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/**
	 * @return Returns the isoCountryCode.
	 */
	public String getIsoCountryCode() {
		return isoCountryCode;
	}
	/**
	 * @param isoCountryCode The isoCountryCode to set.
	 */
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}
	
	@Override
	public String toString() {
		return "CountryBO [countryName=" + countryName
				+ ", countryAbbreviation=" + countryAbbreviation
				+ ", isoCountryCode=" + isoCountryCode + "]";
	}
	
	
}

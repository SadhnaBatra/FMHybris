/*
 * Created on Jun 6, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.fmo.wom.domain;

/**
 * Type safe representation of possible Customer biz types
 */
public enum CustomerBusinessType  {
	
	JOBBER("J", "Jobber"), 
	WAREHOUSE("D", "Distributor");
	
	private String code;
	private String displayName;

	CustomerBusinessType(final String code, final String displayName) {
		this.code = code;
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public String getCode() {
		return this.code;
	}

	public static CustomerBusinessType getFromCode(final String aCode) {

		for (CustomerBusinessType aCBT : CustomerBusinessType.values()) {

			if (aCode.equals(aCode) == true) {
				return aCBT;
			}
		}

		throw new IllegalStateException("CustomerBusinessType " + aCode + " does not exist.");

	}



}

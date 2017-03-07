package com.fmo.wom.domain;

import java.io.Serializable;

/**
 * The persistent class for the APPLICATION_USAGE database table.
 * 
 */

public class ApplicationUsageDetailsBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long identifier;
	
	private String requestBod;

	private String responseBod;
	
    private ApplicationUsage appUsage;

	public Long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public String getRequestBod() {
		return requestBod;
	}
	public void setRequestBod(String requestBod) {
		this.requestBod = requestBod;
	}
	public String getResponseBod() {
		return responseBod;
	}
	public void setResponseBod(String responseBod) {
		this.responseBod = responseBod;
	}
	public ApplicationUsage getAppUsage() {
		return appUsage;
	}
	public void setAppUsage(ApplicationUsage appUsage) {
		this.appUsage = appUsage;
	}

	
}
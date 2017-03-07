package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.domain.enums.Market;

/**
 * The persistent class for the APPLICATION_USAGE database table.
 * 
 */

public class ApplicationUsage implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long identifier;

	private String customerAccountCode;

	private Date processEndTime;

	private String processName;

	private String hostName;

	private Date processStartTime;

	private String processType;

	private String userId;

	private String sessionId;

	private String marketCode;

	private ApplicationUsageDetailsBO appUsageDetails;

	public Date getProcessStartTime() {
		return processStartTime;
	}

	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomerAccountCode() {
		return customerAccountCode;
	}

	public void setCustomerAccountCode(String customerAccountCode) {
		this.customerAccountCode = customerAccountCode;
	}

	public Date getProcessEndTime() {
		return processEndTime;
	}

	public void setProcessEndTime(Date processEndTime) {
		this.processEndTime = processEndTime;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public ApplicationUsageDetailsBO getAppUsageDetails() {
		return appUsageDetails;
	}

	public void setAppUsageDetails(ApplicationUsageDetailsBO appUsageDetails) {
		this.appUsageDetails = appUsageDetails;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

    public Market getMarket() {
        if (!GenericValidator.isBlankOrNull(marketCode)) {
            return Market.getFromCode(marketCode);
        }
        return null;
    }
    
    public void setMarket(Market aMarket) {
        if (aMarket != null) {
            marketCode = aMarket.getCode();
        } else {
            marketCode = null;
        }
    }

}
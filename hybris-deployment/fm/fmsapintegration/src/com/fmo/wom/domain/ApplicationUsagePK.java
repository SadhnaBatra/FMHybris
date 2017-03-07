package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The primary key class for the CUSTOMER_COMMENTS database table.
 * 
 * @author vishws74
 */
public class ApplicationUsagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Date processStartTime;

	private String processType;

	private String userId;
	
	
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
	@Override
    public int hashCode(){
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(processStartTime);
        builder.append(processType);
        builder.append(userId);
        return builder.toHashCode();
    }
    public int compareTo(Object obj) {
        int returnValue = -1;
        if (obj instanceof ApplicationUsagePK)
        {
            final ApplicationUsagePK other = (ApplicationUsagePK)obj;
            final CompareToBuilder builder = new CompareToBuilder();
            builder.append(processStartTime, other.processStartTime);
            builder.append(processType, other.processType);
            builder.append(userId, other.userId);
            returnValue = builder.toComparison();
        }
        return returnValue;
    }
    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }
	
}
package com.fmo.wom.domain;

import java.io.Serializable;


/**
 * The primary key class for the CUSTOMER_RELATIONSHIP database table.
 * 
 * @author vishws74
 */

public class AccountRelationshipPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	private long primaryCustomerId;
	
	private long secondaryCustomerId;
	
	private String relationshipType;

    public AccountRelationshipPK() {
    }

	public long getPrimaryCustomerId() {
		return this.primaryCustomerId;
	}
	public void setPrimaryCustomerId(long primaryCustomerId) {
		this.primaryCustomerId = primaryCustomerId;
	}

	public long getSecondaryCustomerId() {
		return this.secondaryCustomerId;
	}
	public void setSecondaryCustomerId(long secondaryCustomerId) {
		this.secondaryCustomerId = secondaryCustomerId;
	}

	public String getRelationshipType() {
		return this.relationshipType;
	}
	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountRelationshipPK)) {
			return false;
		}
		AccountRelationshipPK castOther = (AccountRelationshipPK)other;
		return 
			this.primaryCustomerId == castOther.primaryCustomerId
			&& (this.secondaryCustomerId == castOther.secondaryCustomerId)
			&& (this.relationshipType.equals(castOther.relationshipType));
		
    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.primaryCustomerId ^ (this.primaryCustomerId >>> 32)));
		hash = hash * prime + ((int) (this.secondaryCustomerId ^ (this.secondaryCustomerId >>> 32)));
		hash = hash * prime + this.relationshipType.hashCode();
		
		return hash;
    }
}
package com.fmo.wom.domain;

import java.io.Serializable;

/**
 * The primary key class for the CUSTOMER_COMMENTS database table.
 * 
 * @author vishws74
 */
public class AccountCommentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String commentType;

	private long commentSeq;

	private long customerId;

    public AccountCommentPK() {
    }
	public String getCommentType() {
		return this.commentType;
	}
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	public long getCommentSeq() {
		return this.commentSeq;
	}
	public void setCommentSeq(long commentSeq) {
		this.commentSeq = commentSeq;
	}
	public long getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AccountCommentPK)) {
			return false;
		}
		AccountCommentPK castOther = (AccountCommentPK)other;
		return 
			this.commentType.equals(castOther.commentType)
			&& (this.commentSeq == castOther.commentSeq)
			&& (this.customerId == castOther.customerId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.commentType.hashCode();
		hash = hash * prime + ((int) (this.commentSeq ^ (this.commentSeq >>> 32)));
		hash = hash * prime + ((int) (this.customerId ^ (this.customerId >>> 32)));
		
		return hash;
    }
}
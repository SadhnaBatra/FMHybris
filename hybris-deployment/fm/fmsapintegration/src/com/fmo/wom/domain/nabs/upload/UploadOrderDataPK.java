package com.fmo.wom.domain.nabs.upload;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Embeddable
public class UploadOrderDataPK implements Serializable {

    @ManyToOne
    UploadOrderBO parentOrderDataFK;
    
	@Column (name="LINE_SEQ_NBR")
	private String lineSeqNbr;

	public UploadOrderDataPK() { ; }
	
	@Transient
	public String getTransactionId() {
	    return getParentOrderDataFK().getTransactionId();
	}

	public void setTransactionId(String transactionId) {
	    getParentOrderDataFK().setTransactionId(transactionId);
	}

	    
    public UploadOrderBO getParentOrderDataFK() {
        return parentOrderDataFK;
    }

    public void setParentOrderDataFK(UploadOrderBO parentOrderDataFK) {
        this.parentOrderDataFK = parentOrderDataFK;
    }

    public String getLineSeqNbr() {
       
        if (lineSeqNbr != null) {
            return lineSeqNbr.trim();
        }
		return lineSeqNbr;
	}

	public void setLineSeqNbr(int lineSeqNbr) {
		this.lineSeqNbr = String.format("%05d", lineSeqNbr);
	}

    public boolean equals(Object object) {
        if (object instanceof UploadOrderDataPK) {
            UploadOrderDataPK pk = (UploadOrderDataPK) object;
            return getParentOrderDataFK().equals(pk) && lineSeqNbr == pk.lineSeqNbr;
        }
        return false;
    }
 
    public int hashCode() {
    	int hash = 7;
    	hash = (hash * 17) + (null ==  getParentOrderDataFK() ? 0 :  getParentOrderDataFK().hashCode());
    	hash = (hash * 31) + (null == lineSeqNbr ? 0 : lineSeqNbr.hashCode());
        return hash;
    }
}

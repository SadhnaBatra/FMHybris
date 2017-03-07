package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PartResolutionInventoryPK implements Serializable {

	@Column (name="TRANS_SEQ_ID")
	private String transactionId;

	@Column (name="LINE_SEQ_NBR")
	private String lineSeqNbr;

	@Column (name="TRANS_CD")
	private String txnCd;
	
	public PartResolutionInventoryPK() { ; }
	
	public PartResolutionInventoryPK(String transactionId, String lineSeqNbr, String txnCd) {
		this.transactionId = transactionId !=null?transactionId.trim():transactionId;
		this.lineSeqNbr = lineSeqNbr!=null?lineSeqNbr.trim():lineSeqNbr;
		this.txnCd = txnCd!=null?txnCd.trim():txnCd;
	}

	public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getLineSeqNbr() {
       
        if (lineSeqNbr != null) {
            return lineSeqNbr.trim();
        }
		return lineSeqNbr;
	}

	public void setLineSeqNbr(String lineSeqNbr) {
		this.lineSeqNbr = lineSeqNbr;
	}

	public String getTxnCd() {
		return txnCd;
	}

	public void setTxnCd(String txnCd) {
		this.txnCd = txnCd;
	}

    public boolean equals(Object object) {
    	boolean isEqual = false;
        if (object instanceof PartResolutionInventoryPK) {
        	PartResolutionInventoryPK pk = (PartResolutionInventoryPK) object;
        	boolean isTransactionIdEqual = this.transactionId !=null ?(transactionId.equalsIgnoreCase(pk.transactionId)):false;
        	boolean isLineSeqNbrEqual = this.lineSeqNbr !=null ?(lineSeqNbr.equalsIgnoreCase(pk.lineSeqNbr)):false;
        	boolean isTxnCdEqual = this.txnCd != null ?(txnCd.equals(pk.txnCd)):false; 
        	isEqual = isTransactionIdEqual && isLineSeqNbrEqual && isTxnCdEqual; 
            return isEqual; 
        }
        return false;
    }
 
    public int hashCode() {
    	int hash = 7;
    	hash = (hash * 17) + (null == transactionId ? 0 : transactionId.hashCode());
    	hash = (hash * 31) + (null == lineSeqNbr ? 0 : lineSeqNbr.hashCode());
    	hash = (hash * 13) + (null == txnCd ? 0 : txnCd.hashCode());
        return hash;
    }

	@Override
	public String toString() {
		return "PartResolutionInventoryPK [transactionId=" + transactionId
				+ ", lineSeqNbr=" + lineSeqNbr + ", txnCd=" + txnCd + "]";
	}
    
    
}

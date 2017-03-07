package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class KitCompOversizeDtlPK implements Serializable {

    @Column (name="TRANS_SEQ_ID")
    private String transactionId;
    
	@Column (name="LINE_SEQ_NBR")
	private String lineSeqNbr;

	@Column (name="TRANS_CD")
	private String txnCd;
	
	@Column (name="COMP_SEQ_NBR")
	private int compSeqNbr;
	
	@Column (name="OVRSZ_SEQ_NBR")
	private int oversizeSeqNbr;
	
	public KitCompOversizeDtlPK() { ; }
	
	public KitCompOversizeDtlPK(String transactionId, String lineSeqNbr, String txnCd,int compSeqNbr, int oversizeSeqNbr) {
		this.transactionId = transactionId;
		this.lineSeqNbr = lineSeqNbr;
		this.txnCd = txnCd;
		this.compSeqNbr = compSeqNbr;
		this.oversizeSeqNbr = oversizeSeqNbr;
	}

	public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getLineSeqNbr() {
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

    public int getCompSeqNbr() {
		return compSeqNbr;
	}

	public void setCompSeqNbr(int compSeqNbr) {
		this.compSeqNbr = compSeqNbr;
	}

	public int getOversizeSeqNbr() {
		return oversizeSeqNbr;
	}

	public void setOversizeSeqNbr(int oversizeSeqNbr) {
		this.oversizeSeqNbr = oversizeSeqNbr;
	}

	public boolean equals(Object object) {
        if (object instanceof KitCompOversizeDtlPK) {
        	KitCompOversizeDtlPK pk = (KitCompOversizeDtlPK) object;
            return transactionId.equals(pk.transactionId) && lineSeqNbr == pk.lineSeqNbr 
            		&& txnCd.equals(pk.txnCd) && compSeqNbr == pk.compSeqNbr
            		&& oversizeSeqNbr == pk.oversizeSeqNbr;
        }
        return false;
    }
 
    public int hashCode() {
    	int hash = 7;
    	hash = (hash * 17) + (null == transactionId ? 0 : transactionId.hashCode());
    	hash = (hash * 31) + (null == lineSeqNbr ? 0 : lineSeqNbr.hashCode());
    	hash = (hash * 13) + (null == txnCd ? 0 : txnCd.hashCode());
    	hash = (hash * 21) + compSeqNbr;
    	hash = (hash * 35) + oversizeSeqNbr;
        return hash;
    }
}

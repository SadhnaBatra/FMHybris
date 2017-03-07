package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class KitComponentDtlPK implements Serializable {

    @Column (name="TRANS_SEQ_ID")
    private String transactionId;

	@Column (name="LINE_SEQ_NBR")
	private String lineSeqNbr;

	@Column (name="TRANS_CD")
	private String txnCd;
	
	@Column (name="COMP_SEQ_NBR")
	private int compSeqNbr;
	
	public KitComponentDtlPK() { ; }
	
	public KitComponentDtlPK(String transactionId, String lineSeqNbr, String txnCd, int compSeqNbr) {
		this.transactionId = transactionId;
		this.lineSeqNbr = lineSeqNbr;
		this.txnCd = txnCd;
		this.compSeqNbr = compSeqNbr;
	}

	public String getTransactionId() {
        return transactionId;
    }
	
	public String getLineSeqNbr() {
        return lineSeqNbr;
    }

    public String getTxnCd() {
        return txnCd;
    }

    public int getCompSeqNbr() {
        return compSeqNbr;
    }

    public boolean equals(Object object) {
        if (object instanceof KitComponentDtlPK) {
        	KitComponentDtlPK pk = (KitComponentDtlPK) object;
            return transactionId.equals(pk.transactionId) && lineSeqNbr == pk.lineSeqNbr 
            		&& txnCd.equals(pk.txnCd) && compSeqNbr == pk.compSeqNbr;
        }
        return false;
    }
 
    public int hashCode() {
    	int hash = 7;
    	hash = (hash * 17) + (null == transactionId ? 0 : transactionId.hashCode());
    	hash = (hash * 31) + (null == lineSeqNbr ? 0 : lineSeqNbr.hashCode());
    	hash = (hash * 13) + (null == txnCd ? 0 : txnCd.hashCode());
    	hash = (hash * 21) + compSeqNbr;
        return hash;
    }
}

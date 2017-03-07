package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Embeddable
public class ProblemPartPK implements Serializable {

    @ManyToOne
    PartResolutionInventoryBO parentPartResolutionFK;
    
	@Column (name="PROB_SEQ_NBR")
	private int probSeqNbr;

	@Transient
	public String getTransactionId() {
		return getParentPartResolutionFK().getId().getTransactionId();
	}

	public void setTransactionId(String transactionId) {
	    getParentPartResolutionFK().getId().setTransactionId(transactionId);
	}

	@Transient
    public String getLineSeqNbr() {
		return getParentPartResolutionFK().getId().getLineSeqNbr();
	}

	public void setLineSeqNbr(String lineSeqNbr) {
	    getParentPartResolutionFK().getId().setLineSeqNbr(lineSeqNbr);
	}

	@Transient
    public String getTxnCd() {
		return getParentPartResolutionFK().getId().getTxnCd();
	}

	public void setTxnCd(String txnCd) {
	    getParentPartResolutionFK().getId().setTxnCd(txnCd);
	}

    public int getProbSeqNbr() {
		return probSeqNbr;
	}

	public void setProbSeqNbr(int probSeqNbr) {
		this.probSeqNbr = probSeqNbr;
	}

	public PartResolutionInventoryBO getParentPartResolutionFK() {
        return parentPartResolutionFK;
    }

    public void setParentPartResolutionFK(
            PartResolutionInventoryBO parentPartResolutionFK) {
        this.parentPartResolutionFK = parentPartResolutionFK;
    }

    public boolean equals(Object object) {
        if (object instanceof ProblemPartPK) {
        	ProblemPartPK pk = (ProblemPartPK) object;
        	/*masterOrderNbr.equals(pk.masterOrderNbr) && lineSeqNbr == pk.lineSeqNbr && txnCd.equals(pk.txnCd) &&  probSeqNbr == pk.probSeqNbr*/
            return parentPartResolutionFK.getId().equals(pk.getParentPartResolutionFK()) && 
                  probSeqNbr == pk.probSeqNbr;
        }
        return false;
    }
 
    public int hashCode() {
    	int hash = 7;
    	//hash = (hash * 17) + (null == masterOrderNbr ? 0 : masterOrderNbr.hashCode());
    	//hash = (hash * 31) + lineSeqNbr;
    	//hash = (hash * 13) + (null == txnCd ? 0 : txnCd.hashCode());
    	hash = (hash * 17) + (null == parentPartResolutionFK ? 0 : parentPartResolutionFK.hashCode());
    	hash = (hash * 21) + probSeqNbr;
        return hash;
    }
}

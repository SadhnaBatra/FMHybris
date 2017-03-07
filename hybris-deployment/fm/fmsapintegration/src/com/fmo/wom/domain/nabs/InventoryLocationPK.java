package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InventoryLocationPK implements Serializable {

	@Column (name="TRANS_SEQ_ID")
	private String transactionId;

	@Column (name="LINE_SEQ_NBR")
	private String lineSeqNbr;

	@Column (name="TRANS_CD")
	private String txnCd;
	
	@Column (name="PLANT_CD")
	private String plantCd;
	
	public InventoryLocationPK() {  ; }
	
	public InventoryLocationPK(String transactionId, String lineSeqNbr, String txnCd, String plantCd) {
		this.transactionId = transactionId;
		this.lineSeqNbr = lineSeqNbr;
		this.txnCd = txnCd;
		this.plantCd = plantCd;
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

    public String getPlantCd() {
		return plantCd;
	}

	public void setPlantCd(String plantCd) {
		this.plantCd = plantCd;
	}

	public boolean equals(Object object) {
        if (object instanceof InventoryLocationPK) {
        	InventoryLocationPK pk = (InventoryLocationPK) object;
            return transactionId == pk.transactionId && lineSeqNbr == pk.lineSeqNbr 
            		&& txnCd.equals(pk.txnCd) && plantCd.equals(pk.plantCd);
        }
        return false;
    }
 
    public int hashCode() {
    	int hash = 7;
    	hash = (hash * 17) + (null == transactionId ? 0 : transactionId.hashCode());
    	hash = (hash * 31) + (null == lineSeqNbr ? 0 : lineSeqNbr.hashCode());
    	hash = (hash * 13) + (null == txnCd ? 0 : txnCd.hashCode());
    	hash = (hash * 21) + (null == plantCd ? 0 : plantCd.hashCode());
        return hash;
    }
}

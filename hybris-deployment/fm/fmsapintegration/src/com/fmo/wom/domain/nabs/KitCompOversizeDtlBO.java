package com.fmo.wom.domain.nabs;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (schema="WOM8", name="KIT_COMP_OVRSZ")
@NamedQueries({
    @NamedQuery(name = "findByParentKitCompId",
                    query = "from KitCompOversizeDtlBO oversize where oversize.id.transactionId = :transactionId and oversize.id.txnCd = :transactionCode and oversize.id.lineSeqNbr = :lineSeqNbr and oversize.id.compSeqNbr = :compSeqNbr"),
                    
    @NamedQuery(name = "findByParentKitId",
                    query = "from KitCompOversizeDtlBO oversize where oversize.id.transactionId = :transactionId and oversize.id.txnCd = :transactionCode and oversize.id.lineSeqNbr = :lineSeqNbr")
                    
})
public class KitCompOversizeDtlBO {
    
	@EmbeddedId 
	private KitCompOversizeDtlPK id;

	@Column (name="MSTR_ORD_NBR")
	private String masterOrderNbr;

	@Column (name="OVRSZ_TEXT")
	private String oversizeText;
	
	@Column (name="OVRSZ_CTLGPART_NBR")
	private String oversizeCtlgPartNbr;
	
	@Column (name="AVAIL_QTY")
    private int availableQuantity;

	public KitCompOversizeDtlBO() {
	}

	public KitCompOversizeDtlPK getId() {
		return id;
	}

	public void setId(KitCompOversizeDtlPK id) {
		this.id = id;
	}

	public String getMasterOrderNbr() {
        return masterOrderNbr;
    }

    public void setMasterOrderNbr(String masterOrderNbr) {
        this.masterOrderNbr = masterOrderNbr;
    }

    public String getOversizeText() {
		return oversizeText;
	}

	public void setOversizeText(String oversizeText) {
		this.oversizeText = oversizeText;
	}

	public String getOversizeCtlgPartNbr() {
		return oversizeCtlgPartNbr;
	}

	public void setOversizeCtlgPartNbr(String oversizeCtlgPartNbr) {
		this.oversizeCtlgPartNbr = oversizeCtlgPartNbr;
	}

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

	
}

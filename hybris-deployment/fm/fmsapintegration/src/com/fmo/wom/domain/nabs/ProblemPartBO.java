package com.fmo.wom.domain.nabs;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name="PROBLEM_PART_DTL", schema="WOM8")
@AssociationOverrides( {
    @AssociationOverride(name = "id.probSeqNbr",
                         joinColumns = @JoinColumn(name = "PROB_SEQ_NBR")),
    @AssociationOverride(name = "id.parentPartResolutionFK",
                         joinColumns = {
                            @JoinColumn(name = "trans_seq_id",
                                        referencedColumnName = "TRANS_SEQ_ID"),
                            @JoinColumn(name = "line_seq_nbr",
                                        referencedColumnName = "LINE_SEQ_NBR"),
                            @JoinColumn(name = "trans_cd",
                                        referencedColumnName = "TRANS_CD") })
})
public class ProblemPartBO {

	@EmbeddedId
	private ProblemPartPK id;
	
	@Column (name="CTLG_PART_NBR")
	private String ctlgPartNbr;
	
	@Column (name="PROD_FLG")
	private String prodFlg;
	
	@Column (name="BRAND_STATE")
	private String brandState;
	
	@Column (name="PART_DESC")
	private String partDesc;
	
	@Column (name="ALLWD_TO_ORD_FLG")
	private String allwdToOrdFlg;
	
	@Column (name="MSG")
	private String msg;
	
	public ProblemPartBO() {
	}

	public ProblemPartPK getId() {
		return id;
	}

    public void setId(ProblemPartPK id) {
		this.id = id;
	}

    @Transient
    public PartResolutionInventoryBO getParentPartResolutionFK() {
        return getId().parentPartResolutionFK;
    }
    
    public void setParentPartResolutionFK(PartResolutionInventoryBO foreignKey) {
        getId().setParentPartResolutionFK(foreignKey);
    }
    
    @Transient
    public int getProbSeqNbr() {
        return getId().getProbSeqNbr();
    }

    public void setProbSeqNbr(int probSeqNbr) {
        getId().setProbSeqNbr(probSeqNbr);
    }

    public String getCtlgPartNbr() {
		return ctlgPartNbr;
	}

	public void setCtlgPartNbr(String ctlgPartNbr) {
		this.ctlgPartNbr = ctlgPartNbr;
	}

	public String getProdFlg() {
		return prodFlg;
	}

	public void setProdFlg(String prodFlg) {
		this.prodFlg = prodFlg;
	}

	public String getBrandState() {
		return brandState;
	}

	public void setBrandState(String brandState) {
		this.brandState = brandState;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public String getAllwdToOrdFlg() {
		return allwdToOrdFlg;
	}

	public void setAllwdToOrdFlg(String allwdToOrdFlg) {
		this.allwdToOrdFlg = allwdToOrdFlg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

package com.fmo.wom.domain.nabs;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;


@Entity
@Table (schema="WOM8", name="PART_INVENTORY")
@NamedQueries({
    @NamedQuery(name = "findByTransactionId",
                    query = "from PartResolutionInventoryBO part where part.id.transactionId = :transactionId and part.id.txnCd = :transactionCode")
})
public class PartResolutionInventoryBO {
	
	@EmbeddedId
	private PartResolutionInventoryPK id;
	
	@OneToMany(mappedBy = "id.parentPartResolutionFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<ProblemPartBO> problemParts;
	
	@Column (name="MSTR_ORD_NBR")
    private String masterOrderNbr;

	@Column (name="ITEM_STATUS_CD")
	private String itemStatusCd;
	
	@Column (name="ITEM_STATUS_MSG")
	private String itemStatusMsg;
	
	@Column (name="PART_NBR")
	private String partNbr;

	@Column (name="PROD_FLG")
	private String prodFlg;
	
	@Column (name="FM_BRAND_CODE")
	private String fmBrandCode;

	@Column (name="BRAND_STATE")
	private String brandState;

	@Column (name="ORD_QTY", precision = 7, scale = 2)
	private double ordQty;

	@Column (name="SQSH_PART_NBR")
	private String sqshPartNbr;

	@Column (name="CTLG_PART_NBR")
    private String catalogPartNbr;

	@Column (name="KIT_FLG")
	private String kitFlg;

	@Column (name="PART_DESC")
	private String partDesc;

	@Column (name="MULT_QTY")
	private double multQty;

	@Column (name="ALT_CTLG_PART")
	private String altCtlgPart;

	@Column (name="ALT_PROD_FLG")
	private String altProdFlg;

	@Column (name="ALT_BRAND_STATE")
	private String altBrandState;

	@Column (name="ALT_DESC")
	private String altDesc;

	@Column (name="ALT_MULT_QTY")
	private double altMultQty;

	@Column (name="VENDOR_DIRECT_FLG")
	private String vendorDirectFlg;
	
	@Column(name="VENDOR_DIRECT_FLG", insertable = false, updatable = false)
    @Type(type="yes_no")
    private Boolean vendorDirect;
	
	@Column (name="NO_RETURN_PART_FLG")
	private String noReturnPartFlg;

	@Column (name="NO_RETURN_PART_FLG", insertable = false, updatable = false)
	@Type(type="yes_no")
    private Boolean noReturn;
	
	@Column (name="PART_FLIPPED_FLG")
	private String partFlippedFlg;

	@Column (name="PRICE_TYP_CD")
	private String priceTypeCd;

	@Column (name="DISPLAY_PRICE")
	private double displayPrice;

	@Column (name="FRGHT_NET_PRICE")
	private double frghtNetPrice;

	@Column (name="PCE_WGT_LBS")
	private double pceWgtLbs;

	@Column (name="VERIFY_LOQ")
	private double verifyLOQ;

	@Column (name="OK_TO_ADD_TO_A_KIT")
	private String okToAddToAKit;
	
	@Column (name="OK_TO_ADD_TO_A_KIT", insertable = false, updatable = false)
	@Type(type="yes_no")
    private Boolean kitAddable;

	@Column (name="ENGKIT_EXPRESS_FLG")
	private String engKitExpressFlg;
	
	@Column (name="ENGKIT_EXPRESS_FLG", insertable = false, updatable = false)
	@Type(type="yes_no")
    private Boolean engKitExpress;

	@Column (name="DISCONTINUED_FLG")
	private String discontinuedFlg;
	
	@Column (name="DISCONTINUED_FLG", insertable = false, updatable = false)
	@Type(type="yes_no")
    private Boolean beingDiscontinued;

	@Column (name="SUBJ_TO_BCKORD_FLG")
	private String subjToBackordFlg;

	@Column (name="LOQ_QTY")
	private double loqQty;

	@Column (name="BRND_ST_SHRT_DESC")
	private String brandStateShortDesc;

	@Column (name="SALES_SYMBOL")
	private String salesSymbol;

	@Column (name="PKG_CD")
	private String pkgCd;

	@Column (name="LINE_COMMENT")
	private String lineComment;

	@Column (name="ROUNDED_ORD_QTY")
	private double roundedOrdQty;

	@Column (name="CUST_PART_NBR")
	private String custPartNbr;

	@Column (name="TEN_DIGIT_UPC")
	private String tenDigitUPC;

	@Column (name="FACTOR_QTY")
	private double factorQty;

	@Column (name="UOM")
	private String uom;

	@Column (name="ORIG_PART_NBR")
	private String origPartNbr;

	@Column (name="BULK_PART_FLG")
	private String bulkPartFlg;

	@Column (name="KIT_ASSGN_TYP_FLG")
	private String kitAssgnTypeFlg;

	@Column (name="ALLW_COMP_ADDS_FLG")
	private String allowCompAdditionsFlg;

	@Column (name="FREE_FRGHT_KIT_FLG")
	private String freeFrghtKitFlg;

	@Column (name="MFG_CD")
	private String mfgCd;

	@Column (name="KIT_OVRSZ_BYPS_FLG")
	private String kitOversizeBypassFlg;

	@Column (name="NBR_OF_CATEGORIES")
	private int nbrOfCategories;

	@Column (name="NBR_OF_COMPONENTS")
	private int nbrOfComp;

	@Column (name="MSG")
	private String msg;
	
	@Column (name="VINTAGE_PART_FLG")
	private String vintageFlg;

	@Temporal( TemporalType.DATE)
    @Column (name="CREATION_DATE")
    private Date creationDate = new Date();
	
	public PartResolutionInventoryBO() {
	}

	public PartResolutionInventoryPK getId() {
		return id;
	}

	public void setId(PartResolutionInventoryPK id) {
		this.id = id;
	}

	public List<ProblemPartBO> getProblemParts() {
        return problemParts;
    }

    public void setProblemParts(List<ProblemPartBO> problemParts) {
        this.problemParts = problemParts;
    }

    public String getMasterOrderNbr() {
        return masterOrderNbr;
    }

    public void setMasterOrderNbr(String masterOrderNbr) {
        this.masterOrderNbr = masterOrderNbr;
    }

    public String getItemStatusCd() {
		return itemStatusCd;
	}

	public void setItemStatusCd(String itemStatusCd) {
		this.itemStatusCd = itemStatusCd;
	}

	public String getItemStatusMsg() {
		return itemStatusMsg;
	}

	public void setItemStatusMsg(String itemStatusMsg) {
		this.itemStatusMsg = itemStatusMsg;
	}

	public String getPartNbr() {
		return partNbr;
	}

	public void setPartNbr(String partNbr) {
		this.partNbr = partNbr;
	}

	public String getProdFlg() {
		return prodFlg;
	}

	public void setProdFlg(String prodFlg) {
		this.prodFlg = prodFlg;
	}
	
	public String getFmBrandCode() {
		return fmBrandCode;
	}

	public void setFmBrandCode(String fmBrandCode) {
		this.fmBrandCode = fmBrandCode;
	}

	public String getBrandState() {
		return brandState;
	}

	public void setBrandState(String brandState) {
		this.brandState = brandState;
	}

	public double getOrdQty() {
		return ordQty;
	}

	public void setOrdQty(double ordQty) {
		this.ordQty = ordQty;
	}

	public String getSqshPartNbr() {
		return sqshPartNbr;
	}

	public void setSqshPartNbr(String sqshPartNbr) {
		this.sqshPartNbr = sqshPartNbr;
	}

	public String getCatalogPartNbr() {
        return catalogPartNbr;
    }

    public void setCatalogPartNbr(String catalogPartNbr) {
        this.catalogPartNbr = catalogPartNbr;
    }

    public String getKitFlg() {
		return kitFlg;
	}
	
	public boolean isKit() {
	    return "Y".equals(getKitFlg());
	}

	public void setKitFlg(String kitFlg) {
		this.kitFlg = kitFlg;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public double getMultQty() {
		return multQty;
	}

	public void setMultQty(double multQty) {
		this.multQty = multQty;
	}

	public String getAltCtlgPart() {
		return altCtlgPart;
	}

	public void setAltCtlgPart(String altCtlgPart) {
		this.altCtlgPart = altCtlgPart;
	}

	public String getAltProdFlg() {
		return altProdFlg;
	}

	public void setAltProdFlg(String altProdFlg) {
		this.altProdFlg = altProdFlg;
	}

	public String getAltBrandState() {
		return altBrandState;
	}

	public void setAltBrandState(String altBrandState) {
		this.altBrandState = altBrandState;
	}

	public String getAltDesc() {
		return altDesc;
	}

	public void setAltDesc(String altDesc) {
		this.altDesc = altDesc;
	}

	public double getAltMultQty() {
		return altMultQty;
	}

	public void setAltMultQty(double altMultQty) {
		this.altMultQty = altMultQty;
	}

	public String getVendorDirectFlg() {
		return vendorDirectFlg;
	}

	public void setVendorDirectFlg(String vendorDirectFlg) {
		this.vendorDirectFlg = vendorDirectFlg;
	}

	public boolean isVendorDirect() {
	    if (vendorDirect == null) return false;
        return vendorDirect;
    }

    public void setVendorDirect(boolean vendorDirect) {
        this.vendorDirect = vendorDirect;
    }

	public String getNoReturnPartFlg() {
		return noReturnPartFlg;
	}

	public void setNoReturnPartFlg(String noReturnPartFlg) {
		this.noReturnPartFlg = noReturnPartFlg;
	}

	public boolean isNoReturn() {
	    if (noReturn == null) return false;
	    return noReturn;
    }

    public void setNoReturn(boolean noReturn) {
        this.noReturn = noReturn;
    }

    public String getPartFlippedFlg() {
		return partFlippedFlg;
	}

	public void setPartFlippedFlg(String partFlippedFlg) {
		this.partFlippedFlg = partFlippedFlg;
	}

	public String getPriceTypeCd() {
		return priceTypeCd;
	}

	public void setPriceTypeCd(String priceTypeCd) {
		this.priceTypeCd = priceTypeCd;
	}

	public double getDisplayPrice() {
		return displayPrice;
	}

	public void setDisplayPrice(double displayPrice) {
		this.displayPrice = displayPrice;
	}

	public double getFrghtNetPrice() {
		return frghtNetPrice;
	}

	public void setFrghtNetPrice(double frghtNetPrice) {
		this.frghtNetPrice = frghtNetPrice;
	}

	public double getPceWgtLbs() {
		return pceWgtLbs;
	}

	public void setPceWgtLbs(double pceWgtLbs) {
		this.pceWgtLbs = pceWgtLbs;
	}

	public double getVerifyLOQ() {
		return verifyLOQ;
	}

	public void setVerifyLOQ(double verifyLOQ) {
		this.verifyLOQ = verifyLOQ;
	}

	public String getOkToAddToAKit() {
		return okToAddToAKit;
	}

	public void setOkToAddToAKit(String okToAddToAKit) {
		this.okToAddToAKit = okToAddToAKit;
	}
	
    public boolean isKitAddable() {
        if (kitAddable == null) return false;
        return kitAddable;
    }

    public void setKitAddable(boolean kitAddable) {
        this.kitAddable = kitAddable;
    }

    public String getEngKitExpressFlg() {
		return engKitExpressFlg;
	}

	public void setEngKitExpressFlg(String engKitExpressFlg) {
		this.engKitExpressFlg = engKitExpressFlg;
	}

	public boolean isEngKitExpress() {
	    if (engKitExpress == null) return false;
        return engKitExpress;
    }

    public void setEngKitExpress(boolean engKitExpress) {
        this.engKitExpress = engKitExpress;
    }

    public String getDiscontinuedFlg() {
		return discontinuedFlg;
	}

	public void setDiscontinuedFlg(String discontinuedFlg) {
		this.discontinuedFlg = discontinuedFlg;
	}
	
	/**
	 * This is nullable field
	 * @return false if false or null
	 */
	public boolean isBeingDiscontinued() {
	    if (beingDiscontinued == null)
	        return false;
        return beingDiscontinued;
    }

    public void setBeingDiscontinued(boolean beingDiscontinued) {
        this.beingDiscontinued = beingDiscontinued;
    }

    public String getSubjToBackordFlg() {
		return subjToBackordFlg;
	}

	public void setSubjToBackordFlg(String subjToBackordFlg) {
		this.subjToBackordFlg = subjToBackordFlg;
	}

	public double getLoqQty() {
		return loqQty;
	}

	public void setLoqQty(double loqQty) {
		this.loqQty = loqQty;
	}

	public String getBrandStateShortDesc() {
		return brandStateShortDesc;
	}

	public void setBrandStateShortDesc(String brandStateShortDesc) {
		this.brandStateShortDesc = brandStateShortDesc;
	}

	public String getSalesSymbol() {
		return salesSymbol;
	}

	public void setSalesSymbol(String salesSymbol) {
		this.salesSymbol = salesSymbol;
	}

	public String getPkgCd() {
		return pkgCd;
	}

	public void setPkgCd(String pkgCd) {
		this.pkgCd = pkgCd;
	}

	public String getLineComment() {
		return lineComment;
	}

	public void setLineComment(String lineComment) {
		this.lineComment = lineComment;
	}

	public double getRoundedOrdQty() {
		return roundedOrdQty;
	}

	public void setRoundedOrdQty(double roundedOrdQty) {
		this.roundedOrdQty = roundedOrdQty;
	}

	public String getCustPartNbr() {
		return custPartNbr;
	}

	public void setCustPartNbr(String custPartNbr) {
		this.custPartNbr = custPartNbr;
	}

	public String getTenDigitUPC() {
		return tenDigitUPC;
	}

	public void setTenDigitUPC(String tenDigitUPC) {
		this.tenDigitUPC = tenDigitUPC;
	}

	public double getFactorQty() {
		return factorQty;
	}

	public void setFactorQty(double factorQty) {
		this.factorQty = factorQty;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getOrigPartNbr() {
		return origPartNbr;
	}

	public void setOrigPartNbr(String origPartNbr) {
		this.origPartNbr = origPartNbr;
	}

	public String getBulkPartFlg() {
		return bulkPartFlg;
	}

	public void setBulkPartFlg(String bulkPartFlg) {
		this.bulkPartFlg = bulkPartFlg;
	}

	public String getKitAssgnTypeFlg() {
		return kitAssgnTypeFlg;
	}

	public boolean isShortShipAllowed() {
	    return (getKitAssgnTypeFlg() != null &&  "Y".equals(getKitAssgnTypeFlg()));
	}
	
	public void setKitAssgnTypeFlg(String kitAssgnTypeFlg) {
		this.kitAssgnTypeFlg = kitAssgnTypeFlg;
	}

	public String getAllowCompAdditionsFlg() {
		return allowCompAdditionsFlg;
	}

	public boolean isAddOtherComponentsAllowed() {
	    return (getAllowCompAdditionsFlg() != null &&  "Y".equals(getAllowCompAdditionsFlg()));
	}
	
	public void setAllowCompAdditionsFlg(String allowCompAdditionsFlg) {
		this.allowCompAdditionsFlg = allowCompAdditionsFlg;
	}

	public String getFreeFrghtKitFlg() {
		return freeFrghtKitFlg;
	}

	public void setFreeFrghtKitFlg(String freeFrghtKitFlg) {
		this.freeFrghtKitFlg = freeFrghtKitFlg;
	}

	public String getMfgCd() {
		return mfgCd;
	}

	public void setMfgCd(String mfgCd) {
		this.mfgCd = mfgCd;
	}

	public String getKitOversizeBypassFlg() {
		return kitOversizeBypassFlg;
	}

	public void setKitOversizeBypassFlg(String kitOversizeBypassFlg) {
		this.kitOversizeBypassFlg = kitOversizeBypassFlg;
	}

	public int getNbrOfCategories() {
		return nbrOfCategories;
	}

	public void setNbrOfCategories(int nbrOfCategories) {
		this.nbrOfCategories = nbrOfCategories;
	}

	public int getNbrOfComp() {
		return nbrOfComp;
	}

	public void setNbrOfComp(int nbrOfComp) {
		this.nbrOfComp = nbrOfComp;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
	public String getVintageFlg() {
		return vintageFlg;
	}

	public void setVintageFlg(String vintageFlg) {
		this.vintageFlg = vintageFlg;
	}

	@Override
	public String toString() {
		return "PartResolutionInventoryBO [id=" + id + ", problemParts="
				+ problemParts + ", masterOrderNbr=" + masterOrderNbr
				+ ", itemStatusCd=" + itemStatusCd + ", itemStatusMsg="
				+ itemStatusMsg + ", partNbr=" + partNbr + ", prodFlg="
				+ prodFlg + ", fmBrandCode=" + fmBrandCode + ", brandState="
				+ brandState + ", ordQty=" + ordQty + ", sqshPartNbr="
				+ sqshPartNbr + ", catalogPartNbr=" + catalogPartNbr
				+ ", kitFlg=" + kitFlg + ", partDesc=" + partDesc
				+ ", multQty=" + multQty + ", altCtlgPart=" + altCtlgPart
				+ ", altProdFlg=" + altProdFlg + ", altBrandState="
				+ altBrandState + ", altDesc=" + altDesc + ", altMultQty="
				+ altMultQty + ", vendorDirectFlg=" + vendorDirectFlg
				+ ", vendorDirect=" + vendorDirect + ", noReturnPartFlg="
				+ noReturnPartFlg + ", noReturn=" + noReturn
				+ ", partFlippedFlg=" + partFlippedFlg + ", priceTypeCd="
				+ priceTypeCd + ", displayPrice=" + displayPrice
				+ ", frghtNetPrice=" + frghtNetPrice + ", pceWgtLbs="
				+ pceWgtLbs + ", verifyLOQ=" + verifyLOQ + ", okToAddToAKit="
				+ okToAddToAKit + ", kitAddable=" + kitAddable
				+ ", engKitExpressFlg=" + engKitExpressFlg + ", engKitExpress="
				+ engKitExpress + ", discontinuedFlg=" + discontinuedFlg
				+ ", beingDiscontinued=" + beingDiscontinued
				+ ", subjToBackordFlg=" + subjToBackordFlg + ", loqQty="
				+ loqQty + ", brandStateShortDesc=" + brandStateShortDesc
				+ ", salesSymbol=" + salesSymbol + ", pkgCd=" + pkgCd
				+ ", lineComment=" + lineComment + ", roundedOrdQty="
				+ roundedOrdQty + ", custPartNbr=" + custPartNbr
				+ ", tenDigitUPC=" + tenDigitUPC + ", factorQty=" + factorQty
				+ ", uom=" + uom + ", origPartNbr=" + origPartNbr
				+ ", bulkPartFlg=" + bulkPartFlg + ", kitAssgnTypeFlg="
				+ kitAssgnTypeFlg + ", allowCompAdditionsFlg="
				+ allowCompAdditionsFlg + ", freeFrghtKitFlg="
				+ freeFrghtKitFlg + ", mfgCd=" + mfgCd
				+ ", kitOversizeBypassFlg=" + kitOversizeBypassFlg
				+ ", nbrOfCategories=" + nbrOfCategories + ", nbrOfComp="
				+ nbrOfComp + ", msg=" + msg + ", vintageFlg=" + vintageFlg
				+ ", creationDate=" + creationDate + "]";
	}
	
}

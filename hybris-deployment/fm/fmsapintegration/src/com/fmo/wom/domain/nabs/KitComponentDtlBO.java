package com.fmo.wom.domain.nabs;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (schema="WOM8", name="KIT_COMPONENT_DTL")
@NamedQueries({
    @NamedQuery(name = "findByParentPartId",
                    query = "from KitComponentDtlBO kit where kit.id.transactionId = :transactionId and kit.id.txnCd = :transactionCode and kit.id.lineSeqNbr = :lineSeqNbr")
})
public class KitComponentDtlBO {

	@EmbeddedId
	private KitComponentDtlPK id;
	
	@Column (name="MSTR_ORD_NBR")
	private String masterOrderNbr;

	@Column (name="PART_NBR")
	private String partNbr;
	
	@Column (name="PRINT_SEQ_NBR")
	private String printSeqNbr;
	
	@Column (name="CATEGORY")
	private String category;
	
	@Column (name="LEVEL_NBR")
	private int levelNbr;
	
	@Column (name="PROD_FLG")
	private String prodFlg;
	
	@Column (name="BRAND_STATE")
	private String brandState;
	
	@Column (name="COMMENTS")
	private String comments;
	
	@Column (name="QTY_PER_ASSEMBLY")
	private int qtyPerAssembly;
	
	@Column (name="METRIC_FLG")
	private String metricFlg;
	
	@Column (name="PARENT_PART_NBR")
	private String parentPartNbr;
	
	@Column (name="PRICE_TYP_CD")
	private String priceTypeCd;
	
	@Column (name="DISPLAY_PRICE")
	private double displayPrice;
	
	@Column (name="FRGHT_NET_PRICE")
	private double frghtNetPrice;
	
	@Column (name="PCE_WGT_LBS")
	private double pceWeightLbs;
	
	@Column (name="MSG")
	private String msg;
	
	@Column (name="AVAIL_QTY")
    private int availableQuantity;
    
	public KitComponentDtlBO() {
	}

	public KitComponentDtlPK getId() {
		return id;
	}

	public void setId(KitComponentDtlPK id) {
		this.id = id;
	}

	public String getMasterOrderNbr() {
        return masterOrderNbr;
    }

    public void setMasterOrderNbr(String masterOrderNbr) {
        this.masterOrderNbr = masterOrderNbr;
    }

    public String getPartNbr() {
		return partNbr;
	}

	public void setPartNbr(String partNbr) {
		this.partNbr = partNbr;
	}

	public String getPrintSeqNbr() {
		return printSeqNbr;
	}

	public void setPrintSeqNbr(String printSeqNbr) {
		this.printSeqNbr = printSeqNbr;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getLevelNbr() {
		return levelNbr;
	}

	public void setLevelNbr(int levelNbr) {
		this.levelNbr = levelNbr;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getQtyPerAssembly() {
		return qtyPerAssembly;
	}

	public void setQtyPerAssembly(int qtyPerAssembly) {
		this.qtyPerAssembly = qtyPerAssembly;
	}

	public String getMetricFlg() {
		return metricFlg;
	}
	
	public boolean isMetric() {
	    return getMetricFlg() != null && "Y".equals(getMetricFlg());
	}

	public void setMetricFlg(String metricFlg) {
		this.metricFlg = metricFlg;
	}

	public String getParentPartNbr() {
		return parentPartNbr;
	}

	public void setParentPartNbr(String parentPartNbr) {
		this.parentPartNbr = parentPartNbr;
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

	public double getPceWeightLbs() {
		return pceWeightLbs;
	}

	public void setPceWeightLbs(double pceWeightLbs) {
		this.pceWeightLbs = pceWeightLbs;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
	

}

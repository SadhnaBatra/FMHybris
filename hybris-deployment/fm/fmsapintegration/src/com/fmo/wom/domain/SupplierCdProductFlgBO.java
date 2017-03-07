package com.fmo.wom.domain;

import java.io.Serializable;
//import javax.persistence.*;

import com.fmo.wom.domain.enums.ExternalSystem;

import java.util.Date;


/**
 * The persistent class for the SUPPLIERCD_PRODUCTFLG database table.
 * 
 * //author vishws74
 */
/*//Entity
//Table(name="SUPPLIERCD_PRODUCTFLG")
//NamedQueries({
	//NamedQuery(name = "findProdFlagBySupplCd",
	           	query = "from SupplierCdProductFlgBO spbf " +
	           			"where upper(spbf.supplierCd) = upper(:suppCode) " +
	           			"and upper(spbf.active) in :activeFlags")
})
*/
public class SupplierCdProductFlgBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	//Id
	//SequenceGenerator(name="SUPPLIERCD_PRODUCTFLG_SUPPLIERCDPRODFLGID_GENERATOR", sequenceName="SEQ_SUPPLIERCD_PRODFLG_ID")
	//GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SUPPLIERCD_PRODUCTFLG_SUPPLIERCDPRODFLGID_GENERATOR")
	//Column(name="SUPPLIERCD_PRODFLG_ID")
	private long supplierCdProdFlgId;

	//Column(name="ACTIV_FLG")
	//Type(type="yes_no")
	private boolean active;

	//Column(name="BRAND_DESC")
	private String brandDesc;

    //Temporal( TemporalType.DATE)
	//Column(name="INACTIV_FROM_DATE")
	private Date inactivFromDate;

	//Column(name="PROD_FLG")
	private String prodFlg;

	//Column(name="SUPPLIER_CD")
	private String supplierCd;
	
	//Column(name="EXTERNAL_SYSTEM_CD")
	private String externalSystemCd;
	

    public SupplierCdProductFlgBO() {
    }

	public long getSupplierCdProdFlgId() {
		return this.supplierCdProdFlgId;
	}

	public void setSupplierCdProdFlgId(long supplierCdProdFlgId) {
		this.supplierCdProdFlgId = supplierCdProdFlgId;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getBrandDesc() {
		return this.brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public Date getInactivFromDate() {
		return this.inactivFromDate;
	}

	public void setInactivFromDate(Date inactivFromDate) {
		this.inactivFromDate = inactivFromDate;
	}

	public String getProdFlg() {
		return this.prodFlg;
	}

	public void setProdFlg(String prodFlg) {
		this.prodFlg = prodFlg;
	}

	public String getSupplierCd() {
		return this.supplierCd;
	}

	public void setSupplierCd(String supplierCd) {
		this.supplierCd = supplierCd;
	}

	public String getExternalSystemCd() {
		return externalSystemCd;
	}

	public ExternalSystem getExternalSystem() {
	    if (externalSystemCd != null) {
	        return ExternalSystem.valueOf(externalSystemCd);
	    }
	    return null;
    }

	
	public void setExternalSystemCd(String externalSystemCd) {
		this.externalSystemCd = externalSystemCd;
	}

	@Override
	public String toString() {
		return "SupplierCdProductFlgBO [supplierCdProdFlgId="
				+ supplierCdProdFlgId + ", active=" + active + ", brandDesc="
				+ brandDesc + ", prodFlg=" + prodFlg + ", supplierCd="
				+ supplierCd + ", externalSystemCd=" + externalSystemCd + "]";
	}

}
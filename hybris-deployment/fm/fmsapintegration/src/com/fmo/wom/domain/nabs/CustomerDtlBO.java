package com.fmo.wom.domain.nabs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="com.fmo.wom.domain.nabs.CustomerDtlBO")
@Table (schema="WOM8", name="CUSTOMER_DTL")
public class CustomerDtlBO {

	@Id
	@Column (name="TRANS_SEQ_ID")
	private String transactionId;
	
	@Column (name="MSTR_ORD_NBR")
	private String masterOrderNbr;
	
	@Column (name="ACCT_CD")
	private String acctCd;
	
	@Column (name="CNTRY_CD_NABS")
	private String cntryCdNabs;
	
	@Column (name="CNTRY_CD_ISO")
	private String cntryCdIso;
	
	@Column (name="VIEW_INVOICE_FLG")
	private String viewInvoiceFlag;
	
	@Column (name="SALES_CHANNEL")
	private String salesChannel;
	
	@Column (name="ACCT_TYP_CD")
	private String acctTypeCd;
	
	@Column (name="CRDT_RSTRCT_FLG")
	private String creditRestrictFlag;
	
	@Column (name="XMPT_FRM_EMERG_FLG")
	private String exemptFromEmergencyFlag;
	
	@Column (name="HOME_SRVC_CNTR_CD")
	private String homeSrvcCntrCd;
	
	@Column (name="DFLT_BCKORD_TYP_CD")
	private String dfltBackOrderTypeCd;
	
	@Column (name="FRZ_DFLT_BO_TYP_CD")
	private String freezeDefaultBackOrderTypeCd;
	
	@Column (name="DFLT_SHIP_VIA")
	private String dfltShipVia;
	
	@Column (name="CROSS_BORDER_IND")
	private String crossBorderInd;
	
	@Column (name="CRDT_CRD_ALLWD_FLG")
	private String creditCardAllowedFlag;
	
	@Column (name="CRDT_CRD_TYP_CD")
	private String creditCardTypeCd;
	
	@Column (name="CRDT_CRD_ACCT_NBR")
	private String creditCardAcctNbr;
	
	@Column (name="CRDT_CRD_EXPIRE_DT")
	private String creditCardExpireDt;
	
	@Column (name="CRDT_CRD_CNTY")
	private String creditCardCounty;
	
	@Column (name="VIEW_ONLY_COMMENT1")
	private String viewOnlyComment1;
	
	@Column (name="VIEW_ONLY_COMMENT2")
	private String viewOnlyComment2;
	
	@Column (name="CUST_NAME")
	private String custName;
	
	@Column (name="ADDR1")
	private String addr1;
	
	@Column (name="ADDR2")
	private String addr2;
	
	@Column (name="CITY")
	private String city;
	
	@Column (name="STATE_OR_PRVNC_CD")
	private String stateOrPrvncCd;
	
	@Column (name="ZIP_OR_POSTAL_CD")
	private String zipOrPostalCd;
	
	@Column (name="CNTRY_NAME")
	private String cntryName;

	@Temporal( TemporalType.DATE)
	@Column (name="CREATION_DATE")
	private Date creationDate = new Date();
	
	public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getMasterOrderNbr() {
		return masterOrderNbr;
	}
	public void setMasterOrderNbr(String masterOrderNbr) {
		this.masterOrderNbr = masterOrderNbr;
	}
	
	public String getAcctCd() {
		return acctCd;
	}
	public void setAcctCd(String acctCd) {
		this.acctCd = acctCd;
	}
	
	public String getCntryCdNabs() {
		return cntryCdNabs;
	}
	public void setCntryCdNabs(String cntryCdNabs) {
		this.cntryCdNabs = cntryCdNabs;
	}
	
	public String getCntryCdIso() {
		return cntryCdIso;
	}
	public void setCntryCdIso(String cntryCdIso) {
		this.cntryCdIso = cntryCdIso;
	}
	
	public String getViewInvoiceFlag() {
		return viewInvoiceFlag;
	}
	public void setViewInvoiceFlag(String viewInvoiceFlag) {
		this.viewInvoiceFlag = viewInvoiceFlag;
	}
	
	public boolean isViewInvoice() {
        return "Y".equals(viewInvoiceFlag);
    }
    
	public void setViewInvoice(boolean viewInvoice) {
	    setViewInvoiceFlag("N");
	    if (viewInvoice) {
            setViewInvoiceFlag("Y");
        }
    }
    
	public String getSalesChannel() {
		return salesChannel;
	}
	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	
	public String getAcctTypeCd() {
		return acctTypeCd;
	}
	public void setAcctType(String acctTypeCd) {
		this.acctTypeCd = acctTypeCd;
	}
	
	public String getCreditRestrictFlag() {
		return creditRestrictFlag;
	}
	public void setCreditRestrictFlag(String creditRestrictFlag) {
		this.creditRestrictFlag = creditRestrictFlag;
	}
	
	public boolean isCreditRestrict() {
        return "Y".equals(creditRestrictFlag);
    }
	
    public void setCreditRestrict(boolean creditRestrict) {
        setCreditRestrictFlag("N");
        if (creditRestrict) {
            setCreditRestrictFlag("Y");
        }
    }
    
    public String getExemptFromEmergencyFlag() {
		return exemptFromEmergencyFlag;
	}
	
    public void setExemptFromEmergencyFlag(String exemptFromEmergencyFlag) {
		this.exemptFromEmergencyFlag = exemptFromEmergencyFlag;
	}
	
	public String getHomeSrvcCntrCd() {
		return homeSrvcCntrCd;
	}
	public void setHomeSrvcCntrCd(String homeSrvcCntrCd) {
		this.homeSrvcCntrCd = homeSrvcCntrCd;
	}
	
	public String getDfltBackOrderTypeCd() {
		return dfltBackOrderTypeCd;
	}
	
	public void setDfltBackOrderTypeCd(String dfltBackOrderTypeCd) {
		this.dfltBackOrderTypeCd = dfltBackOrderTypeCd;
	}
	
	public String getFreezeDefaultBackOrderTypeCd() {
        return freezeDefaultBackOrderTypeCd;
    }
    public void setFreezeDefaultBackOrderTypeCd(String freezeDefaultBackOrderTypeCd) {
        this.freezeDefaultBackOrderTypeCd = freezeDefaultBackOrderTypeCd;
    }
    public String getDfltShipVia() {
		return dfltShipVia;
	}
	public void setDfltShipVia(String dfltShipVia) {
		this.dfltShipVia = dfltShipVia;
	}
	
	public String getCrossBorderInd() {
		return crossBorderInd;
	}
	public void setCrossBorderInd(String crossBorderInd) {
		this.crossBorderInd = crossBorderInd;
	}
	
	public String getCreditCardAllowedFlag() {
		return creditCardAllowedFlag;
	}
	public void setCreditCardAllowedFlag(String creditCardAllowedFlag) {
		this.creditCardAllowedFlag = creditCardAllowedFlag;
	}
	
	public boolean isCreditCardAllowed() {
        return "Y".equals(creditCardAllowedFlag);
    }
    
    public String getCreditCardTypeCd() {
		return creditCardTypeCd;
	}
	public void setCreditCardTypeCd(String creditCardTypeCd) {
		this.creditCardTypeCd = creditCardTypeCd;
	}
	
	public String getCreditCardAcctNbr() {
		return creditCardAcctNbr;
	}
	public void setCreditCardAcctNbr(String creditCardAcctNbr) {
		this.creditCardAcctNbr = creditCardAcctNbr;
	}
	
	public String getCreditCardExpireDt() {
		return creditCardExpireDt;
	}
	public void setCreditCardExpireDt(String creditCardExpireDt) {
		this.creditCardExpireDt = creditCardExpireDt;
	}
	
	public String getCreditCardCounty() {
		return creditCardCounty;
	}
	public void setCreditCardCounty(String creditCardCounty) {
		this.creditCardCounty = creditCardCounty;
	}
	
	public String getViewOnlyComment1() {
		return viewOnlyComment1;
	}
	public void setViewOnlyComment1(String viewOnlyComment1) {
		this.viewOnlyComment1 = viewOnlyComment1;
	}
	
	public String getViewOnlyComment2() {
		return viewOnlyComment2;
	}
	public void setViewOnlyComment2(String viewOnlyComment2) {
		this.viewOnlyComment2 = viewOnlyComment2;
	}
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getStateOrPrvncCd() {
		return stateOrPrvncCd;
	}
	public void setStateOrPrvncCd(String stateOrPrvncCd) {
		this.stateOrPrvncCd = stateOrPrvncCd;
	}
	
	public String getZipOrPostalCd() {
		return zipOrPostalCd;
	}
	public void setZipOrPostalCd(String zipOrPostalCd) {
		this.zipOrPostalCd = zipOrPostalCd;
	}
	
	public String getCntryName() {
		return cntryName;
	}
	public void setCntryName(String cntryName) {
		this.cntryName = cntryName;
	}
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
	
	
}

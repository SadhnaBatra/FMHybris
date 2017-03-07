package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the TP_ACCOUNT database table.
 * 
 * @author vishws74
 */
/*@Entity
@Table(name="TP_ACCOUNT")
@NamedQuery(name = "findByFmBillToAccountCode",
            query = "from TpAccountBO tpa " +
            		"where upper(tpa.fmBillToAcctCd) = upper(:fmBillToAcctCd) " +
            		"and upper(tpa.active) in :activeFlags")
*/public class TpAccountBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TP_ACCOUNT_TPACCTID_GENERATOR", sequenceName="SEQ_TP_ACCT_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TP_ACCOUNT_TPACCTID_GENERATOR")
	@Column(name="TP_ACCT_ID")
	private long tpAcctId;

	@Column(name="ACTIV_FLG")
	@Type(type="yes_no")
	private boolean active;

	@Column(name="FM_BILLTO_ACCT_CD")
	private String fmBillToAcctCd;

    @Temporal( TemporalType.DATE)
	@Column(name="INACTIV_FROM_DATE")
	private Date inactiveFromDate;

	//bi-directional many-to-one association to TradingPartnerBO
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TP_ID")
	private TradingPartnerBO tradingPartner;

    public TpAccountBO() {
    }

	public long getTpAcctId() {
		return this.tpAcctId;
	}

	public void setTpAcctId(long tpAcctId) {
		this.tpAcctId = tpAcctId;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getFmBillToAcctCd() {
		return this.fmBillToAcctCd;
	}

	public void setFmBillToAcctCd(String fmBillToAcctCd) {
		this.fmBillToAcctCd = fmBillToAcctCd;
	}

	public Date getInactiveFromDate() {
		return this.inactiveFromDate;
	}

	public void setInactiveFromDate(Date inactiveFromDate) {
		this.inactiveFromDate = inactiveFromDate;
	}

	public TradingPartnerBO getTradingPartner() {
		return this.tradingPartner;
	}

	public void setTradingPartner(TradingPartnerBO tradingPartner) {
		this.tradingPartner = tradingPartner;
	}

	@Override
	public String toString() {
		return "TpAccountBO [tpAcctId=" + tpAcctId + ", active=" + active
				+ ", fmBillToAcctCd=" + fmBillToAcctCd + "]";
	}
	
}
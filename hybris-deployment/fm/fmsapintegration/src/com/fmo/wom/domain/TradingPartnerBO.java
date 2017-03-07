package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the TRADING_PARTNER database table.
 * Only 'Active' Trading Partners will be returned.
 * 
 * @author vishws74
 */
/*@Entity
@Table(name="TRADING_PARTNER")
@DiscriminatorColumn(name="ACTIV_FLG", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("Y")
@DiscriminatorOptions(force = true)
@NamedQueries({
 @NamedQuery(name = "getTradingPartnerForSecurityKey",
             query = "from TradingPartnerBO tp " +
             		 "where tp.ipoSecurityKey = :secKey")
})*/
public class TradingPartnerBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TRADING_PARTNER_TPID_GENERATOR", sequenceName="SEQ_TP_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRADING_PARTNER_TPID_GENERATOR")
	@Column(name="TP_ID")
	private long tpId;

	@Column(name="ACTIV_FLG")
	@Type(type="yes_no")
	private boolean active;

    @Temporal( TemporalType.DATE)
	@Column(name="INACTIV_FROM_DATE")
	private Date inactiveFromDate;

	@Column(name="IPO_SECURITY_KEY")
	private String ipoSecurityKey;

	@Column(name="TP_NAME")
	private String tpName;

	@Column(name="TP_GRP_CD")
	private String groupCode;

	@Column(name="ENABLE_SHIPPING_FROM_TSC_FLG")
	@Type(type="yes_no")
	private boolean enableShippingFromTsc;
	
	//bi-directional many-to-one association to TpAccountBO
	@OneToMany(mappedBy="tradingPartner",fetch=FetchType.EAGER)
	private List<TpAccountBO> tpAccounts;

	//bi-directional many-to-one association to TpShipperShipMthdBO
	//@OneToMany(mappedBy="tradingPartner")
	//private List<TpShipperShipMthdBO> tpCarrierShipmthds;

    public TradingPartnerBO() {
    }

	public long getTpId() {
		return this.tpId;
	}

	public void setTpId(long tpId) {
		this.tpId = tpId;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getInactiveFromDate() {
		return this.inactiveFromDate;
	}

	public void setInactiveFromDate(Date inactiveFromDate) {
		this.inactiveFromDate = inactiveFromDate;
	}

	public String getIpoSecurityKey() {
		return this.ipoSecurityKey;
	}

	public void setIpoSecurityKey(String ipoSecurityKey) {
		this.ipoSecurityKey = ipoSecurityKey;
	}

	public String getTpName() {
		return this.tpName;
	}

	public void setTpName(String tpName) {
		this.tpName = tpName;
	}

	public List<TpAccountBO> getTpAccounts() {
		return this.tpAccounts;
	}

	public void setTpAccounts(List<TpAccountBO> tpAccounts) {
		this.tpAccounts = tpAccounts;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public boolean isEnableShippingFromTsc() {
		return enableShippingFromTsc;
	}

	public void setEnableShippingFromTsc(boolean enableShippingFromTsc) {
		this.enableShippingFromTsc = enableShippingFromTsc;
	}

	@Override
	public String toString() {
		return "TradingPartnerBO [tpId=" + tpId + ", active=" + active
				+ ", ipoSecurityKey=" + ipoSecurityKey + ", tpName=" + tpName
				+ ", groupCode=" + groupCode + ", enableShippingFromTsc=" + enableShippingFromTsc + "]";
	}
	
}
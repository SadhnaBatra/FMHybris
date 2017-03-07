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
 * The persistent class for the TP_CARRIER_SHIPMTHD database table.
 * 
 */
/*@Entity
@Table(name = "TP_CARRIER_SHIPMTHD")
@NamedQueries({
	@NamedQuery(name = "findByTradingPartnerAndScacCode", 
				query = "from TradingPartnerShippingMethodBO ts " +
						"where ts.tradingPartner.tpId = :tpId " +
						"and upper(ts.scacCode) = upper(:scacCode) " +
						"and upper(ts.active) in :activeFlags"),
	@NamedQuery(name = "findByTpCarrierAndShipmethod", 
				query = "from TradingPartnerShippingMethodBO ts " +
						"where ts.tradingPartner.tpId = :tpId " +
						"and upper(ts.carrierCode) = upper(:carrierCode) " +
						"and upper(ts.shippingMethodCode) = upper(:shipMtdCode) " + 
						"and upper(ts.active) in :activeFlags")	
})*/
public class TradingPartnerShippingMethodBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TP_CARRIER_SHIPMTHD_TPCARRIERSHIPMTHDID_GENERATOR", sequenceName = "SEQ_TP_CARRIER_SHIPMTHD_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TP_CARRIER_SHIPMTHD_TPCARRIERSHIPMTHDID_GENERATOR")
	@Column(name = "TP_CARRIER_SHIPMTHD_ID")
	private long tpShippingMethodId;

	@Column(name = "ACTIV_FLG")
	@Type(type="yes_no")
	private boolean active;

	@Temporal(TemporalType.DATE)
	@Column(name = "INACTIV_FROM_DATE")
	private Date inactiveFromDate;

	@Column(name = "CARRIER_CD")
	private String carrierCode;

	@Column(name = "SCAC_CD")
	private String scacCode;

	@Column(name = "SHIP_MTHD_CD")
	private String shippingMethodCode;
	
	@Column(name = "TRNSPRT_MTHD_CD")
	private String transportationMethodCode;

	// bi-directional many-to-one association to CarrierShippingMethodBO
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CARRIER_SHIPMTHD_ID")
	private ShippingMethodBO shippingMethod;

	// bi-directional many-to-one association to TradingPartnerBO
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TP_ID")
	private TradingPartnerBO tradingPartner;

	public TradingPartnerShippingMethodBO() {
	}

	public long getTpShippingMethodId() {
		return tpShippingMethodId;
	}

	public void setTpShippingMethodId(long tpShippingMethodId) {
		this.tpShippingMethodId = tpShippingMethodId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getInactiveFromDate() {
		return inactiveFromDate;
	}

	public void setInactiveFromDate(Date inactiveFromDate) {
		this.inactiveFromDate = inactiveFromDate;
	}

	
	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getScacCode() {
		return scacCode;
	}

	public void setScacCode(String scacCode) {
		this.scacCode = scacCode;
	}

	public String getShippingMethodCode() {
		return shippingMethodCode;
	}

	public void setShippingMethodCode(String shippingMethodCode) {
		this.shippingMethodCode = shippingMethodCode;
	}

	public String getTransportationMethodCode() {
		return transportationMethodCode;
	}

	public void setTransportationMethodCode(String transportationMethodCode) {
		this.transportationMethodCode = transportationMethodCode;
	}

	public ShippingMethodBO getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethodBO shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public TradingPartnerBO getTradingPartner() {
		return this.tradingPartner;
	}

	public void setTradingPartner(TradingPartnerBO tradingPartner) {
		this.tradingPartner = tradingPartner;
	}

	@Override
	public String toString() {
		return "TradingPartnerShippingMethodBO [tpShippingMethodId="
				+ tpShippingMethodId + ", active=" + active + ", carrierCode="
				+ carrierCode + ", scacCode=" + scacCode
				+ ", shippingMethodCode=" + shippingMethodCode
				+ ", transportationMethodCode=" + transportationMethodCode
				+ "]";
	}

}
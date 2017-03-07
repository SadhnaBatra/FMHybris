package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.*;


/**
 * The persistent class for the CARRIER_SHIPMTHD_COUNTRY database table.
 * 
 */
/*//Entity
//Table(name="CARRIER_SHIPMTHD_COUNTRY")
//NamedQuery(name = "findByShippingMethodAndCountryCodes",
			query = "from ShippingMethodCountryBO smc " +
					"where smc.shippingMethodId = :shipMthdId " +
					"and upper(smc.fromIsoCountryCode) = upper(:fromCntryCode) " +
					"and upper(smc.toIsoCountryCode) = upper(:toCntryCode) " +
					"and upper(smc.active) in :activeFlags")
*/					
public class ShippingMethodCountryBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	//Id
	//SequenceGenerator(name="CARRIER_SHIPMTHD_COUNTRY_CARRIERSHIPMTHDCNTRYID_GENERATOR", sequenceName="SEQ_CARRIER_SHIPMTHD_CNTRY_ID")
	//GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CARRIER_SHIPMTHD_COUNTRY_CARRIERSHIPMTHDCNTRYID_GENERATOR")
	//Column(name="CARRIER_SHIPMTHD_CNTRY_ID")
	private long shippingMethodCountryId;

	//Column(name="ACTIV_FLG")
	//Type(type="yes_no")
	private boolean active;

	//Column(name="CARRIER_SHIPMTHD_ID")
	private long shippingMethodId;

	//Column(name="FROM_ISO_CNTRY_CD")
	private String fromIsoCountryCode;

    //Temporal( TemporalType.DATE)
	//Column(name="INACTIV_FROM_DATE")
	private Date inactiveFromDate;

	//Column(name="TO_ISO_CNTRY_CD")
	private String toIsoCountryCode;

    public ShippingMethodCountryBO() {
    }

	public long getShippingMethodCountryId() {
		return shippingMethodCountryId;
	}

	public void setShippingMethodCountryId(long shippingMethodCountryId) {
		this.shippingMethodCountryId = shippingMethodCountryId;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getShippingMethodId() {
		return shippingMethodId;
	}

	public void setShippingMethodId(long shippingMethodId) {
		this.shippingMethodId = shippingMethodId;
	}

	public String getFromIsoCntryCd() {
		return this.fromIsoCountryCode;
	}

	public void setFromIsoCntryCd(String fromIsoCntryCd) {
		this.fromIsoCountryCode = fromIsoCntryCd;
	}

	public Date getInactivFromDate() {
		return this.inactiveFromDate;
	}

	public void setInactivFromDate(Date inactivFromDate) {
		this.inactiveFromDate = inactivFromDate;
	}

	public String getToIsoCntryCd() {
		return this.toIsoCountryCode;
	}

	public void setToIsoCntryCd(String toIsoCntryCd) {
		this.toIsoCountryCode = toIsoCntryCd;
	}

	@Override
	public String toString() {
		return "ShippingMethodCountryBO [shippingMethodCountryId="
				+ shippingMethodCountryId + ", active=" + active
				+ ", shippingMethodId=" + shippingMethodId
				+ ", fromIsoCountryCode=" + fromIsoCountryCode + ", toIsoCountryCode="
				+ toIsoCountryCode + "]";
	}
	
}
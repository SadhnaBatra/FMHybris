package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.*;


/**
 * The persistent class for the SAP_SHIPPING_CODE database table.
 * 
 */
//Entity
//Table(name="SAP_SHIPPING_CODE")
public class SapShippingCodeBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	//EmbeddedId
	private SapShippingCodePK id;

	//Column(name="ACTIV_FLG")
	//Type(type="yes_no")
	private boolean active;

	//Column(name="CARRIER_CD")
	private String carrierCode;

    //Temporal( TemporalType.DATE)
	//Column(name="INACTIV_FROM_DATE")
	private Date inactiveFromDate;

	//Column(name="INCO_TERMS")
	private String incoTerms;

	private String route;

    public SapShippingCodeBO() {
    }

	public SapShippingCodePK getId() {
		return this.id;
	}

	public void setId(SapShippingCodePK id) {
		this.id = id;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public Date getInactiveFromDate() {
		return inactiveFromDate;
	}

	public void setInactiveFromDate(Date inactiveFromDate) {
		this.inactiveFromDate = inactiveFromDate;
	}

	public String getIncoTerms() {
		return this.incoTerms;
	}

	public void setIncoTerms(String incoTerms) {
		this.incoTerms = incoTerms;
	}

	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "SapShippingCodeBO [id=" + id + ", active=" + active
				+ ", carrierCode=" + carrierCode + ", incoTerms=" + incoTerms
				+ ", route=" + route + "]";
	}

}
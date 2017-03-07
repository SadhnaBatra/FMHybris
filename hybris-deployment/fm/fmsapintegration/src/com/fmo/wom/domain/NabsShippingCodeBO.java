package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.*;
//import org.hibernate.annotations.Type;


/**
 * The persistent class for the NABS_SHIPPING_CODE database table.
 * 
 */
//Entity
//Table(name="NABS_SHIPPING_CODE")
public class NabsShippingCodeBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	//EmbeddedId
	private NabsShippingCodePK id;

	//Column(name="ACTIV_FLG")
	//Type(type="yes_no")
	private boolean active;

	//Column(name="EMERG_SHIP_CD")
	private String emergencyShippingCode;

    //Temporal( TemporalType.DATE)
	//Column(name="INACTIV_FROM_DATE")
	private Date inactiveFromDate;

	//Column(name="NORMAL_SHIP_CD")
	private String normalShippingCode;

	//Column(name="STOCK_SHIP_CD")
	private String stockShippingCode;

    public NabsShippingCodeBO() {
    }

	public NabsShippingCodePK getId() {
		return this.id;
	}

	public void setId(NabsShippingCodePK id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmergencyShippingCode() {
		return emergencyShippingCode;
	}

	public void setEmergencyShippingCode(String emergencyShippingCode) {
		this.emergencyShippingCode = emergencyShippingCode;
	}

	public Date getInactiveFromDate() {
		return inactiveFromDate;
	}

	public void setInactiveFromDate(Date inactiveFromDate) {
		this.inactiveFromDate = inactiveFromDate;
	}

	public String getNormalShippingCode() {
		return normalShippingCode;
	}

	public void setNormalShippingCode(String normalShippingCode) {
		this.normalShippingCode = normalShippingCode;
	}

	public String getStockShippingCode() {
		return stockShippingCode;
	}

	public void setStockShippingCode(String stockShippingCode) {
		this.stockShippingCode = stockShippingCode;
	}

	@Override
	public String toString() {
		return "NabsShippingCodeBO [id=" + id + ", active=" + active
				+ ", emergencyShippingCode=" + emergencyShippingCode
				+ ", normalShippingCode=" + normalShippingCode
				+ ", stockShippingCode=" + stockShippingCode + "]";
	}
	
}
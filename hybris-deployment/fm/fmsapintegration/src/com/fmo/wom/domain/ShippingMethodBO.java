package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the CARRIER_SHIPPING_METHOD database table.
 * 
 */
/*//Entity
//Table(name="CARRIER_SHIPPING_METHOD")
//NamedQueries({
    //NamedQuery(name = "getStandardShippingMethod",
    		query = "from ShippingMethodBO sm where sm.shippingMethodDescription = 'Standard Shipping' and sm.carrier.carrierName = 'FM'")
})*/
public class ShippingMethodBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	//Id
	//SequenceGenerator(name="CARRIER_SHIPPING_METHOD_CARRIERSHIPMTHDID_GENERATOR", sequenceName="SEQ_CARRIER_SHIPMTHD_ID")
	//GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CARRIER_SHIPPING_METHOD_CARRIERSHIPMTHDID_GENERATOR")
	//Column(name="CARRIER_SHIPMTHD_ID")
	private long shippingMethodId;

	//Column(name="ACTIV_FLG")
	//Type(type="yes_no")
	private boolean active;

    //Temporal( TemporalType.DATE)
	//Column(name="INACTIV_FROM_DATE")
	private Date inactiveFromDate;

	//Column(name="SHIP_MTHD_DESC")
	private String shippingMethodDescription;
		
	//Column(name="SHIP_MTHD_CD")
	private String shippingMethodCode;

	//bi-directional many-to-one association to CarrierBO
	//ManyToOne()
	//JoinColumn(name="CARRIER_ID")
	private CarrierBO carrier;

	//Column(name = "TRNSPRT_MTHD_CD")
	private String transportationMethodCode;
	
	//Column(name="QUAL_FREE_FRGHT_FLG")
	//Type(type="yes_no")
	private boolean qualifiesForFreeFreight;
	
	public ShippingMethodBO() {
    }

	public boolean isQualifiesForFreeFreight() {
		return qualifiesForFreeFreight;
	}

	public void setQualifiesForFreeFreight(boolean qualifiesForFreeFreight) {
		this.qualifiesForFreeFreight = qualifiesForFreeFreight;
	}

	public long getShippingMethodId() {
		return shippingMethodId;
	}

	public void setShippingMethodId(long shippingMethodId) {
		this.shippingMethodId = shippingMethodId;
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

	public String getShippingMethodDescription() {
		return shippingMethodDescription;
	}

	public void setShippingMethodDescription(String shippingMethodDescription) {
		this.shippingMethodDescription = shippingMethodDescription;
	}

	@XmlTransient
	public CarrierBO getCarrier() {
		return this.carrier;
	}

	public void setCarrier(CarrierBO carrier) {
		this.carrier = carrier;
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

	@Override
	public String toString() {
		return "ShippingMethodBO [shippingMethodId=" + shippingMethodId
				+ ", active=" + active 
//				+ ", inactiveFromDate="	+ inactiveFromDate 
				+ ", shippingMethodDescription=" + shippingMethodDescription 
				+ ", shippingMethodCode=" + shippingMethodCode 
				+ ", carrier=" + carrier
				+ ", transportationMethodCode=" + transportationMethodCode
				+ ", qualifiesForFreeFreight=" + qualifiesForFreeFreight 
				+ "]";
	}


}
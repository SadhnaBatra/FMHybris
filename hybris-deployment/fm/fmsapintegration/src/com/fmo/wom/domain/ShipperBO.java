package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//import javax.persistence.*;


/**
 * The persistent class for the SHIPPER database table.
 * 
 */
//Entity
//Table(name="SHIPPER")
public class ShipperBO implements Serializable {
	private static final long serialVersionUID = 1L;

	//Id
	//Column(name="SHIPPER_ID")
	private long shipperId;

	//Column(name="ACTIV_FLG")
	//Type(type="yes_no")
	private boolean active;

	//OneToOne(fetch = FetchType.LAZY)
	//JoinColumn(name = "CARRIER_ID")
	private CarrierBO carrier;

    //Temporal( TemporalType.DATE)
	//Column(name="INACTIV_FROM_DATE")
	private Date inactivFromDate;

	//Column(name="SHIPPER_NAME")
	private String shipperName;

	//Column(name="SHIPPER_URL")
	private String shipperUrl;

	//bi-directional many-to-one association to ShipperDetail
	//OneToMany(mappedBy="shipper")
	private List<ShipperDetailBO> shipperDetails;

    public ShipperBO() {
    }

	public long getShipperId() {
		return this.shipperId;
	}

	public void setShipperId(long shipperId) {
		this.shipperId = shipperId;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CarrierBO getCarrier() {
		return carrier;
	}

	public void setCarrier(CarrierBO carrier) {
		this.carrier = carrier;
	}

	public Date getInactivFromDate() {
		return this.inactivFromDate;
	}

	public void setInactivFromDate(Date inactivFromDate) {
		this.inactivFromDate = inactivFromDate;
	}

	public String getShipperName() {
		return this.shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public String getShipperUrl() {
		return this.shipperUrl;
	}

	public void setShipperUrl(String shipperUrl) {
		this.shipperUrl = shipperUrl;
	}

	public List<ShipperDetailBO> getShipperDetails() {
		return this.shipperDetails;
	}

	public void setShipperDetails(List<ShipperDetailBO> shipperDetails) {
		this.shipperDetails = shipperDetails;
	}

	@Override
	public String toString() {
		return "ShipperBO [shipperId=" + shipperId + ", active=" + active
				+ ", carrier=" + carrier + ", inactivFromDate="
				+ inactivFromDate + ", shipperName=" + shipperName
				+ ", shipperUrl=" + shipperUrl + ", shipperDetails="
				+ shipperDetails + "]";
	}
	
}
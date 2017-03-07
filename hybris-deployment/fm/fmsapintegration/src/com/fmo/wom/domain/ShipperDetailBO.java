package com.fmo.wom.domain;

import java.io.Serializable;

//import javax.persistence.*;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.domain.enums.ExternalSystem;


/**
 * The persistent class for the SHIPPER_DTL database table.
 * 
 */
/*//Entity
//Table(name="SHIPPER_DTL")
//NamedQuery(name = "findByExtSysCodeAndShipperCode",
            query = "from ShipperDetailBO sd " +
            		"where upper(sd.externalSystemCode) = upper(:extSysCd) " +
            		"and upper(sd.externalSystemShipperCode) = upper(:extSysShipperCd) " +
            		"and upper(sd.shipper.active) = true")
            		*/
public class ShipperDetailBO implements Serializable {
	private static final long serialVersionUID = 1L;

	//Id
	//Column(name="SHIPPER_DTL_ID")
	private long shipperDetailId;

	//Column(name="EXTERNAL_SYSTEM_CD")
	private String externalSystemCode;

	//Column(name="EXTERNAL_SYSTEM_SHIPPER_CD")
	private String externalSystemShipperCode;

	//bi-directional many-to-one association to ShipperBO
    //ManyToOne
	//JoinColumn(name="SHIPPER_ID")
	private ShipperBO shipper;

    public ShipperDetailBO() {
    }

	public long getShipperDetailId() {
		return this.shipperDetailId;
	}

	public void setShipperDetailId(long shipperDetailId) {
		this.shipperDetailId = shipperDetailId;
	}

    public ExternalSystem getExternalSystem() {
        if (GenericValidator.isBlankOrNull(externalSystemCode)) {
            return null;
        }
        return ExternalSystem.getFromCode(externalSystemCode);
    }
    
    public void setExternalSystem(ExternalSystem anExternalSystem) {
        if (anExternalSystem == null) {
            externalSystemCode = null;
        } else {
            externalSystemCode = anExternalSystem.getCode();
        }
    }

	public String getExternalSystemShipperCode() {
		return this.externalSystemShipperCode;
	}

	public void setExternalSystemShipperCode(String externalSystemShipperCode) {
		this.externalSystemShipperCode = externalSystemShipperCode;
	}

	public ShipperBO getShipper() {
		return this.shipper;
	}

	public void setShipper(ShipperBO shipper) {
		this.shipper = shipper;
	}
	
}
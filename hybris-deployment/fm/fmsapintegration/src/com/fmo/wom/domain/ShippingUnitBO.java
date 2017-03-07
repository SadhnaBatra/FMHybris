/*
 * Created on Jun 2, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.fmo.wom.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fmo.wom.domain.enums.ExternalSystem;

/**
 * //author yangx017
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShippingUnitBO {

    private String scacCode;
	private String carrierName;
    
    private String carrierCode;
    private String transportationMethodCode;
    private String shippingMethodCode;
    
    private String trackingNumber;
    private Date shipDate;
    private String packingSlip;
    private DistributionCenterBO shipFrom;
    
    // move to enum?
    private String packingStatus;
    private String packingStatusDescription;
    List<ShippedItemBO> shippedItemsList;
    
    @XmlTransient
    private String trackingURL;
    
    @XmlTransient
    private ExternalSystem  externalSystmem;

    public ExternalSystem getExternalSystmem() {
		return externalSystmem;
	}
	public void setExternalSystmem(ExternalSystem externalSystmem) {
		this.externalSystmem = externalSystmem;
	}
	public String getScacCode() {
        return scacCode;
    }
    public void setScacCode(String scacCode) {
        this.scacCode = scacCode;
    }
    public String getCarrierCode() {
        return carrierCode;
    }
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }
    public String getCarrierName() {
        return carrierName;
    }
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }
    public String getTransportationMethodCode() {
        return transportationMethodCode;
    }
    public void setTransportationMethodCode(String transportationMethodCode) {
        this.transportationMethodCode = transportationMethodCode;
    }
    public String getShippingMethodCode() {
        return shippingMethodCode;
    }
    public void setShippingMethodCode(String shippingMethodCode) {
        this.shippingMethodCode = shippingMethodCode;
    }
    public String getTrackingNumber() {
        return trackingNumber;
    }
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    public Date getShipDate() {
        return shipDate;
    }
    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }
    public String getPackingSlip() {
        return packingSlip;
    }
    public void setPackingSlip(String packingSlip) {
        this.packingSlip = packingSlip;
    }
    public DistributionCenterBO getShipFrom() {
        return shipFrom;
    }
    public void setShipFrom(DistributionCenterBO shipFrom) {
        this.shipFrom = shipFrom;
    }
    public String getPackingStatus() {
        return packingStatus;
    }
    public void setPackingStatus(String packingStatus) {
        this.packingStatus = packingStatus;
    }
    public String getPackingStatusDescription() {
        return packingStatusDescription;
    }
    public void setPackingStatusDescription(String packingStatusDescription) {
        this.packingStatusDescription = packingStatusDescription;
    }
    public List<ShippedItemBO> getShippedItemsList() {
    	if(this.shippedItemsList == null) {
    		this.shippedItemsList = new ArrayList<ShippedItemBO>(0);
    	}
        return shippedItemsList;
    }
    public void setShippedItemsList(List<ShippedItemBO> shippedItemsList) {
        this.shippedItemsList = shippedItemsList;
    }
    @XmlElement
	public double getFreightCost() {
		return 0;
	}
    @XmlElement
	public double getTotalAmount() {
		return 0;
	}
    
    public String getTrackingURL() {
		return trackingURL;
	}
	public void setTrackingURL(String trackingURL) {
		this.trackingURL = trackingURL;
	}

	
	
    //Override
    /*public String toString() {
        return "ShippingUnitBO [scacCode=" + scacCode + ", carrierCode="
                + carrierCode + ", transportationMethodCode="
                + transportationMethodCode + ", shippingMethodCode="
                + shippingMethodCode + ", trackingNumber=" + trackingNumber
                + ", shipDate=" + shipDate + ", packingSlip=" + packingSlip
                + ", shipFrom=" + shipFrom + ", packingStatus=" + packingStatus
                + ", packingStatusDescription=" + packingStatusDescription
                + ", shippedItemsList=" + shippedItemsList + "]";
    }*/
   
    @Override
	public String toString() {
		return "ShippingUnitBO [scacCode=" + scacCode + ", carrierName="
				+ carrierName + ", carrierCode=" + carrierCode
				+ ", transportationMethodCode=" + transportationMethodCode
				+ ", shippingMethodCode=" + shippingMethodCode
				+ ", trackingNumber=" + trackingNumber + ", shipDate="
				+ shipDate + ", packingSlip=" + packingSlip + ", shipFrom="
				+ shipFrom + ", packingStatus=" + packingStatus
				+ ", packingStatusDescription=" + packingStatusDescription
				+ ", shippedItemsList=" + shippedItemsList + ", trackingURL="
				+ trackingURL + ", externalSystmem=" + externalSystmem + "]";
	}
    
	public void addShippedItem(ShippedItemBO shippedItem) {
        if (shippedItem == null) return;
            
        if (getShippedItemsList() == null) {
            shippedItemsList = new ArrayList<ShippedItemBO>();
        }
            
        shippedItemsList.add(shippedItem);
    }

	/* ORDER STATISTICS */
	//SuppressWarnings("unused")
	public int getTotalLines() {
		int totalLines = 0;
		if (shippedItemsList != null) {
			for (ShippedItemBO shippedItem : shippedItemsList) {
				totalLines += 1;
			}
		}
		return totalLines;
	}
	
	public int getLinesShipped() {
		int linesShipped = 0;
		if (shippedItemsList != null) {
			for (ShippedItemBO shippedItem : shippedItemsList) {
				if (shippedItem.getShippedQuantity() > 0) {
					linesShipped += 1;
				}
			}
		}
		return linesShipped;
	}

	public int getTotalPieces() {
		int totalPieces = 0;
		if (shippedItemsList != null) {
			for (ShippedItemBO shippedItem : shippedItemsList) {
				totalPieces += shippedItem.getOrderedQuantity();
			}
		}
		return totalPieces;
	}

	public int getTotalPiecesShipped() {
		int piecesShipped = 0;
		List<ShippedItemBO> shippedItemsList = getShippedItemsList();
		if (shippedItemsList != null) {
			for (ShippedItemBO shippedItem : shippedItemsList) {
				piecesShipped += shippedItem.getShippedQuantity();
			}
		}
		return piecesShipped;
	}
	
}

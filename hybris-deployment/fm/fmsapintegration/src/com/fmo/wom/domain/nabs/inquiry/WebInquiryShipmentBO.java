package com.fmo.wom.domain.nabs.inquiry;


import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (schema="DD09", name="WEB_INQ_SHIPMENT")
@AssociationOverrides( {
    @AssociationOverride(name = "id.trackingNumber",
                         joinColumns = @JoinColumn(name = "TRACKING_NBR")),
   @AssociationOverride(name = "id.trackingNumberTypeCode",
                       joinColumns = @JoinColumn(name = "TRACK_NBR_TYPE_CD")),
   @AssociationOverride(name = "id.parentWebInquiryOrderHeaderFK",
                        joinColumns = {
                            @JoinColumn(name = "fmo_mstr_ord_nbr",
                                        referencedColumnName = "FMO_MSTR_ORD_NBR"),
                            @JoinColumn(name = "order_nbr",
                                        referencedColumnName = "ORDER_NBR"),
                            @JoinColumn(name = "inquiry_key",
                                        referencedColumnName = "INQUIRY_KEY") })
})
public class WebInquiryShipmentBO {
  
    @EmbeddedId
    private WebInquiryShipmentPK id;

    @Column (name="CARRIER_ID")
    private String carrierID;
    
    @Column (name="SHIP_VIA_CODE")
    private String shipViaCode;
    
    @Column (name="SHIP_DATE")
    private String shipDate;

    public WebInquiryShipmentPK getId() {
        return id;
    }

    public void setId(WebInquiryShipmentPK id) {
        this.id = id;
    }

    public String getCarrierID() {
        return carrierID;
    }

    public void setCarrierID(String carrierID) {
        this.carrierID = carrierID;
    }

    public String getShipViaCode() {
        return shipViaCode;
    }

    public void setShipViaCode(String shipViaCode) {
        this.shipViaCode = shipViaCode;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }
    
    
}

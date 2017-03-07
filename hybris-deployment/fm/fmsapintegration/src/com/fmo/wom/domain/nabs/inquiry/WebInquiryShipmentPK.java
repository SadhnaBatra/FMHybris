package com.fmo.wom.domain.nabs.inquiry;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class WebInquiryShipmentPK implements Serializable {

    @ManyToOne
    WebInquiryOrderHeaderBO parentWebInquiryOrderHeaderFK;
    
    @Column (name="TRACKING_NBR")
    private String trackingNumber;
    
    @Column (name="TRACK_NBR_TYPE_CD")
    private String trackingNumberTypeCode;
    
    public WebInquiryOrderHeaderBO getParentWebInquiryOrderHeaderFK() {
        return parentWebInquiryOrderHeaderFK;
    }

    public void setParentWebInquiryOrderHeaderFK(
            WebInquiryOrderHeaderBO parentWebInquiryOrderHeaderFK) {
        this.parentWebInquiryOrderHeaderFK = parentWebInquiryOrderHeaderFK;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumberTypeCode() {
        return trackingNumberTypeCode;
    }

    public void setTrackingNumberTypeCode(String trackingNumberTypeCode) {
        this.trackingNumberTypeCode = trackingNumberTypeCode;
    }

    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(parentWebInquiryOrderHeaderFK);
        builder.append(trackingNumber);
        builder.append(trackingNumberTypeCode);
        return builder.toHashCode();
    }
    
    public int compareTo(Object obj) {
        int returnValue = -1;
        if (obj instanceof WebInquiryShipmentPK)
        {
            final WebInquiryShipmentPK other = (WebInquiryShipmentPK)obj;
            final CompareToBuilder builder = new CompareToBuilder();
            builder.append(parentWebInquiryOrderHeaderFK, other.parentWebInquiryOrderHeaderFK);
            builder.append(trackingNumber, other.trackingNumber);
            builder.append(trackingNumberTypeCode, other.trackingNumberTypeCode);
            returnValue = builder.toComparison();
        }
        return returnValue;
    }
    
    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }
    
}

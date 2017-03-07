package com.fmo.wom.domain.nabs.inquiry;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fmo.wom.domain.ApplicationUsagePK;
import com.fmo.wom.domain.nabs.DB2StringPK;
import com.fmo.wom.domain.nabs.OrderDetailPK;

@Embeddable
public class WebInquiryOrderHeaderPK extends DB2StringPK {


    private static final int MON_FIELD_SIZE = 15;
    private static final int ORDER_NUMBER_FIELD_SIZE = 10;
    
    @ManyToOne  
    WebInquiryBO parentWebInquiryFK;
    
    @Column (name="FMO_MSTR_ORD_NBR")
    private String masterOrderNumber;

    @Column (name="ORDER_NBR")
    private String orderNumber;

    public WebInquiryBO getParentWebInquiryFK() {
        return parentWebInquiryFK;
    }

    public void setParentWebInquiryFK(WebInquiryBO parentWebInquiryFK) {
        this.parentWebInquiryFK = parentWebInquiryFK;
    }

    public String getMasterOrderNumber() {
        if (masterOrderNumber != null) {
            this.masterOrderNumber = stringPad(masterOrderNumber, ' ', MON_FIELD_SIZE);
        }
        return masterOrderNumber;
    }

    public void setMasterOrderNumber(String masterOrderNumber) {
        this.masterOrderNumber = masterOrderNumber;
        if (masterOrderNumber != null) {
            this.masterOrderNumber = stringPad(masterOrderNumber, ' ', MON_FIELD_SIZE);
        }
    }

    public String getOrderNumber() {
        if (orderNumber != null) {
            this.orderNumber = stringPad(orderNumber, ' ', ORDER_NUMBER_FIELD_SIZE);
        }
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        if (orderNumber != null) {
            this.orderNumber = stringPad(orderNumber, ' ', ORDER_NUMBER_FIELD_SIZE);
        }
    }
    
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(parentWebInquiryFK);
        builder.append(masterOrderNumber);
        builder.append(orderNumber);
        return builder.toHashCode();
    }
    
    public int compareTo(Object obj) {
        int returnValue = -1;
        if (obj instanceof WebInquiryOrderHeaderPK)
        {
            final WebInquiryOrderHeaderPK other = (WebInquiryOrderHeaderPK)obj;
            final CompareToBuilder builder = new CompareToBuilder();
            builder.append(parentWebInquiryFK, other.parentWebInquiryFK);
            builder.append(masterOrderNumber, other.masterOrderNumber);
            builder.append(orderNumber, other.orderNumber);
            returnValue = builder.toComparison();
        }
        return returnValue;
    }
    
    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }
}

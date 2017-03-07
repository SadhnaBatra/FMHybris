package com.fmo.wom.domain.nabs.inquiry;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class WebInquiryKitComponentPK implements Serializable {

    @ManyToOne
    WebInquiryOrderDetailBO parentFK;

    @Column (name="COMP_SEQ_NBR")
    private int componentSequenceNumber;

    public WebInquiryOrderDetailBO getParentFK() {
        return parentFK;
    }

    public void setParentFK(WebInquiryOrderDetailBO parentFK) {
        this.parentFK = parentFK;
    }

    public int getComponentSequenceNumber() {
        return componentSequenceNumber;
    }

    public void setComponentSequenceNumber(int componentSequenceNumber) {
        this.componentSequenceNumber = componentSequenceNumber;
    }
    
}
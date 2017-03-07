package com.fmo.wom.domain.nabs.inquiry;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class WebInquiryOrderDetailPK implements Serializable {

    @ManyToOne
    WebInquiryOrderHeaderBO parentFK;

    @Column (name="ITEM_SEQ_NBR")
    private int itemSequenceNumber;

    public WebInquiryOrderHeaderBO getParentFK() {
        return parentFK;
    }

    public void setParentFK(WebInquiryOrderHeaderBO parentFK) {
        this.parentFK = parentFK;
    }

    
    public int getItemSequenceNumber() {
        return itemSequenceNumber;
    }

    public void setItemSequenceNumber(int itemSequenceNumber) {
        this.itemSequenceNumber = itemSequenceNumber;
    }
    
    
}

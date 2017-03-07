package com.fmo.wom.domain.nabs.inquiry;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class WebInquiryOrderCommentPK implements Serializable {

    @ManyToOne
    WebInquiryOrderHeaderBO parentOrderHeaderFK;

    @Column (name="SEQ_NBR")
    private int sequenceNumber;

    @Column (name="COMNT_TYPE_ID")
    private String commentTypeID;

    public WebInquiryOrderHeaderBO getParentOrderHeaderFK() {
        return parentOrderHeaderFK;
    }

    public void setParentOrderHeaderFK(WebInquiryOrderHeaderBO parentOrderHeaderFK) {
        this.parentOrderHeaderFK = parentOrderHeaderFK;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getCommentTypeID() {
        return commentTypeID;
    }

    public void setCommentTypeID(String commentTypeID) {
        this.commentTypeID = commentTypeID;
    }
}

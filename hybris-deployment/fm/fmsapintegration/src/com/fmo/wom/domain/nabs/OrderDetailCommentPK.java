package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderDetailCommentPK implements Serializable {

    @ManyToOne
    private OrderDetailBO orderDetailFK;
    
    @Column (name="COMNT_TYPE_ID")
    private String commentTypeId = OrderDetailCommentBO.NABS_PART_COMMENT_CODE;
    
    @Column (name="COMMENT_SEQ_NBR")
    private int commentSequenceNumber;

    public OrderDetailBO getOrderDetailFK() {
        return orderDetailFK;
    }

    public void setOrderDetailFK(OrderDetailBO orderDetailFK) {
        this.orderDetailFK = orderDetailFK;
    }

    public String getCommentTypeId() {
        return commentTypeId;
    }

    public void setCommentTypeId(String commentTypeId) {
        // this cannot be changed
        ;
    }

    public int getCommentSequenceNumber() {
        return commentSequenceNumber;
    }

    public void setCommentSequenceNumber(int commentSequenceNumber) {
        this.commentSequenceNumber = commentSequenceNumber;
    }
    
}

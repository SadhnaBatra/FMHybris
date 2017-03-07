package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderCommentPK implements Serializable {

    
    @ManyToOne
    private OrderHeaderBO orderHeaderFK;
    
    @Column (name="COMNT_TYPE_ID")
    private String commentTypeId = OrderCommentBO.NABS_ORDER_COMMENT_CODE;
    
    @Column (name="COMMENT_SEQ_NBR")
    private int commentSequenceNumber;

    public OrderHeaderBO getOrderHeaderFK() {
        return orderHeaderFK;
    }

    public void setOrderHeaderFK(OrderHeaderBO orderHeaderFK) {
        this.orderHeaderFK = orderHeaderFK;
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
    
    public boolean equals(Object object) {
        if (object instanceof OrderCommentPK) {
            OrderCommentPK pk = (OrderCommentPK) object;
            
            return orderHeaderFK.getId().equals(pk.getOrderHeaderFK()) && 
            commentSequenceNumber == pk.getCommentSequenceNumber() &&
            commentTypeId.equals(pk.getCommentTypeId());
        }
        return false;
    }
 
    public int hashCode() {
        int hash = 7;
        hash = (hash * 17) + (null == orderHeaderFK ? 0 : orderHeaderFK.hashCode());
        hash = (hash * 13) + (null == commentTypeId ? 0 : commentTypeId.hashCode());
        hash = (hash * 21) + commentSequenceNumber;
        return hash;
    }

	@Override
	public String toString() {
		return "OrderCommentPK [commentTypeId=" + commentTypeId
				+ ", commentSequenceNumber=" + commentSequenceNumber + "]";
	}
    
}

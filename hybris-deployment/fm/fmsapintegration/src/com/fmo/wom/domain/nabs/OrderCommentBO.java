package com.fmo.wom.domain.nabs;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


@Entity
@Table (schema="DD04", name="WOM_ORD_COMMENT")
@AssociationOverrides( {
    @AssociationOverride(name = "id.commentSequenceNumber", joinColumns = @JoinColumn(name = "COMMENT_SEQ_NBR")),
    @AssociationOverride(name = "id.orderHeaderFK",
                         joinColumns = @JoinColumn(name = "fmo_mstr_ord_nbr", referencedColumnName = "FMO_MSTR_ORD_NBR") )
})
public class OrderCommentBO  {

    protected static final String NABS_ORDER_COMMENT_CODE = "05";
    
    @Id
    private OrderCommentPK id;
    
    @Column(name="\"COMMENT\"") 
    private String comment = " ";

    public OrderCommentPK getId() {
        return id;
    }

    public void setId(OrderCommentPK id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

	@Override
	public String toString() {
		return "OrderCommentBO [id=" + id + ", comment=" + comment + "]";
	}
    
}

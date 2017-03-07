package com.fmo.wom.domain.nabs;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


@Entity
@Table (schema="DD04", name="WOM_DTL_COMMENT")
@AssociationOverrides( {
    @AssociationOverride(name = "id.commentSequenceNumber", joinColumns = @JoinColumn(name = "COMMENT_SEQ_NBR")),
    @AssociationOverride(name = "id.commentTypeId", joinColumns = @JoinColumn(name = "COMNT_TYPE_ID")),
    @AssociationOverride(name = "id.orderDetailFK",
                         joinColumns = { @JoinColumn(name = "fmo_mstr_ord_nbr", referencedColumnName = "FMO_MSTR_ORD_NBR"),
                                         @JoinColumn(name = "line_seq_nbr", referencedColumnName = "LINE_SEQ_NBR")} )
})
public class OrderDetailCommentBO {
    
    protected static final String NABS_PART_COMMENT_CODE  = "06";
    
    @Id
    private OrderDetailCommentPK id;
    
    @Column(name="\"COMMENT\"") 
    private String comment;

    public OrderDetailCommentPK getId() {
        return id;
    }

    public void setId(OrderDetailCommentPK id) {
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
		return "OrderDetailCommentBO [id=" + id + ", comment=" + comment + "]";
	}
    
}

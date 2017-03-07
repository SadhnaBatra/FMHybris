package com.fmo.wom.domain.nabs.inquiry;


import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table (schema="DD09", name="WEB_INQ_ORD_COMNT")
@AssociationOverrides( {
    @AssociationOverride(name = "id.sequenceNumber", joinColumns = @JoinColumn(name = "SEQ_NBR")),
    @AssociationOverride(name = "id.commentTypeID", joinColumns = @JoinColumn(name = "COMNT_TYPE_ID")),
    @AssociationOverride(name = "id.parentOrderHeaderFK",
                         joinColumns = {
                            @JoinColumn(name = "fmo_mstr_ord_nbr", referencedColumnName = "FMO_MSTR_ORD_NBR"),
                            @JoinColumn(name = "order_nbr", referencedColumnName = "ORDER_NBR"),
                            @JoinColumn(name = "inquiry_key", referencedColumnName = "INQUIRY_KEY")} )
})
public class WebInquiryOrderCommentBO {
   
    @EmbeddedId
    private WebInquiryOrderCommentPK id;
 
    @Column (name="COMMENT_TEXT")
    private String commentText;

    public WebInquiryOrderCommentPK getId() {
        return id;
    }

    public void setId(WebInquiryOrderCommentPK id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    
    
}
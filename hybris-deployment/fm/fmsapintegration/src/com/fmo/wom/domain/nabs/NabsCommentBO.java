package com.fmo.wom.domain.nabs;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class NabsCommentBO {

    @Column (name="COMNT_TYPE_ID")
    private String commentTypeId;
    
    @Column (name="COMMENT_SEQ_NBR")
    private int commentSequenceNumber;
    
    @Column (name="COMMENT")
    private String comment;

    public String getCommentTypeId() {
        return commentTypeId;
    }

    public void setCommentTypeId(String commentTypeId) {
        this.commentTypeId = commentTypeId;
    }

    public int getCommentSequenceNumber() {
        return commentSequenceNumber;
    }

    public void setCommentSequenceNumber(int commentSequenceNumber) {
        this.commentSequenceNumber = commentSequenceNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}

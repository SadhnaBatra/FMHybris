package com.fmo.wom.domain.nabs.upload;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (schema="WOM8", name="UPLOAD_ORDER_DATA")
@AssociationOverrides( {
    @AssociationOverride(name = "id.lineSeqNbr",
                         joinColumns = @JoinColumn(name = "LINE_SEQ_NBR")),
    @AssociationOverride(name = "id.parentOrderDataFK",
                         joinColumns = {
                            @JoinColumn(name = "trans_seq_id",
                                        referencedColumnName = "TRANS_SEQ_ID")
                                        })
})
@NamedQueries({
    @NamedQuery(name = "findUploadOrderDataByTransactionId",
            query = "from UploadOrderDataBO WHERE id.parentOrderDataFK.transactionId = :transactionId")
})
public class UploadOrderDataBO {

    @EmbeddedId
    private UploadOrderDataPK id;
		
	@Column (name="ORDER_DATA")
	private String data = " ";

    public UploadOrderDataPK getId() {
        return id;
    }

    public void setId(UploadOrderDataPK id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
	
}

package com.fmo.wom.domain.nabs.upload;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="com.fmo.wom.domain.nabs.upload.UploadOrderBO")
@Table (schema="WOM8", name="UPLOAD_ORDER")
public class UploadOrderBO {

    @Id
    @Column (name="TRANS_SEQ_ID")
    private String transactionId;
    
    @OneToMany(mappedBy = "id.parentOrderDataFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<UploadOrderDataBO> orderData;
   
    @Column (name="STATUS_CODE")
    private String status = " ";
    
    @Column (name="CUSTOMER_PO_NUMBER")
    private String customerPONumber = " ";
        
    @Column (name="BILL_TO_CD")
    private String billToAccountCd = " ";
       
    @Temporal( TemporalType.TIMESTAMP)
    @Column (name="CREATION_DATE")
    private Date creationDate = new Date(0);
      
    @Temporal( TemporalType.TIMESTAMP)
    @Column (name="RETRIEVAL_DATE")
    private Date retrievalDate = new Date(0);

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<UploadOrderDataBO> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<UploadOrderDataBO> orderData) {
        this.orderData = orderData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerPONumber() {
        return customerPONumber;
    }

    public void setCustomerPONumber(String customerPONumber) {
        if (customerPONumber == null || "".equals(customerPONumber)) {
            this.customerPONumber = " ";
        } else {
            this.customerPONumber = customerPONumber;
        }
    }

    public String getBillToAccountCd() {
        return billToAccountCd;
    }

    public void setBillToAccountCd(String billToAccountCd) {
        if (billToAccountCd == null || "".equals(billToAccountCd)) {
            this.billToAccountCd = " ";
        } else {
            this.billToAccountCd = billToAccountCd;
        }
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        if (creationDate == null) {
            this.creationDate = new Date(0);
        } else {
            this.creationDate = creationDate;
        }
    }

    public Date getRetrievalDate() {
        return retrievalDate;
    }

    public void setRetrievalDate(Date retrievalDate) {
        if (retrievalDate == null) {
            this.retrievalDate = new Date(0);
        } else {
           this.retrievalDate = retrievalDate;
        }
    }
	
    
}

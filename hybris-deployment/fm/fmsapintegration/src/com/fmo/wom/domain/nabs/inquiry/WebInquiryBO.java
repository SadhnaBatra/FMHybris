package com.fmo.wom.domain.nabs.inquiry;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (schema="DD09", name="WEB_INQUIRY")
public class WebInquiryBO {
  
    @EmbeddedId
    private WebInquiryPK id;

    @OneToMany(mappedBy = "id.parentWebInquiryFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<WebInquiryOrderHeaderBO> orderHeaderList;
    
    @Column (name="ADD_TS")
    //TIMESTAMP     NOT NULL WITH DEFAULT,     00210000
    private Date addTime;
    
    @Column (name="WOM_CUST_CODE")
    private String customerCode;
    
    @Column (name="WOM_RQST_TYPE_CD")
    private String requestTypeCode;
    
    @Column (name="PARM_MAST_ORD_NBR")
    private String masterOrderNumber;
    
    @Column (name="PARM_ORDER_NBR")
    private String orderNumber;
    
    @Column (name="PARM_CUST_PO")
    private String customerPONumber;
    
    @Column (name="PARM_ORD_START_DT")
    private String orderStartDate;
    
    @Column (name="PARM_ORD_END_DT")
    private String orderEndDate;
    
    @Column (name="PARM_BILL_TO_CUST")
    private String billToCustomerCode;
    
    @Column (name="PARM_SHIP_TO_CUST")
    private String shipToCustomerCode;
    
    @Column (name="PARM_PART_NBR")
    private String partNumber;
    
    @Column (name="PARM_PROD_FLG")
    private String productFlag;
    
    @Column (name="PARM_BRST")
    private String brandState;
    
    @Column (name="PARM_ORD_STATUS")
    private String orderStatus;
    
    @Column (name="TRANS_END_TS")
    //         TIMESTAMP     NOT NULL WITH DEFAULT,     00350000
    private Date transactionEndTime;
    
    @Column (name="ERROR_MSG")
    private String errorMessage;
    
    @Column (name="PARM_LOCATION")
    private String location;

    public WebInquiryPK getId() {
        return id;
    }

    public void setId(WebInquiryPK id) {
        this.id = id;
    }

    public List<WebInquiryOrderHeaderBO> getOrderHeaderList() {
        return orderHeaderList;
    }

    public void setOrderHeaderList(List<WebInquiryOrderHeaderBO> orderHeaderList) {
        this.orderHeaderList = orderHeaderList;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getRequestTypeCode() {
        return requestTypeCode;
    }

    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
    }

    public String getMasterOrderNumber() {
        return masterOrderNumber;
    }

    public void setMasterOrderNumber(String masterOrderNumber) {
        this.masterOrderNumber = masterOrderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerPONumber() {
        return customerPONumber;
    }

    public void setCustomerPONumber(String customerPONumber) {
        this.customerPONumber = customerPONumber;
    }

    public String getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(String orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    public String getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(String orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    public String getBillToCustomerCode() {
        return billToCustomerCode;
    }

    public void setBillToCustomerCode(String billToCustomerCode) {
        this.billToCustomerCode = billToCustomerCode;
    }

    public String getShipToCustomerCode() {
        return shipToCustomerCode;
    }

    public void setShipToCustomerCode(String shipToCustomerCode) {
        this.shipToCustomerCode = shipToCustomerCode;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getProductFlag() {
        return productFlag;
    }

    public void setProductFlag(String productFlag) {
        this.productFlag = productFlag;
    }

    public String getBrandState() {
        return brandState;
    }

    public void setBrandState(String brandState) {
        this.brandState = brandState;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getTransactionEndTime() {
        return transactionEndTime;
    }

    public void setTransactionEndTime(Date transactionEndTime) {
        this.transactionEndTime = transactionEndTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }   
    
    
}

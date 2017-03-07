package com.fmo.wom.domain.nabs.inquiry;

import java.util.Date;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (schema="DD09", name="WEB_INQ_ORD_HDR")
@AssociationOverrides( {
    @AssociationOverride(name = "id.masterOrderNumber", joinColumns = @JoinColumn(name = "FMO_MSTR_ORD_NBR")),
    @AssociationOverride(name = "id.orderNumber", joinColumns = @JoinColumn(name = "ORDER_NBR")),
    @AssociationOverride(name = "id.parentWebInquiryFK",
                         joinColumns =
                            @JoinColumn(name = "inquiry_key", referencedColumnName = "INQUIRY_KEY") )
})
public class WebInquiryOrderHeaderBO {
 
    @EmbeddedId
    private WebInquiryOrderHeaderPK id;
    
    @OneToMany(mappedBy = "id.parentWebInquiryOrderHeaderFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<WebInquiryShipmentBO> shipmentList;
    
    @OneToMany(mappedBy = "id.parentOrderHeaderFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<WebInquiryOrderCommentBO> orderCommentList;
   
    @OneToMany(mappedBy = "id.parentFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<WebInquiryOrderDetailBO> orderDetailList;
   
    @Column (name="SHIP_FROM_LOC_ID")
    private String shipFromLocationID;
    
    @Column (name="ORIG_ORD_DATE")
    //         DATE          NOT NULL WITH DEFAULT,     00730000
    private Date originalOrderDate;
    
    @Column (name="THIS_ORD_DATE")
    //         DATE          NOT NULL WITH DEFAULT,     00740000
    private Date orderDate;
    
    @Column (name="CUST_PO_NBR")
    private String customerPONumber;
    
    @Column (name="ORDER_TYPE")
    private String orderType;
    
    @Column (name="ORDER_STATUS")
    private String orderStatus;
    
    @Column (name="ENTRY_METHOD")
    private String entryMethod;
    
    @Column (name="BILL_TO_CUST")
    private String billToCustomerCode;
    
    @Column (name="SHIP_TO_CUST")
    private String shipToCustomerCode;
    
    @Column (name="TOT_LINES")
    //     DECIMAL(7,0)  NOT NULL WITH DEFAULT,     00820000
    private int totalLines;

    @Column (name="TOT_LINES_SHIPPED")
    //     DECIMAL(7,0)  NOT NULL WITH DEFAULT,     00820000
    private int totalLinesShipped;
    
    @Column (name="TOT_ORDER_QTY")
    //         DECIMAL(7,0)  NOT NULL WITH DEFAULT,     00830000
    private int totalOrderQuantity;
    
    @Column (name="TOT_SHIPPED_QTY")
    //       DECIMAL(7,0)  NOT NULL WITH DEFAULT,     00840000
    private int totalShippedQuantity;
    
    @Column (name="PARENT_ORDER_NBR")
    private String parentOrderNumber;
    
    @Column (name="SHIP_DATE")
    //             DATE          DEFAULT NULL,              00860000
    private Date shipDate;
    
    @Column (name="CARRIER_ID")
    private String carrierID;
    
    @Column (name="CARRIER_NAME")
    private String carrierName;
    
    @Column (name="SHIP_VIA_CODE")
    private String shipViaCode;
    
    @Column (name="SHIP_VIA_DESC")
    private String shipViaDescription;
    
    @Column (name="SHIPTO_NAME")
    private String shipToName;
    
    @Column (name="SHIPTO_ADDR1")
    private String shipToAddress1;
    
    @Column (name="SHIPTO_ADDR2")
    private String shipToAddress2;
    
    @Column (name="SHIPTO_CITY")
    private String shipToCity;
    
    @Column (name="SHIPTO_STATE")
    private String shipToState;
    
    @Column (name="SHIPTO_POSTAL_CODE")
    private String shipToPostalCode;
    
    @Column (name="SHIPTO_CNTRY_CODE")
    private String shipToCountryCode;
    
    @Column (name="SHIPTO_CNTRY_NAME")
    private String shipToCountryName;
    
    @Column (name="EMERGENCY_ORD_FLG")
    private String emergencyOrderFlag;
    
    @Column (name="ORDERED_BY")
    private String orderedBy;
    
    @Column (name="INVOICE_DATE")
    //          DATE          DEFAULT NULL,              01010000
    private Date invoiceDate;
    
    @Column (name="CANCELLED_DATE")
    //       DATE          DEFAULT NULL,              01020000
    private Date cancelledDate;
    
    @Column (name="INIT_BY_CUST_CODE")
    private String initByCustomerCode;
    
    @Column (name="WANT_DATE")
    //             DATE          DEFAULT NULL,              01040000
    private Date wantDate;
    
    // This appears to be a longer field than the other cust_po_nbr column for some reason
    @Column (name="CUSTOMER_PO_NBR")
    private String customerPONumberLong;

    public WebInquiryOrderHeaderPK getId() {
        return id;
    }

    public void setId(WebInquiryOrderHeaderPK id) {
        this.id = id;
    }
    
    public List<WebInquiryOrderDetailBO> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<WebInquiryOrderDetailBO> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
    public List<WebInquiryShipmentBO> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<WebInquiryShipmentBO> shipmentList) {
        this.shipmentList = shipmentList;
    }

    public List<WebInquiryOrderCommentBO> getOrderCommentList() {
        return orderCommentList;
    }

    public void setOrderCommentList(List<WebInquiryOrderCommentBO> orderCommentList) {
        this.orderCommentList = orderCommentList;
    }

    public String getShipFromLocationID() {
        return shipFromLocationID;
    }

    public void setShipFromLocationID(String shipFromLocationID) {
        this.shipFromLocationID = shipFromLocationID;
    }

    public Date getOriginalOrderDate() {
        return originalOrderDate;
    }

    public void setOriginalOrderDate(Date originalOrderDate) {
        this.originalOrderDate = originalOrderDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerPONumber() {
        return customerPONumber;
    }

    public void setCustomerPONumber(String customerPONumber) {
        this.customerPONumber = customerPONumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getEntryMethod() {
        return entryMethod;
    }

    public void setEntryMethod(String entryMethod) {
        this.entryMethod = entryMethod;
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

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public int getTotalLinesShipped() {
        return totalLinesShipped;
    }

    public void setTotalLinesShipped(int totalLinesShipped) {
        this.totalLinesShipped = totalLinesShipped;
    }

    public int getTotalOrderQuantity() {
        return totalOrderQuantity;
    }

    public void setTotalOrderQuantity(int totalOrderQuantity) {
        this.totalOrderQuantity = totalOrderQuantity;
    }

    public int getTotalShippedQuantity() {
        return totalShippedQuantity;
    }

    public void setTotalShippedQuantity(int totalShippedQuantity) {
        this.totalShippedQuantity = totalShippedQuantity;
    }

    public String getParentOrderNumber() {
        return parentOrderNumber;
    }

    public void setParentOrderNumber(String parentOrderNumber) {
        this.parentOrderNumber = parentOrderNumber;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getCarrierID() {
        return carrierID;
    }

    public void setCarrierID(String carrierID) {
        this.carrierID = carrierID;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getShipViaCode() {
        return shipViaCode;
    }

    public void setShipViaCode(String shipViaCode) {
        this.shipViaCode = shipViaCode;
    }

    public String getShipViaDescription() {
        return shipViaDescription;
    }

    public void setShipViaDescription(String shipViaDescription) {
        this.shipViaDescription = shipViaDescription;
    }

    public String getShipToName() {
        return shipToName;
    }

    public void setShipToName(String shipToName) {
        this.shipToName = shipToName;
    }

    public String getShipToAddress1() {
        return shipToAddress1;
    }

    public void setShipToAddress1(String shipToAddress1) {
        this.shipToAddress1 = shipToAddress1;
    }

    public String getShipToAddress2() {
        return shipToAddress2;
    }

    public void setShipToAddress2(String shipToAddress2) {
        this.shipToAddress2 = shipToAddress2;
    }

    public String getShipToCity() {
        return shipToCity;
    }

    public void setShipToCity(String shipToCity) {
        this.shipToCity = shipToCity;
    }

    public String getShipToState() {
        return shipToState;
    }

    public void setShipToState(String shipToState) {
        this.shipToState = shipToState;
    }

    public String getShipToPostalCode() {
        return shipToPostalCode;
    }

    public void setShipToPostalCode(String shipToPostalCode) {
        this.shipToPostalCode = shipToPostalCode;
    }

    public String getShipToCountryCode() {
        return shipToCountryCode;
    }

    public void setShipToCountryCode(String shipToCountryCode) {
        this.shipToCountryCode = shipToCountryCode;
    }

    public String getShipToCountryName() {
        return shipToCountryName;
    }

    public void setShipToCountryName(String shipToCountryName) {
        this.shipToCountryName = shipToCountryName;
    }

    public String getEmergencyOrderFlag() {
        return emergencyOrderFlag;
    }

    public boolean isEmergency() {
       return "Y".equals(getEmergencyOrderFlag());
    }
    
    public void setEmergencyOrderFlag(String emergencyOrderFlag) {
        this.emergencyOrderFlag = emergencyOrderFlag;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public String getInitByCustomerCode() {
        return initByCustomerCode;
    }

    public void setInitByCustomerCode(String initByCustomerCode) {
        this.initByCustomerCode = initByCustomerCode;
    }

    public Date getWantDate() {
        return wantDate;
    }

    public void setWantDate(Date wantDate) {
        this.wantDate = wantDate;
    }

    public String getCustomerPONumberLong() {
        return customerPONumberLong;
    }

    public void setCustomerPONumberLong(String customerPONumberLong) {
        this.customerPONumberLong = customerPONumberLong;
    }

}


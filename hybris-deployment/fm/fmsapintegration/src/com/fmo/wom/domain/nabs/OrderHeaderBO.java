package com.fmo.wom.domain.nabs;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (schema="DD04", name="WOM_ORD_HDR")
@NamedQueries({
    @NamedQuery(name = "findErrorMessage",
                query = "select errorMessage from OrderHeaderBO oh where oh.id.masterOrderNumber = :masterOrderNumber")
})
public class OrderHeaderBO {

    @Id
    private OrderHeaderPK id;
    
    @OneToMany(mappedBy = "id.parentOrderHeaderFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<OrderDetailBO> orderDetails;
    
    @OneToOne(mappedBy = "orderHeader", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    private OrderCreditCardBO orderCreditCard;
    
    @OneToMany(mappedBy = "id.orderHeaderFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<OrderCommentBO> orderComments;
 
    @Column (name="USER_GUID")
    private String userGuid = " ";
    
    @Column (name="INIT_BY_CUST")
    private String initByCustomer = " ";
    
    @Column (name="BILL_TO_CUST")
    private String billToCustomer = " ";
    
    @Column (name="SHIP_TO_CUST")
    private String shipToCustomer = " ";
    
    @Column (name="HOME_LOC_ID")
    private String homeLocationId = " ";
    
    @Column (name="PO_NBR")
    private String poNumber = " ";
    
    @Column (name="FREE_FRGHT_FLG")
    private String freeFreightFlag = " ";
    
    @Column (name="BACKORDER_TYPE")
    private String backOrderType = " ";
    
    @Column (name="WOM_STATUS_CD")
    private String womStatusCode = " ";
    
    @Column (name="WOM_STATUS_CD_TS")
    private Date womStatusCodeTimestamp = new Date();
    
    @Column (name="HOST_STATUS_CD")
    private String hostStatusCode = " ";
    
    @Column (name="HOST_STATUS_CD_TS")
    private Date hostStatusCodeTimestamp = new Date();
    
    @Column (name="WANT_DATE")
    private String wantDate = " ";
    
    @Column (name="EMERG_FLG")
    private String emergencyFlag = " ";
    
    @Column (name="ORDERED_BY_NAME")
    private String orderedByName = " ";
    
    @Column (name="SHIPTO_NAME")
    private String shipToName = " ";
    
    @Column (name="SHIPTO_ADDR1")
    private String shipToAddress1 = " ";
    
    @Column (name="SHIPTO_ADDR2")
    private String shipToAddress2 = " ";
    
    @Column (name="SHIPTO_CITY")
    private String shipToCity = " ";
    
    @Column (name="SHIPTO_STATE")
    private String shipToState = " ";
    
    @Column (name="SHIPTO_POSTAL_CD")
    private String shipToPostalCode = " ";
    
    @Column (name="SHIPTO_CNTRY_ISO")
    private String shipToCountryISO = " ";
    
    @Column (name="CANCEL_REASON_CD")
    private String cancelReasonCode = " ";
    
    @Column (name="ERROR_MSG")
    private String errorMessage = " ";
   
    public OrderHeaderPK getId() {
        return id;
    }

    public void setId(OrderHeaderPK id) {
        this.id = id;
    }

    public List<OrderDetailBO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailBO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderCreditCardBO getOrderCreditCard() {
        return orderCreditCard;
    }

    public void setOrderCreditCard(OrderCreditCardBO orderCreditCard) {
        this.orderCreditCard = orderCreditCard;
    }

    public List<OrderCommentBO> getOrderComments() {
        return orderComments;
    }

    public void setOrderComments(List<OrderCommentBO> orderComments) {
        this.orderComments = orderComments;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getInitByCustomer() {
        return initByCustomer;
    }

    public void setInitByCustomer(String initByCustomer) {
        this.initByCustomer = initByCustomer;
    }

    public String getBillToCustomer() {
        return billToCustomer;
    }

    public void setBillToCustomer(String billToCustomer) {
        this.billToCustomer = billToCustomer;
    }

    public String getShipToCustomer() {
        return shipToCustomer;
    }

    public void setShipToCustomer(String shipToCustomer) {
        this.shipToCustomer = shipToCustomer;
    }

    public String getHomeLocationId() {
        return homeLocationId;
    }

    public void setHomeLocationId(String homeLocationId) {
        this.homeLocationId = homeLocationId;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getFreeFreightFlag() {
        return freeFreightFlag;
    }

    public void setFreeFreightFlag(String freeFreightFlag) {
        this.freeFreightFlag = freeFreightFlag;
    }

    public String getBackOrderType() {
        return backOrderType;
    }

    public void setBackOrderType(String backOrderType) {
        this.backOrderType = backOrderType;
    }

    public String getWomStatusCode() {
        return womStatusCode;
    }

    public void setWomStatusCode(String womStatusCode) {
        this.womStatusCode = womStatusCode;
    }

    public Date getWomStatusCodeTimestamp() {
        return womStatusCodeTimestamp;
    }

    public void setWomStatusCodeTimestamp(Date womStatusCodeTimestamp) {
        this.womStatusCodeTimestamp = womStatusCodeTimestamp;
    }

    public String getHostStatusCode() {
        return hostStatusCode;
    }

    public void setHostStatusCode(String hostStatusCode) {
        this.hostStatusCode = hostStatusCode;
    }

    public Date getHostStatusCodeTimestamp() {
        return hostStatusCodeTimestamp;
    }

    public void setHostStatusCodeTimestamp(Date hostStatusCodeTimestamp) {
        this.hostStatusCodeTimestamp = hostStatusCodeTimestamp;
    }

    public String getWantDate() {
        return wantDate;
    }

    public void setWantDate(String wantDate) {
        this.wantDate = wantDate;
    }

    public String getEmergencyFlag() {
        return emergencyFlag;
    }

    public void setEmergencyFlag(String emergencyFlag) {
        this.emergencyFlag = emergencyFlag;
    }

    public String getOrderedByName() {
        return orderedByName;
    }

    public void setOrderedByName(String orderedByName) {
        if (orderedByName == null || "".equals(orderedByName)) {
            this.orderedByName = " ";
        } else {
            this.orderedByName = orderedByName;
        }
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
        this.shipToAddress1 = shipToAddress1 != null ? shipToAddress1 : " ";
    }

    public String getShipToAddress2() {
        return shipToAddress2;
    }

    public void setShipToAddress2(String shipToAddress2) {
        this.shipToAddress2 = shipToAddress2 != null ? shipToAddress2 : " ";
    }

    public String getShipToCity() {
        return shipToCity;
    }

    public void setShipToCity(String shipToCity) {
        this.shipToCity = shipToCity != null ? shipToCity : " ";
    }

    public String getShipToState() {
        return shipToState;
    }

    public void setShipToState(String shipToState) {
        this.shipToState = shipToState != null ? shipToState : " ";
    }

    public String getShipToPostalCode() {
        return shipToPostalCode;
    }

    public void setShipToPostalCode(String shipToPostalCode) {
        this.shipToPostalCode = shipToPostalCode != null ? shipToPostalCode : " ";
    }

    public String getShipToCountryISO() {
        return shipToCountryISO;
    }

    public void setShipToCountryISO(String shipToCountryISO) {
        if (shipToCountryISO != null && shipToCountryISO.length() > 3) {
            // trim it
            shipToCountryISO = shipToCountryISO.substring(0, 3);
        }
        this.shipToCountryISO = shipToCountryISO;
    }

    public String getCancelReasonCode() {
        return cancelReasonCode;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        this.cancelReasonCode = cancelReasonCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean equals(Object object) {
        if (object instanceof OrderHeaderBO) {
            OrderHeaderBO pk = (OrderHeaderBO) object;
            
            return getId().equals(pk.getId()); 
           
        }
        return false;
    }

	@Override
	public String toString() {
		return "OrderHeaderBO [id=" + id + ", orderDetails=" + orderDetails
				+ ", orderCreditCard=" + orderCreditCard + ", orderComments="
				+ orderComments + ", userGuid=" + userGuid
				+ ", initByCustomer=" + initByCustomer + ", billToCustomer="
				+ billToCustomer + ", shipToCustomer=" + shipToCustomer
				+ ", homeLocationId=" + homeLocationId + ", poNumber="
				+ poNumber + ", freeFreightFlag=" + freeFreightFlag
				+ ", backOrderType=" + backOrderType + ", womStatusCode="
				+ womStatusCode + ", womStatusCodeTimestamp="
				+ womStatusCodeTimestamp + ", hostStatusCode=" + hostStatusCode
				+ ", hostStatusCodeTimestamp=" + hostStatusCodeTimestamp
				+ ", wantDate=" + wantDate + ", emergencyFlag=" + emergencyFlag
				+ ", orderedByName=" + orderedByName + ", shipToName="
				+ shipToName + ", shipToAddress1=" + shipToAddress1
				+ ", shipToAddress2=" + shipToAddress2 + ", shipToCity="
				+ shipToCity + ", shipToState=" + shipToState
				+ ", shipToPostalCode=" + shipToPostalCode
				+ ", shipToCountryISO=" + shipToCountryISO
				+ ", cancelReasonCode=" + cancelReasonCode + ", errorMessage="
				+ errorMessage + "]";
	}
 
}

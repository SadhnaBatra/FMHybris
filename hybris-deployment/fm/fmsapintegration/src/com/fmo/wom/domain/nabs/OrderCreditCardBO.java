package com.fmo.wom.domain.nabs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table (schema="DD04", name="WOM_CRED_CARD")
public class OrderCreditCardBO {
    
    // This is a little bizarre but is to populate the id of this entity
    // via the foreign key object as the PK and FK for this guy are the
    // same.
    @GenericGenerator(name = "monGen", strategy = "foreign", 
            parameters = @Parameter(name = "property", value = "orderHeader"))
    @Id
    @GeneratedValue(generator = "monGen")
    @Column (name="FMO_MSTR_ORD_NBR", unique = true, nullable = false)
    private OrderHeaderPK orderHeaderPK;
    
    @OneToOne
    @PrimaryKeyJoinColumn
    private OrderHeaderBO orderHeader;
    
    @Column (name="CRED_CARD_TYPE_CD")
    private String creditCardTypeCode = " ";
    
    @Column (name="ACCT_NBR")
    private String accountNumber = " ";;
    
    @Column (name="EXPIRE_MMYY")
    private String expireMMYY = " ";;

    public OrderHeaderPK getOrderHeaderPK() {
        return orderHeaderPK;
    }

    public void setOrderHeaderPK(OrderHeaderPK orderHeaderPK) {
        this.orderHeaderPK = orderHeaderPK;
    }

    public OrderHeaderBO getOrderHeader() {
        return orderHeader;
    }

    public void setOrderHeader(OrderHeaderBO orderHeader) {
        this.orderHeader = orderHeader;
    }

    public String getCreditCardTypeCode() {
        return creditCardTypeCode;
    }

    public void setCreditCardTypeCode(String creditCardTypeCode) {
        this.creditCardTypeCode = creditCardTypeCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getExpireMMYY() {
        return expireMMYY;
    }

    public void setExpireMMYY(String expireMMYY) {
        this.expireMMYY = expireMMYY;
    }
    

}

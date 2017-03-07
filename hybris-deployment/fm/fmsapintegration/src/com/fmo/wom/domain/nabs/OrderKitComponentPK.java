package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Embeddable
public class OrderKitComponentPK implements Serializable {
    
    @ManyToOne(fetch=FetchType.LAZY)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name = "ORD_DTL_ID")
    OrderDetailBO orderDetailFK;

    @Column (name="COMP_SEQ_NBR")
    private int componentSequenceNumber;
    
    public OrderDetailBO getOrderDetailFK() {
        return orderDetailFK;
    }

    public void setOrderDetailFK(OrderDetailBO orderDetailFK) {
        this.orderDetailFK = orderDetailFK;
    }
    
    public int getComponentSequenceNumber() {
        return componentSequenceNumber;
    }

    public void setComponentSequenceNumber(int componentSequenceNumber) {
        this.componentSequenceNumber = componentSequenceNumber;
    }

    public boolean equals(Object object) {
        if (object instanceof OrderKitComponentPK) {
            OrderKitComponentPK pk = (OrderKitComponentPK) object;
            
            return componentSequenceNumber == pk.getComponentSequenceNumber() &&
                   orderDetailFK.getId().equals(pk.getOrderDetailFK().getId());
        }
        return false;
    }
 
    public int hashCode() {
        int hash = 7;
        hash = (hash * 17) + (null == orderDetailFK ? 0 : orderDetailFK.hashCode());
        hash = (hash * 21) + componentSequenceNumber;
        return hash;
    }
    
}

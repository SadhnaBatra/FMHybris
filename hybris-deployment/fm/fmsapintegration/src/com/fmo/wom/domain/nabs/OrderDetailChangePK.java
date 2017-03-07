package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderDetailChangePK implements Serializable {
    
    @ManyToOne
    OrderDetailBO orderDetailFK;

    public OrderDetailBO getOrderDetailFK() {
        return orderDetailFK;
    }

    public void setOrderDetailFK(OrderDetailBO orderDetailFK) {
        this.orderDetailFK = orderDetailFK;
    }

    public boolean equals(Object object) {
        if (object instanceof OrderDetailChangePK) {
            OrderDetailChangePK pk = (OrderDetailChangePK) object;
            
            return orderDetailFK.getId().equals(pk.getOrderDetailFK().getId());
        }
        return false;
    }
 
    public int hashCode() {
        int hash = 7;
        hash = (hash * 17) + (null == orderDetailFK ? 0 : orderDetailFK.hashCode());
        return hash;
    }
    
}

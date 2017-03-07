package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderKitComponentChangePK implements Serializable {
    
    @ManyToOne
    private OrderKitComponentBO orderKitComponentFK;

    public OrderKitComponentBO getOrderKitComponentFK() {
        return orderKitComponentFK;
    }

    public void setOrderKitComponentFK(OrderKitComponentBO orderKitComponentFK) {
        this.orderKitComponentFK = orderKitComponentFK;
    }

    public boolean equals(Object object) {
        if (object instanceof OrderDetailChangePK) {
            OrderDetailChangePK pk = (OrderDetailChangePK) object;
            
            return orderKitComponentFK.getId().equals(pk.getOrderDetailFK().getId());
        }
        return false;
    }
 
    public int hashCode() {
        int hash = 7;
        hash = (hash * 17) + (null == orderKitComponentFK ? 0 : orderKitComponentFK.hashCode());
        return hash;
    }
    
}

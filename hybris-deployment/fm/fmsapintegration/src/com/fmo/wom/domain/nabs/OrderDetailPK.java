package com.fmo.wom.domain.nabs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderDetailPK implements Serializable {

    @ManyToOne  
    OrderHeaderBO parentOrderHeaderFK;
    
    @Column (name="LINE_SEQ_NBR")
    private int lineSequenceNumber;

    public OrderHeaderBO getParentOrderHeaderFK() {
        return parentOrderHeaderFK;
    }

    public void setParentOrderHeaderFK(OrderHeaderBO parentOrderHeaderFK) {
        this.parentOrderHeaderFK = parentOrderHeaderFK;
    }

    public String getMasterOrderNumber() {
        return parentOrderHeaderFK.getId().getMasterOrderNumber();
    }

    public int getLineSequenceNumber() {
        return lineSequenceNumber;
    }

    public void setLineSequenceNumber(int lineSequenceNumber) {
        this.lineSequenceNumber = lineSequenceNumber;
    }
    
    public boolean equals(Object object) {
        if (object instanceof OrderDetailPK) {
            OrderDetailPK pk = (OrderDetailPK) object;
            
            return parentOrderHeaderFK.getId().equals(pk.getParentOrderHeaderFK().getId()) && 
                lineSequenceNumber == pk.lineSequenceNumber;
        }
        return false;
    }
 
    public int hashCode() {
        int hash = 7;
        hash = (hash * 17) + (null == parentOrderHeaderFK ? 0 : parentOrderHeaderFK.hashCode());
        hash = (hash * 21) + lineSequenceNumber;
        return hash;
    }

	@Override
	public String toString() {
		return "OrderDetailPK [lineSequenceNumber=" + lineSequenceNumber + "]";
	}
    
}

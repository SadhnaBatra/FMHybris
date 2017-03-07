package com.fmo.wom.domain.nabs;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderHeaderPK extends DB2StringPK {

    private static final int FIELD_SIZE = 20;
    
    @Column (name="FMO_MSTR_ORD_NBR")
    private String masterOrderNumber;
    
    public String getMasterOrderNumber() {
        if (masterOrderNumber != null) {
            this.masterOrderNumber = stringPad(masterOrderNumber, ' ', FIELD_SIZE);
        }
        return masterOrderNumber;
    }

    public void setMasterOrderNumber(String masterOrderNumber) {
        this.masterOrderNumber = masterOrderNumber;
        if (masterOrderNumber != null) {
            this.masterOrderNumber = stringPad(masterOrderNumber, ' ', FIELD_SIZE);
        }
    }
    
    public boolean equals(Object object) {
        if (object instanceof OrderHeaderPK) {
            OrderHeaderPK pk = (OrderHeaderPK) object;
            
            return getMasterOrderNumber().equals(pk.getMasterOrderNumber());
        }
        return false;
    }
 
    public int hashCode() {
        int hash = 7;
        hash = (hash * 17) + (null == getMasterOrderNumber() ? 0 : getMasterOrderNumber().hashCode());
        return hash;
    }

	@Override
	public String toString() {
		return "OrderHeaderPK [masterOrderNumber=" + masterOrderNumber + "]";
	}
    
}

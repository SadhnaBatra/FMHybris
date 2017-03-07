package com.fmo.wom.domain.nabs;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table (name="INVENTORY_LOCATION", schema="WOM8")
public class InventoryLocationBO {

	@EmbeddedId
	private InventoryLocationPK id;
	
	@Column (name="MSTR_ORD_NBR")
    private String masterOrderNbr;

	@Column (name="AVAIL_QTY")
	private long availQty;
	
	@Column (name="CAN_STOCK_FLG")
	@Type(type="yes_no")
	private boolean canStock;
	
	@Column (name="HOME_INVNT_LOC_FLG")
    @Type(type="yes_no")
    private boolean homeInventoryLocation;
    
	public InventoryLocationBO() {
	}

	public InventoryLocationPK getId() {
		return id;
	}

	public void setId(InventoryLocationPK id) {
		this.id = id;
	}

    public String getMasterOrderNbr() {
        return masterOrderNbr;
    }

    public void setMasterOrderNbr(String masterOrderNbr) {
        this.masterOrderNbr = masterOrderNbr;
    }

	public long getAvailQty() {
		return availQty;
	}

	public void setAvailQty(long availQty) {
		this.availQty = availQty;
	}

	public boolean isCanStock() {
		return canStock;
	}

	public void setCanStock(boolean canStock) {
		this.canStock = canStock;
	}
	
	public boolean isHomeInventoryLocation() {
        return homeInventoryLocation;
    }

	public void setHomeInventoryLocation(boolean homeInventoryLocation) {
        this.homeInventoryLocation = homeInventoryLocation;
    }

    
}

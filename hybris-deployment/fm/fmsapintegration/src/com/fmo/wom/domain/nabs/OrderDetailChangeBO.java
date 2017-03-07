package com.fmo.wom.domain.nabs;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (schema="DD04", name="WOM_ORD_DTL_CHNG")
@AssociationOverrides( {
   @AssociationOverride(name = "id.orderDetailFK",
                         joinColumns = {
                            @JoinColumn(name = "fmo_mstr_ord_nbr", referencedColumnName = "FMO_MSTR_ORD_NBR"),
                            @JoinColumn(name = "line_seq_nbr", referencedColumnName = "LINE_SEQ_NBR") })
})
@NamedQueries({
    @NamedQuery(name = "resetChangeStatusCode",
            query = "update OrderDetailChangeBO set changeStatus = '00' WHERE id.orderDetailFK.id.parentOrderHeaderFK.id.masterOrderNumber = :masterOrderNumber")
})
public class OrderDetailChangeBO {
    
    @Id
    private OrderDetailChangePK id;
    
    @Column (name="SHIP_LOC_ID")
    private String shipLocationId = " ";
    
    @Column (name="ORDER_QTY")
    private int orderQuantity;
    
    @Column (name="ASSIGN_QTY")
    private int assignQuantity;
    
    @Column (name="ALT_SHIP_LOC_ID")
    private String altShipLocationId = " ";
    
    @Column (name="ADD_OR_REMOVE_IND")
    private String addOrRemoveIndicator = " ";
    
    @Column (name="CHANGE_STATUS")
    private String changeStatus = " ";
    
    @Column (name="ERROR_MSG")
    private String errorMessage = " ";

    public OrderDetailChangePK getId() {
        return id;
    }

    public void setId(OrderDetailChangePK id) {
        this.id = id;
    }

    public String getShipLocationId() {
        return shipLocationId;
    }

    public void setShipLocationId(String shipLocationId) {
        this.shipLocationId = shipLocationId;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getAssignQuantity() {
        return assignQuantity;
    }

    public void setAssignQuantity(int assignQuantity) {
        this.assignQuantity = assignQuantity;
    }

    public String getAltShipLocationId() {
        return altShipLocationId;
    }

    public void setAltShipLocationId(String altShipLocationId) {
        this.altShipLocationId = altShipLocationId;
    }

    public String getAddOrRemoveIndicator() {
        return addOrRemoveIndicator;
    }

    public void setAddOrRemoveIndicator(String addOrRemoveIndicator) {
        this.addOrRemoveIndicator = addOrRemoveIndicator;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

	@Override
	public String toString() {
		return "OrderDetailChangeBO [id="+id+", shipLocationId="
				+ shipLocationId + ", orderQuantity=" + orderQuantity
				+ ", assignQuantity=" + assignQuantity + ", altShipLocationId="
				+ altShipLocationId + ", addOrRemoveIndicator="
				+ addOrRemoveIndicator + ", changeStatus=" + changeStatus
				+ ", errorMessage=" + errorMessage + "]";
	}
    
}
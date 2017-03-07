package com.fmo.wom.domain.nabs;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table (schema="DD04", name="WOM_KIT_COMP_CHNG")
@AssociationOverrides( {
   @AssociationOverride(name = "id.orderKitComponentFK",
                         joinColumns = {
                            @JoinColumn(name = "fmo_mstr_ord_nbr", referencedColumnName = "FMO_MSTR_ORD_NBR"),
                            @JoinColumn(name = "comp_seq_nbr", referencedColumnName = "COMP_SEQ_NBR"),
                            @JoinColumn(name = "line_seq_nbr", referencedColumnName = "LINE_SEQ_NBR") })
})
//@NamedQueries({
//    @NamedQuery(name = "resetChangeStatusCode",
//            query = "update OrderDetailChangeBO set changeStatus = '00' WHERE id.orderDetailFK.id.parentOrderHeaderFK.id.masterOrderNumber = :masterOrderNumber")
//})
public class OrderKitComponentChangeBO {
    
    @Id
    private OrderKitComponentChangePK id;
    
    @Column (name="ASSIGN_QTY")
    private int assignQuantity;
    
    @Column (name="ALT_SHIP_LOC_ID")
    private String altShipLocationId = " ";
    
    @Column (name="ALT_SHIP_VIA")
    private String altShipVia = " ";
    
    @Column (name="REMOVE_FROM_KIT")
    private String removeFromKit = " ";
    
    @Column (name="CHANGE_STATUS")
    private String changeStatus = " ";
    
    @Column (name="ERROR_MSG")
    private String errorMessage = " ";

    public OrderKitComponentChangePK getId() {
        return id;
    }

    public void setId(OrderKitComponentChangePK id) {
        this.id = id;
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

    public String getAltShipVia() {
        return altShipVia;
    }

    public void setAltShipVia(String altShipVia) {
        this.altShipVia = altShipVia;
    }

    public String getRemoveFromKit() {
        return removeFromKit;
    }

    public void setRemoveFromKit(String removeFromKit) {
        this.removeFromKit = removeFromKit;
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

  
}
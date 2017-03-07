package com.fmo.wom.domain.nabs;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table (schema="DD04", name="WOM_KIT_COMP")
@AssociationOverrides( {
   @AssociationOverride(name = "id.componentSequenceNumber", joinColumns = @JoinColumn(name = "COMP_SEQ_NBR")),
   @AssociationOverride(name = "id.orderDetailFK",
                         joinColumns = {
                            @JoinColumn(name = "fmo_mstr_ord_nbr", referencedColumnName = "FMO_MSTR_ORD_NBR"),
                            @JoinColumn(name = "line_seq_nbr", referencedColumnName = "LINE_SEQ_NBR") })
})
//@NamedQueries({
//    @NamedQuery(name = "resetChangeStatusCode",
//            query = "update OrderDetailChangeBO set changeStatus = '00' WHERE id.orderDetailFK.id.parentOrderHeaderFK.id.masterOrderNumber = :masterOrderNumber")
//})
public class OrderKitComponentBO {
    
    @Id
    private OrderKitComponentPK id;
   
    public OrderKitComponentPK getId() {
        return id;
    }

    public void setId(OrderKitComponentPK id) {
        this.id = id;
    }
    
    @OneToOne(mappedBy = "id.orderKitComponentFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    private OrderKitComponentChangeBO orderKitComponentChange;
    
    @Column (name="SQSH_PT_NBR")
    private String squashedPartNumber = " ";

    @Column (name="PROD_FLG")
    private String productFlag = " ";
    
    @Column (name="BRST")
    private String brandState = " ";
    
    @Column (name="QTY_PER_KIT")
    private int quantityPerKit;
             
    @Column (name="ORDER_QTY")
    private int orderQuantity;
               
    @Column (name="SHIP_LOC_ID")
    private String shippedLocationId = " ";
    
    @Column (name="ASSIGN_QTY")
    private int assignedQuantity;
              
    @Column (name="ALT_SHIP_LOC_ID")
    private String altShippedLocationId = " ";
    
    @Column (name="ALT_SHIP_VIA")
    private String altShipVia = " ";
    
    @Column (name="REMOVED_FLG")
    private String removedFlag = " ";
    
    @Column (name="ORIGINAL_COMP_FLG")
    private String originalComponentFlag = " ";
    
    @Column (name="SUB_KIT_PT_NBR")
    private String subKitPartNumber = " ";

    public OrderKitComponentChangeBO getOrderKitComponentChange() {
        return orderKitComponentChange;
    }

    public void setOrderKitComponentChange(OrderKitComponentChangeBO orderKitComponentChange) {
        this.orderKitComponentChange = orderKitComponentChange;
    }

    public String getSquashedPartNumber() {
        return squashedPartNumber;
    }

    public void setSquashedPartNumber(String squashedPartNumber) {
        this.squashedPartNumber = squashedPartNumber;
    }

    public String getProductFlag() {
        return productFlag;
    }

    public void setProductFlag(String productFlag) {
        this.productFlag = productFlag;
    }

    public String getBrandState() {
        return brandState;
    }

    public void setBrandState(String brandState) {
        this.brandState = brandState;
    }

    public int getQuantityPerKit() {
        return quantityPerKit;
    }

    public void setQuantityPerKit(int quantityPerKit) {
        this.quantityPerKit = quantityPerKit;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getShippedLocationId() {
        return shippedLocationId;
    }

    public void setShippedLocationId(String shippedLocationId) {
        this.shippedLocationId = shippedLocationId;
    }

    public int getAssignedQuantity() {
        return assignedQuantity;
    }

    public void setAssignedQuantity(int assignedQuantity) {
        this.assignedQuantity = assignedQuantity;
    }

    public String getAltShippedLocationId() {
        return altShippedLocationId;
    }

    public void setAltShippedLocationId(String altShippedLocationId) {
        this.altShippedLocationId = altShippedLocationId;
    }

    public String getAltShipVia() {
        return altShipVia;
    }

    public void setAltShipVia(String altShipVia) {
        this.altShipVia = altShipVia;
    }

    public String getRemovedFlag() {
        return removedFlag;
    }

    public void setRemovedFlag(String removedFlag) {
        this.removedFlag = removedFlag;
    }

    public String getOriginalComponentFlag() {
        return originalComponentFlag;
    }

    public void setOriginalComponentFlag(String originalComponentFlag) {
        this.originalComponentFlag = originalComponentFlag;
    }

    public String getSubKitPartNumber() {
        return subKitPartNumber;
    }

    public void setSubKitPartNumber(String subKitPartNumber) {
        if (subKitPartNumber == null || "".equals(subKitPartNumber)) {
            this.subKitPartNumber = " ";
        } else {
            this.subKitPartNumber = subKitPartNumber;
        }
    }
    
}
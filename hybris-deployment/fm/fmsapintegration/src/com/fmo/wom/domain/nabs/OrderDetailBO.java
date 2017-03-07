package com.fmo.wom.domain.nabs;

import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (schema="DD04", name="WOM_ORD_DTL")
@AssociationOverrides( {
    @AssociationOverride(name = "id.lineSeqNbr", joinColumns = @JoinColumn(name = "LINE_SEQ_NBR")),
    @AssociationOverride(name = "id.parentOrderHeaderFK",
                         joinColumns =
                            @JoinColumn(name = "fmo_mstr_ord_nbr", referencedColumnName = "FMO_MSTR_ORD_NBR") )
})
public class OrderDetailBO {
    
    @Id
    private OrderDetailPK id;
    
    @OneToOne(mappedBy = "id.orderDetailFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    private OrderDetailChangeBO orderDetailChange;
    
    @OneToMany(mappedBy = "id.orderDetailFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<OrderDetailCommentBO> orderDetailComments;
 
    @OneToMany(mappedBy = "id.orderDetailFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<OrderKitComponentBO> orderKitComponents;
 
    @Column (name="SQSH_PT_NBR")
    private String squashedPartNumber = " ";
    
    @Column (name="PROD_FLG")
    private String productFlag = " ";
    
    @Column (name="BRST")
    private String brandState = " ";
    
    @Column (name="KIT_FLG")
    private String kitFlag = " ";
    
    @Column (name="KIT_COMP_NET_DLETS")
    private int kitComponentNetDeletes = 0;
    
    @Column (name="ORDER_QTY")
    private int orderQuantity;
    
    @Column (name="SHIP_LOC_ID")
    private String shipLocationId = " ";
    
    @Column (name="SHIP_VIA_CODE")
    private String shipViaCode = " ";
    
    @Column (name="ASSIGN_QTY")
    private int assignQuantity;
    /*        DECIMAL(7,0) NOT NULL WITH DEFAULT,    00010200*/
    
    @Column (name="ALT_SHIP_LOC_ID")
    private String altShipLocationId = " ";
    
    @Column (name="ALT_SHIP_VIA")
    private String altShipVia = " ";
    
    @Column (name="ENG_EXPRESS_FLG")
    private String engineExpressFlag = " ";
    
    @Column (name="PART_FLIPPED_REAS")
    private String partFlippedReason = " ";
    
    @Column (name="ORIG_ORDERED_PART")
    private String originalOrderedPart = " ";
    
    @Column (name="ORIG_ORDERED_FLAG")
    private String originalOrderedFlag = " ";
    
    @Column (name="ORIG_ORDERED_BRST")
    private String originalOrderedBrandState = " ";
    
    @Column (name="ERROR_MSG")
    private String errorMessage = " ";
    
    @Column (name="BACKORDER_TYPE")
    private String backorderType = " ";
   
    public OrderDetailPK getId() {
        return id;
    }

    public void setId(OrderDetailPK id) {
        this.id = id;
    }

    public OrderDetailChangeBO getOrderDetailChange() {
        return orderDetailChange;
    }

    public void setOrderDetailChange(OrderDetailChangeBO orderDetailChange) {
        this.orderDetailChange = orderDetailChange;
    }

    public List<OrderDetailCommentBO> getOrderDetailComments() {
        return orderDetailComments;
    }

    public void setOrderDetailComments(
            List<OrderDetailCommentBO> orderDetailComments) {
        this.orderDetailComments = orderDetailComments;
    }

    public List<OrderKitComponentBO> getOrderKitComponents() {
        return orderKitComponents;
    }

    public void setOrderKitComponents(List<OrderKitComponentBO> orderKitComponents) {
        this.orderKitComponents = orderKitComponents;
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

    public String getKitFlag() {
        return kitFlag;
    }

    public void setKitFlag(String kitFlag) {
        this.kitFlag = kitFlag;
    }

    public int getKitComponentNetDeletes() {
        return kitComponentNetDeletes;
    }

    public void setKitComponentNetDeletes(int kitComponentNetDeletes) {
        this.kitComponentNetDeletes = kitComponentNetDeletes;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getShipLocationId() {
        return shipLocationId;
    }

    public void setShipLocationId(String shipLocationId) {
        this.shipLocationId = shipLocationId;
    }

    public String getShipViaCode() {
        return shipViaCode;
    }

    public void setShipViaCode(String shipViaCode) {
        this.shipViaCode = shipViaCode;
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

    public String getEngineExpressFlag() {
        return engineExpressFlag;
    }

    public void setEngineExpressFlag(String engineExpressFlag) {
        this.engineExpressFlag = engineExpressFlag;
    }

    public String getPartFlippedReason() {
        return partFlippedReason;
    }

    public void setPartFlippedReason(String partFlippedReason) {
        this.partFlippedReason = partFlippedReason;
    }

    public String getOriginalOrderedPart() {
        return originalOrderedPart;
    }

    public void setOriginalOrderedPart(String originalOrderedPart) {
        this.originalOrderedPart = originalOrderedPart;
    }

    public String getOriginalOrderedFlag() {
        return originalOrderedFlag;
    }

    public void setOriginalOrderedFlag(String originalOrderedFlag) {
        this.originalOrderedFlag = originalOrderedFlag;
    }

    public String getOriginalOrderedBrandState() {
        return originalOrderedBrandState;
    }

    public void setOriginalOrderedBrandState(String originalOrderedBrandState) {
        this.originalOrderedBrandState = originalOrderedBrandState;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getBackorderType() {
        return backorderType;
    }

    public void setBackorderType(String backorderType) {
        this.backorderType = backorderType;
    }

	@Override
	public String toString() {
		return "OrderDetailBO [id= "+id+", orderDetailChange="
				+ orderDetailChange + ", orderDetailComments="
				+ orderDetailComments + ", orderKitComponents="
				+ orderKitComponents + ", squashedPartNumber="
				+ squashedPartNumber + ", productFlag=" + productFlag
				+ ", brandState=" + brandState + ", kitFlag=" + kitFlag
				+ ", kitComponentNetDeletes=" + kitComponentNetDeletes
				+ ", orderQuantity=" + orderQuantity + ", shipLocationId="
				+ shipLocationId + ", shipViaCode=" + shipViaCode
				+ ", assignQuantity=" + assignQuantity + ", altShipLocationId="
				+ altShipLocationId + ", altShipVia=" + altShipVia
				+ ", engineExpressFlag=" + engineExpressFlag
				+ ", partFlippedReason=" + partFlippedReason
				+ ", originalOrderedPart=" + originalOrderedPart
				+ ", originalOrderedFlag=" + originalOrderedFlag
				+ ", originalOrderedBrandState=" + originalOrderedBrandState
				+ ", errorMessage=" + errorMessage + ", backorderType="
				+ backorderType + "]";
	}
  
}

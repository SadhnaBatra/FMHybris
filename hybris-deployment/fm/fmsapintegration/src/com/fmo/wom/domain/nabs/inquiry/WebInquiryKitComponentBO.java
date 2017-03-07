package com.fmo.wom.domain.nabs.inquiry;


import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table (schema="DD09", name="WEB_INQ_KIT_COMP")
@AssociationOverrides( {
    @AssociationOverride(name = "id.componentSequenceNumber", joinColumns = @JoinColumn(name = "COMP_SEQ_NBR")),
    @AssociationOverride(name = "id.parentFK",
                         joinColumns = {
                            @JoinColumn(name = "fmo_mstr_ord_nbr", referencedColumnName = "FMO_MSTR_ORD_NBR"),
                            @JoinColumn(name = "order_nbr", referencedColumnName = "ORDER_NBR"),
                            @JoinColumn(name = "item_seq_nbr", referencedColumnName = "ITEM_SEQ_NBR"),
                            @JoinColumn(name = "inquiry_key", referencedColumnName = "INQUIRY_KEY")} )
})
public class WebInquiryKitComponentBO {
   
    @EmbeddedId
    private WebInquiryKitComponentPK id;
    
    @Column (name="PART_NBR")
    private String partNumber;
    
    @Column (name="PROD_FLG")
    private String productFlag;
    
    @Column (name="BRST")
    private String brandState;
    
    @Column (name="PART_DESCRIPTION")
    private String partDescription;
    
    @Column (name="SHIPPED_QTY")
    private int shippedQuantity;
    
    @Column (name="BO_QTY")
    private int backOrderedQuantity;

    @Column (name="BO_DISPOSITION")
    private String backOrderedDisposition;
    
    @Column (name="ASGN_QTY")
    private int assignedQuantity;

    @Column (name="QTY_PER_KIT")
    private int quantityPerKit;

    @Column (name="ORDER_QTY")
    private int orderedQuantity;

    public WebInquiryKitComponentPK getId() {
        return id;
    }

    public void setId(WebInquiryKitComponentPK id) {
        this.id = id;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
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

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public int getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(int shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    public int getBackOrderedQuantity() {
        return backOrderedQuantity;
    }

    public void setBackOrderedQuantity(int backOrderedQuantity) {
        this.backOrderedQuantity = backOrderedQuantity;
    }

    public String getBackOrderedDisposition() {
        return backOrderedDisposition;
    }

    public void setBackOrderedDisposition(String backOrderedDisposition) {
        this.backOrderedDisposition = backOrderedDisposition;
    }

    public int getAssignedQuantity() {
        return assignedQuantity;
    }

    public void setAssignedQuantity(int assignedQuantity) {
        this.assignedQuantity = assignedQuantity;
    }

    public int getQuantityPerKit() {
        return quantityPerKit;
    }

    public void setQuantityPerKit(int quantityPerKit) {
        this.quantityPerKit = quantityPerKit;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }
    
}


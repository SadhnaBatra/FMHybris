package com.fmo.wom.domain.nabs.inquiry;


import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (schema="DD09", name="WEB_INQ_ORD_DTL")
@AssociationOverrides( {
    @AssociationOverride(name = "id.itemSequenceNumber", joinColumns = @JoinColumn(name = "ITEM_SEQ_NBR")),
    @AssociationOverride(name = "id.parentFK",
                         joinColumns = {
                            @JoinColumn(name = "fmo_mstr_ord_nbr", referencedColumnName = "FMO_MSTR_ORD_NBR"),
                            @JoinColumn(name = "order_nbr", referencedColumnName = "ORDER_NBR"),
                            @JoinColumn(name = "inquiry_key", referencedColumnName = "INQUIRY_KEY")} )
})
public class WebInquiryOrderDetailBO {
   
    @EmbeddedId
    private WebInquiryOrderDetailPK id;
    
    @OneToMany(mappedBy = "id.parentFK", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<WebInquiryKitComponentBO> kitComponentList;
    
    @Column (name="PART_NBR")
    private String partNumber;
    
    @Column (name="PROD_FLG")
    private String productFlag;
    
    @Column (name="BRST")
    private String brandState;
    
    @Column (name="PART_DESCRIPTION")
    private String partDescription;
    
    @Column (name="ORIG_ORDER_QTY")
    private int originalOrderQuantity;
    
    @Column (name="THIS_ORDER_QTY")
    private int thisOrderQuantity;
    
    @Column (name="SHIPPED_QTY")
    private int shippedQuantity;
    
    @Column (name="BO_QTY")
    private int backOrderedQuantity;

    @Column (name="BO_DISPOSITION")
    private String backOrderedDisposition;
    
    @Column (name="KIT_FLG")
    private String kitFlag;
    
    @Column (name="ASGN_QTY")
    private int assignedQuantity;

    public WebInquiryOrderDetailPK getId() {
        return id;
    }

    public void setId(WebInquiryOrderDetailPK id) {
        this.id = id;
    }

    public List<WebInquiryKitComponentBO> getKitComponentList() {
        return kitComponentList;
    }

    public void setKitComponentList(List<WebInquiryKitComponentBO> kitComponentList) {
        this.kitComponentList = kitComponentList;
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

    public int getOriginalOrderQuantity() {
        return originalOrderQuantity;
    }

    public void setOriginalOrderQuantity(int originalOrderQuantity) {
        this.originalOrderQuantity = originalOrderQuantity;
    }

    public int getThisOrderQuantity() {
        return thisOrderQuantity;
    }

    public void setThisOrderQuantity(int thisOrderQuantity) {
        this.thisOrderQuantity = thisOrderQuantity;
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

    public String getKitFlag() {
        return kitFlag;
    }

    public void setKitFlag(String kitFlag) {
        this.kitFlag = kitFlag;
    }

    public int getAssignedQuantity() {
        return assignedQuantity;
    }

    public void setAssignedQuantity(int assignedQuantity) {
        this.assignedQuantity = assignedQuantity;
    }
    
    
}


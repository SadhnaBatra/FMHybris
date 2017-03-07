package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.List;

import com.fmo.wom.domain.enums.ExternalSystem;
//import javax.persistence.*;

public class ShippedItemBO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int lineNumber;
	private String partNumber;
	private String displayPartNumber;
	private String description;
	private String aaiaBrand;
	private String productFlag;
	private String brandState;
	
	/* Added for ECC*/
	 private int invoicedQuantity;  
     private int returnableQuantity;
     private double netValue;
     private double tax; 
     private double shipCharge; 
     private double totalValue;
     private String reasonForRejection;
	
     private String productImgUrl;
	
	private boolean kit;
	private List<ShippedItemBO> kitComponents;
	
	private ExternalSystem externalSystem;
	
	private int orderedQuantity;
	private int backorderedQuantity;
	private int assignedQuantity;
	private int shippedQuantity;
	
    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
    public String getPartNumber() {
        return partNumber;
    }
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }
    public String getDisplayPartNumber() {
        return displayPartNumber;
    }
    public void setDisplayPartNumber(String displayPartNumber) {
        this.displayPartNumber = displayPartNumber;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAaiaBrand() {
        return aaiaBrand;
    }
    public void setAaiaBrand(String aaiaBrand) {
        this.aaiaBrand = aaiaBrand;
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
    public ExternalSystem getExternalSystem() {
        return externalSystem;
    }
    public void setExternalSystem(ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
    }
    public int getShippedQuantity() {
        return shippedQuantity;
    }
    public void setShippedQuantity(int shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }
    public int getOrderedQuantity() {
        return orderedQuantity;
    }
    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }
    public int getBackorderedQuantity() {
        return backorderedQuantity;
    }
    public void setBackorderedQuantity(int backorderedQuantity) {
        this.backorderedQuantity = backorderedQuantity;
    }
    public int getAssignedQuantity() {
        return assignedQuantity;
    }
    public void setAssignedQuantity(int assignedQuantity) {
        this.assignedQuantity = assignedQuantity;
    }
    public boolean isKit() {
        return kit;
    }
    public void setKit(boolean kit) {
        this.kit = kit;
    }
    public List<ShippedItemBO> getKitComponents() {
        return kitComponents;
    }
    public void setKitComponents(List<ShippedItemBO> kitComponents) {
        this.kitComponents = kitComponents;
    }
	public int getInvoicedQuantity() {
		return invoicedQuantity;
	}
	public void setInvoicedQuantity(int invoicedQuantity) {
		this.invoicedQuantity = invoicedQuantity;
	}
	public int getReturnableQuantity() {
		return returnableQuantity;
	}
	public void setReturnableQuantity(int returnableQuantity) {
		this.returnableQuantity = returnableQuantity;
	}
	public double getNetValue() {
		return netValue;
	}
	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getShipCharge() {
		return shipCharge;
	}
	public void setShipCharge(double shipCharge) {
		this.shipCharge = shipCharge;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	public String getReasonForRejection() {
		return reasonForRejection;
	}
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	
	
	public String getProductImgUrl() {
		return productImgUrl;
	}
	public void setProductImgUrl(String productImgUrl) {
		this.productImgUrl = productImgUrl;
	}
	
	@Override
	public String toString() {
		return "ShippedItemBO [lineNumber=" + lineNumber + ", partNumber="
				+ partNumber + ", displayPartNumber=" + displayPartNumber
				+ ", description=" + description + ", aaiaBrand=" + aaiaBrand
				+ ", productFlag=" + productFlag + ", brandState=" + brandState
				+ ", invoicedQuantity=" + invoicedQuantity
				+ ", returnableQuantity=" + returnableQuantity + ", netValue="
				+ netValue + ", tax=" + tax + ", shipCharge=" + shipCharge
				+ ", totalValue=" + totalValue + ", reasonForRejection="
				+ reasonForRejection + ", kit=" + kit + ", kitComponents="
				+ kitComponents + ", externalSystem=" + externalSystem
				+ ", orderedQuantity=" + orderedQuantity
				+ ", backorderedQuantity=" + backorderedQuantity
				+ ", assignedQuantity=" + assignedQuantity
				+ ", shippedQuantity=" + shippedQuantity 
				+ ", productImgUrl="+productImgUrl+"]";
	}
    
    
    
    
   /* @Override
    public String toString() {
        return "ShippedItemBO [lineNumber=" + lineNumber + ", partNumber=" + partNumber + ", displayPartNumber=" + displayPartNumber + ", description="
                + description + ", aaiaBrand=" + aaiaBrand + ", productFlag=" + productFlag + ", brandState=" + brandState + ", externalSystem="
                + externalSystem + ", orderedQuantity=" + orderedQuantity + ", backorderedQuantity=" + backorderedQuantity + ", assignedQuantity="
                + assignedQuantity + ", shippedQuantity=" + shippedQuantity + "]";
    }*/

	
}

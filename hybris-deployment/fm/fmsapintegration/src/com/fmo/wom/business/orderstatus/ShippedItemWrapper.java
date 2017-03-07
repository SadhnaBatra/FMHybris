package com.fmo.wom.business.orderstatus;

import java.util.List;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.domain.ShippedItemBO;

public class ShippedItemWrapper {

	private ShippedItemBO shippedItemBO;
	private String packingSlipNumber = "";
	
	public ShippedItemWrapper(ShippedItemBO shippedItemBO) {
		super();
		this.shippedItemBO = shippedItemBO;
	}

	/* ShippedItemBO passthrough getters */

	public int getLineNumber() {
		int lineNumber = 0;
		if (shippedItemBO != null) {
			lineNumber = shippedItemBO.getLineNumber();
		}
        return lineNumber;
    }
    public String getPartNumber() {
    	String partNumber = null;
		if (shippedItemBO != null) {
			
			partNumber = shippedItemBO.getPartNumber();
			
			if (!GenericValidator.isBlankOrNull(shippedItemBO.getDisplayPartNumber())
					&& shippedItemBO.getDisplayPartNumber().trim().length()>0 ){
				partNumber = shippedItemBO.getDisplayPartNumber();
			}
		
		
		}
    	return partNumber;
    }
    public String getDescription() {
    	String description = null;
		if (shippedItemBO != null) {
			description = shippedItemBO.getDescription();
		}
        return description;
    }
    public int getAssignedQuantity() {
    	int assignedQuantity = 0;
		if (shippedItemBO != null) {
			assignedQuantity = shippedItemBO.getAssignedQuantity();
		}
        return assignedQuantity;
    }
    public int getBackorderedQuantity() {
    	int backorderedQuantity = 0;
		if (shippedItemBO != null) {
			backorderedQuantity = shippedItemBO.getBackorderedQuantity();
		}
        return backorderedQuantity;
    }
    public int getShippedQuantity() {
    	int shippedQuantity = 0;
		if (shippedItemBO != null) {
			shippedQuantity = shippedItemBO.getShippedQuantity();
		}
        return shippedQuantity;
    }
    public int getOrderedQuantity() {
    	int orderedQuantity = 0;
		if (shippedItemBO != null) {
			orderedQuantity = shippedItemBO.getOrderedQuantity();
		}
        return orderedQuantity;
    }
	
	/* Object Accessors */
    
	public ShippedItemBO getShippedItemBO() {
		return shippedItemBO;
	}

	public void setShippedItemBO(ShippedItemBO shippedItemBO) {
		this.shippedItemBO = shippedItemBO;
	}

	public String getPackingSlipNumber() {
		return packingSlipNumber;
	}

	public void setPackingSlipNumber(String packingSlipNumber) {
		this.packingSlipNumber = packingSlipNumber;
	}
	
	public boolean isKit() {
	    return this.shippedItemBO.isKit();
	}
	
	public List<ShippedItemBO> getKitComponents() {
        //return shippedItemBO.getKitComponents();
	    if (shippedItemBO != null) { 
	        return shippedItemBO.getKitComponents();
	    } 
	    return null;
    }
}

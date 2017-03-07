
package com.fmo.wom.domain;

import java.util.List;

import com.fmo.wom.domain.enums.BackOrderPolicy;

public class ComponentBO extends PartBO {

	private String componentType;
	private String oversizeChosen = null;
	private String parentPartNumber;
	private List<OversizeBO> oversizeSpecList;
	
	private int defaultQuantity;
	private int availableQuantity;
	private int assignedQuantity;
	
	public String getComponentType() {
        return componentType;
    }
    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }
    public String getOversizeChosen() {
        return oversizeChosen;
    }
    public void setOversizeChosen(String oversizeChosen) {
        this.oversizeChosen = oversizeChosen;
    }
    public String getParentPartNumber() {
        return parentPartNumber;
    }
    public void setParentPartNumber(String parentPartNumber) {
        this.parentPartNumber = parentPartNumber;
    }
    public List<OversizeBO> getOversizeSpecList() {
        return oversizeSpecList;
    }
    public void setOversizeSpecList(List<OversizeBO> oversizeSpecList) {
        this.oversizeSpecList = oversizeSpecList;
    }
    public int getDefaultQuantity() {
        return defaultQuantity;
    }
    public void setDefaultQuantity(int defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }
    public int getAvailableQuantity() {
        return availableQuantity;
    }
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
   
    /**
     * helper method here to return this components "requested quantity".  This will
     * access this objects quantityBO and get its requested quantity value.  This is 
     * dependent on the backend populating the "requested" quantity for a component
     * as the Kit requested quantity * the quantity necessary for the component 
     * @return
     */
    public int getRequestedQuantity() {
        
        if (getItemQty() != null) {
            return getItemQty().getRequestedQuantity();
        }
        return 0;
        
    }
    
    public int getAssignedQuantity() {
        return assignedQuantity;
    }
    
    public void setAssignedQuantity(int assignedQuantity) {
        this.assignedQuantity = assignedQuantity;
    }
	
    public int getQuantityToBeOrdered(int kitQuantity) {
        
        int requestedComponentQuantity = kitQuantity * getDefaultQuantity();
        int availableComponentQuantity = getAvailableQuantity();
        if (getSelectedOversize() != null) {
            availableComponentQuantity = getSelectedOversize().getAvailableQuantity();
        }
        
        int orderedQuantity = 0;
        BackOrderPolicy bop = getBackorderPolicy();
        // if the available is greater than the requested, we can say availble is equal to the requested for next calcs.
        if (availableComponentQuantity >= requestedComponentQuantity) {
            availableComponentQuantity = requestedComponentQuantity;
        }
        
        if (bop == null) { 
            orderedQuantity = requestedComponentQuantity;
        } else {
            
            // otherwise we need to check the back order policy.
            if (bop == null || bop == BackOrderPolicy.SHIP_AND_CANCEL || bop == BackOrderPolicy.SHIP_AND_BACKORDER) {
                // no back order policy or ship and * policy. just put out the available.
                orderedQuantity = availableComponentQuantity;
            } else if (bop == BackOrderPolicy.CANCEL_ALL || bop == BackOrderPolicy.BACKORDER_ALL) {
                // cancelling or backordering all, so nothing ordered
                orderedQuantity = 0;
            } else if (bop == BackOrderPolicy.SHIP_AND_BACKORDER) {
                orderedQuantity = availableComponentQuantity;
            } 
        }
        return orderedQuantity;
    }
    
    public int getQuantityToBeBackordered(int kitQuantity) {
        
        int requestedComponentQuantity = kitQuantity * getDefaultQuantity();
        int availableComponentQuantity = getAvailableQuantity();
        if (getSelectedOversize() != null) {
            availableComponentQuantity = getSelectedOversize().getAvailableQuantity();
        }
        
        int backorderedQuantity = 0;
        BackOrderPolicy bop = getBackorderPolicy();
        // if the available is greater than the requested, we can say availble is equal to the requested for next calcs.
        if (availableComponentQuantity >= requestedComponentQuantity) {
            availableComponentQuantity = requestedComponentQuantity;
        }
        // If no policy, let's say 0    
        if (bop == null) {
            backorderedQuantity = 0;
        } else {
            
            // otherwise we need to check the back order policy.
            if (bop == null || bop == BackOrderPolicy.SHIP_AND_CANCEL || bop == BackOrderPolicy.CANCEL_ALL) {
                // no back order policy or a cancel policy. No back orders
                backorderedQuantity = 0;
            } else if (bop == BackOrderPolicy.BACKORDER_ALL) {
                backorderedQuantity = requestedComponentQuantity;
            } else if (bop == BackOrderPolicy.SHIP_AND_BACKORDER) {
                backorderedQuantity = requestedComponentQuantity - availableComponentQuantity;
            } 
        }
        return backorderedQuantity;
    }
    
    public OversizeBO getSelectedOversize() {
        List<OversizeBO> oversizeList = getOversizeSpecList();
        
        if (oversizeList == null) {
            return null;
        }
        
        for (OversizeBO anOversize : oversizeList) {
            if (anOversize.isSelected()) {
                return anOversize;
            }
        }
        
        return null;
    }

    public String getPartNumberWithSelectedOversize() {
        String partNumber = getPartNumber();
        if (getSelectedOversize() != null) {
             partNumber = getSelectedOversize().getDisplayPartNumber();
        }
        return partNumber;
    }
    
    @Override
    /**
     * The back order policy is getting set for this component.  Rework all the quantities
     * appropriately.
     */
    public void setBackorderPolicy(BackOrderPolicy policy) {
        super.setBackorderPolicy(policy);
        
        
    }
    
    
}

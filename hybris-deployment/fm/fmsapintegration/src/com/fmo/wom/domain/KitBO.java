/*
 * Created on Jun 6, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.fmo.wom.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.KitType;

/**
 * @author amarnr85
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class KitBO extends ItemBO {

	private Map<String, List<ComponentBO>> kitComponentCategoryMap;
	private Map<String, List<ComponentBO>> configuredKitComponentCategoryMap;
    
	private KitType type;
	private String predecessorReason;
	private boolean shortShipAllowed;
	private boolean addOtherComponentsAllowed;
	private boolean configured = false;
	
	public KitBO() {
	    super();
	    setKit(true);
	}
	
	public Map<String, List<ComponentBO>> getKitComponentCategoryMap() {
        return kitComponentCategoryMap;
    }
	@XmlTransient
    public void setKitComponentCategoryMap(Map<String, List<ComponentBO>> kitComponentCategoryMap) {
        this.kitComponentCategoryMap = kitComponentCategoryMap;
    }

    public Set<String> getKitComponentCategories() {
        if (kitComponentCategoryMap == null) {
            return new HashSet<String>();
        }
        return kitComponentCategoryMap.keySet();    
    }

    public List<ComponentBO> getKitComponents(String aCategory) {
        if (GenericValidator.isBlankOrNull(aCategory) || kitComponentCategoryMap == null) {
            return null;
        } 
        return kitComponentCategoryMap.get(aCategory);
    }
    
    public List<ComponentBO> getAllKitComponents() {
        List<ComponentBO> allComponents = new ArrayList<ComponentBO>();
        
        for (String aCategory : getKitComponentCategories()) {
            if (getKitComponents(aCategory) == null) {
                continue;
            }
            allComponents.addAll(getKitComponents(aCategory));
        }
        
        return allComponents;
    }
    
    public int getNumberOfCategories() {
		return getKitComponentCategories().size();
	}

	public KitType getType() {
		return type;
	}

	public void setType(KitType type) {
		this.type = type;
	}

	public String getPredecessorReason() {
		return predecessorReason;
	}

	public void setPredecessorReason(String predecessorReason) {
		this.predecessorReason = predecessorReason;
	}

    public boolean isShortShipAllowed() {
        return shortShipAllowed;
    }

    public void setShortShipAllowed(boolean shortShipAllowed) {
        this.shortShipAllowed = shortShipAllowed;
    }

    public boolean isAddOtherComponentsAllowed() {
        return addOtherComponentsAllowed;
    }

    public void setAddOtherComponentsAllowed(boolean addOtherComponentsAllowed) {
        this.addOtherComponentsAllowed = addOtherComponentsAllowed;
    }

    public boolean isKit() {
        return true;
    }

    public boolean isConfigured() {
        return configured;
    }

	@XmlTransient
    public void setConfigured(boolean configured) {
        this.configured = configured;
    }

    public Map<String, List<ComponentBO>> getConfiguredKitComponentCategoryMap() {
        return configuredKitComponentCategoryMap;
    }

	@XmlTransient
    public void setConfiguredKitComponentCategoryMap(Map<String, List<ComponentBO>> configuredKitComponentCategoryMap) {
        this.configuredKitComponentCategoryMap = configuredKitComponentCategoryMap;
    }
    
    public Set<String> getConfiguredKitComponentCategories() {
        if (configuredKitComponentCategoryMap == null) {
            return new HashSet<String>();
        }
        return configuredKitComponentCategoryMap.keySet();    
    }

    public List<ComponentBO> getConfiguredKitComponents(String aCategory) {
        if (GenericValidator.isBlankOrNull(aCategory) || configuredKitComponentCategoryMap == null) {
            return null;
        } 
        return configuredKitComponentCategoryMap.get(aCategory);
    }
    
    public List<ComponentBO> getAllConfiguredKitComponents() {
        List<ComponentBO> allConfiguredComponents = new ArrayList<ComponentBO>();
        
        for (String aCategory : getConfiguredKitComponentCategories()) {
            allConfiguredComponents.addAll(getConfiguredKitComponents(aCategory));
        }
        
        return allConfiguredComponents;
    }
    /**
     * Utility to copy certain fields on kit that are required for the initial
     * check orderable call (and possibly other calls later). 
     * @return a new object with relevant input fields retained
     */
    @Override
    public KitBO createInitialCopy() {
        
        KitBO newKit = new KitBO();
        super.initialCopy(this, newKit);
       
        newKit.setConfiguredKitComponentCategoryMap(this.getConfiguredKitComponentCategoryMap());
        newKit.setKitComponentCategoryMap(this.getKitComponentCategoryMap());
        newKit.setConfigured(this.isConfigured());
        
        return newKit;
    }
    
    @Override
    public void populateInitialCopy(ItemBO inputItem) {
        initialCopy(inputItem, this);
    }
    
    
    public boolean isBeingDiscontinued(){
    	if(this.hasProblemItem()){
	    	for(ProblemBO problem : this.getProblemItemList()){
		    	if ("PART_BEING_DISCONTINUED".equalsIgnoreCase(problem.getMessageKey())){
		    		problem.setAllowedToProcess(false);
		    		return true;
		    	}		    	
	    	}
    	}  	
    	return false;
    }
    
    public void autoConfigureKitComponents() {

        Map<String, List<ComponentBO>> configuredMap = new HashMap<String, List<ComponentBO>>();

        // "configure" component type kits
        if (getType() == KitType.COMPONENT) {
        
            for (String aKitComponentCategory : getKitComponentCategories()) {
            
                List<ComponentBO> configuredComponentsForCategory = new ArrayList<ComponentBO>();
                List<ComponentBO> allComponentsForCategory = getKitComponents(aKitComponentCategory);
                if (allComponentsForCategory == null || allComponentsForCategory.size() == 0) {
                    continue;
                }
            
               int componentSelection = getLineNumber();
                if (componentSelection >= allComponentsForCategory.size()) {
                    componentSelection = allComponentsForCategory.size() - 1;
                }
    
                // configure component by line number
                ComponentBO configuredComponent = allComponentsForCategory.get(componentSelection);
                // give all kits the same components
                //ComponentBO configuredComponent = allComponentsForCategory.get(0));
    
                configuredComponentsForCategory.add(configuredComponent);
                configuredMap.put(aKitComponentCategory, configuredComponentsForCategory);
            }
            
        } else {
            // i think the preselected ones are all done.
            configuredMap = getKitComponentCategoryMap(); 
        }

        setConfiguredKitComponentCategoryMap(configuredMap);
        setBackorderPolicy(BackOrderPolicy.SHIP_AND_BACKORDER);
        // now go through all the configured components and "configure" any oversize thingamabobbers
        for (ComponentBO aConfiguredComponent : getAllConfiguredKitComponents()) {
            if (aConfiguredComponent.getOversizeSpecList() != null && aConfiguredComponent.getOversizeSpecList().size() > 0) {
                OversizeBO firstOversize = aConfiguredComponent.getOversizeSpecList().get(0);
                firstOversize.setSelected(true);
            }
        }
        
        // we "configured" this!
        setConfigured(true);
    }
    
    /**
     * Get the available quantity for this kit. if the kit is not configured, we will just return the requested quantity
     * since we're not sure what components the user will select.  In essence, the available is uncalculatable on an
     * unconfigured kit so it will default to the requested.
     * 
     * If the kit is configured, we can go through the selected items and determine the actual available 
     * quantity now based on the lowest number of kits available per configured component availability.
     *  For example, if the availability of one configured component is 0, there are 0 of that kit available.
     * If one configured component needs 16 and there are 18 available, there is one kit available etc etc.
     * If any components availability is 0 OR lower than the necessary for 1 kit, we set kit available to 0 and abort. 
     * @param aKit
     * @return the calculated available quantity
     */
    public int getAvailableQuantityForKit() {
        
        // requested number of kits
        int requestedQuantity = getItemQty().getRequestedQuantity();
        
        // assume we can get the requested
        int availableQuantity = requestedQuantity;
        
        // if the kit is not configured, we will just use the requested since we're not sure what
        // components the user will select.  In essence, the available is uncalculatable on an
        // unconfigured kit so it will default to the requested.
       
        // If the kit is configured, we can go through the selected items and determine the actual available 
        // quantity now based on the lowest number of kits available per configured component availability.
        // For example, if the availability of one configured component is 0, there are 0 of that kit available.
        // If one configured component needs 16 and there are 18 available, there is one kit available etc etc.
        // If any components availability is 0 OR lower than the necessary for 1 kit, we set kit available to 0 and abort.
        if (isConfigured() == true) {
                  
            List<ComponentBO> configuredComponents = getAllConfiguredKitComponents();
            
            // assume we can get all of them
            int lowestNumberOfKitsAvailable = requestedQuantity;
            for (ComponentBO aConfiguredComponent : configuredComponents) {
                int componentAvailableQuantity = aConfiguredComponent.getAvailableQuantity();
                if (aConfiguredComponent.getSelectedOversize() != null) {
                    componentAvailableQuantity = aConfiguredComponent.getSelectedOversize().getAvailableQuantity();
                }
                int kitsAvailableForThisComponent = (int) Math.floor(componentAvailableQuantity / aConfiguredComponent.getDefaultQuantity());
                if (kitsAvailableForThisComponent < lowestNumberOfKitsAvailable) {
                    lowestNumberOfKitsAvailable = kitsAvailableForThisComponent;
                }
            
                if (lowestNumberOfKitsAvailable == 0) {
                    break;
                }
            }
            
            availableQuantity = lowestNumberOfKitsAvailable;
        }
        
        return availableQuantity;
    }
    
    /**
     * A kit is a part, so has a weight attribute on it.  However, once the kit is configured, 
     * we should be able to delve into the selected components and total them up for a more
     * accurate weight.  This method will get the parent weightbo, but only for the unit
     * of measure attribute to ensure the parent attributes remain in case this kit becomes
     * "unconfigured" at any point.
     */
    @Override
    public WeightBO getItemWeight() {
        WeightBO itemWeight = super.getItemWeight();
        if (itemWeight == null) return null;
        if (isConfigured() == false) {
            return itemWeight;
        }
        
        // configured.  Go through the map the do some math
        WeightBO configuredKitWeight = new WeightBO();
        configuredKitWeight.setWtUOM(itemWeight.getWtUOM());
        double totalWeight = 0.0;
        for (ComponentBO aConfiguredComponent : getAllConfiguredKitComponents()) {
            totalWeight += (aConfiguredComponent.getItemWeight().getWeight() * aConfiguredComponent.getDefaultQuantity());
        }
        
        configuredKitWeight.setWeight(totalWeight);
        return configuredKitWeight;
    }

    /**
     * preconfigured kits get their price determined off the sum of the components
     */
    @Override
    public PriceBO getItemPrice() {
        PriceBO itemPrice = super.getItemPrice();
        if (itemPrice == null) return null;
        
        // If this is not a preselect, return whatever we have
        if (KitType.PRESELECT != getType()) {
            return itemPrice;
        }
        
        if (isConfigured() == false) {
            return itemPrice;
        }
        
        // Its a preselect so sum up the components.  Go through the map and do some math
        PriceBO configuredKitPrice = new PriceBO();
        configuredKitPrice.setPriceType(itemPrice.getPriceType());
        
        // not sure if both of these are necessary or even desired, but this method
        // will properly calculate and fill them.
        double totalDisplayPrice = 0.0;
        double totalFreightPrice = 0.0;
        for (ComponentBO aConfiguredComponent : getAllKitComponents()) {
            // This will give the price of configured ordered components.  Don't want that necessarily
//            int totalQuantityToBeOrdered = aConfiguredComponent.getQuantityToBeOrdered(1) + aConfiguredComponent.getQuantityToBeBackordered(1);
//            totalDisplayPrice += (aConfiguredComponent.getItemPrice().getDisplayPrice() * totalQuantityToBeOrdered);
//            totalFreightPrice += (aConfiguredComponent.getItemPrice().getFreightPrice() * totalQuantityToBeOrdered);
            totalDisplayPrice += (aConfiguredComponent.getItemPrice().getDisplayPrice() * aConfiguredComponent.getDefaultQuantity());
            totalFreightPrice += (aConfiguredComponent.getItemPrice().getFreightPrice() * aConfiguredComponent.getDefaultQuantity());
        }
        
        configuredKitPrice.setDisplayPrice(totalDisplayPrice);
        configuredKitPrice.setFreightPrice(totalFreightPrice);
        return configuredKitPrice;
    }
    
    public PriceBO getAverageItemPrice() {
        PriceBO itemPrice = super.getItemPrice();
        if (itemPrice == null) return null;
        
        // If this is not a preselect, return whatever we have
        if (KitType.PRESELECT != getType()) {
            return itemPrice;
        }
        
        if (isConfigured() == false) {
            return itemPrice;
        }
        
        int requestedKitQuantity = getItemQty().getRequestedQuantity();
        
        // Its a preselect so sum up the components.  Go through the map and do some math
        PriceBO averageKitPrice = new PriceBO();
        averageKitPrice.setPriceType(itemPrice.getPriceType());
        
        // not sure if both of these are necessary or even desired, but this method
        // will properly calculate and fill them.
        double totalDisplayPrice = 0.0;
        double totalFreightPrice = 0.0;
        for (ComponentBO aConfiguredComponent : getAllKitComponents()) {
            // This will get the total price of configured ordered components. 
            int totalQuantityToBeOrdered = aConfiguredComponent.getQuantityToBeOrdered(requestedKitQuantity) + aConfiguredComponent.getQuantityToBeBackordered(requestedKitQuantity);
            totalDisplayPrice += (aConfiguredComponent.getItemPrice().getDisplayPrice() * totalQuantityToBeOrdered);
            totalFreightPrice += (aConfiguredComponent.getItemPrice().getFreightPrice() * totalQuantityToBeOrdered);
        }
        
        averageKitPrice.setDisplayPrice(totalDisplayPrice/requestedKitQuantity);
        averageKitPrice.setFreightPrice(totalFreightPrice/requestedKitQuantity);
        return averageKitPrice;
    }
    
    
	@Override
	public String toString() {
		
		return super.toString() + "\nKitBO [kitComponentCategoryMap=" + kitComponentCategoryMap
				+ ", configuredKitComponentCategoryMap="
				+ configuredKitComponentCategoryMap + ", type=" + type
				+ ", predecessorReason="
				+ predecessorReason + ", shortShipAllowed=" + shortShipAllowed
				+ ", addOtherComponentsAllowed=" + addOtherComponentsAllowed
				+ ", configured=" + configured + "]";
	}

}
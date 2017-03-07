package com.fmo.wom.business.as;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.KitBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.ProblemBOFactory;

public abstract class InventoryASBase {

	private static Logger logger = Logger.getLogger(InventoryASBase.class);

	protected abstract MarketAS getMarketAS();
	
	protected void setInventoryProblem(List<ItemBO> itemList) {
		setInventoryProblem(itemList, null);
	}
	
	
	/**
     * For IPO orders and Non-IPO orders when parts are found in ONLY ONE DC:
     * 		- Handle zero inventory and insufficient inventory return codes
     * For Non-IPO orders when parts are found in MULTIPLE DCs:
     * 		- Handle 'Part found in multiple locations' problem.
     * @param itemList
     * @param orderSource	For IPO orders: OrderSource.IPO.<br>
     * 						For all other order types: null.
     * @param bypassMultipleInventoryLocationsProblem For Emergency orders, this flag can be set to TRUE (when calling Check Inventory with
     * 													Order Type as 'Stock') so all Inventory Locations returned by SAP can be displayed
     * 													on the screen thereby allowing GATP Proposed Inventory Locations override capability!!
     */
	protected void setInventoryProblem(List<ItemBO> itemList, OrderSource orderSource, boolean bypassMultipleInventoryLocationsProblem) {

		logger.info("setInventoryProblem(...): bypassMultipleInventoryLocationsProblem: " + bypassMultipleInventoryLocationsProblem);
		for (ItemBO anItem : itemList) {
		    
			if (anItem.canProcessItem() == false) {
			    // not going to process this one anyways so we can skip it
			    continue;
			}

			if (anItem.getInventory() == null || anItem.getInventory().size() == 0) {
			    // this wasn't supposed to be possible, but we have found certain parts in certain markets not properly 
			    // set up that cause a successful resolution but no inventory results (they were called "3rd party sales"
			    // in one email if you need to track them down.  For these parts, we will add a setup error problem to 
			    // ensure no processing.  Returning any fake inventories may result in a backordered situation with an
			    // undefined DC which could cause all sorts of weirdness.
				if (anItem.getInventory() != null) {
					logger.debug("setInventoryProblem(...): anItem.getInventory().size(): " + anItem.getInventory().size());
				} else {
					logger.debug("setInventoryProblem(...): anItem.getInventory() is null");
				}
			    ProblemBO setupProblem = ProblemBOFactory.createProblem(MessageResourceUtil.SETUP_ERROR);
			    setupProblem.setAllowedToProcess(false);
			    anItem.addProblemItem(setupProblem);
			}
			
			if (anItem.isKit()) {
			    setAvailableQuantityForKit((KitBO)anItem);
			}
			
			// we can process this one.  Get the requested quantity to use
			int updatedRequestedQuantity = getUpdatedRequestedQuantityForSoldInMultiples(anItem);
			
			// if this updated requested quantity is lower than the minimum order quantity, use the
			// minimum order quantity.  Presumably, the minimum order quantity is always a multiple
			// of the sold in multiples quantity right?
			if (anItem.getItemQty().getMinimumOrderQuantity() > updatedRequestedQuantity) {
			    updatedRequestedQuantity = anItem.getItemQty().getMinimumOrderQuantity();
			}
			
			if (anItem.getInventory() != null && anItem.getInventory().size() > 0) {

				if ((orderSource != null && orderSource == OrderSource.IPO) || 
						anItem.getInventory().size() == 1) { // OrderSource = 'IPO' --OR-- Part found in only one DC
					logger.info("setInventoryProblem(...): Inside 'if (... || anItem.getInventory().size() == 1)");
					// Scenarios here are:
					//  - max available = 0, home dc = 0                    --> use max available, attach no inventory
		            //  - max available >= requested, home dc >= requested  --> use home dc
		            //  - max available >= requested, home dc < requested   --> use max available
		            //  - max available < requested, home dc < requested    --> use max available, attach insufficient inventory
		            
					// Using these rules, get the inventory to use
		            InventoryBO inventoryToUse = getInventoryToUse(anItem);
					

					// get the updated available quantity
		            int updatedAvailableQuantity = getUpdatedAvailableQuantityForSoldInMultiples(anItem, inventoryToUse);
					
		            if (updatedAvailableQuantity == 0) {
		                attachNoInventoryProblem(anItem, inventoryToUse, updatedRequestedQuantity);
		            } else if (updatedAvailableQuantity < updatedRequestedQuantity) {
	    				attachInsufficientInventoryProblem(anItem, inventoryToUse, updatedRequestedQuantity, updatedAvailableQuantity);
		            } else {
	    				// we have enough inventory it seems, so set the assigned to the rounded up requested
	    				inventoryToUse.setAssignedQty(updatedRequestedQuantity);
		            }

		            inventoryToUse.setSelectedLocation(true);
		            
		            anItem.setFoundInMultipleLocations(false);
		            anItem.setChosenInventoryLocation(inventoryToUse);

				} else if (anItem.getInventory().size() > 1) { // Part found in multiple DCs
					
					logger.info("setInventoryProblem(...): Inside 'else if (anItem.getInventory().size() > 1)");
					if (bypassMultipleInventoryLocationsProblem) {
						// Skip attaching 'Part found in Multiple Locations' problem to this Item!!
						logger.debug("setInventoryProblem(...): Skipping attachPartFoundInMultipleLocationsProblem(...)");
						continue;
					}

					anItem.setFoundInMultipleLocations(true);
					InventoryBO chosenInventoryLocation = anItem.getChosenInventoryLocation();
					logger.debug("setInventoryProblem(...): chosenInventoryLocation: " + chosenInventoryLocation);
					if (chosenInventoryLocation == null) { // No inventory location has been chosen yet
						logger.debug("setInventoryProblem(...): Calling attachPartFoundInMultipleLocationsProblem(...)");
						attachPartFoundInMultipleLocationsProblem(anItem, updatedRequestedQuantity);
					} else { // User has already chosen the preferred inventory location
						// Replace the returned inventory list with the chosen inventory location.
						List<InventoryBO> inventoryList = new ArrayList<InventoryBO>();
						inventoryList.add(anItem.getChosenInventoryLocation());
						anItem.setInventory(inventoryList);
						anItem.setProblemItemList(null);
					}
				}
			}
		}
	}
	
	/**
     * For IPO orders and Non-IPO orders when parts are found in ONLY ONE DC:
     * 		- Handle zero inventory and insufficient inventory return codes
     * For Non-IPO orders when parts are found in MULTIPLE DCs:
     * 		- Handle 'Part found in multiple locations' problem.
     * @param itemList
     * @param orderSource	For IPO orders: OrderSource.IPO.<br>
     * 						For all other order types: null.
     */
	protected void setInventoryProblem(List<ItemBO> itemList, OrderSource orderSource) {
		setInventoryProblem(itemList, orderSource, false);
	}

	private void setAvailableQuantityForKit(KitBO aKit) {
        
        // calculate the available quantity based on the market strategy
        int availableQuantity = 0;
        if (getMarketAS().getKitComponentInventoryStrategy().isPartialOrderingAllowed() == true) {
            // partials allowed, so let the kit calculate its available based on config
            availableQuantity = aKit.getAvailableQuantityForKit();
        } else {
            // if partials is not allowed, the available is the requested and we fulfill whatever we can
            availableQuantity = aKit.getItemQty().getRequestedQuantity();
        }

        // now find the inventory to use
        // first try get the home dc inventory to know where this is coming from
        InventoryBO inventory = aKit.getMainDCInventory();
        
        if (inventory != null) {
            // set it
            inventory.setAvailableQty(availableQuantity);
        }
    }
	
	/**
     * Updates the requested quantity in the given item to the rounded down sold
     * in multiples amount if necessary
     * @param anItem
     * @return
     */
    private int getUpdatedRequestedQuantityForSoldInMultiples(ItemBO anItem) {
        
        int requestedQuantity = anItem.getItemQty().getRequestedQuantity();
        if (isSoldInMultiples(anItem) == false) {
            // not sold in multiples
            return requestedQuantity;
        }
        
        // this item is sold in multiples.  update the available quantity.
        int soldInMultiplesUnit = anItem.getItemQty().getSoldInMultipleQuantity();

        return roundUpToMultiplesQuantity(requestedQuantity, soldInMultiplesUnit);
    }
    
    /**
     * Determine if the given item is sold in multiples
     * @param anItem
     * @return
     */
    private boolean isSoldInMultiples(ItemBO anItem) {
        
        if (anItem.hasProblemItem()) {
            for (ProblemBO aProblem : anItem.getProblemItemList()) {
                if (MessageResourceUtil.SOLD_IN_MULTIPLES.equals(aProblem.getMessageKey())) {
                    return true;
                }
            }
        }
        
        // the previous check was against an already populated problem.  This check is for when
        // the requested amount was a multiple of the sold in multiples. 
        if (anItem.canProcessItem()) {
            return anItem.getItemQty().isSoldInMultiples();
        }
        
        return false;
    }
    
    /**
     * For the given sold in multiples quantity, rounds the given input quantity up to
     * the closest multiple.
     * @param inputQuantity
     * @param soldInMultiplesQuantity
     * @return
     */
    private int roundUpToMultiplesQuantity(int inputQuantity, int soldInMultiplesQuantity) {
        
        int upMultiple = (int)Math.ceil((double)inputQuantity / (double)soldInMultiplesQuantity);
        return upMultiple * soldInMultiplesQuantity;
    }
    
    /**
     * From the given item bo, return the inventory to use to check for inventory issues.  Note that "max available"
     * and "home dc" MAY be the same one, but should be transparent to users.
     * Rules are:
     * - home dc >= requested  --> return home dc inventory
     * - home dc < requested   --> use max available inventory
     * @param anItem
     * @return
     */
    private InventoryBO getInventoryToUse(ItemBO anItem) {
        
        // get the home dc inventory
        InventoryBO homeDCInventory = anItem.getMainDCInventory();
        
        int updatedHomeDCAvailableQuantity = getUpdatedHomeDCAvailableQuantityForSoldInMultiples(anItem);
        int requestedQuantity = getUpdatedRequestedQuantityForSoldInMultiples(anItem);
        
        // check if home DC can satisfy.  If so, return it.  If not, return the maximum available
        if(updatedHomeDCAvailableQuantity >= requestedQuantity) {
            // looks good
            return homeDCInventory;
        } else {
            InventoryBO maxAvailable = anItem.getMaximumAvailableQty();
            if (maxAvailable == null) {
                maxAvailable = new InventoryBO();
                anItem.addInventory(maxAvailable);
            }
            return maxAvailable;
        }
    }
    
    /**
     * Updates the available quantity in the inventory from the home dc's available quantity
     * to the rounded up sold in multiples if necessary.
     * @param anItem
     * @return
     */
    private int getUpdatedHomeDCAvailableQuantityForSoldInMultiples(ItemBO anItem) {
        
        InventoryBO homeDCInventory = anItem.getMainDCInventory();
        return getUpdatedAvailableQuantityForSoldInMultiples(anItem, homeDCInventory);
    }
	
    
    private int getUpdatedAvailableQuantityForSoldInMultiples(ItemBO anItem, InventoryBO anInventory) {
        
        // if the inventory is null, there's no available quantity here
        if (anInventory == null) return 0;
        
        int availableQuantity = anInventory.getAvailableQty();
        
        // If the minimum order quantity is greater than the available quantity, there is really 0 available
        if (anItem.getItemQty().getMinimumOrderQuantity() > availableQuantity) {
            return 0;
        }
        
        if (isSoldInMultiples(anItem) == false) {
            // not sold in multiples
            return availableQuantity;
        }
        
        // this item is sold in multiples.  update the available quantity.
        int soldInMultiplesUnit = anItem.getItemQty().getSoldInMultipleQuantity();
        return roundDownToMultiplesQuantity(availableQuantity, soldInMultiplesUnit);
    }
    
    /**
     * For the given sold in multiples quantity, rounds the given input quantity down to the
     * closest multiple.
     * @param inputQuantity
     * @param soldInMultiplesQuantity
     * @return
     */
    private int roundDownToMultiplesQuantity(int inputQuantity, int soldInMultiplesQuantity) {
        
        int downMultiple = (int)Math.floor((double) inputQuantity / (double) soldInMultiplesQuantity);
        return downMultiple * soldInMultiplesQuantity;
    }
    
    /**
     * Attach a no inventory problem to the input item bo for the inventory given.  If no inventory is given,
     * it will be instantiated.  Sets the assigned to the given requested amount and the available to 0
     * for that inventory.   
     * @param anItem
     * @param inventoryToUse
     * @param requestedQuantityToUse
     */
    private void attachNoInventoryProblem(ItemBO anItem, InventoryBO inventoryToUse, int requestedQuantityToUse) {
     
        // if the given inventory object to use is null, we instantiate one and
        // add it to the list.  
        if (inventoryToUse == null) {
            inventoryToUse = new InventoryBO();
            anItem.addInventory(inventoryToUse);
        }
        inventoryToUse.setAssignedQty(requestedQuantityToUse);
        inventoryToUse.setAvailableQty(0);
        
        if(isDiscontinuedPart(anItem) == false) {
            // not discontinued but no inventory, so processable.
	        ProblemBO noInventoryProblem = ProblemBOFactory.createProblem(MessageResourceUtil.NO_INVENTORY);
	        noInventoryProblem.setAllowedToProcess(true);
	        noInventoryProblem.addMessageParam(0, ""+requestedQuantityToUse);
	        anItem.addProblemItem(noInventoryProblem);
        }else{
        	ProblemBO noInventoryProblem = ProblemBOFactory.createProblem(MessageResourceUtil.DISCONTINUED_AND_NO_INVENTORY);
  	        noInventoryProblem.setAllowedToProcess(false);
  	        anItem.addProblemItem(noInventoryProblem);
  	        //DISCONTINUED_AND_NO_INVENTORY problem  will replace DISCONTINUED problem
 	        removeDiscontinuedProblem(anItem);
        }
    }
    
    /**
     * Determine if the given item is a discontinued part
     * @param anItem
     * @return
     */
    private boolean isDiscontinuedPart(ItemBO anItem){
        boolean discontinuedPart = false;
        if (anItem.hasProblemItem()) {
            for (ProblemBO aProblem : anItem.getProblemItemList()) {
            	//if a part with no inventory is a discontinued part,then it's no longer can be processed
                if (MessageResourceUtil.PART_BEING_DISCONTINUED.equals(aProblem.getMessageKey())) {
                    discontinuedPart = true;
                    break;
                }
            }
        }       
        return discontinuedPart;    	
    }
    
    /**
     * Remove a discontinued part problem from item's problem list
     * @param anItem
     * @return
     */
    private void removeDiscontinuedProblem(ItemBO anItem){
        for (ProblemBO aProblem : anItem.getProblemItemList()) {
            if (MessageResourceUtil.PART_BEING_DISCONTINUED.equals(aProblem.getMessageKey())) {
         	   anItem.getProblemItemList().remove(aProblem);
                break;
            }
         }
    }
    
	private void attachInsufficientInventoryProblem(ItemBO anItem,
			InventoryBO inventoryToUse, int requestedQuantityToUse,
			int availableQuantityToUse) {

		inventoryToUse.setAssignedQty(requestedQuantityToUse);
		inventoryToUse.setAvailableQty(availableQuantityToUse);
		ProblemBO inSufficientInventoryProblem;

		if (isDiscontinuedPart(anItem) == false) {
			inSufficientInventoryProblem = ProblemBOFactory
					.createProblem(MessageResourceUtil.INSUFFICIENT_INVENTORY);
			inSufficientInventoryProblem.setAllowedToProcess(true);
		} else {
			inSufficientInventoryProblem = ProblemBOFactory
					.createProblem(MessageResourceUtil.DISCONTINUED_AND_INSUFFICIENT_INVENTORY);
			inSufficientInventoryProblem.addMessageParam(0,
					availableQuantityToUse);
			inSufficientInventoryProblem.setAllowedToProcess(false);

			// DISCONTINUED_AND_INSUFFICIENT_INVENTORY problem will replace
			// DISCONTINUED problem
			removeDiscontinuedProblem(anItem);
		}

		anItem.addProblemItem(inSufficientInventoryProblem);
	}
	
	/**
     * Attach a 'Part found in multiple locations' problem to the passed-in ItemBO.
     * @param anItem
     * @param requestedQuantityToUse
     */
    private void attachPartFoundInMultipleLocationsProblem(ItemBO anItem, int requestedQuantityToUse) {
    	
    	if (anItem == null || anItem.getInventory() == null || anItem.getInventory().size() <= 1) {
    		return;
    	}
    	
   		ProblemBO partFoundInMultipleLocationProblem = ProblemBOFactory.createProblem(MessageResourceUtil.PART_FOUND_IN_MULTIPLE_LOCATIONS);
   		partFoundInMultipleLocationProblem.setAllowedToProcess(false);
		anItem.addProblemItem(partFoundInMultipleLocationProblem);

		// Get the inventory location to use.
		InventoryBO inventoryToUse = getInventoryToUse(anItem);
		// Create a map with DC Id as the key and Location as the value.
   		Map availableLocationsMap = new HashMap();
		// Add the inventory location to use to the map first.
		availableLocationsMap.put(inventoryToUse.getDistCntrId(), inventoryToUse);

   		List<InventoryBO> inventoryList = anItem.getInventory();

		for (InventoryBO anInventory : inventoryList) {
			anInventory.setAssignedQty(requestedQuantityToUse);
			// Get the updated Available Quantity for sold in multiples.
            int updatedAvailableQuantity = getUpdatedAvailableQuantityForSoldInMultiples(anItem, anInventory);
            if (updatedAvailableQuantity < requestedQuantityToUse) {
            	anInventory.setAvailableQty(updatedAvailableQuantity);
            }

    		// Add the other inventory locations to the map now.
            if (anInventory.getDistCntrId() != inventoryToUse.getDistCntrId()) {
            	availableLocationsMap.put(anInventory.getDistCntrId(), anInventory);
            }
		}
		
		// Set the 'Available Locations' map as the Corrective Options to resolve this problem.
		partFoundInMultipleLocationProblem.setCorrectiveOptions(availableLocationsMap);
    }
}

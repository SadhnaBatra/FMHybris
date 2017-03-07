package com.fmo.wom.business.as;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.ProblemBOFactory;

public abstract class ItemASBase {

	private static Logger logger = Logger.getLogger(ItemASBase.class);
	
	public final static int REASONABLE_QTY_DEFAULT = 50;
	
	public abstract Logger getLogger();
	
	/**
	 * Utility method to copy the item list
	 * 
	 * @param itemList
	 * @return
	 */
	public List<ItemBO> copyItemList(List<ItemBO> itemList) {
        List<ItemBO> newItemList = new ArrayList<ItemBO>();
        for (ItemBO anItem : itemList) {
            ItemBO newItem = anItem.createInitialCopy();
            // transfer the display part number to the part number just in case it doesn't get populated by
            // the back end
            if(GenericValidator.isBlankOrNull(newItem.getPartNumber()))
                newItem.setPartNumber(anItem.getDisplayPartNumber());
            // transfer the problems
            newItem.setProblemItemList(anItem.getProblemItemList());
            newItemList.add(newItem);
        }

        return newItemList;
    }
	
	/**
	 * Mark all items in the given list with part not found if they don't have a problem already.
	 * @param itemList
	 */
	public void markListNotAllowedToOrder(List<ItemBO> itemList) {
	
	    for (ItemBO anItem : itemList) {
            if (anItem.hasProblemItem() == false) { 
                ProblemBO notAllowedToOrderProblem = ProblemBOFactory.createProblem(MessageResourceUtil.NOT_ALLOWED_TO_ORDER);
                anItem.addProblemItem(notAllowedToOrderProblem);
            }
	    }
	}
	
	protected void processSoldInMultiple(List<ItemBO> itemList) {

		for (ItemBO lineItem : itemList) {
			processSoldInMultiple(lineItem);
		}
	}
	
	protected void processSoldInMultiple(ItemBO anItem) {
	    
	    if (anItem.canProcessItem()) {
            QuantityBO qty = anItem.getItemQty();
            int qtyUnit = qty.getSoldInMultipleQuantity();
            qty.setSoldInMultiples(false);

            if (qtyUnit > 0) {
                qty.setSoldInMultiples(true);
                if ((qty.getRequestedQuantity() % qtyUnit) != 0) {
                    // create a problem BO
                    ProblemBO soldInMultipleProblem = ProblemBOFactory.createProblem(MessageResourceUtil.SOLD_IN_MULTIPLES);
                    soldInMultipleProblem.addMessageParam(0, anItem.getPartNumber());
                    soldInMultipleProblem.addMessageParam(1, qtyUnit + "");
                    soldInMultipleProblem.setAllowedToProcess(true);
                    anItem.addProblemItem(soldInMultipleProblem);
                }
            }
        }
	}
	
	protected void processPriceUnavailable(List<ItemBO> itemList) {

		for (ItemBO lineItem : itemList) {
			if (lineItem.canProcessItem() == true) {
				if(lineItem.getItemPrice().getDisplayPrice() == 0.0) {
					// Create a problemBO
//					lineItem.setItemPrice(null);  REMOVED AS PART OF ISSUE 320 - NEED TO ACCESS FREIGHT PRICE IN WEB LAYER
					ProblemBO priceUnavailableProblem = ProblemBOFactory.createProblem(MessageResourceUtil.PRICE_UNAVAILABLE);
					priceUnavailableProblem.addMessageParam(0, lineItem.getDisplayPartNumber());
					priceUnavailableProblem.setAllowedToProcess(true);
					lineItem.addProblemItem(priceUnavailableProblem);
				}
			}
		}
	}
	
	/**
	 * Apply large and reasonable quantity threshold messages.
	 * @param itemList
	 */
    public void checkLoqThreshold(List<ItemBO> itemList) {
    	
    	for (ItemBO item : itemList) {
    	    
    	    if (item.canProcessItem() == false) {
    	        // don't worry about it
    	        continue;
    	    }
			QuantityBO quantity = item.getItemQty();
			int largeQuantityThreshold = quantity.getLargeQuantityThreshold();
			
			if ( largeQuantityThreshold > 0) { 
			    logger.error("Request quantity is over LoqQuantity for Line " + item.getLineNumber());
			    ProblemBO problem = ProblemBOFactory.createProblem(MessageResourceUtil.LARGE_QUANTITY_THRESHOLD);
			    problem.setAllowedToProcess(true);
			    item.addProblemItem(problem);
			} else {
			    // for some reason we only check this one if the prior one did not apply.
			    int requestedQuantity = quantity.getRequestedQuantity();
	            int reasonableQuantity = quantity.getReasonableQuantityThreshold();
				if(reasonableQuantity == 0) {
				    reasonableQuantity = REASONABLE_QTY_DEFAULT ;
				}
				if(requestedQuantity > reasonableQuantity){
					logger.error("Request quantity is over Reasonable Quantity for Line " + item.getLineNumber());
					ProblemBO problem = ProblemBOFactory.createProblem(MessageResourceUtil.REASONABLE_QUANTITY_THRESHOLD);
					problem.setAllowedToProcess(true);
					item.addProblemItem(problem); 
				}
			}
    	}
    }

}

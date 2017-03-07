package com.fmo.wom.business.as;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMBusinessAccessException;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMNoResultException;
import com.fmo.wom.common.exception.WOMNonUniqueResultException;
import com.fmo.wom.common.exception.WOMPersistanceException;
import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.ManualShipToAddressBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.OrderFulfillmentBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.ShippingCodeBO;
import com.fmo.wom.domain.ShippingMethodBO;
import com.fmo.wom.domain.ShippingMethodCountryBO;
import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.LineStatus;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderStatus;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.ProblemBOFactory;

public abstract class OrderASBase {
	
	protected abstract Logger getLogger();
	
	protected ShippingAS shippingAs;
	
	
	public OrderASBase() {
		this.shippingAs = new ShippingAS();
	}

	/**
     * Base work flow for processing an order. This is meant to define the workflow for all implementations
     * allowing abstract hooks into the functionality that differs across region and keeping common code
     * called for each workflow where it is meant to be called.
     * @param order The input order to process
     * @return the processed and persisted order
     * @throws WOMResourceException
     * @throws WOMValidationException
     * @throws WOMProcessException
     */
	public OrderBO processOrder(OrderBO order) throws WOMResourceException, WOMValidationException, WOMProcessException {
		
		String masterOrderNumber = order.getMstrOrdNbr();
		//String customerOrderNumber = order.getCustPoNbr();
		
		if (GenericValidator.isBlankOrNull(order.getMstrOrdNbr())) {
            // order needs a master order number to process
            masterOrderNumber = getMasterOrderNumber();
            order.setMstrOrdNbr(masterOrderNumber);
        }
		
		// make sure market is set
        //order.setMarketCode(getMarket());
		
     	// If a manual ship to address is associated with the order, make sure the ID of the address is 0
        // so that it will be persisted correctly.  Manual ship to address has a one to one relationship
        // with an order
        ManualShipToAddressBO manualShipToAddress = order.getManualShipToAddress();
        if (manualShipToAddress != null && manualShipToAddress.getManualShipToAddrId()!= 0) {
        	manualShipToAddress.setManualShipToAddrId(0);
        }
        validateOrderData(order);
        if (order.hasAtLeastOneGoodItem() == false) {
            order.setOrderStatus(OrderStatus.UNPROCESSED); 
            return order;
        }
        
        // Check the parts on the order
        //List<ItemBO> resolvedParts = checkOrderable(order);
        
        // process resolved results for processing this order.  Will use both the order and the resolved parts.
        //resolveItemProblems(order, resolvedParts);
        
        // check inventory stage complete
       //order.setOrderStatus(OrderStatus.CHECK_INVENTORY); 
        //order.setItemList(resolvedParts);
        //Modified Sid
     
        if(null == shippingAs){
        	shippingAs = new ShippingAS();	
        }
        shippingAs.setPromisedShipDate(order);
        
        /* if(orderDao == null){
    		orderDao = new OrderDAO();
    	}*/
    
	    /*if (order.hasAtLeastOneGoodItem() == false) {
	        // no items to process on the order
	        getLogger().debug("Master Order {}: No processable items remaining on order.  Process order completing prematurely", 
	        					masterOrderNumber);
	        orderDao.persist(order);
	        return order;
	    }
   
	    if (UsageType.QUOTE == order.getUsageType()) {
	        // this is a quote so we are done.
	        orderDao.persist(order);
	        return order;
	    }*/ //Modified Sid
	    
	    // We are passed the IPO stage so this order is going to be processed.  Need to rework the
	    // quantities a touch based on user back order policy selection where applicable.
	    //updateItemQuantities(order);
    

	    order = prepareForOrderSubmissionForAllMarket(order);
    
		// if order is not allowed to proceed then we stop
		if(canContinueProcessing(order) == false){
			return order;
		}

	    // submitOrder
	    OrderBO submittedOrder = submitOrder(order);
	    
	    // allow any clean up
	    OrderBO completedOrder = postProcessOrder(submittedOrder); 
    
	    // create order fulfillments
	    createOrderFulfillment(completedOrder);
    
	    submittedOrder.setOrderStatus(OrderStatus.FULFILLED); 
	    // send confirmation 
	    if(order.getOrderSource() != OrderSource.IPO) {
	    	sendOrderConfirmationEmail(completedOrder);
	    }

	    // persist
	    //orderDao.persist(completedOrder);
	    
	    return completedOrder;

	}
	
	protected void resolveItemProblems(OrderBO order, List<ItemBO> resolvedPartsList) {
    	
        //For web order, after return checkInventory, try to handle zero inventory and insufficient inventory based on order type
        resolveInventoryProblemWithOrderType(order, resolvedPartsList);
    }
	
	private void resolveInventoryProblemWithOrderType(OrderBO order, List<ItemBO> resolvedPartsList){
	     for (ItemBO anItem : resolvedPartsList) {
		    if (anItem.hasProblemItem() == true) {
		        // these only apply if the order type is emergency
		        if (OrderType.EMERGENCY == order.getOrderType()) {
		            for (ProblemBO aProblem: anItem.getProblemItemList()) {
		                if( MessageResourceUtil.NO_INVENTORY.equals(aProblem.getMessageKey())) { 
		                    // this is a weird one.  Probably stop for now
		                    aProblem.setAllowedToProcess(false);
		                } else if (MessageResourceUtil.INSUFFICIENT_INVENTORY.equals(aProblem.getMessageKey())) {
		                    // got insufficient inventory.  If there is not an appropriate back order policy, this one is no good.
		                    if (anItem.getBackorderPolicy() != BackOrderPolicy.SHIP_AND_CANCEL) {
		                        aProblem.setAllowedToProcess(false);
		                    }
		                }
					}							
				}			
		    }
		}
	}
	
	/**
     * The quantities in the given order will have all available and requested
     * amounts in them. To process this order, we need to take the back order
     * policy supplied by the user and "move" some of the quantities. <br>
     * Example 1: user requests 10, there are 5 available. This is reported as
     * insufficient quantity. If the user selected "Back order all", we need to
     * reduce the assigned quantity to 0 to force the back order of all. <br>
     * Example 2: user requests 10, there are 9 available. This is reported as
     * insufficient quantity. If the user selected "Ship 9 and cancel the rest",
     * the new requested quantity is 9. However, if there are now 8 available, we
     * need to reduce the assigned to 8 to accommodate their request.
     * 
     * @param order
     */
    protected void updateItemQuantities(OrderBO order) {

        List<ItemBO> itemList = order.getItemList();

        for (ItemBO anItem : itemList) {
            // if there is a problem on the item but it is allowed to process,
            // apply logic
            if (anItem.canProcessItem() == true && anItem.hasProblemItem()) {

                // go through the problems. If there is an insufficient
                // inventory problem, we check into
                // the quantities
                for (ProblemBO aProblem : anItem.getProblemItemList()) {

                    if (MessageResourceUtil.INSUFFICIENT_INVENTORY.equals(aProblem.getMessageKey())) {
                        // got insufficient inventory.
                        if (anItem.getBackorderPolicy() == BackOrderPolicy.SHIP_AND_CANCEL) {
                            // insufficient inventory on ship and cancel -
                            // update quantities
                            InventoryBO selectedInventory = anItem.getSelectedInventory();
                            if (selectedInventory != null) {
                                // we are going to back order all so assign NONE
                                selectedInventory.setAssignedQty(selectedInventory.getAvailableQty());
                            }
                        } else if (anItem.getBackorderPolicy() == BackOrderPolicy.BACKORDER_ALL) {
                            // insufficient inventory on back order all - update
                            // quantities to assign none
                            InventoryBO selectedInventory = anItem.getSelectedInventory();
                            if (selectedInventory != null) {
                                // we are going to back order all so assign NONE
                                selectedInventory.setAssignedQty(0);
                            }
                        }
                    }
                }
            }
        }
    }
	
	protected abstract Market getMarket();
	
	public abstract String getMasterOrderNumber() ;

	protected abstract OrderBO preprocessOrder(OrderBO order) 
	        throws WOMValidationException, WOMBusinessDataException, WOMBusinessAccessException, WOMResourceException;
	
	protected abstract OrderBO prepareForOrderSubmission(OrderBO order) throws WOMValidationException;

	protected abstract OrderBO submitOrder(OrderBO order) 
	        throws WOMResourceException,WOMProcessException, WOMValidationException;
    
	protected abstract OrderBO postProcessOrder(OrderBO order);
    
	protected abstract List<ItemBO> checkOrderable( OrderBO anOrder) 
            throws WOMExternalSystemException, WOMTransactionException, WOMBusinessDataException, WOMValidationException;
	
	protected void validateOrderData(OrderBO anOrder) throws WOMValidationException {

	    if (anOrder.getItemList() == null || anOrder.getItemList().size() == 0) {
	        // no items
	        return;
	    }
	    
	    // first check all the line numbers for dups.  Encountering this scenario is really bad and results in
	    // an exception thrown currently.  We could simply mark the dup lines at unprocessable if desired.
	    if (hasDuplicateLineNumbers(anOrder.getItemList()) == true) {
	        throw new WOMValidationException("Invalid input - duplicate Line Number number found in line items list.");
	    }
	    
	    // check for required data on each item
	    for (ItemBO item : anOrder.getItemList()) {
	        QuantityBO quantity = item.getItemQty();
            if (quantity == null) {
                ProblemBO problem = ProblemBOFactory.createProblem(MessageResourceUtil.MISSING_REQ_QTY);
                item.addProblemItem(problem);
            } else if (quantity.getRequestedQuantity() == 0) {
                ProblemBO problem = ProblemBOFactory.createProblem(MessageResourceUtil.ZERO_REQ_QTY);
                item.addProblemItem(problem);
            }

            if (GenericValidator.isBlankOrNull(item.getDisplayPartNumber())) {
                ProblemBO problem = ProblemBOFactory.createProblem(MessageResourceUtil.MISSING_PART_NUMBER);
                item.addProblemItem(problem);
            } else if (item.getDisplayPartNumber().length() > 35) {
                //trim the display part number for persistent purpose
                item.setDisplayPartNumber(item.getDisplayPartNumber().substring(0, 34));
                ProblemBO problem = ProblemBOFactory.createProblem(MessageResourceUtil.PART_NOT_FOUND);  
                item.addProblemItem(problem);
            } 
	    }
	}
	
	protected boolean hasDuplicateLineNumbers(List<ItemBO> itemList) {
	    
	    if (itemList == null || itemList.size() == 0) return false;
	    
	    // map to hold sequence numbers.  They must be unique and populated
        Set<Integer> lineSeqNumberMap = new HashSet<Integer>();
        
        for (ItemBO anItem : itemList) {
            
            if (lineSeqNumberMap.contains(anItem.getLineNumber()) == true) {
                return true;
            } else {
                lineSeqNumberMap.add(anItem.getLineNumber());
            }
        }

        return false;
	}
	
	
	public OrderBO prepareForOrderSubmissionForAllMarket(OrderBO order)throws WOMValidationException{
  	  
        processFutureOrderDate(order);
        return prepareForOrderSubmission(order);
    }
	
	public void processFutureOrderDate(OrderBO order) {
        
	    //check if the order is future order
	    if (order.getFutureDate() != null) {
	    	 for (ItemBO anItem: order.getItemList()) {
		        InventoryBO selectedInventory = anItem.getSelectedInventory();
		        if (selectedInventory != null) {
		        	// we are going to assign NONE for future order
		            selectedInventory.setAssignedQty(0);
		        }		        	
	        }	        	
	    }    	
    }
	
	
	protected void resolveShippingCodes(ItemBO anItem, AddressBO shipToAddress) {
        // determine shipping method codes		
		ShippingMethodBO selectedShippingMethod = anItem.getSelectedShippingMethod();
        if(selectedShippingMethod == null) {
            // if shipping method does not exist then return
            ProblemBO noShippingMethod = ProblemBOFactory.createProblem(MessageResourceUtil.INVALID_SCAC_CODE_OR_SHIPPING_METHOD);
            anItem.addProblemItem(noShippingMethod);
            return;
        }
		
		InventoryBO anInventory = anItem.getSelectedInventory();
		String fromCountryCode = anInventory.getDistributionCenter().getAddress().getCountry().getIsoCountryCode();
		long shippingMethodId = selectedShippingMethod.getShippingMethodId();
		String toCountryCode = shipToAddress.getCountry().getIsoCountryCode();
		try {
		    ShippingMethodCountryBO fromAndToCountry = null;/*shippingMethodCountryDao.findByActiveShippingMethodAndCountryCodes(shippingMethodId, 
		    												fromCountryCode, toCountryCode);*/
			ShippingCodeBO shippingCode = getShippingCode(anItem, anInventory, fromAndToCountry);
			anInventory.setShippingCode(shippingCode);
		} catch (WOMPersistanceException e) {
			// we need SCAC for all inventories - typically there is 1
			// if 2 exist then SCAC for both are needed
			ProblemBO problemScac = ProblemBOFactory.createProblem(MessageResourceUtil.NO_INTERNAL_SHIPCODE_MATCH_FOR_SCAC_CODE);
			anItem.addProblemItem(problemScac);
		}
	}
	
	protected ShippingCodeBO getShippingCode(ItemBO theItem, InventoryBO theInventory, ShippingMethodCountryBO fromAndToCountry)
	        throws WOMNoResultException, WOMNonUniqueResultException {
	    
	    ShippingCodeBO shippingCode = null;
	    if(theItem.getExternalSystem() == ExternalSystem.NABS) {
            shippingCode = shippingAs.findNabsShipCode(theInventory.getDistributionCenter().getDistCenterId(), 
            							theItem.getSelectedShippingMethod(), fromAndToCountry.getShippingMethodCountryId());
        } /*else if (theItem.getExternalSystem() == ExternalSystem.BPCS) {
            shippingCode = shippingAs.findBPCSShipCode(theInventory.getDistributionCenter().getDistCenterId(), 
            							theItem.getSelectedShippingMethod(), fromAndToCountry.getShippingMethodCountryId());
        } */else {   
            shippingCode = shippingAs.findSapShipCode(theInventory.getDistributionCenter().getDistCenterId(), 
            							theItem.getSelectedShippingMethod(), fromAndToCountry.getShippingMethodCountryId());
        }
	    return shippingCode;
    }
	
	protected boolean canContinueProcessing(OrderBO order) {
        return (OrderSource.IPO == order.getOrderSource() ||
                order.hasAllProcessableItems() == true);
    }
	
	protected void clearAllOrderFulfillments(OrderBO order) throws WOMResourceException {
		
		List<ItemBO> itemList = order.getItemList();
		
		for(ItemBO anItem: itemList) {
			
			anItem.getOrderFulfillmentList().clear();
			anItem.setLineStatusCd(LineStatus.ERROR);
		}
		
		order.setOrderStatus(OrderStatus.CANCELLED);
		//orderDao.persist(order);
	}
	
	protected void createOrderFulfillment(OrderBO order) {
		
	    // create order fulfillment objects as per the order and persist.
		List<ItemBO> itemList = order.getItemList();
		
		for(ItemBO anItem: itemList) {
		
		    List<OrderFulfillmentBO> fulfillmentList = new ArrayList<OrderFulfillmentBO>();
	        
		    if(anItem.canProcessItem()) {
		        
				InventoryBO selectedInventory = anItem.getSelectedInventory();
				if (selectedInventory == null) {
					anItem.setLineStatusCd(LineStatus.PROBLEM);
				    continue;
				}
				
				OrderFulfillmentBO fulfillment = new OrderFulfillmentBO();
				
				if(order.getBackOrderPolicy() == BackOrderPolicy.SHIP_AND_BACKORDER ||
				   order.getBackOrderPolicy() == BackOrderPolicy.BACKORDER_ALL) {
				    fulfillment.setBackorderedQty(anItem.getItemQty().getRequestedQuantity() - selectedInventory.getAssignedQty());
				}
				
				fulfillment.setAssignedQty(selectedInventory.getAssignedQty());
				fulfillment.setDistCntrId(selectedInventory.getDistCntrId());
				fulfillmentList.add(fulfillment);
				anItem.setLineStatusCd(LineStatus.SUCCESS);
		    }
		    
			anItem.setOrderFulfillmentList(fulfillmentList);
		}
	}
	
	/**
	 * Updates order fulfillment objects for persisting and keeping track
	 * @param order
	 * @throws WOMResourceException
	 */
	protected void updateOrderFulfillment(OrderBO order) throws WOMResourceException {
		// update order fulfillment objects as per the order and persist.
		List<ItemBO> itemList = order.getItemList();
		
		for(ItemBO anItem: itemList) {
			if(anItem.hasProblemItem()) {
				List<OrderFulfillmentBO> fulfillmentList = anItem.getOrderFulfillmentList();
				if(fulfillmentList != null) {
					anItem.getOrderFulfillmentList().clear();
					anItem.setLineStatusCd(LineStatus.PROBLEM);
				}
			}
		}
		//orderDao.persist(order);
	}
	
	protected void sendOrderConfirmationEmail(OrderBO completedOrder) {
    	//List<String> toAddressList = getOrderConfirmationEmailToAddressList(completedOrder);
    	//OrderConfirmationReportType reportType = getOrderConfirmationReportType(completedOrder);
    	//emailAS.sendOrderConfirmationEmail(completedOrder, toAddressList, reportType );
    }
	
	/*protected OrderConfirmationReportType getOrderConfirmationReportType(OrderBO completedOrder) { 
    	OrderConfirmationReportType reportType = OrderConfirmationReportType.BY_PARTNUMBER;
    	return reportType;
    }*/

    protected List<String> getOrderConfirmationEmailToAddressList(OrderBO order) {
    	List<String> toAddressList = null;
//    	UserAccountBO userAccount = userAcctAS.getUserAccount(order.getUserId());
//    	if (userAccount != null) {
//    		UserBO userBO = userAccount.getLdapUser();
//    		if (userBO != null) {
//    			String emailAddress = userBO.getEmailAddress();
//    			if (emailAddress != null) {
//    				toAddressList = new ArrayList<String>(1);
//    				toAddressList.add(emailAddress);
//    			}
//    			List<String> roles = userBO.getRoles();
//    			getLogger().debug("USER ROLES ARE {}", roles);
//    		}
//    	}
    	return toAddressList;
    }
	
}



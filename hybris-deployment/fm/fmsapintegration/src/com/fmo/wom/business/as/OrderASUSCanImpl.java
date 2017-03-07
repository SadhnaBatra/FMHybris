package com.fmo.wom.business.as;

import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.business.as.config.USCanAS;
import com.fmo.wom.common.exception.WOMBaseCheckedException;
import com.fmo.wom.common.exception.WOMBusinessAccessException;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMPersistanceException;
import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.PossibleOrderTypeBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.SapShippingCodeBO;
import com.fmo.wom.domain.ShippingCodeBO;
import com.fmo.wom.domain.TradingPartnerBO;
import com.fmo.wom.domain.TradingPartnerShippingMethodBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderStatus;
import com.fmo.wom.integration.NabsService;
import com.fmo.wom.integration.SAPService;
import com.fmo.wom.integration.util.ItemListUtil;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.ProblemBOFactory;

public class OrderASUSCanImpl extends OrderASBase implements OrderASUSCan, USCanAS{

	private static Logger logger = Logger.getLogger(OrderASUSCanImpl.class);

	@Override
    protected Logger getLogger() {
        return logger;
    }
	
	private NabsService nabsService;
	private FreeFreightAS freeFreightAs;
	
	public OrderASUSCanImpl() {
		super();
		nabsService = WOMServices.getNabsService();
		freeFreightAs = new FreeFreightAS();
	}


	@Override
	/** 
	 * NA gets master order numbers via the nabs service    
	 */
	public String getMasterOrderNumber() {
	    return nabsService.getMasterOrderNumber();
	}


	@Override
    public OrderBO processUploadOrder(OrderBO order)
            throws WOMValidationException, WOMResourceException  {
        
        // Get some of the attributes we'll need throughout
        String masterOrderNumber = order.getMstrOrdNbr();
        String customerOrderNumber = order.getCustPoNbr();
        getLogger().info(" processUploadOrder() .. Customer Order: "+ customerOrderNumber+ ":  Generated Master Order Number "+ masterOrderNumber);
        if (GenericValidator.isBlankOrNull(order.getMstrOrdNbr())) {
            // order needs a master order number to process
            masterOrderNumber = getMasterOrderNumber();
            order.setMstrOrdNbr(masterOrderNumber);
        }
        getLogger().info(" processUploadOrder() start order validaton for Master Order "+masterOrderNumber);
        
        // validate the order data 
        validateOrderData(order);
        getLogger().info("processUploadOrder() -Completed order validation for Master Order "+ masterOrderNumber);

        if (order.hasAtLeastOneGoodItem() == false) {
            getLogger().info("processUploadOrder() - No processable items remaining on order. Upload Process order completing prematurely for Master Order "+ masterOrderNumber);
            order.setOrderStatus(OrderStatus.UNPROCESSED); 
            return order;
        }
        
        
        //Check if the order contains both FM parts and Carter parts
        checkCarterMixedOrder(order.getItemList());
        
        // determine free freight here as back end needs it
        order.setReceivesFreeFreight(freeFreightAs.isReachedFreeFreightAmt(order));
        getLogger().info(" processUploadOrder() NABS Upload Order for Master Order "+masterOrderNumber);
        // in theory, good to go. Send it to NABS first
        int nabsUploadedOrderSequenceId = nabsService.executeUploadOrder(order);
        getLogger().info(" processUploadOrder() NABS Upload Order created for Master Order "+masterOrderNumber);
        
        // now take it to the SAP BAPI
        SapAcctBO sapAccount = order.getBillToAcct().getSapAccount();
            
        String sapShipToAccountCode = null;
        if (order.getShipToAcct() != null) {
            SapAcctBO sapShipTo = order.getShipToAcct().getSapAccount();
            if (sapShipTo != null) {
                sapShipToAccountCode = sapShipTo.getSapAccountCode();
            }
        }
            
        if( sapAccount != null ) {
            // has a sap account - send it to them.
            try {
                // set shipping method to truck
                updateShippingMethodForUpload(order);
                getLogger().info(" processUploadOrder() ECC Upload Order for Master Order "+masterOrderNumber);
                String sapConfirmationNumber = SAPService.processOrder(order, sapAccount.getSapAccountCode(), 
                                                                    sapShipToAccountCode, sapAccount.getCustomerSalesOrganization());
                order.setSapConfirmationNumber(sapConfirmationNumber);
                getLogger().info(" processUploadOrder() ECC Upload Order created for Master Order "+masterOrderNumber+" sapConfirmationNumber "+sapConfirmationNumber);
            } catch (WOMExternalSystemException ext) {
                try {
                    cancelNabsOrder(order.getMstrOrdNbr(),nabsUploadedOrderSequenceId,ext);
                } catch(Exception ex){
                	throw new WOMResourceException(ex);
                }
            } catch (WOMValidationException val) {
                try {
                    cancelNabsOrder(order.getMstrOrdNbr(),nabsUploadedOrderSequenceId,val);
                } catch(Exception ex){
                	throw new WOMResourceException(ex);
                } 
            }
        } 

        // we have process the order.  We may have attached InventoryBO objects to the items in this order for quantity 
        // and assigned values or shipping codes, but there is no DC info for uploads. Because of this, we have to get
        // rid of these on the order to avoid any foreign key issues on not finding DCs.  
        removeInventories(order);
        
        // good to go - tell nabs we're good to go
        nabsService.updateUploadOrderToProcessable(nabsUploadedOrderSequenceId);
        // this is fulfilled.  We are NOT persisting here.  Leave that to the top level upload AS
        order.setOrderStatus(OrderStatus.FULFILLED); 
        getLogger().info("processUploadOrder() - Master Order "+ order.getMstrOrdNbr()+ ": Customer Order="+ order.getCustPoNbr()+ " upload processed.");
        return order;
    }

	private void updateShippingMethodForUpload(OrderBO order) {

		ShippingCodeBO standardShippingCodesForUpload = getUploadShippingCode();
	    for (ItemBO anItem : order.getItemList()) {
	        InventoryBO inventoryToUse = anItem.getSelectedInventory();
	        if (inventoryToUse == null) {
	            inventoryToUse = new InventoryBO();
	            inventoryToUse.setAssignedQty(0);
	            inventoryToUse.setSelectedLocation(true);
	            anItem.addInventory(inventoryToUse);
	        }
	        
	        inventoryToUse.setShippingCode(standardShippingCodesForUpload);
	    }
	}
	
	/**
	 * construct the sap and nabs shipping information for upload orders since there are no DCs
	 * involved.  Could data drive this if we foresee the need for changeability.
	 * @return
	 */
	private synchronized static ShippingCodeBO getUploadShippingCode() {
	    
		ShippingCodeBO uploadShippingCode = new ShippingCodeBO();
        SapShippingCodeBO uploadSapShippingCode = new SapShippingCodeBO();
        uploadSapShippingCode.setCarrierCode("");
        uploadSapShippingCode.setRoute("");
        uploadSapShippingCode.setIncoTerms("ZPA");
        
        uploadShippingCode.setSapShippingCode(uploadSapShippingCode);
	    return uploadShippingCode;
	}
	
	 private void removeInventories(OrderBO order) {

        for (ItemBO anItem : order.getItemList()) {
            
            List<InventoryBO> inventoryList = anItem.getInventory();
            anItem.setInventory(null);
            if (inventoryList == null) {
                continue;
            }

            for (InventoryBO anInventory : inventoryList) {
                // make sure parent reference is dust
                anInventory.setItem(null);
            }
        }        
	 }
	 
	private void cancelNabsOrder(String masterOrderNumber, int nabsUploadedOrderSequenceId, WOMBaseCheckedException e) throws Exception{
	    getLogger().error("Master Order "+ masterOrderNumber+ ": Problem in submitting uploaded order to SAP: "+ e.getMessage());
        // if we've already sent parts to NABS, this catch will allow us to deal with them.  Either mark all sap parts as not
        // ordered and say the order was processed, or delete all the NABS parts?
        nabsService.cancelUploadOrder(nabsUploadedOrderSequenceId);
	}
	
	@Override
	public List<PossibleOrderTypeBO> calculatePossibleOrderTypes(OrderBO anOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Market getMarket() {
		return Market.US_CANADA;
	}


	@Override
	protected OrderBO preprocessOrder(OrderBO order) throws WOMValidationException, WOMBusinessDataException,
																		WOMBusinessAccessException, WOMResourceException {
		return order;
	}


	@Override
	protected OrderBO prepareForOrderSubmission(OrderBO order)
			throws WOMValidationException {
		List<ItemBO> itemList = order.getItemList();
        
        //Check if the order contains both FM parts and Carter parts
        checkCarterMixedOrder(itemList);
         
        for(ItemBO anItem: itemList) {
        	if(anItem.canProcessItem()) {
        		AccountBO shipTo = order.getShipToAcct();
        		
        		// if this order is IPO, we need to retrieve the shipping method via the trading partner
        		/*if(order.getOrderSource() == OrderSource.IPO) {
        			TradingPartnerBO tp = order.getTradingPartner();
        			if(!assignShippingMethodForInventory(anItem, tp)) {
        				// No shipping method found, do not bother resolving for shipping codes.
        				continue;
        			}
        		}*/
        		AddressBO shipToAddress = null;
        		if (shipTo != null) {
        		    shipToAddress = shipTo.getAddress();
        		}
        		
        		if (order.getManualShipToAddress() != null) {
        		    shipToAddress = order.getManualShipToAddress().getAddress();
        		}
        		if (shipToAddress != null) {
        		    //resolveShippingCodes(anItem, shipToAddress);
        		}
        	} 
        }

        return order;
	}

	
	/**
	  * Check if the order contains both FM parts and Carter parts
	  * @param itemList
	  * @return
	  * @throws WOMValidationException 
	  */
	public void checkCarterMixedOrder(List<ItemBO> itemList) throws WOMValidationException{
		
		boolean hasFmPart = false;
		boolean hasCarterPart = false;;
					
	   for (ItemBO anItem : itemList) {
							   
		   if( anItem.isCarterPart()) { 
	           // This is a Carter part
			   hasCarterPart = true;
	        }else {
	           hasFmPart = true;
	        }
		   if(hasFmPart && hasCarterPart)
			   throw new WOMValidationException("Carter parts and Federal Mogul parts can not be combined in one order.");
		 }			   			    			  		   

	}
	
	/**
	 * Finds the SAP and NABS shipping code for each item. All inventory in the item need to have shipping method identified.
	 * Treat as problem if shipping method is not found.
	 * @param anItem
	 * @param tradingPartner
	 */
	private boolean assignShippingMethodForInventory(ItemBO anItem, TradingPartnerBO tradingPartner) {
		TradingPartnerShippingMethodBO tpShippingMethod = null;
		try {
			String scac = anItem.getScacCode();
			if(GenericValidator.isBlankOrNull(scac)) {
				tpShippingMethod = shippingAs.findTradingPartnerShipMtd(tradingPartner, anItem.getCarrierCode(), anItem.getShippingMethodCode());
			} else {
				tpShippingMethod = shippingAs.findTradingPartnerShipMtd(tradingPartner, anItem.getScacCode());
			}
			anItem.setSelectedShippingMethod(tpShippingMethod.getShippingMethod());
		} catch (WOMPersistanceException e) {
			ProblemBO invalidScac = ProblemBOFactory.createProblem(MessageResourceUtil.INVALID_SCAC_CODE_OR_SHIPPING_METHOD);
			anItem.addProblemItem(invalidScac);
			return false;
		} 
		return true;
	}


	@Override
	public OrderBO submitOrder(OrderBO order) throws WOMResourceException,
			WOMProcessException, WOMValidationException {
		// reserve and submit inventory
		OrderBO reservedOrder = null;
		if(orderHasNabsItemsForCheckout(order.getItemList())){
			reservedOrder = reserveOrder(order); 
		} 
    	OrderBO submittedOrder = submitOrderToBackEnds(reservedOrder !=null ?reservedOrder:order);
		//Modified Sid
    	//OrderBO submittedOrder = submitOrderToBackEnds(order);
    	return submittedOrder;	
    }

	private boolean orderHasNabsItemsForCheckout(List<ItemBO> itemList) {
        
        // if there's anything wrong with the input, there aren't any items to process
        if (itemList == null || itemList.size() == 0) {
            return false;
        }
        
        // first start with all the NABS items only
        List<ItemBO> nabsItemList = ItemListUtil.getNabsItemList(itemList);
        boolean hasNabsItemToCheckout = false;
        for (ItemBO anItem : nabsItemList ) {
            if (anItem.canProcessItem()) {
                // no problem on this item or the problem is a processable one, so we'll process it
                hasNabsItemToCheckout = true;
                break;
            }
        }
        
        return hasNabsItemToCheckout;
    }
	
	/**
	 * Reserve order in NABS. Does the 'INITIAL_ASSIGN'. Nothing in SAP.
	 * @param order
	 * @return
	 * @throws WOMResourceException
	 * @throws WOMProcessException
	 */
	protected OrderBO reserveOrder(OrderBO order) throws WOMResourceException, WOMProcessException {
		
		OrderBO returnedOrder = null;
		
		try {
			returnedOrder = nabsService.executeInitialAssignment(order);
		} catch (WOMResourceException e) {
			clearAllOrderFulfillments(order);
			throw e;
		}
		
		return returnedOrder;
	}
	
	private OrderBO submitOrderToBackEnds(OrderBO order) 
	        throws WOMResourceException, WOMProcessException, WOMValidationException {
		OrderBO bookedOrder = null;
		if(orderHasNabsItemsForCheckout(order.getItemList())){
			try {
			    OrderBO changedOrder = changeOrder(order);
				bookedOrder = nabsService.executeBook(changedOrder);
			} catch (WOMResourceException e) {
			    cancelNabsOrder(order);
				clearAllOrderFulfillments(order);
				throw e;
			}
		}
		
		//Commented by Sid
		
		String sapConfirmationNumber = "NO ECC ITEMS TO PLACE ORDER";
		List<ItemBO> sapItemsList = ItemListUtil.getSapItemList(order.getItemList());
		if(sapItemsList != null && (! (sapItemsList.isEmpty()))){
			try {
				SapAcctBO sapAcct = order.getBillToAcct().getSapAccount();
				
				String sapShipToAccountCode = null;
				if (order.getShipToAcct() != null) {
				    SapAcctBO sapShipTo = order.getShipToAcct().getSapAccount();
				    if (sapShipTo != null) {
				        sapShipToAccountCode = sapShipTo.getSapAccountCode();
				    }
				}
				
				if( sapAcct != null ) {
					sapConfirmationNumber = SAPService.processOrder(order, sapAcct.getSapAccountCode(), 
				                                       sapShipToAccountCode, sapAcct.getCustomerSalesOrganization());
				} else {
				}
				
			} catch (WOMResourceException e) {
				cancelNabsOrder(order);
				clearAllOrderFulfillments(order);
				throw e;
			} catch (WOMValidationException e) {
				cancelNabsOrder(order);
				clearAllOrderFulfillments(order);
				throw e;
			}
		}
		order.setSapConfirmationNumber(sapConfirmationNumber);
		logger.info("Submited the Order sapConfirmationNumber= "+sapConfirmationNumber);
		return order;
	}

	protected void cancelNabsOrder(OrderBO order) throws WOMResourceException, WOMProcessException {
		
		clearAllOrderFulfillments(order);
		// initiate NABS cancellation at the minimum
		try {
			nabsService.executeCancel(order);
		} catch (Exception e) {
			// nothing we can do...just log it
			logger.error("Error cancelling order, continuing....", e);
		}
		// SAP has no issues.
	}
	
	
	@Override
    protected OrderBO postProcessOrder(OrderBO order) {
        return order;
    }
	
	@Override
    protected List<ItemBO> checkOrderable(OrderBO anOrder) 
            throws WOMExternalSystemException, WOMTransactionException, WOMBusinessDataException, WOMValidationException {
        
        List<ItemBO> resolvedParts;
        // call checkOrderable
        
        /*if(null == freeFreightAs){
        	freeFreightAs = new FreeFreightAS();
        }*/
        
        /*if (anOrder.getOrderSource() == OrderSource.IPO) {
            resolvedParts = itemASUSCan.ipoCheckOrderable(anOrder, anOrder.getMstrOrdNbr(), anOrder.getItemList(), anOrder.getBillToAcct(), anOrder.getShipToAcct());
        } else*/ {
           resolvedParts = WOMServices.getPartResolutionService().checkOrderableAndInventory(anOrder.getMstrOrdNbr(), anOrder.getItemList(), anOrder.getBillToAcct(), anOrder.getShipToAcct());
        }
        
        // determine free freight here as IPO RFQ may want it
        //anOrder.setReceivesFreeFreight(freeFreightAs.isQualifyForFreeFreight(anOrder));
        return resolvedParts;
    }
	
	/**
	 * change order in NABS. Does the 'PROCESS_CHANGE'. Nothing in SAP.
	 * @param order
	 * @return
	 * @throws WOMResourceException
	 * @throws WOMProcessException
	 */
	protected OrderBO changeOrder(OrderBO order) throws WOMResourceException, WOMProcessException {
		OrderBO returnedOrder = null;
		
		try {
			returnedOrder = nabsService.executeProcessChanges(order);		
		} catch (WOMResourceException e) {
			clearAllOrderFulfillments(order);
			throw e;
		}
		
		try {
			updateOrderFulfillment(order);
		} catch (WOMResourceException e) {
			cancelNabsOrder(order);
			throw e;
		}
		return returnedOrder;
	}
}

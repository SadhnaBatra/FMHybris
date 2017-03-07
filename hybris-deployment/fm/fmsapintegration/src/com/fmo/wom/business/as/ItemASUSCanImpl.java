package com.fmo.wom.business.as;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fmo.wom.business.as.config.USCanAS;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.ExternalSystemStatusBO.StatusCategory;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.KitBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.integration.NabsService;
import com.fmo.wom.integration.SAPService;
import com.fmo.wom.integration.util.ItemListUtil;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.ProblemBOFactory;

public class ItemASUSCanImpl extends ItemASBase implements ItemAS, USCanAS {

	private static Logger logger = Logger.getLogger(ItemASUSCanImpl.class);
	
	@Override
	public Logger getLogger() {
		return logger;
	}
	
	private NabsService nabsService;
	
	@Override
	public Market getMarket() {
		return Market.US_CANADA;
	}
	
	
	public ItemASUSCanImpl() {
		super();
		nabsService   = WOMServices.getNabsService();
	}


	/**
     * 
     * @param masterOrderNumber
     * @param itemList
     * @param billToAccount
     * @param shipToAccount
     * @return
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     * @throws WOMBusinessDataException
	 * @throws WOMValidationException 
     */
	@Override
	public List<ItemBO> checkOrderableAndInventory(String masterOrderNumber, List<ItemBO> itemList, AccountBO billToAccount, 
													AccountBO shipToAccount) 
				throws WOMExternalSystemException, WOMTransactionException, WOMBusinessDataException, WOMValidationException {	
	    return checkOrderableWithOptionalInventory(masterOrderNumber, itemList, billToAccount, shipToAccount, true);
    }

	
	@Override
	public List<ItemBO> checkOrderable(String masterOrderNumber, List<ItemBO> itemList, AccountBO billToAccount, 
										AccountBO shipToAccount) 
	    	throws WOMExternalSystemException,WOMTransactionException, WOMBusinessDataException, WOMValidationException {    
	    
	    return checkOrderableWithOptionalInventory(masterOrderNumber, itemList, billToAccount, shipToAccount, false);
	}
	
	/**
	 * Common method that takes check orderable params with added param on whether to check inventory or not
	 * @param masterOdrNum
	 * @param itemList
	 * @param billToAccount
	 * @param shipToAccount
	 * @param checkInventory
	 * @return
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 * @throws WOMBusinessDataException
	 * @throws WOMValidationException
	 */
	private List<ItemBO> checkOrderableWithOptionalInventory(String masterOrderNumber, List<ItemBO> itemList, 
	                                                        	AccountBO billToAccount, AccountBO shipToAccount, 
	                                                        	boolean checkInventory) 
	        throws WOMExternalSystemException, WOMTransactionException, WOMBusinessDataException, WOMValidationException {   
		logger.info("checkOrderableWithOptionalInventory masterOrderNumber= "+masterOrderNumber+ " No Of Items= "+itemList.size()
								+ " billToAccount= "+billToAccount.getAccountCode()+ "  shipToAccount= "+shipToAccount.getAccountCode());
		// copy itemlist, pre-process the product flag and send to Nabs/SAP systems
        List<ItemBO> nabsItemList = copyItemList(itemList);
        List<ItemBO> sapItemList = copyItemList(itemList);      
        
        logger.info(" NABS Part Resoultion START ");
        
        // Call NABS
        String nabsAcctCode = billToAccount.getAccountCode();
         
        nabsItemList = executeNabsCheckOrderable(masterOrderNumber, nabsItemList, nabsAcctCode, 
				(shipToAccount != null) ? shipToAccount.getAccountCode():null);

        // massage sap list for migrated parts
        migrateParts(nabsItemList, sapItemList);
        
        //Check Nabs part list, mark non returnable parts as problem parts with warning message
        checkReturnable(nabsItemList);
        
        checkVintagePart(nabsItemList);
        logger.info(" NABS Part Resoultion END ");
        
        // Call SAP
        logger.info(" ECC Part Resoultion START ");
        
        sapItemList = executeSAPCheckOrderable(sapItemList,billToAccount.getSapAccount());
        logger.info(" ECC Part Resoultion END ");
        
        // We now have the returned part info from each system in the respective lists. Process these results
        
        return processResults(masterOrderNumber, billToAccount, shipToAccount, itemList, nabsItemList, sapItemList, checkInventory, null);
	}	
	
	private List<ItemBO> executeSAPCheckOrderable(List<ItemBO> inputItemList, SapAcctBO sapAccount) 
															throws WOMValidationException, WOMExternalSystemException {
		
	    List<ItemBO> resolvedItemList = inputItemList;
	    if ( sapAccount != null ) {
	        resolvedItemList = SAPService.checkOrderable(inputItemList, sapAccount.getSapAccountCode(), sapAccount.getCustomerSalesOrganization());
        } else {
            // not allowed to order any of the sap parts
            markListNotAllowedToOrder(resolvedItemList);
        }
	    
	    return resolvedItemList;
	}
	
	public void checkReturnable(List<ItemBO> nabsItemList) {

		for (ItemBO anItem : nabsItemList) {
			
			if ( !anItem.isCanReturnForCredit() ) {
				ProblemBO noReturnProblem = ProblemBOFactory.createProblem(MessageResourceUtil.NO_RETURN);
				noReturnProblem.setAllowedToProcess(true);
				anItem.addProblemItem(noReturnProblem);
			}
		}	
	}
	
	public void checkVintagePart(List<ItemBO> nabsItemList) {

		for (ItemBO anItem : nabsItemList) {
			
			if ( anItem.isVintagePart()) {
				final ProblemBO vintageProblem = ProblemBOFactory.createProblem(MessageResourceUtil.VINTAGE_PART);
				vintageProblem.setAllowedToProcess(true);
				anItem.addProblemItem(vintageProblem);
			}
		}	
	}
	
	private List<ItemBO> executeNabsCheckOrderable(String masterOrderNumber, List<ItemBO> inputItemList, String nabsBillToAccountCode, String nabsShipToAccountCode) 
            throws WOMTransactionException, WOMExternalSystemException { 
        return nabsService.checkOrderable(masterOrderNumber, inputItemList, nabsBillToAccountCode, nabsShipToAccountCode);
    }
	
	// Part migration from NABS to SAP.  Parts from a DC that used to be in NABS and have moved to SAP
    // will now be reported with a special status/category.  The part number we need to use to get SAP 
    // to find them will have been reported from NABS.  This will go through the returned nabs parts 
    // and look for this category of the problem to determine which part number to send to SAP.
    //
	private void migrateParts(List<ItemBO> nabsItemList, List<ItemBO> sapItemList) {
        Map<Integer, ItemBO> sapLineNumberItemMap = ItemListUtil.createLineNumberItemMap(sapItemList);
        
        // go through the list of resolved nabs parts and check for part migrated problem category.
        for (ItemBO aResolvedNabsPart : nabsItemList) {

            if (aResolvedNabsPart.isMigrated() == true) {
                // this one is migrated - get the sap part corresponding to the line number
                ItemBO correspondingSapPart = sapLineNumberItemMap.get(aResolvedNabsPart.getLineNumber());
                correspondingSapPart.setDisplayPartNumber(aResolvedNabsPart.getPartNumber());
                // product flag has been prepended by nabs
                correspondingSapPart.setProductFlag("");
                // this guy is migrated, so created the message
                ProblemBO supercededPartProblem = ProblemBOFactory.createSupercededPartProblem( aResolvedNabsPart.getPartNumber(), 
                																		aResolvedNabsPart.getDisplayPartNumber());
                correspondingSapPart.setPredecessorPartNumber( aResolvedNabsPart.getDisplayPartNumber());
                correspondingSapPart.addProblemItem(supercededPartProblem);
                
            }
        }
    }
	/**
	 * Process the results from the check inventory calls to resolve the parts between the 2 back end system supported
	 * by US Can.  If checkInventory is true, also check the inventory on the resolved items and sets the selected location
	 * per that algorithm.
	 * 
	 * @param masterOrderNumber
	 * @param billToAccount
	 * @param shipToAccount
	 * @param inputItemList
	 * @param nabsResolvedParts
	 * @param sapResolvedParts
	 * @param checkInventory
	 * @param orderSource Passed for IPO OrderSource. Null otherwise.
	 * @return
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 * @throws WOMBusinessDataException
	 * @throws WOMValidationException
	 */
	private List<ItemBO> processResults(String masterOrderNumber, AccountBO billToAccount, AccountBO shipToAccount, 
	                                    List<ItemBO> inputItemList,  List<ItemBO> nabsResolvedParts, 
	                                    List<ItemBO> sapResolvedParts, boolean checkInventory,
	                                    OrderSource orderSource) 
	         throws WOMExternalSystemException, WOMTransactionException, WOMBusinessDataException, WOMValidationException  {
	    
	    List<ItemBO> returnList = mergeResult(inputItemList, nabsResolvedParts, sapResolvedParts);
        
	    /*for (ItemBO anItem : returnList) {
            if (anItem.isKit()) {
                KitBO theKit = (KitBO) anItem;
                // get all the components if its not already configured.
                if (theKit.isConfigured() == false) {
                    //nabsService.getKitComponents(masterOrderNumber, billToAccount.getAccountCode(), theKit);
                }
            }
        }*/
	    
        // go through itemList check sold-in-multiple condition
        processSoldInMultiple(returnList);
        
        processPriceUnavailable(returnList);
        checkLoqThreshold(returnList);     
        
        if (checkInventory == true) {
        	/* Blocking the Inventory Check from Partresolution*/
            //WOMServices.getCheckInventoryService().checkInventory(masterOrderNumber, returnList, billToAccount, shipToAccount, orderSource);
            
            // reduce inventory list to selected ones for each item
           // WOMServices.getCheckInventoryService().getSelectedInventoryForAllItems(returnList);
        }
        return returnList;
	}
	
	/**
	 * Once we have resolved a given parts list in both NABS and SAP, we need to
	 * merge the two lists together based on the return codes of each part from
	 * each system.
	 * 
	 * @param itemList the original list of parts
	 * @param nabsLIs the check orderable response from NABS
	 * @param sapLIs the check orderable response from SAP
	 * @return the merged list of resolved parts
	 * @throws WOMBusinessDataException
	 */
	public List<ItemBO> mergeResult(List<ItemBO> itemList, List<ItemBO> nabsItemList, List<ItemBO> sapItemList)
			throws WOMBusinessDataException {

		List<ItemBO> result = new ArrayList<ItemBO>();
		
		if (nabsItemList.size() != sapItemList.size()) {
			// this is bad
			throw new WOMBusinessDataException("Returned parts lists from NABS and SAP not the same size!");
		}

		// go through the input item list and retrieve the associated item from
		// each response list.
		// if the item has no problem associated to it in one list and a problem
		// in the other list,
		// it was found in the first list.
		//
		// If the status is NOT FOUND in the NABS list, we will use the SAP part
		// problem.
		// If the status is NOT FOUND in the SAP list, we will use the NABS part
		// problem.
		for (int i = 0; i < itemList.size(); i++) {

			ItemBO inputItem = itemList.get(i);
			ItemBO nabsPart = nabsItemList.get(i);
			ItemBO sapPart = sapItemList.get(i);
			ProblemBO nabsProblem = nabsPart.getProblemItem();
			ProblemBO sapProblem = sapPart.getProblemItem();

			// quick sanity check here to make sure we're dealing with the same
			// part.
			if (inputItem.getLineNumber() != nabsPart.getLineNumber()
					&& inputItem.getLineNumber() != sapPart.getLineNumber()) {
				// what is going on here.
				logger.error("Input, NABS and SAP Line numbers at element "+i+ " do not match.");
				logger.error("Input line number = "+inputItem.getLineNumber()+ ", part number = "+inputItem.getDisplayPartNumber());
				logger.error("NABS line number = "+nabsPart.getLineNumber()+ ", part number = "+ nabsPart.getDisplayPartNumber());
				logger.error("SAP line number = "+sapPart.getLineNumber()+ ", part number = "+sapPart.getDisplayPartNumber());
				throw new WOMBusinessDataException("Returned parts lists from Input, NABS and SAP do not match line numbers!");
			}

			// If there is no problem on both systems, that's weird.
			if (nabsProblem == null && sapProblem == null) {
				logger.error("NABS Part " + nabsPart.getPartNumber() + " and SAP Part " + sapPart.getPartNumber()
						+ " found on both systems.");
				throw new WOMBusinessDataException("NABS Part "	+ nabsPart.getPartNumber() + " and SAP Part "
						+ sapPart.getPartNumber() + " found on both systems.");
			} 
			//Modified Sid
			/*if(null == nabsProblem){
				nabsProblem = new ProblemBO();
				nabsProblem.setMessageKey("");
			}
			
			if(null == sapProblem){
				sapProblem = new ProblemBO();
				sapProblem.setStatusCategory(StatusCategory.PART_FOUND);
				sapProblem.setMessageKey("");
			}*/
			// If there was no problem on NABS and a problem on SAP, this is a
			// good NABS part
			if (nabsProblem == null && sapProblem != null) {
				// The chosen inventory location info' needs to be set, else it will be lost.
				nabsPart.setChosenInventoryLocation(inputItem.getChosenInventoryLocation());
				logger.info("mergeResult(): nabsPart.getChosenInventoryLocation(): "+nabsPart.getChosenInventoryLocation());
				// put the NABS part in the result list
				result.add(i, nabsPart);
			}
			// If there was no problem on SAP and a problem on NABS, this is a
			// good SAP part
			else if (sapProblem == null && nabsProblem != null) {
				// The chosen inventory location info' needs to be set, else it will be lost.
				sapPart.setChosenInventoryLocation(inputItem.getChosenInventoryLocation());
				// put the SAP part in the result list
				result.add(i, sapPart);
			}
			// If we're here, this means there are problems on both NABS and
			// SAP. We want to use the part from the system that is found if
			// possible as that should be more informative
			else if (nabsProblem.getStatusCategory() == StatusCategory.PART_FOUND || 
			         nabsProblem.getStatusCategory() == StatusCategory.MULTIPLE_PART_FOUND) {
				// Item was FOUND in NABS, so we'll use the NABS part
				result.add(i, nabsPart);
			} else if (sapProblem.getStatusCategory() == StatusCategory.PART_FOUND ||
			        sapProblem.getStatusCategory() == StatusCategory.PART_SETUP_ERROR ) {
				// Item was FOUND on SAP, so we'll use the SAP part.
				result.add(i, sapPart);
			} else if (sapProblem.getMessageKey().equals(nabsProblem.getMessageKey()) == true) {
			    // Problems on both systems are the same but neither is PART_FOUND, doesn't matter which one we go with. 
			    result.add(i, nabsPart);
			} else {
			    // Some other weird kind of case here that we didn't plan for.  Problems on both systems, neither is PART_FOUND but 
			    // different and not in the previous cases.  Should this be an exception or just pick one?
				logger.error("Part merge error in NABS and SAP Line numbers at element "+i);
				logger.error("NABS part number = "+nabsPart.getDisplayPartNumber()
						+ ", problem status:key = "+nabsProblem.getStatusCategory()+":"+nabsProblem.getMessageKey());
				logger.error("SAP part number = "+sapPart.getDisplayPartNumber()
						+ ",  problem status:key = "+ sapProblem.getStatusCategory()+":"+sapProblem.getMessageKey());
				logger.error("Returning part number "+sapPart.getDisplayPartNumber()
						+ "with problem status:key = "+ nabsProblem.getStatusCategory()+":"+nabsProblem.getMessageKey());
                result.add(i, nabsPart);
			}
		}

		return result;
	}
	
}

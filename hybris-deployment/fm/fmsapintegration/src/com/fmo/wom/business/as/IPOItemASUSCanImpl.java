package com.fmo.wom.business.as;

import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.federalmogul.falcon.core.model.FMDCCenterModel;
import com.fmo.wom.business.as.config.USCanAS;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.ExternalSystemStatusBO.StatusCategory;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.KitBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.SupplierCdProductFlgBO;
import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.NabsService;
import com.fmo.wom.integration.SAPService;
import com.fmo.wom.integration.dao.SupplrCdProdFlgDAO;
import com.fmo.wom.integration.dao.SupplrCdProdFlgDAOImpl;
import com.fmo.wom.integration.util.ItemListUtil;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.ProblemBOFactory;

public class IPOItemASUSCanImpl extends ItemASBase implements ItemAS, USCanAS {

	private static Logger logger = Logger.getLogger(IPOItemASUSCanImpl.class);
	
	@Override
	public Logger getLogger() {
		return logger;
	}
	
	private NabsService nabsService;
	
	@Override
	public Market getMarket() {
		return Market.US_CANADA;
	}
	
	public IPOItemASUSCanImpl() {
		super();
		nabsService = WOMServices.getNabsService();
	}
	
	/** 
	 * USCan specific implementation for check orderable and inventory for the IPO call.
	 * @param masterOdrNum
	 * @param itemList
	 * @param billToAccount
	 * @param shipToAccount
	 * @return
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 * @throws WOMBusinessDataException
	 * @throws WOMValidationException
	 */
	public List<ItemBO> ipoCheckOrderable(OrderBO order, String masterOdrNum, List<ItemBO> itemList,
                                          AccountBO billToAccount, AccountBO shipToAccount ) 
                throws WOMExternalSystemException,WOMTransactionException, WOMBusinessDataException, WOMValidationException {   
	    
		if (itemList != null && itemList.size() > 0) {
			logger.info("ipoCheckOrderable(...): itemList.get(0).getRequestedInventory(): " + itemList.get(0).getRequestedInventory());
		}
	    populateFMInternalFlags(itemList);
	    
	    // copy itemlist, pre-process the product flag and send to Nabs/SAP systems
        List<ItemBO> nabsItemList = copyItemList(itemList);
        List<ItemBO> sapItemList = copyItemList(itemList);      
     
        if (nabsItemList != null && nabsItemList.size() > 0) {
        	logger.info("ipoCheckOrderable(...): nabsItemList.get(0).getRequestedInventory(): (BEFORE executeIPONabsCheckOrderable(...)): " + nabsItemList.get(0).getRequestedInventory());
        }

		// IPO specific prep
        prepareIpoItemList(nabsItemList,ExternalSystem.NABS);
        prepareIpoItemList(sapItemList,ExternalSystem.EVRST);   
        
        // Call nabs
        String nabsAcctCode = billToAccount.getAccountCode();
        nabsItemList = executeIPONabsCheckOrderable(masterOdrNum, nabsItemList, nabsAcctCode, 
        					(shipToAccount != null) ? shipToAccount.getAccountCode():null);
        
        if (nabsItemList != null && nabsItemList.size() > 0) {
        	logger.info("ipoCheckOrderable(...): nabsItemList.get(0).getRequestedInventory(): (AFTER executeIPONabsCheckOrderable(...)): " + nabsItemList.get(0).getRequestedInventory());
        }

       //mark all kit as problem items
        updateKitProblem(nabsItemList);
        
        // massage sap list for migrated parts
        migrateParts(nabsItemList, sapItemList);
        
        if (sapItemList != null && sapItemList.size() > 0) {
        	logger.info("ipoCheckOrderable(...): sapItemList.get(0).getRequestedInventory(): (BEFORE executeSAPCheckOrderable(...)): " + sapItemList.get(0).getRequestedInventory());
        }

        // Call SAP 
        sapItemList = executeSAPCheckOrderable(sapItemList,billToAccount.getSapAccount());
        
        if (sapItemList != null && sapItemList.size() > 0) {
        	logger.info("ipoCheckOrderable(...): sapItemList.get(0).getRequestedInventory(): (AFTER executeSAPCheckOrderable(...)): " + sapItemList.get(0).getRequestedInventory());
        }

        // Check if Shipping from TSCs has been enabled for this Trading Partner.
        boolean shippingFromTscEnabledForTP = ((order.getTradingPartner() != null && order.getTradingPartner().isEnableShippingFromTsc()) ? true : false);
        if (order.getTradingPartner() == null) {
        	logger.info("ipoCheckOrderable(...): order.getTradingPartner() returned NULL");
        }
        logger.info("ipoCheckOrderable(...): shippingFromTscEnabledForTP: " + shippingFromTscEnabledForTP);

        // We now have the returned part info from each system in the respective lists. Process these results  
        List<ItemBO> resolvedParts = ipoProcessResults(masterOdrNum, billToAccount, shipToAccount, itemList, 
        												nabsItemList, sapItemList, true, shippingFromTscEnabledForTP);
        
        if (resolvedParts != null && resolvedParts.size() > 0) {
        	logger.info("ipoCheckOrderable(...): resolvedParts.get(0).getRequestedInventory(): (AFTER ipoProcessResults(...)): " + resolvedParts.get(0).getRequestedInventory());
        }

        // updates specific for ipo
        updateForIpo(order, resolvedParts);
        if (resolvedParts != null && resolvedParts.size() > 0) {
        	logger.info("ipoCheckOrderable(...): resolvedParts.get(0).getRequestedInventory(): (AFTER updateForIpo(...)): " + resolvedParts.get(0).getRequestedInventory());
        }
		logger.debug(" Before updateItemShippingInfo(): resolvedParts " + resolvedParts);
		resolvedParts = updateItemShippingInfo(resolvedParts);
		logger.debug(" After updateItemShippingInfo(): resolvedParts " + resolvedParts);

		return resolvedParts;
	}
	
	/**
	 * For IPO orders, we need to look up and populate the product flag via the
	 * brand and flag mapping, one or more match will be found
	 * 
	 * @param itemList list of parts to look up.
	 */
	public void populateFMInternalFlags(List<ItemBO> itemList) {

		// local map for caching against the aaidBrand on each item. Saves db
		// calls on duplicate
		// brands in the order
		Map<String, List<SupplierCdProductFlgBO>> aaiaBrandMap = new HashMap<String, List<SupplierCdProductFlgBO>>();

		// populate FM internal product flag for item list for IPO order
		SupplrCdProdFlgDAO supplierDAO = new SupplrCdProdFlgDAOImpl();
		for (ItemBO anItem : itemList) {

			// if the item has a problem on it, no need to process
			if (anItem.hasProblemItem()) {
				continue;
			}

			String aaiaBrand = anItem.getAaiaBrand();

			List<SupplierCdProductFlgBO> supplierCodeProducFlagList = aaiaBrandMap.get(aaiaBrand);
			// if its not in the cache, check the db
			if (supplierCodeProducFlagList == null) {
				supplierCodeProducFlagList = supplierDAO.findActiveProdFlagBySupplCd(aaiaBrand);
				if(supplierCodeProducFlagList == null){
					supplierCodeProducFlagList = new ArrayList<SupplierCdProductFlgBO>(0);
				}
				aaiaBrandMap.put(aaiaBrand, supplierCodeProducFlagList);
			}

			if (supplierCodeProducFlagList.size() == 0) {
				// AAIA brand mapping not match to any product flag in Nabs
				// option list, set up PART_NOT_FOUND problem
				ProblemBO partNoFndProblem = ProblemBOFactory.createProblem(MessageResourceUtil.PART_NOT_FOUND);
				anItem.addProblemItem(partNoFndProblem);
			}

			// put the list found on the item
			anItem.setSupplierCodeProductFlagList(supplierCodeProducFlagList);
		}
	}

	/**
	 * For IPO order, need to manually pre-process productFlag in ITEM based on
	 * Nabs or SAP
	 * 
	 * @param itemList
	 * @param extSystem
	 * @return
	 */
	public void prepareIpoItemList(List<ItemBO> itemList, ExternalSystem externalSystem) {

		for (ItemBO anItem : itemList) {
			// if the item has a problem on it, no need to process
			if (anItem.hasProblemItem()) {
				continue;
			}

			List<SupplierCdProductFlgBO> supplierCodeProductFlagList = anItem.getSupplierCodeProductFlagList();
			// in theory, this list should always be populated, but if this
			// method ever gets called in isolation, we
			// want to protect against issues.
			if (supplierCodeProductFlagList == null	|| supplierCodeProductFlagList.size() == 0) {
				continue;
			}

			SupplierCdProductFlgBO firstProductFlag = supplierCodeProductFlagList.get(0);

			// if the external systems do not match, this is a part not found
			if (firstProductFlag.getExternalSystem() != externalSystem) {
				// lookup external system not match with specified input
				// external system set up PART_NOT_FOUND problem
				ProblemBO partNoFndProblem = ProblemBOFactory.createProblem(MessageResourceUtil.PART_NOT_FOUND);
				anItem.addProblemItem(partNoFndProblem);
			} else {
				// external systems match. If there was only one returned, we
				// can safely use this as the product flag.
				// Otherwise, we will do nothing to the Item.
				if (supplierCodeProductFlagList.size() == 1) {
					anItem.setProductFlag(supplierCodeProductFlagList.get(0).getProdFlg());
				}
			}
		}
	}
	
	/**
	 * For IPO order, after return from DD114, try to resolve multiple part
	 * found problem using input AAIA brand/supplier code
	 * 
	 * @param itemList
	 * @return true if we were given an item that was multiple parts found and
	 *         we resolved it against its product flag mapping. false otherwise
	 */
	public boolean resolveProductFlag(List<ItemBO> nabsItemList) {

		boolean isProductFlagResolved = false;

		for (ItemBO anItem : nabsItemList) {
			if (anItem.hasProblemItem()) { // problem existed after nabs return

			    List<SupplierCdProductFlgBO> supplierCodeProductFlagList = anItem.getSupplierCodeProductFlagList();
			    for (ProblemBO aProblem : anItem.getProblemItemList()) {
			        
			        if (aProblem.getStatusCategory() == StatusCategory.MULTIPLE_PART_FOUND) {
    					// problem is a Nabs multiple part found problem.
    					// get the list of matched parts from the problem and go
    					// through them to match up against the
    					// product flags list retrieved via the supplier code
    					Collection<ItemBO> matchingPartsList = (Collection<ItemBO>) (aProblem.getCorrectiveOptions().values());
    					boolean itemMatchFound = false;
    
    					for (ItemBO nabsOption : matchingPartsList) {
    						itemMatchFound = false;
    						for (SupplierCdProductFlgBO aSupplierCodeProductFlag : supplierCodeProductFlagList) {
    							if (aSupplierCodeProductFlag.getProdFlg().equalsIgnoreCase(nabsOption.getProductFlag())) {
    								// match found, problem solved,set lineItem
    								// product flag to match flag
    								anItem.setProductFlag(nabsOption.getProductFlag());
    								// clear problems
    								anItem.setProblemItemList(null);
    								itemMatchFound = true;
    								isProductFlagResolved = true;
    								break;
    							}
    						}
    						if (itemMatchFound == true)
    							break;
    					}
    					// if we did not find a match, this means AAIA brand mapping
    					// not match to any product flag
    					// in the Nabs option list. Set up PART_NOT_FOUND problem
    					if (itemMatchFound == false) {
    					    anItem.setProblemItemList(null);
    						ProblemBO partNoFndProblem = ProblemBOFactory.createProblem(MessageResourceUtil.PART_NOT_FOUND);
    						anItem.addProblemItem(partNoFndProblem);
    					}
			        }
				}
			}
		}
		return isProductFlagResolved;
	}
	
	private List<ItemBO> executeIPONabsCheckOrderable(String masterOrderNumber, List<ItemBO> inputItemList, String nabsBillToAccountCode, String nabsShipToAccountCode) 
	        throws WOMTransactionException, WOMExternalSystemException { 
	    List<ItemBO> resolvedItemList = nabsService.checkOrderable(masterOrderNumber, inputItemList, nabsBillToAccountCode, nabsShipToAccountCode);
	    resolveProductFlag(masterOrderNumber, resolvedItemList, nabsBillToAccountCode, nabsShipToAccountCode);
	    return resolvedItemList;
	}
	
	private List<ItemBO> executeSAPCheckOrderable(List<ItemBO> inputItemList, SapAcctBO sapAccount) throws WOMValidationException, WOMExternalSystemException {
	    List<ItemBO> resolvedItemList = inputItemList;
	    if ( sapAccount != null ) {
	        resolvedItemList = SAPService.checkOrderable(inputItemList, sapAccount.getSapAccountCode(), sapAccount.getCustomerSalesOrganization());
        } else {
            // not allowed to order any of the sap parts
            markListNotAllowedToOrder(resolvedItemList);
        }
	    return resolvedItemList;
	}
	
	private List<ItemBO> resolveProductFlag(String masterOdrNum, List<ItemBO> itemList,
            String nabsAccountCode, String nabShipTo) throws WOMExternalSystemException, WOMTransactionException{
        //Go through Nabs return itemList, if MULTIPLE_PART_FOUND, find right product flag, call check orderable again
        boolean isProductFlagResolved = resolveProductFlag(itemList);
  
        //	if we were able to resolve the product flag, we need to call check orderable again
        if(isProductFlagResolved == true) {
        	itemList = nabsService.checkOrderable(masterOdrNum, itemList, nabsAccountCode, nabShipTo);
        }

        return itemList;
    }
	
	/**
    * This method does specific updates for IPO responses,mark all kit as problem items
    * @param items
    */
   private void updateKitProblem(List<ItemBO> items) {
       
       for (ItemBO anItem : items) {
          if(anItem.isKit()){
        	  ProblemBO nokitsAllowedProblem = ProblemBOFactory.createProblem(MessageResourceUtil.NO_KITS_ALLOWED, ExternalSystem.NABS);
        	  nokitsAllowedProblem.setAllowedToProcess(false);
        	  anItem.addProblemItem(nokitsAllowedProblem);
          }
       }
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
	
	private List<ItemBO> ipoProcessResults(String masterOrderNumber, AccountBO billToAccount, AccountBO shipToAccount, 
					List<ItemBO> inputItemList,  List<ItemBO> nabsResolvedParts, List<ItemBO> sapResolvedParts, boolean checkInventory,
					boolean shippingFromTscEnabledForTP) 
							throws WOMExternalSystemException, WOMTransactionException, WOMBusinessDataException, WOMValidationException {
		// 	Call the processResults() method with the OrderSource parameter.
		return processResults(masterOrderNumber, billToAccount, shipToAccount, inputItemList, nabsResolvedParts, sapResolvedParts, true, OrderSource.HYBRIS, shippingFromTscEnabledForTP);
	}
	
	/**
	 * Process the results from the check inventory calls to resolve the parts between the 2 back end system supported
	 * by US Can.  If checkInventory is true, also check the inventory on the resolved items and set the selected location
	 * per that algorithm if shipping from TSCs has NOT been enabled for the associated Trading Partner.
	 * 
	 * @param masterOrderNumber
	 * @param billToAccount
	 * @param shipToAccount
	 * @param inputItemList
	 * @param nabsResolvedParts
	 * @param sapResolvedParts
	 * @param checkInventory
	 * @param orderSource Passed for IPO OrderSource. Null otherwise.
	 * @param shippingFromTscEnabledForTP Flag indicating whether Shipping from TSCs has been enabled for the associated Trading Partner.
	 * @return
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 * @throws WOMBusinessDataException
	 * @throws WOMValidationException
	 */
	private List<ItemBO> processResults(String masterOrderNumber, AccountBO billToAccount, AccountBO shipToAccount, 
	                                    List<ItemBO> inputItemList,  List<ItemBO> nabsResolvedParts, 
	                                    List<ItemBO> sapResolvedParts, boolean checkInventory,
	                                    OrderSource orderSource, boolean shippingFromTscEnabledForTP) 
	         throws WOMExternalSystemException, WOMTransactionException, WOMBusinessDataException, WOMValidationException  {

		if (inputItemList != null && inputItemList.size() > 0) {
			logger.info("processResults(...): inputItemList.get(0).getRequestedInventory(): (BEFORE mergeResult(...)): " + inputItemList.get(0).getRequestedInventory());
		}
		if (nabsResolvedParts != null && nabsResolvedParts.size() > 0) { 
			logger.info("processResults(...): nabsResolvedParts.get(0).getRequestedInventory(): (BEFORE mergeResult(...)): " + nabsResolvedParts.get(0).getRequestedInventory());
		}
		if (sapResolvedParts != null && sapResolvedParts.size() > 0) {
			logger.info("processResults(...): sapResolvedParts.get(0).getRequestedInventory(): (BEFORE mergeResult(...)): " + sapResolvedParts.get(0).getRequestedInventory());
		}
	    List<ItemBO> returnList = mergeResult(inputItemList, nabsResolvedParts, sapResolvedParts);
	    if (returnList != null && returnList.size() > 0) {
	    	logger.info("processResults(...): returnList.get(0).getRequestedInventory(): (AFTER mergeResult(...)): " + returnList.get(0).getRequestedInventory());
	    }
        
	    for (ItemBO anItem : returnList) {
            if (anItem.isKit()) {
                KitBO theKit = (KitBO) anItem;
                // get all the components if its not already configured.
                if (theKit.isConfigured() == false) {
                    nabsService.getKitComponents(masterOrderNumber, billToAccount.getAccountCode(), theKit);
                }
            }
        }
	    
        // go through itemList check sold-in-multiple condition
        processSoldInMultiple(returnList);
        
        // processPriceUnavailable(returnList);
        
        checkLoqThreshold(returnList);     
  
        // check inventory if need be
        if (checkInventory == true) {
        	InventoryASUSCanImpl inventoryASUSCan = new InventoryASUSCanImpl();
        	SapAcctBO sapAcct = billToAccount.getSapAccount();
        	CustomerSalesOrgBO salesOrg = sapAcct.getCustomerSalesOrganization();

        	OrderType orderType = null;
        	if (shippingFromTscEnabledForTP) { // Shipping from TSCs Enabled for Trading Partner
        		// Use 'Regular' as Order Type so SAP returns multiple Inventory Locations (if present) instead of just one!!
        		orderType = OrderType.REGULAR;
        	} else { // Shipping from TSCs Not Enabled for Trading Partner
        		orderType = OrderType.EMERGENCY;
        	}
        	
        	if (returnList != null && returnList.size() > 0) {
        		logger.info("processResults(...): returnList.get(0).getRequestedInventory(): (BEFORE inventoryASUSCan.checkInventory(...)): " + returnList.get(0).getRequestedInventory());
        	}
        	inventoryASUSCan.checkInventory(masterOrderNumber, returnList, billToAccount, shipToAccount, salesOrg, orderType, orderSource);
        	if (returnList != null && returnList.size() > 0) {
        		logger.info("processResults(...): returnList.get(0).getRequestedInventory(): (AFTER inventoryASUSCan.checkInventory(...)): " + returnList.get(0).getRequestedInventory());
        	}
        	// Use Selected/Main DC Inventory Location OR segregate Selected/Main DC Inventory Location from Additional Inventory Location 
        	// for all items in the Item List.
        	inventoryASUSCan.getSelectedOrSegregateInventoryForAllItems(returnList, shippingFromTscEnabledForTP);
        }
        
        if (returnList != null && returnList.size() > 0) {
        	logger.info("processResults(...): returnList.get(0).getRequestedInventory(): (AFTER inventoryASUSCan.checkInventory(...)): " + returnList.get(0).getRequestedInventory());
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
			throw new WOMBusinessDataException("mergeResult(...): Returned parts lists from NABS and SAP not the same size!");
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
				logger.error("mergeResult(...): Input, NABS and SAP Line numbers at element "+i+ " do not match.");
				logger.error("mergeResult(...): Input line number = "+inputItem.getLineNumber()+ ", part number = "+inputItem.getDisplayPartNumber());
				logger.error("mergeResult(...): NABS line number = "+nabsPart.getLineNumber()+ ", part number = "+ nabsPart.getDisplayPartNumber());
				logger.error("mergeResult(...): SAP line number = "+sapPart.getLineNumber()+ ", part number = "+sapPart.getDisplayPartNumber());
				throw new WOMBusinessDataException("mergeResult(...): Returned parts lists from Input, NABS and SAP do not match line numbers!");
			}

			// If there is no problem on both systems, that's weird.
		    if (nabsProblem == null && sapProblem == null) {
				logger.error("mergeResult(...): NABS Part " + nabsPart.getPartNumber() + " and SAP Part " + sapPart.getPartNumber()
						+ " found on both systems.");
				throw new WOMBusinessDataException("mergeResult(...): NABS Part " + nabsPart.getPartNumber() + " and SAP Part "
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
				logger.info("mergeResult(): nabsPart.getChosenInventoryLocation(): " + nabsPart.getChosenInventoryLocation());
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
				logger.error("mergeResult(...): Part merge error in NABS and SAP Line numbers at element "+i);
				logger.error("mergeResult(...): NABS part number = "+nabsPart.getDisplayPartNumber()
						+ ", problem status:key = "+nabsProblem.getStatusCategory()+":"+nabsProblem.getMessageKey());
				logger.error("mergeResult(...): SAP part number = "+sapPart.getDisplayPartNumber()
						+ ",  problem status:key = "+ sapProblem.getStatusCategory()+":"+sapProblem.getMessageKey());
				logger.error("mergeResult(...): Returning part number "+sapPart.getDisplayPartNumber()
						+ "with problem status:key = "+ nabsProblem.getStatusCategory()+":"+nabsProblem.getMessageKey());
                result.add(i, nabsPart);
			}
		}

		return result;
	}

	/**
	 * This method does specific updates for IPO responses. Currently these are: <dd>- change no inventory problem from
	 * processable to non processable
	 * 
	 * @param resolvedItems
	 */
	private void updateForIpo( OrderBO order,  List<ItemBO> resolvedItems)
	{
		for ( ItemBO anItem : resolvedItems)
		{
			if (anItem.hasProblemItem() == false)
			{
				continue;
			}

			//fix IPO multiple problemBO issue - End
			boolean isInventoryfound = false;
			final List<ProblemBO> problemList = anItem.getProblemItemList();
			for (final ProblemBO aProblem : problemList)
			{
				if (MessageResourceUtil.NO_INVENTORY.equals(aProblem.getMessageKey()))
				{
					aProblem.setAllowedToProcess(false);
				}
				if (MessageResourceUtil.SETUP_ERROR.equals(aProblem.getMessageKey())
						&& (null == anItem.getInventory() || anItem.getInventory().size() == 0))
				{

					for (final ItemBO ipoItem : resolvedItems)
					{
						if (anItem.getPartNumber().equalsIgnoreCase(ipoItem.getPartNumber())
								&& anItem.getLineNumber() != ipoItem.getLineNumber())
						{
							anItem.setInventory(ipoItem.getInventory());
							isInventoryfound = true;
						}
					}

				}
				// if we have insufficient quantity, the assigned needs to be
				// the available.
				// we really need a status category here 
				if (MessageResourceUtil.INSUFFICIENT_INVENTORY.equals(aProblem.getMessageKey())
						|| MessageResourceUtil.DISCONTINUED_AND_INSUFFICIENT_INVENTORY.equals(aProblem.getMessageKey())
						|| MessageResourceUtil.NO_INVENTORY.equals(aProblem.getMessageKey())
						|| MessageResourceUtil.DISCONTINUED_AND_NO_INVENTORY.equals(aProblem.getMessageKey()))
				{

					final InventoryBO theInventory = anItem.getSelectedInventory();
					if (theInventory != null)
					{
						theInventory.setAssignedQty(theInventory.getAvailableQty());
						// just reduced the assigned and this is IPO.  
						anItem.setBackorderPolicy(BackOrderPolicy.SHIP_AND_CANCEL);
						
						if (anItem.getRequestedInventory() != null) {
							// Reduce the Assigned Qty for Requested Inventory as well.
							anItem.getRequestedInventory().setAssignedQty(theInventory.getAvailableQty());
						}
					}
				}
			}

			if (isInventoryfound)
			{
				final List<ProblemBO> tempprobList = new ArrayList<ProblemBO>();
				for (final ProblemBO aProblem : problemList)
				{
					if (!MessageResourceUtil.SETUP_ERROR.equals(aProblem.getMessageKey()))
					{
						tempprobList.add(aProblem);
					}
				}
				if (tempprobList.size() >= 1)
				{
					anItem.setProblemItemList(tempprobList);
				}
				else
				{
					anItem.setProblemItemList(null);
				}
			}

			//fix IPO multiple problemBO issue - Start
			final List<ProblemBO> probList = anItem.getProblemItemList();
			final List<ProblemBO> tempprobList = new ArrayList<ProblemBO>();
			if (null != probList && probList.size() >= 2)
			{
				tempprobList.add(probList.get(0));
				anItem.setProblemItemList(tempprobList);
			}
		}
	}

	@Override
	public List<ItemBO> checkOrderableAndInventory(String masterOrderNumber,
			List<ItemBO> itemList, AccountBO billToAcount,
			AccountBO shipToAccount) throws WOMExternalSystemException,
			WOMTransactionException, WOMBusinessDataException,
			WOMValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ItemBO> checkOrderable(String masterOrderNumber,
			List<ItemBO> itemList, AccountBO billToAcount,
			AccountBO shipToAccount) throws WOMExternalSystemException,
			WOMTransactionException, WOMBusinessDataException,
			WOMValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	private FlexibleSearchService getFlexibleSearchService()
	{
		final FlexibleSearchService flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext().getBean(
				"flexibleSearchService");
		return flexibleSearchService;
	}

	private DistributionCenterBO getFMDistributionCenterBO(final String dcCode)
	{
		final DistributionCenterBO distributionCenterBO = new DistributionCenterBO();
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {dc:" + FMDCCenterModel.PK + "} ");
		query.append(" FROM  {" + FMDCCenterModel._TYPECODE + " AS dc }");
		query.append(" WHERE {dc." + FMDCCenterModel.DCCODE + "} = ?" + FMDCCenterModel.DCCODE);
		logger.debug("getFMDistributionCenterBO(): " + dcCode);

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMDCCenterModel.DCCODE, dcCode.trim());

		final SearchResult<FMDCCenterModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		if (searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			final FMDCCenterModel dc = searchResult.getResult().get(0);
			distributionCenterBO.setCode(dcCode);
			distributionCenterBO.setName(dc.getDcName());
			final AddressBO address = new AddressBO();
			address.setAddrLine1(dc.getAddressLine1());
			//address.setAddrLine2(dc.geta);
			address.setCity(dc.getCity());
			address.setStateOrProv(dc.getState());
			address.setZipOrPostalCode(String.valueOf(dc.getZipCode().intValue()));
			final CountryBO country = new CountryBO();
			country.setIsoCountryCode(dc.getIsoCountryCode());
			address.setCountry(country);
			distributionCenterBO.setAddress(address);
			//logger.info("getFMDistributionCenterBO(): distributionCenterBO " + distributionCenterBO);
		}

		return distributionCenterBO;
	}

	private List<ItemBO> updateItemShippingInfo(final List<ItemBO> resolvedItems)
	{
		final List<ItemBO> items = new ArrayList<ItemBO>();
		for (final ItemBO anItem : resolvedItems)
		{
			final List<InventoryBO> inventorys = new ArrayList<InventoryBO>();
			final List<InventoryBO> additionalInventorys = new ArrayList<InventoryBO>();
			final List<ProblemBO> problems = new ArrayList<ProblemBO>();
			final List<InventoryBO> tempInventorys = new ArrayList<InventoryBO>();
			if (null != anItem.getProblemItemList() && !anItem.getProblemItemList().isEmpty())
			{
				for (final ProblemBO problem : anItem.getProblemItemList())
				{
					if (!MessageResourceUtil.PART_FOUND_IN_MULTIPLE_LOCATIONS.equals(problem.getMessageKey()))
					{
						problems.add(problem);
					}
				}
				anItem.setProblemItemList(problems);
			}

			if (null != anItem.getInventory() && !anItem.getInventory().isEmpty())
			{
				if (anItem.getInventory().size() > 1)
				{
					int availableQty = 0;
					for (final InventoryBO inventory : anItem.getInventory())
					{
						if (inventory.getAvailableQty() > availableQty)
						{
							tempInventorys.clear();
							availableQty = inventory.getAvailableQty();
							tempInventorys.add(inventory);
						}
					}
				}
				if (!tempInventorys.isEmpty())
				{
					for (final InventoryBO inventory : tempInventorys)
					{
						final DistributionCenterBO distributionCenter = getFMDistributionCenterBO(inventory.getDistributionCenter()
								.getCode());
						inventory.setDistributionCenter(distributionCenter);
						inventory.setSelectedLocation(true);
						inventorys.add(inventory);
					}
					anItem.setInventory(inventorys);
				}
				else
				{
					for (final InventoryBO inventory : anItem.getInventory())
					{
						final DistributionCenterBO distributionCenter = getFMDistributionCenterBO(inventory.getDistributionCenter().getCode());
						inventory.setDistributionCenter(distributionCenter);
						inventory.setSelectedLocation(true);
						inventorys.add(inventory);
					}
					anItem.setInventory(inventorys);
				}
			}
			
			if (null != anItem.getAdditionalInventory() && !anItem.getAdditionalInventory().isEmpty())
			{
				for (final InventoryBO anAdditionalInventory : anItem.getAdditionalInventory())
				{
					final DistributionCenterBO distributionCenter = getFMDistributionCenterBO(anAdditionalInventory.getDistributionCenter().getCode());
					anAdditionalInventory.setDistributionCenter(distributionCenter);
					additionalInventorys.add(anAdditionalInventory);
				}
				anItem.setAdditionalInventory(additionalInventorys);
			}

			items.add(anItem);

		}
		return items;
	}
}
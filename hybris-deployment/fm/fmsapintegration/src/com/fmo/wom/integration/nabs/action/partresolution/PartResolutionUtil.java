package com.fmo.wom.integration.nabs.action.partresolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.CurrencyTypeBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.ExternalSystemStatusBO.StatusCategory;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.KitBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.WeightBO;
import com.fmo.wom.domain.enums.CustomerBusinessType;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.domain.nabs.InventoryLocationBO;
import com.fmo.wom.domain.nabs.PartResolutionInventoryBO;
import com.fmo.wom.domain.nabs.PartResolutionInventoryPK;
import com.fmo.wom.domain.nabs.ProblemPartBO;
import com.fmo.wom.integration.dao.nabs.InventoryLocationDAO;
import com.fmo.wom.integration.dao.nabs.InventoryLocationDAOImpl;
import com.fmo.wom.integration.dao.nabs.PartResolutionDAO;
import com.fmo.wom.integration.dao.nabs.PartResolutionDAOImpl;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.util.DistributionCenterCache;
import com.fmo.wom.integration.util.ItemListUtil;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.PartNumberSquasher;
import com.fmo.wom.integration.util.ProblemBOFactory;

public class PartResolutionUtil {
	
	private static Logger logger = Logger.getLogger(PartResolutionUtil.class);
	private static final String SUCCESS_KEY = "00";
	private PartResolutionDAO partResolutionDAO;
	private InventoryLocationDAO inventoryLocationDAO;

	
	public PartResolutionUtil() {
		partResolutionDAO = new PartResolutionDAOImpl();
		inventoryLocationDAO = new InventoryLocationDAOImpl();
	}
	
	//@Transactional(propagation=Propagation.REQUIRES_NEW) //, value="dtransactionManager")
    public void storeInputItems(int transactionId, String transactionCode, String masterOrderNumber, List<ItemBO> itemList, boolean useDisplayPartNumber) throws WOMTransactionException{
        // go through the input parts and create objects for them.
    	PartResolutionDAOImpl daoImpl = (PartResolutionDAOImpl)partResolutionDAO;
    	EntityTransaction transaction = null;
    	try{
    		transaction = daoImpl.getEntityManager().getTransaction(); 
    		if(transaction != null && (!transaction.isActive())){
	    		transaction.begin();
	    		for (ItemBO anItem : itemList ) {
	                PartResolutionInventoryBO aPart = createPartResolutionEntry(transactionId, transactionCode, masterOrderNumber, useDisplayPartNumber, anItem);  
	                partResolutionDAO.persist(aPart);
	            }
	    		transaction.commit();
    		}
    	}catch (Exception commitException) {
    		if(transaction !=null && transaction.isActive()){
    			try {
    				transaction.rollback();
    			} catch (Exception rollBackException) {
    				throw new WOMTransactionException(rollBackException);
    			}
    		}
    		throw new WOMTransactionException(commitException);
    	}
    }
    
    /**
     * @param transactionId
     * @param transactionCode
     * @param masterOrderNumber
     * @param useDisplayPartNumber
     * @param anItem
     * @return
     */
    private PartResolutionInventoryBO createPartResolutionEntry(int transactionId, String transactionCode, String masterOrderNumber,
            boolean useDisplayPartNumber, ItemBO anItem) {
        PartResolutionInventoryBO aPart = new PartResolutionInventoryBO();
      
        // set the PK
        PartResolutionInventoryPK aPK = new PartResolutionInventoryPK(NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId, ""+anItem.getLineNumber(), transactionCode);
        
        aPart.setId(aPK);
        
        String inputPartNumberToUse = "";
        if (useDisplayPartNumber == true) {
            // display part number can be anything from the user, so need to squash and upper
            inputPartNumberToUse = anItem.getDisplayPartNumber();
            inputPartNumberToUse = PartNumberSquasher.squashNabsPartNumber(inputPartNumberToUse);
            inputPartNumberToUse = inputPartNumberToUse.toUpperCase();
        } else {
            inputPartNumberToUse = anItem.getPartNumber();
        }
        
        aPart.setPartNbr(inputPartNumberToUse);
        aPart.setMasterOrderNbr(masterOrderNumber);
        aPart.setProdFlg(anItem.getProductFlag());
        aPart.setFmBrandCode(anItem.getFmBrandCode());

        aPart.setBrandState(anItem.getBrandState());
        
        aPart.setOrdQty(1);
        if (anItem.getItemQty() != null) {
            aPart.setOrdQty(anItem.getItemQty().getRequestedQuantity());
        }
        
        aPart.setLineComment(anItem.getComment());
        return aPart;
    }
    
    
    /**
     * After the nabs call, all the needed data should be in the INVENTORY_LOCATION table, keyed via the
     * input elements.  There should be one-to-many rows in this table for each input item.
     *   
     * Load all the entries in there for each part for the master order number and populate response Items with 
     * the Distribution Centers they represent
     * @return List of resolved parts with their inventories/distribution centers populated.
     */
    //@Transactional(propagation=Propagation.REQUIRES_NEW)
    public void loadInventoryLocationResponseData(int transactionId, String transactionCode, List<ItemBO> inputItemList, OrderType ordertype) throws WOMTransactionException{
        InventoryLocationDAOImpl daoImpl = (InventoryLocationDAOImpl)inventoryLocationDAO;
        EntityTransaction transaction = null;
        try{
        	transaction = daoImpl.getEntityManager().getTransaction(); 
        	if(transaction != null && (!transaction.isActive())){
        		transaction.begin();
        		// for each input item, load the inventory locations for it
		        for (ItemBO anItem : inputItemList ) {
		        
		            List<InventoryBO> dcList = new ArrayList<InventoryBO>();
		             
		            // load all the inventory locations for this master order number and line sequence
		            List<InventoryLocationBO> inventoryLocationList = 
		                inventoryLocationDAO.findByTransactionIdAndLineSeq(transactionId, anItem.getLineNumber(), 
		                                                            NabsConstants.NABS_CHECK_INVENTORY_TRANS_CODE);
		            
		            InventoryBO homeInventoryLocation = null;
		            for (InventoryLocationBO aLocation : inventoryLocationList ) {
		                // this is a 7 digit field, so this cast should never be
		                // in danger of going over MAXINT
		                int availableAtLocation = (int)aLocation.getAvailQty();
		                // create the inventory bo item for this entry
		                InventoryBO anInventory = new InventoryBO();
		                anInventory.setItem(anItem);
		                DistributionCenterBO distCenter = createDistributionCenter(aLocation);
		                anInventory.setDistributionCenter(distCenter);
		                anInventory.setDistCntrId(distCenter.getDistCenterId());
		                // this is a 7 digit field, so this cast should never be
		                // in danger of going over MAXINT
		                anInventory.setAvailableQty(availableAtLocation);
		                // nabs has told us this is the home inventory location, set this as selected.
		                if (aLocation.isHomeInventoryLocation()) {
		                    anInventory.setMainDC(true);
		                    // retain this for outside the loop in case no locations stock.
		                    homeInventoryLocation = anInventory;
		                }
		                // if CAN STOCK is false, this is not a valid location
		                if (aLocation.isCanStock() == true) {
		                    // this is a valid stocking location
		                    dcList.add(anInventory);
		                }
		            }
		            // check if any locations can stock
		            if (dcList.size() == 0 && homeInventoryLocation != null) {
		                // none! Add the home.
		                dcList.add(homeInventoryLocation);
		            }
		            anItem.setInventory(dcList);
		            logger.info("Populated Item Data: "+ anItem);
		        }
		        transaction.commit();
	        }
        } catch (Exception commitException) {
        	if(transaction !=null && transaction.isActive()){
        		try {
        			transaction.rollback();
        		} catch (Exception rollBackException) {
        			throw new WOMTransactionException(rollBackException);
        		}
        	}
        	throw new WOMTransactionException(commitException);
        }
    }
    
    /**
     * After the nabs call, all the needed data should be in the INVENTORY_LOCATION table, keyed via the
     * input elements.  There should be one-to-many rows in this table for each input item.
     *   
     * Load all the entries in there for each part for the master order number and populate response Items with 
     * the Distribution Centers they represent
     * @return List of resolved parts with their inventories/distribution centers populated.
     */
    
    
    //@Transactional(propagation=Propagation.REQUIRES_NEW)
    /*public void loadInventoryLocationResponseData(int transactionId, String transactionCode, List<ItemBO> inputItemList, OrderType orderType) {
        
        // for each input item, load the inventory locations for it
        for (ItemBO anItem : inputItemList ) {
             
            // load all the inventory locations for this master order number and line sequence
            List<InventoryLocationBO> inventoryLocationList = 
                inventoryLocationDAO.findByTransactionIdAndLineSeq(transactionId, anItem.getLineNumber(), 
                                                            NabsConstants.NABS_CHECK_INVENTORY_TRANS_CODE);
            
            
            List<InventoryBO> dcList = null;
            
            if(orderType.equals(OrderType.EMERGENCY)){
            	dcList = getProposalForYEMOrder(anItem, inventoryLocationList);
            }else if (orderType.equals(OrderType.REGULAR)) {
				dcList = getProposalForYOROrder(anItem, inventoryLocationList);
			}
            anItem.setInventory(dcList);
            logger.debug("Populated Item Data: {}", anItem);
        }
        
    }*/
    
    
    private DistributionCenterBO createDistributionCenter(InventoryLocationBO aLocation) {
        String code = aLocation.getId().getPlantCd().trim();
        // Find Distribution Center irrespective of whether it is Active/Inactive.
        //DistributionCenterBO distCenter = DistributionCenterCache.getDistributionCenterWithDefault(Market.US_CANADA, code);
        DistributionCenterBO distCenter = new DistributionCenterBO();
        distCenter.setCode(code);
        DistributionCenterBO dcFrmCache = DistributionCenterCache.getDistributionCenterCache().getDistributionCenter(code);
	    if(dcFrmCache != null){
	    	distCenter.setName(dcFrmCache.getName());
	    }
        return distCenter;
    }
    
    /**
     * Load the response data from the NABS output tables and construct the AccountBO it
     * represents
     * @return BillToAcctBO from NABS
     */
    public List<ItemBO> loadPartResolutionResponseData(int transactionId, String transactionCode, List<ItemBO>validItemsList) throws WOMTransactionException{
        List<ItemBO> resolvedItems = new ArrayList<ItemBO>();
        // create a map for easier retrieval of input items via line number
        Map<Integer, ItemBO> lineNumberInputItemMap = ItemListUtil.createLineNumberItemMap(validItemsList);
        PartResolutionDAOImpl daoImpl = (PartResolutionDAOImpl)partResolutionDAO;
        EntityTransaction transaction = null;
        try{
        	transaction = daoImpl.getEntityManager().getTransaction(); 
        	if(transaction != null && (!transaction.isActive())){
        		transaction.begin();
	        	// find all results for the transaction id and this transaction code
	            List<PartResolutionInventoryBO> results = partResolutionDAO.findByTransactionId(transactionId, 
	                                                                                            NabsConstants.NABS_ORDERABLE_TRANS_CODE);
	            for (PartResolutionInventoryBO aResult : results) {
	                // now find the input item based on the line seq
	                ItemBO anItem = lineNumberInputItemMap.get(new Integer(aResult.getId().getLineSeqNbr()));
	                ItemBO newItem = null;
	                // create a new copy. If the input is a kit, clone it
	                newItem = anItem.createInitialCopy();  
	                // Make sure its a kit if the results say it should be
	                if (aResult.isKit()) {
	                    if (newItem instanceof KitBO == false) {
	                        newItem = new KitBO();
	                        newItem.populateInitialCopy(anItem);
	                    }
	                    populateKitData((KitBO)newItem, aResult);
	                }
	                // populate part data from the response
	                populateItemData(newItem,aResult);
	                // process the status code and set problems
	                processStatus(newItem, aResult);
	                // put it on the list
	                resolvedItems.add(newItem);
	            }
	            transaction.commit();
            }
        } catch (Exception commitException) {
        	if(transaction !=null && transaction.isActive()){
        		try {
        			transaction.rollback();
        		} catch (Exception rollBackException) {
        			throw new WOMTransactionException(rollBackException);
        		}
        	}
        	throw new WOMTransactionException(commitException);
        }
        return resolvedItems;
    }
    
    /**
     * Populate Kit specific data from the PartResolutionInventory object
     * @param theKit kit object to populate
     * @param aResult NABS data for the kit
     */
    private void populateKitData(KitBO theKit, PartResolutionInventoryBO aResult) {
        
        theKit.setEngExpressAllowed(aResult.isEngKitExpress());
    }

    /**
     * Using the given PartResolutionInventory object, populate the relevant part data
     * @param anItem ItemBO to be populated
     * @param aResult NABS response data for this part
     */
    private void populateItemData(ItemBO anItem, PartResolutionInventoryBO aResult) {
        
        anItem.setExternalSystem(ExternalSystem.NABS);
        anItem.setLineNumber(new Integer(aResult.getId().getLineSeqNbr()));
        
        // the partNbr field on aResult is merely our input.  The catalog part number
        // now contains the real resolved part value if all went well.
        if (GenericValidator.isBlankOrNull(aResult.getCatalogPartNbr()) == false) {
            anItem.setPartNumber(aResult.getCatalogPartNbr());
        }
        anItem.setProductFlag(aResult.getProdFlg());
        anItem.setBrandState(aResult.getBrandState());
        anItem.setVendorDirect(aResult.isVendorDirect());
        anItem.setDescription(aResult.getPartDesc());
        anItem.setItemPrice(createPrice(aResult));
        anItem.setItemWeight(createWeight(aResult));
        anItem.setItemQty(createQuantity(anItem.getItemQty(), aResult));
        anItem.setKitAddable(aResult.isKitAddable());
        anItem.setPredecessorReason(aResult.getPartFlippedFlg());
        anItem.setBeingDiscontinued(aResult.isBeingDiscontinued());
        anItem.setBrStShortDesc(aResult.getBrandStateShortDesc());
        anItem.setSalesSymbol(aResult.getSalesSymbol());
        anItem.setOrderEntryComment(aResult.getLineComment());
        anItem.setPackageCode(aResult.getPkgCd());
        anItem.setTenDigitUPC(aResult.getTenDigitUPC());
       
        // try to make this readable - too many negations in the old way.
        // If NABS tells us the part is not returnable, that means we cannot return it for credit
        anItem.setCanReturnForCredit(true);
        boolean partNotReturnable = aResult.isNoReturn();
        anItem.setCanReturnForCredit(partNotReturnable == false);
   
        // See if this part has been superceded 
        if (isPartSuperceded(aResult)) {
            createPartSupercededProblem(anItem, aResult);
        }
        
        // see if this part is being discontinued
        if (aResult.isBeingDiscontinued()) {
            ProblemBO beingDiscontinued = ProblemBOFactory.createProblem(MessageResourceUtil.PART_BEING_DISCONTINUED, ExternalSystem.NABS);
            beingDiscontinued.setAllowedToProcess(true);
            anItem.addProblemItem(beingDiscontinued);
        }
        
    }
    
    /**
     * Utility method to determine if a part has been superceded
     * @param aResult - current object in the NABS response representing
     * this part.
     * @return true if the alternate part number is populated 
     */
    private boolean isPartSuperceded(PartResolutionInventoryBO aResult) {
        String newPartNumber = aResult.getAltCtlgPart();
        return (newPartNumber != null && newPartNumber.trim().length() > 0);
    }
    
    private void createPartSupercededProblem(ItemBO anItem, PartResolutionInventoryBO aResult) {
        // set the old part number info
        String oldPartNumber = aResult.getAltCtlgPart();
        
        if (oldPartNumber.equals(anItem.getPartNumber()) == false) {
            
            // part numbers are not equal
            ProblemBO supercededPartProblem = ProblemBOFactory.createSupercededPartProblem( anItem.getPartNumber(), oldPartNumber);
            anItem.setPredecessorPartNumber( anItem.getDisplayPartNumber());
            anItem.addProblemItem(supercededPartProblem);
        }
    }
    
    /**
     * Process the status of each of the parts. Using the status code on the PartResolutionInventory,
     * retrieve all ProblemPart objects and process them appropriately
     * @param anItem
     * @param aResult
     */
    private void processStatus(ItemBO anItem, PartResolutionInventoryBO aResult) {
        
        String statusCode = aResult.getItemStatusCd();
        
        // if the status is not success, process the problem item
        if (SUCCESS_KEY.equals(statusCode) == false) {
            
            String statusKey = MessageResourceUtil.getNabsStatusKeyViaStatusCode(statusCode);
            ProblemBO realProblem = ProblemBOFactory.createProblem(statusKey, ExternalSystem.NABS);
            anItem.addProblemItem(realProblem);
        
            // Multiple found requires special processing of the ProblemPart collection
            if (MessageResourceUtil.MULITPLE_PARTS_FOUND.equals(statusKey)) {
            
                Map matchingPartsMap = new HashMap();
                List<ProblemPartBO> problemParts = aResult.getProblemParts();
                for (ProblemPartBO aProblemPart : problemParts) {
                    
                    ItemBO aMatchedPart = new ItemBO();
                    aMatchedPart.setDisplayPartNumber(aProblemPart.getCtlgPartNbr());
                    aMatchedPart.setPartNumber(aProblemPart.getCtlgPartNbr());
                    aMatchedPart.setProductFlag(aProblemPart.getProdFlg());
                    aMatchedPart.setBrandState(aProblemPart.getBrandState());
                    aMatchedPart.setDescription(aProblemPart.getPartDesc());
                   
                    matchingPartsMap.put(new Integer(aProblemPart.getId().getProbSeqNbr()), aMatchedPart);
                }
                
                realProblem.setCorrectiveOptions(matchingPartsMap);
               
            }
            
            if (realProblem.getStatusCategory() == StatusCategory.PART_MIGRATED) {
                // this guy was migrated.  Alt ctlg part number is the REAL part number to use.
                anItem.setPartNumber(aResult.getAltCtlgPart());
            }
            
            //If it's a Carter part
            if (MessageResourceUtil.CARTER_PARTS.equals(statusKey)) {
            	realProblem.setAllowedToProcess(true);           	  
            }
            
        }
    }
    
    private QuantityBO createQuantity(QuantityBO originalQuantity, PartResolutionInventoryBO aResult) {
        QuantityBO qty = new QuantityBO();
        
        qty.setSoldInMultiples(true);
        qty.setSoldInMultipleQuantity((int)aResult.getMultQty()); 
        if (qty.getSoldInMultipleQuantity() == 0)
        {
            // Don't want divide by 0 exceptions
            qty.setSoldInMultipleQuantity(1);
            qty.setSoldInMultiples(false);
        }
        qty.setOverQuantity((int)aResult.getVerifyLOQ());
        qty.setReasonableQuantityThreshold((int) aResult.getVerifyLOQ());
        qty.setLargeQuantityThreshold((int)aResult.getLoqQty());
        qty.setFactorQuantity((int)aResult.getFactorQty());
        // no need to get requested from response as we have it in the original
        qty.setRequestedQuantity(originalQuantity.getRequestedQuantity());
        if (aResult.getUom() != null && "".equals(aResult.getUom().trim()) == false) {
            qty.setQtyUomCd(aResult.getUom());
        } else {
            qty.setQtyUomCd("EA");
        }
        qty.setRoundedOrderQty((int)aResult.getRoundedOrdQty());
        return qty;
    }

    private WeightBO createWeight(PartResolutionInventoryBO aResult) {
        WeightBO weight = new WeightBO();
        weight.setWeight(aResult.getPceWgtLbs());
        return weight;
    }
    
    private PriceBO createPrice(PartResolutionInventoryBO aResult) {
        PriceBO price = new PriceBO();
        
        CurrencyTypeBO aCurrency = new CurrencyTypeBO();
        aCurrency.setCode(CurrencyTypeBO.USD);
        price.setCurrency(aCurrency);
        price.setPriceType(CustomerBusinessType.getFromCode(aResult.getPriceTypeCd()));
        price.setDisplayPrice(aResult.getDisplayPrice());
        price.setFreightPrice(aResult.getFrghtNetPrice());
        return price;
    }
    
    public void releaseResource(){
    	if(partResolutionDAO != null){
    		partResolutionDAO.releaseEntityManager();
    	}
    	if(inventoryLocationDAO !=null){
    		inventoryLocationDAO.releaseEntityManager();
    	}
    }
}

package com.fmo.wom.integration.nabs.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.ComponentBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.KitBO;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.dao.TransactionIdSequenceDAO;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.partresolution.NABSCheckInventoryUtil;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.ProblemBOFactory;

public class CheckInventoryAction extends NabsActionBase{
	
	private static Logger logger = Logger.getLogger(CheckInventoryAction.class);
	
	public Logger getLogger() {
	    return logger;
	}
	 
	private TransactionIdSequenceDAO db2TransIdSequenceDAO;
	/*private PartResolutionUtil partResolutionUtil;*/
	private NABSCheckInventoryUtil checkInventoryUtil;
	public CheckInventoryAction() {
		db2TransIdSequenceDAO = new TransactionIdSequenceDAO();
		/*partResolutionUtil    = new PartResolutionUtil();*/
		checkInventoryUtil     = new NABSCheckInventoryUtil();
	}
	
	
	/**
     * Execute the NABS call. Constructs the input object based on the member data, stores the required
     * input data in the NABS input tables and then calls NABS.  After successful call, loads the NABS
     * response tables.
     * List of parts with fully populates inventories and distribution centers will be in the input list
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public void executeAction(String masterOrderNumber, List<ItemBO> itemList, String billToAccountCode, String shipToAccountCode, OrderType orderType)
         throws WOMExternalSystemException, WOMTransactionException {
        
        List<ItemBO> validItems = createValidInputItemList(itemList);
        
        // get a list of configured kit components to send. 
        Map<KitBO, List<ItemBO>> validKitComponentsMap = createValidInputKitMap(validItems);
        
        // If there was no valid input, we're done.
        if (validItems.size() == 0 && validKitComponentsMap.size() == 0) {
            return;
        }
   
        // persisting input data, so need an id
        int transactionId = db2TransIdSequenceDAO.nextVal();
        
        // persist the input items with display part number = false since these should be resolved parts now
        checkInventoryUtil.storeInputItems(transactionId, NabsConstants.NABS_CHECK_INVENTORY_TRANS_CODE, masterOrderNumber, validItems, false);
        
        // persist the input kit components as parts.  Also use display part number = false since these should be resolved parts now
        for (ItemBO originalKit : validKitComponentsMap.keySet()) {
            checkInventoryUtil.storeInputItems(transactionId, NabsConstants.NABS_CHECK_INVENTORY_TRANS_CODE, masterOrderNumber, validKitComponentsMap.get(originalKit), false);
        }
        
        // create an input object
        NabsCheckInventoryInput inputObject = new NabsCheckInventoryInput(transactionId, billToAccountCode, shipToAccountCode);
        
        // call nabs
        executeNabsCall(inputObject);
        
        // response was ok - load response data, store info and return it
        
        // load the data corresponding to parts in the items list
        checkInventoryUtil.loadInventoryLocationResponseData(transactionId, NabsConstants.NABS_CHECK_INVENTORY_TRANS_CODE, validItems, orderType);
        
        // now load and put back together components that were just sent as parts
        // persist the input kit components as parts.  Also use display part number = false since these should be resolved parts now
        for (KitBO originalKit : validKitComponentsMap.keySet()) {
            // first load the data for each collection of components
            checkInventoryUtil.loadInventoryLocationResponseData(transactionId, NabsConstants.NABS_CHECK_INVENTORY_TRANS_CODE, validKitComponentsMap.get(originalKit), orderType);
        
            List<ComponentBO> configuredComponents = originalKit.getAllConfiguredKitComponents();
            
            // now put the resulting inventory objects back onto the appropriate components
            InventoryBO parentInventory = originalKit.getMainDCInventory();
            
            for (ItemBO kitComponentAsItem : validKitComponentsMap.get(originalKit)) {
                
                int originalComponentNumber = getOriginalComponentNumber(kitComponentAsItem.getLineNumber());
                ComponentBO originalComponent = findOriginalComponentByLineNumber(configuredComponents, originalComponentNumber);
                if (originalComponent != null) {
                    setComponentAvailableQuantity(originalComponent, parentInventory, kitComponentAsItem.getInventory());
                }
            }
        }
        
        //partResolutionUtil.releaseResource();
    }
    
    @Override
    protected List<ItemBO> createValidInputItemList(List<ItemBO> inputList) {
        
        List<ItemBO> validatedList = new ArrayList<ItemBO>();
        if (inputList == null) {
            return validatedList;
        }
        
        for (ItemBO anInputItem : inputList) {

            // for check inventory, we MUST have a brand state and product flag.  We'll attach problems for these cases
            // and let the super class do the normal filter on problem.
            String productFlag = anInputItem.getProductFlag();
            String brandState = anInputItem.getBrandState();
            if (productFlag == null || "".equals(productFlag.trim())) {
                anInputItem.addProblemItem(ProblemBOFactory.createProblem(MessageResourceUtil.PART_NOT_FOUND));
                getLogger().error(anInputItem.getDisplayPartNumber()+ " has invalid product flag for NABS call: "+ anInputItem.getProductFlag());
            } else if (brandState == null || "".equals(brandState.trim())) {
                anInputItem.addProblemItem(ProblemBOFactory.createProblem(MessageResourceUtil.PART_NOT_FOUND));
                getLogger().error(anInputItem.getDisplayPartNumber()+ " has invalid brand state for NABS call: "+  anInputItem.getProductFlag());
            }
        }
        
        validatedList = super.createValidInputItemList(inputList);
        return validatedList;
    }
    
    private Map<KitBO, List<ItemBO>> createValidInputKitMap(List<ItemBO> inputList) {
        Map<KitBO, List<ItemBO>> validComponentMap = new HashMap<KitBO, List<ItemBO>>();
        for (ItemBO anInputItem : inputList) {
            if (anInputItem.isKit() == true) {
                KitBO theKit = (KitBO) anInputItem;
                // get all the components if its  configured.
                if (theKit.isConfigured() == true) {
                    
                    List<ItemBO> componentsAsItemsList = new ArrayList<ItemBO>();
                    validComponentMap.put(theKit, componentsAsItemsList);
                    // found a configured kit!  Transform the configured components into items for an inventory check
                    // with appropriate requested qty data and made up line number based on input line and component
                    // seq nbr
                    List<ComponentBO> configuredComponents = theKit.getAllConfiguredKitComponents();
                   
                    for (ComponentBO aConfiguredComponent : configuredComponents) {
                        ItemBO newItem = aConfiguredComponent.createInitialCopy();
                        int newLineNumber = createLineNumber(anInputItem, aConfiguredComponent);
                        newItem.setLineNumber(newLineNumber);
                        newItem.getItemQty().setRequestedQuantity(aConfiguredComponent.getRequestedQuantity());
                        componentsAsItemsList.add(newItem);
                    }
                }
            }
        }
        return validComponentMap;
    }
    
    private int createLineNumber(ItemBO anInputItem, ComponentBO aConfiguredComponent) {
        
        int lineNumber = anInputItem.getLineNumber();
        lineNumber++;
        lineNumber = lineNumber * 1000;
        
        int componentNumber = aConfiguredComponent.getLineNumber();
        
        lineNumber += componentNumber;
        
        int original = getOriginalComponentNumber(lineNumber);
        
        return lineNumber;
    }
    
    private int getOriginalComponentNumber(int lineNumber) {
        
        int originalComponentNumber = lineNumber % 1000;
        return originalComponentNumber;
    }
    
    private ComponentBO findOriginalComponentByLineNumber( List<ComponentBO> originalComponents, int lineNumber) {
        ComponentBO matchingComponent = null;
        for (ComponentBO aComponent : originalComponents) {
            if (aComponent.getLineNumber() == lineNumber) {
                matchingComponent = aComponent;
                break;
            }
        }
        return matchingComponent;
    }
    
    private void setComponentAvailableQuantity(ComponentBO originalComponent, InventoryBO parentInventory, List<InventoryBO> inventoryList) {

        long distributionCenterId = parentInventory.getDistCntrId();
        
        originalComponent.setAvailableQuantity(0);
        for (InventoryBO anInventory : inventoryList) {
            if (distributionCenterId == anInventory.getDistCntrId()) {
                // this dc matches the parent dc so use its quantity
                originalComponent.setAvailableQuantity(anInventory.getAvailableQty());
                break;
            }
        }
    }

}

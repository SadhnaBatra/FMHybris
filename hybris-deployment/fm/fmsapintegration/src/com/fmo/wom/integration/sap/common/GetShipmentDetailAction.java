package com.fmo.wom.integration.sap.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShippedItemBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippingUnitBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConstants;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public abstract class GetShipmentDetailAction extends SapBapiAction {

    private static final String ITEM_INFO_TABLE_NAME_KEY = "ITEM_VIEW_DATA";
    private static final String SHIPMENT_INFO_TABLE_NAME_KEY = "DELIVERY_HISTORY";
    protected static final String RETURN_CODE_SUCCESS = "00";
    protected static final String RETURN_CODE_ORDER_NOT_FOUND = "01";
    protected static final String RETURN_CODE_NO_RESULTS = "02";
    protected static final String NO_PACKING_SLIP = "NO_PACKING_SLIP";

    // These are the input parameters
    private OrderUnitBO orderUnit;
    
    // values used for output
    private ShippedOrderBO shippedOrderResult;
    private Map<String, List<ShippedItemBO>> packingSlipItemMap;
    private Map<String, DistributionCenterBO> packingSlipPlantMap;
    private Map<Integer, DistributionCenterBO> lineNumberPlantMap;
    
    private boolean noResultsFound = false;
    private boolean orderNotFound = false;
    private String orderPackageStatusKey = "";
    
    protected String orderReturnFlag;
    
    protected abstract void setOrderRetrunFlag(String orderRetrunFlag);
    
    protected abstract String getOrderRetrunFlag();
    
    protected abstract ExternalSystem getExternalSystem();
    protected abstract CountryBO createShipToCountry(JCoParameterList exportParamList);
    protected abstract Market getMarket();
   
    private GetShipmentDetailAction() { super(); }

    // define constructors to expose search criteria needed
    public GetShipmentDetailAction(OrderUnitBO orderUnit) {
        super();
        this.orderUnit = orderUnit;
    }
    
    public GetShipmentDetailAction(OrderUnitBO orderUnit, SapConnectionManager aCxnMgr) {
        super(aCxnMgr);
        this.orderUnit = orderUnit;
    }

    public OrderUnitBO getOrderUnit() {
        return orderUnit;
    }
    
    public void setNoResultsFound(boolean noResultsFound) {
        this.noResultsFound = noResultsFound;
    }
    public void setOrderNotFound(boolean orderNotFound) {
        this.orderNotFound = orderNotFound;
    }
    
    /**
     * Main method to execute this action.  Returns the results list created by the
     * search function in SAP and processing the out table
     * @throws WOMExternalSystemException
     */
    public ShippedOrderBO execute() throws WOMExternalSystemException {
        
        if (getExternalSystem() == orderUnit.getExternalSystem() == false) {
            // this hasn't been set up as a SAP order.
            return null;
        }
        
        executeSapFunction();
        
        // if we didn't find anything, can return null
        if (noResultsFound || orderNotFound) {
            return null;
        }
        
        // we now have some results in the packing slip / item list maps.  Check
        // against the input and put the items into the proper places
        populateShippingUnits();
        
        return shippedOrderResult;
    }
    
    @Override
    public void initializeImportParams(JCoParameterList importParams) {
        
        importParams.setValue(SapConstants.SAP_ORDER_KEY, orderUnit.getOrderNumber());
        //importParams.setValue(SapConstants.ORDER_RETURN_FLAG, getOrderRetrunFlag());
        
        // I supplied this parameter as per the documentation and no changes in the results were seen
        //if (GenericValidator.isBlankOrNull(deliveryNumber) == false) {
        //    importParams.setValue(SapConstants.DELIVERY_KEY, deliveryNumber);
        // }
    }

    @Override
    public List<String> getInTableKeyList() {
        return constructSingleTableKeyList("");
    }


    @Override
    public void initializeInTableParams(String tableName, JCoTable in) {
       
        // no in table params for this one
        ;
    }   

   
    @Override
    public List<String> getOutTableKeyList() {
        List<String> outTables = new ArrayList<String>();
        outTables.add(ITEM_INFO_TABLE_NAME_KEY);
        outTables.add(SHIPMENT_INFO_TABLE_NAME_KEY);
        return outTables;
    }
    
    @Override
    protected void processExportParamList(JCoParameterList exportParamList) {
        
        // if we didn't find anything, can return null
        if (noResultsFound || orderNotFound) {
            return;
        }
        
        // construct the fully populated ShippedOrderBO based on the 
        // export params
        shippedOrderResult = new ShippedOrderBO();
        shippedOrderResult.setMasterOrderNumber(exportParamList.getString(SapConstants.MASTER_ORDER_NUMBER_KEY));
        shippedOrderResult.setCustomerPurchaseOrderNum(exportParamList.getString(SapConstants.PO_NUMBER_KEY));
        shippedOrderResult.setBillToAccount(createBillToAccount(exportParamList));
        shippedOrderResult.setShipToAccount(createShipToAccount(exportParamList));
        
        boolean isEmergency = getSapBoolean(exportParamList.getString(SapConstants.EMERGENCY_FLAG_KEY));
        shippedOrderResult.setOrderType(OrderType.STOCK);
        if (isEmergency) {
            shippedOrderResult.setOrderType(OrderType.EMERGENCY);
        }
        
        shippedOrderResult.setOrderedBy(exportParamList.getString(SapConstants.ORDERED_BY_KEY));
        
        // create the order unit "list".  There should only be one order unit for this call
        List<OrderUnitBO> orderUnits = new ArrayList<OrderUnitBO>();
        shippedOrderResult.setOrderUnitList(orderUnits);
        
        // even though we're attaching this to the shipped order result, I'm going to retain
        // the reference for ease of finding later
        populateOrderUnit(exportParamList);
        orderUnits.add(orderUnit);
        
        // retain the order package status key
        orderPackageStatusKey = exportParamList.getString(SapConstants.ORD_PKG_STATUS_KEY);
    }
    
    /**
     * Populate the OrderUnitBO object passed in from the export param list.
     * param exportParamList
     * @return the order unit info from the export param list (minus item data)
     */
    private void populateOrderUnit(JCoParameterList exportParamList) {
        
        orderUnit.setExternalSystem(getExternalSystem());
        
        // order source is done via lookup in status on the order type key
        String orderSource;
        String orderTypeKey = "";
        try {
            orderTypeKey = exportParamList.getString(SapConstants.ORDER_TYPE_KEY);
            orderSource = MessageResourceUtil.getEverestStatusKeyViaStatusCode(orderTypeKey);
        } catch (IllegalArgumentException e) {
            // if we didn't find that on the look up, I'm going to just use the returned key.
            // This should not be a fatal error
            orderSource = orderTypeKey;
        }
        orderUnit.setOrderSourceKey(orderSource);
       // orderUnit.setOrderSourceDescription(MessageResourceUtil.getIPOMessage(orderSource, null));
        orderUnit.setOriginalOrderDate(exportParamList.getDate(SapConstants.ORDER_DATE_KEY));
        orderUnit.setInvoiceDate(exportParamList.getDate(SapConstants.INVOICE_DATE_KEY));
        orderUnit.setCancelledDate(exportParamList.getDate(SapConstants.CANCEL_DATE_KEY));
        orderUnit.setRequestedDeliveryDate(exportParamList.getDate(SapConstants.REQ_DELIVERY_DATE_KEY));

        // set the comments
        orderUnit.setCommentsList(new ArrayList<String>());
        orderUnit.addComment(exportParamList.getString(SapConstants.FREIGHT_TEXT_KEY));
        orderUnit.addComment(exportParamList.getString(SapConstants.HEADER_TEXT_1_KEY));
        orderUnit.addComment(exportParamList.getString(SapConstants.HEADER_TEXT_2_KEY));
        orderUnit.addComment(exportParamList.getString(SapConstants.HEADER_TEXT_3_KEY));
    }

    @Override
    public void processOutTable(String outTableName, JCoTable out) {
     
        if (ITEM_INFO_TABLE_NAME_KEY.equals(outTableName)) {
            processItemInfoOutTable(out);
        } else if (SHIPMENT_INFO_TABLE_NAME_KEY.equals(outTableName)) {
            processShipmentInfoOutTable(out);
        }
    }
    
    /**
     * Process the shipment info output table
     * @param out
     */
    private void processShipmentInfoOutTable(JCoTable out) {
        
        // it may be there there is no further information on this table than
        // what we already retrieved from the order search.  Leaving this with
        // no impl for now
        ;
    }
        
    /**
     * Process the Item info output table.  This constructed a map of the item data,
     * keyed by the packing slip.  This will get used later to properly populate
     * the output OrderBO with the right shipping info and the right item lists in
     * each one
     * @param out
     */
    public void processItemInfoOutTable(JCoTable out) {
        
        // construct the parts info.  Store as a map keyed with the delivery number
        // which looks like the packing slip
        packingSlipItemMap = new HashMap<String, List<ShippedItemBO>>();
        packingSlipPlantMap = new HashMap<String, DistributionCenterBO>();
        
        lineNumberPlantMap = new HashMap<Integer, DistributionCenterBO>();
        
        // if any row has no packing slip, we'll need a place to keep them.
        List<ShippedItemBO> noPackingSlipItems = new ArrayList<ShippedItemBO>();
        
        packingSlipItemMap.put(NO_PACKING_SLIP, noPackingSlipItems);
        int numRows = out.getNumRows();
        for (int i = 0; i < numRows; i++) {
            out.setRow(i);
            
            // get the packing slip.  If there isn't one, use the default
            String packingSlip = out.getString(SapConstants.DELIVERY_KEY);
            if (GenericValidator.isBlankOrNull(packingSlip)) {
                packingSlip = NO_PACKING_SLIP;
            }
            
            // now get the list for this one.  If there isn't one, construct it and
            // add to the map
            List<ShippedItemBO> itemsForThisPackingSlip = packingSlipItemMap.get(packingSlip);
            if (itemsForThisPackingSlip == null) {
                itemsForThisPackingSlip = new ArrayList<ShippedItemBO>();
                packingSlipItemMap.put(packingSlip, itemsForThisPackingSlip);
            }
            
            // now construct the item represented by this row and add to the list
            ShippedItemBO anItem = constructShippedItem(out);
            itemsForThisPackingSlip.add(anItem);
            
            if(lineNumberPlantMap.get(anItem.getLineNumber()) == null){
            	DistributionCenterBO distCenter = createDistributionCenter(getMarket(), out);
            	String plantDescription =  out.getString("PLANT_DESC");
            	if(plantDescription != null && plantDescription.trim().length() > 0){
            		distCenter.setName(plantDescription);
            	}
                lineNumberPlantMap.put(anItem.getLineNumber(), distCenter);
            }
            if (packingSlipPlantMap.get(packingSlip) == null) {
                // no dc there yet
            	DistributionCenterBO distCenter = createDistributionCenter(getMarket(), out);
            	String plantDescription =  out.getString("PLANT_DESC");
            	if(plantDescription != null && plantDescription.trim().length() > 0){
            		distCenter.setName(plantDescription);
            	}
                packingSlipPlantMap.put(packingSlip, distCenter);
                
            }
        }
    }
    
    
    
    private ShippedItemBO constructShippedItem(JCoTable out) {
        
        ShippedItemBO anItem = new ShippedItemBO();   
        
        anItem.setLineNumber(out.getInt(SapConstants.ITEM_NUMBER_KEY));
        anItem.setExternalSystem(ExternalSystem.EVRST);
        anItem.setPartNumber(out.getString(SapConstants.SAP_PART_NUM_KEY));
        String displayPartNumber = out.getString(SapConstants.CUSTOMER_MATERIAL_KEY);
        if(GenericValidator.isBlankOrNull(displayPartNumber)) {
            displayPartNumber = anItem.getPartNumber();
        }
        anItem.setDisplayPartNumber(displayPartNumber);
        anItem.setDescription(out.getString(SapConstants.MATERIAL_DESC_KEY));
        anItem.setShippedQuantity(out.getBigDecimal(SapConstants.SHIPPED_QTY_KEY).intValue());
        anItem.setAssignedQuantity(out.getBigDecimal(SapConstants.ASSIGNED_QUANTITY_KEY).intValue());
        anItem.setOrderedQuantity(out.getBigDecimal(SapConstants.ITEM_QUANTITY_KEY).intValue());
        anItem.setBackorderedQuantity(out.getBigDecimal(SapConstants.BACKORDERED_QUANTITY_KEY).intValue());
        anItem.setInvoicedQuantity(out.getBigDecimal("INVOICED_QTY").intValue());
        anItem.setReturnableQuantity(out.getBigDecimal("RETURNABLE_QTY").intValue());
        anItem.setNetValue(out.getDouble("NET_VALUE"));
        anItem.setTax(out.getDouble("TAX"));
        anItem.setShipCharge(out.getDouble("SHIP_CHARGE"));
        anItem.setTotalValue(out.getDouble("TOTAL_VALUE"));
        anItem.setReasonForRejection(out.getString("REASON_FOR_REJ"));
        return anItem;
    }

    /*
     * Couple of extra return codes for this one that don't necessarily mean catastrophic
     * failure.  Check them and retain values for proper processing later
     * @see com.fmo.wom.integration.sap.action.ActionBase#checkReturnStructure(com.sap.conn.jco.JCoStructure)
     */
    protected void checkReturnStructure(JCoStructure returnStructure) throws WOMExternalSystemException {

        String returnCode = getReturnCode(returnStructure); 
        
        if (!isSuccessReturnStructure(returnStructure)) {
            
            // did not receive success code - check for other return codes
            if (RETURN_CODE_NO_RESULTS.equals(returnCode)) {
                noResultsFound = true;
            } else if (RETURN_CODE_ORDER_NOT_FOUND.equals(returnCode)) {
                orderNotFound = true;
            } else {
                String errorMessage = returnStructure.getString(SapConstants.RETURN_MESSAGE);
                String logErrorMessage = "Errors occurred during " + getFunctionName() + ". Message=["+errorMessage+"]";
                getLogger().error(logErrorMessage);
                throw new WOMExternalSystemException(logErrorMessage);
            }
        }
    }
    
    protected String getReturnCode(JCoStructure returnStructure) {
        return returnStructure.getString(SapConstants.RETURN_CODE);
    }
    
    private boolean fieldsEqual(String first, String second) {
        
        // deeming both null to mean equals, although that remains to be
        // seen from sample output
        if (first == null && second == null) {
            return true;
        }
        
        // if first is not null, return equality
        if (first != null) {
            return first.equals(second);
        }
        
        // if we're here, means first is null and second is not.
        return false;
    }
    
    /**
     * pseudo peek ahead a la token parsing. If the given table's pointer is on the last row, return false.
     * True otherwise. This is accomplished via peek ahead with nextRow().  If next row is false, that means
     * we don't have another row.  If its true, that means the row pointer was advanced so we have to put it back.  
     * @param table
     * @return
     */
    private boolean hasAnotherRow(JCoTable table) {
        // peek ahead to see if there's another row
        if (table.nextRow() == false) {
            return false;
        }
        
        // nextRow advanced the pointer but we don't want that.
        table.previousRow();
        return true;
    }
    
    /**
     * Construct the bill to account associated with the data in the given export params from SAP
     * @param exportParamList
     * @return
     */
    private AccountBO createBillToAccount(JCoParameterList exportParamList) {
        AccountBO billToAccount = new AccountBO();
        billToAccount.setAccountCode(exportParamList.getString(SapConstants.SOLD_TO_CUSTOMER_KEY));
        
        	SapAcctBO sapAccount = new SapAcctBO();
        	sapAccount.setSapAccountCode(exportParamList.getString(SapConstants.SOLD_TO_CUSTOMER_KEY));
        	billToAccount.setSapAccount(sapAccount);
        
        billToAccount.setAccountName(exportParamList.getString("SOLDTO_NAME"));
        
        AddressBO billedToAddress = new AddressBO();
        billedToAddress.setAddrName(exportParamList.getString("SOLDTO_NAME"));
        billedToAddress.setAddrLine1(exportParamList.getString("SOLDTO_STREET1"));
        billedToAddress.setAddrLine2(exportParamList.getString("SOLDTO_STREET2"));
        billedToAddress.setCity(exportParamList.getString("SOLDTO_CITY"));
        billedToAddress.setStateOrProv(exportParamList.getString("SOLDTO_STATE"));
        billedToAddress.setZipOrPostalCode(exportParamList.getString("SOLDTO_ZIP"));
        
        CountryBO country = createShipToCountry(exportParamList);
        billedToAddress.setCountry(country);
        
        // defaulting SAP to USA
        billToAccount.setAddress(billedToAddress);
        
        return billToAccount;
    }

    private void populateShippingUnits() {
    	
    	List<ShippingUnitBO> shippingUnitList = new ArrayList<ShippingUnitBO>();
    	
    	if(packingSlipItemMap != null && (! packingSlipItemMap.isEmpty())){
    		
    		for (String packingSlip : packingSlipItemMap.keySet()) {

    			List<ShippedItemBO> itemsForThisPackingSlip = packingSlipItemMap.get(packingSlip);
    			if(NO_PACKING_SLIP.equalsIgnoreCase(packingSlip)){
    				//Remove No packing Slip ShippingUniBO from ShippingUnitList
    				Map<String, List<ShippedItemBO>> itemShippedWithoutPackingslipMap = getItemsShippedWithoutPackingSlip(itemsForThisPackingSlip);
                	if(itemShippedWithoutPackingslipMap !=null && (! itemShippedWithoutPackingslipMap.isEmpty())){
                		for(String shippingDcCode : itemShippedWithoutPackingslipMap.keySet()){
                			List<ShippedItemBO> itemsWithoutPackingSlip = itemShippedWithoutPackingslipMap.get(shippingDcCode);
                			ShippingUnitBO shippingUnit = new ShippingUnitBO();
                            shippingUnit.setPackingSlip(packingSlip);
                            shippingUnit.setShippedItemsList(itemsWithoutPackingSlip);	
                            shippingUnit.setPackingStatus(SapConstants.IN_PROCESS_KEY);
                            if(itemsWithoutPackingSlip !=null && (! itemsWithoutPackingSlip.isEmpty())){
                            	shippingUnit.setShipFrom(lineNumberPlantMap.get(itemsWithoutPackingSlip.get(0).getLineNumber()));
                            }
                            shippingUnit.setExternalSystmem(getExternalSystem());
                            shippingUnitList.add(shippingUnit);
                		}
                	}
    			} else {
    				
    				ShippingUnitBO shippingUnit = getExistingShippingUnitBO(packingSlip);
    				if(shippingUnit == null){
    					shippingUnit = new ShippingUnitBO();
    				}
    				shippingUnit.setShippedItemsList(itemsForThisPackingSlip);
    				if (packingSlipPlantMap.get(packingSlip) != null) {
    					shippingUnit.setShipFrom(packingSlipPlantMap.get(packingSlip));
    				}
    				String packingStatusKey = getSapPackingStatusKey(orderPackageStatusKey);
					shippingUnit.setPackingStatus(packingStatusKey);
					shippingUnitList.add(shippingUnit);
    			}
    		}
    		orderUnit.setShippingUnitList(shippingUnitList);
    	}
   }
    	
   private ShippingUnitBO getExistingShippingUnitBO(String packingSlip){
	   List<ShippingUnitBO> shippingUnitList = orderUnit.getShippingUnitList();
	   ShippingUnitBO shippingUnitBO = null;
	   if (shippingUnitList != null) {
		   for(ShippingUnitBO shippingUnitBOTemp: shippingUnitList){
			   if(packingSlip.trim().equalsIgnoreCase(shippingUnitBOTemp.getPackingSlip().trim())){
				   shippingUnitBO = shippingUnitBOTemp;
				   break;
			   }
		   }
	   }
	   return shippingUnitBO;
   }
    	
    /**
     * 
     */
    /*private void populateShippingUnits() {
        
    	List<ShippingUnitBO> shippingUnitList = orderUnit.getShippingUnitList();
        if (shippingUnitList == null) {
            shippingUnitList = new ArrayList<ShippingUnitBO>();
            orderUnit.setShippingUnitList(shippingUnitList); 
        }
        
        // if the order unit passed in had a shipping unit list, go through that list and associate the 
        // items lists into the proper existing shipping unit.
        // If there was one that is passed in as empty, we'll put all items we retrieved with no packing slip
        // into that list, regardless of if they were one row or multiple in the returned data table.
        for (ShippingUnitBO shippingUnit : orderUnit.getShippingUnitList()) {
                
            String packingSlip = shippingUnit.getPackingSlip();
            if (GenericValidator.isBlankOrNull(packingSlip)) {
                packingSlip = NO_PACKING_SLIP;
            }
			List<ShippedItemBO> itemsForThisPackingSlip = packingSlipItemMap.get(packingSlip);
			shippingUnit.setShippedItemsList(itemsForThisPackingSlip);
			if (packingSlipPlantMap.get(packingSlip) != null) {
				shippingUnit.setShipFrom(packingSlipPlantMap
						.get(packingSlip));
			}
			// if there is no packing slip number (no delivery number, in theory the packing status key is the order
			// status key.  The packing status can be set to in progress if that is the case.
			String packingStatusKey = SapConstants.IN_PROCESS_KEY;
			if (NO_PACKING_SLIP.equals(packingSlip) == false) {
				// we have a packing slip number.  Look up the status
				packingStatusKey = getSapPackingStatusKey(orderPackageStatusKey);
				shippingUnit.setPackingStatus(packingStatusKey);
				//shippingUnit.setPackingStatusDescription(MessageResourceUtil.getIPOMessage(packingStatusKey, null));
			}
			// don't want to put this on anywhere else
			packingSlipItemMap.remove(packingSlip);
        }
        
        // we have now gone through the passed in shipping units and assocated the items lists to the shipping units
        // with matching packing slips.  When we did that, we removed that packing slip key from the map.
        // if there are any left, put them into newly constructed shipping units, albeit with less info on them
        // as that info comes from the order search.  This will typically be when a caller passes in an order unit
        // with JUST the system order number populated.
        for (String packingSlip : packingSlipItemMap.keySet()) {
            
            List<ShippedItemBO> itemsForThisPackingSlip = packingSlipItemMap.get(packingSlip);
                
            if (itemsForThisPackingSlip != null && itemsForThisPackingSlip.size() == 0 && NO_PACKING_SLIP.equals(packingSlip)) {
                // if there are no items and no packing slip, skip it.  Not sure i shouldn't
                // skip it even if there is a packing slip, but thinking I'll report the as yet
                // empty packing slip number
                continue;
            }
               
            // create a shipping unit for this list
            ShippingUnitBO shippingUnit = new ShippingUnitBO();
            shippingUnit.setPackingSlip(packingSlip);
            shippingUnit.setShippedItemsList(itemsForThisPackingSlip);
       
	         // if there is no packing slip number (no delivery number, in theory the packing status key is the order
	         // status key.  The packing status can be set to in progress if that is the case.
	         String packingStatusKey = SapConstants.IN_PROCESS_KEY;
	         if (NO_PACKING_SLIP.equals(packingSlip) == false) {
	             // we have a packing slip number.  Look up the status
	             packingStatusKey = getSapPackingStatusKey(orderPackageStatusKey);
	             shippingUnit.setPackingStatus(packingStatusKey);
	             //shippingUnit.setPackingStatusDescription(MessageResourceUtil.getIPOMessage(packingStatusKey, null));
	         }
	         shippingUnitList.add(shippingUnit);
	     }
    }*/
    
   private Map<String, List<ShippedItemBO>> getItemsShippedWithoutPackingSlip(List<ShippedItemBO> itemsListWithoutPackingSlip){
		Map<String, List<ShippedItemBO>> dcItemsMap = null;

		// DoubleCheck
		if (itemsListWithoutPackingSlip != null
				&& (!itemsListWithoutPackingSlip.isEmpty())) {
			dcItemsMap = new HashMap<String, List<ShippedItemBO>>();

			for (ShippedItemBO shippedItemBO : itemsListWithoutPackingSlip) {
				DistributionCenterBO shippingDc = lineNumberPlantMap
						.get(shippedItemBO.getLineNumber());
				List<ShippedItemBO> itemfromThisDcList = null;

				if ((itemfromThisDcList = dcItemsMap.get(shippingDc.getCode())) == null) {
					itemfromThisDcList = new ArrayList<ShippedItemBO>();
					dcItemsMap.put(shippingDc.getCode(), itemfromThisDcList);
				}
				itemfromThisDcList.add(shippedItemBO);
			}
			return dcItemsMap;
		}
		return null;
   }
    
    /**
     * Construct the ship to account associated with the data in the given export params from SAP
     * @param exportParamList
     * @return
     */
    protected AccountBO createShipToAccount(JCoParameterList exportParamList) {
        
        String shipToAccountCode = exportParamList.getString(SapConstants.SHIP_TO_CUSTOMER_KEY);
        String shipToAccountName = exportParamList.getString(SapConstants.SHIP_TO_NAME_KEY);
        AccountBO shipToAccount = new AccountBO();
        shipToAccount.setAccountCode(shipToAccountCode);
        shipToAccount.setAccountName(shipToAccountName);
        
        // get the address
        AddressBO shipToAddress = new AddressBO();
        shipToAddress.setAddrName(exportParamList.getString(SapConstants.SHIP_TO_NAME_KEY));
        shipToAddress.setAddrLine1(exportParamList.getString(SapConstants.SHIP_TO_STREET1_KEY));
        shipToAddress.setAddrLine2(exportParamList.getString(SapConstants.SHIP_TO_STREET2_KEY));
        shipToAddress.setCity(exportParamList.getString(SapConstants.SHIP_TO_CITY_KEY));
        shipToAddress.setStateOrProv(exportParamList.getString(SapConstants.SHIP_TO_STATE_KEY));
        shipToAddress.setZipOrPostalCode(exportParamList.getString(SapConstants.SHIP_TO_ZIPCODE_KEY));
        
        // defaulting SAP to USA
        CountryBO country = createShipToCountry(exportParamList);
        shipToAddress.setCountry(country);
        shipToAccount.setAddress(shipToAddress);
        return shipToAccount;
    }
}

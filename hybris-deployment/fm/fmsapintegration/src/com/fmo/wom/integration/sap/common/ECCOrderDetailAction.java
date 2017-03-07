package com.fmo.wom.integration.sap.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.ECCShippedOrder;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShippedItemBO;
import com.fmo.wom.domain.ShippedOrderDetail;
import com.fmo.wom.domain.ShippingUnitBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class ECCOrderDetailAction extends SapBapiAction{

	private static Logger logger = Logger.getLogger(ECCOrderDetailAction.class);
	
	private static final String ITEM_INFO_TABLE_NAME_KEY = "ITEM_VIEW_DATA";
    
	private static final String SHIPMENT_INFO_TABLE_NAME_KEY = "DELIVERY_HISTORY";
    
	protected static final String RETURN_CODE_SUCCESS = "00";
    
	protected static final String RETURN_CODE_ORDER_NOT_FOUND = "01";
    
	protected static final String RETURN_CODE_NO_RESULTS = "02";
    
	protected static final String NO_PACKING_SLIP = "NO_PACKING_SLIP";
    
	private boolean noResultsFound = false;
    
	private boolean orderNotFound = false;
	
	private String orderPackageStatusKey = "";
	
	private String orderStatus = null;
    
	private List<ECCShippedOrder> eccOrderHeaderList = null;
	
	private String orderNumber = null;
	
	private ShippedOrderDetail eccOrderDetail = null;
	
	private List<ShippingUnitBO> shippingUnitBOList = null;
	
	private Map<String, List<ShippedItemBO>> packingSlipItemMap;
	
	private Map<String, DistributionCenterBO> packingSlipPlantMap;
	
	private Map<Integer, DistributionCenterBO> lineNumberPlantMap;
	
	public ECCOrderDetailAction(List<ECCShippedOrder> anECCOrderHeaderList) {
		super();
		this.eccOrderHeaderList = anECCOrderHeaderList;
	}
	
	private void createShippingUnits() {
		logger.info("createShippingUnits() .. Creating "+eccOrderHeaderList.size()+" ShippingUnitBO's from eccOrderHeaderList ");
		shippingUnitBOList = new ArrayList<ShippingUnitBO>();
		
		boolean foundShipped = false;
        boolean foundCancelled = false;
        boolean foundBackOrder = false;
        
        orderStatus = "IN_PROCESS";
        
		for(ECCShippedOrder eccshippedOrder:eccOrderHeaderList){
			ShippingUnitBO aShippingUnit = new ShippingUnitBO();
            aShippingUnit.setPackingSlip(eccshippedOrder.getDeliveryNumber());
            aShippingUnit.setPackingStatus(eccshippedOrder.getTrackingStatus());
            if ("BACKORDER".equals(aShippingUnit.getPackingStatus())) {
                foundBackOrder = true;
            } else if ("CANCELLED".equals(aShippingUnit.getPackingStatus())) {
                foundCancelled = true;
            }  else if ("SHIPPED".equals(aShippingUnit.getPackingStatus())) {
                foundShipped = true;
            }
           // aShippingUnit.setPackingStatusDescription(MessageResourceUtil.getIPOMessage(packingStatus, null));
            aShippingUnit.setShipDate(eccshippedOrder.getShipDate());
            aShippingUnit.setShipFrom(eccshippedOrder.getShippingDC());           
            aShippingUnit.setCarrierCode(eccshippedOrder.getCarrierCode());
            aShippingUnit.setCarrierName(eccshippedOrder.getCarrierName());
            aShippingUnit.setTrackingNumber(eccshippedOrder.getTrackingNumber());
            aShippingUnit.setExternalSystmem(ExternalSystem.EVRST);
            shippingUnitBOList.add(aShippingUnit);
		}
		
		// if we're here, means we didn't find an in process one.
        if (foundBackOrder) { orderStatus = "BACKORDER"; }
        if (foundShipped) { orderStatus = "SHIPPED"; }
        if (foundCancelled) { orderStatus = "CANCELLED"; }
	}

	public ShippedOrderDetail getOrderDetail(String eccOrderNumber) throws WOMExternalSystemException {
		
		if(eccOrderNumber == null || eccOrderNumber.isEmpty()){
			logger.error("Failed to get Order detail for order number "+eccOrderNumber);
			return null;
		}
		
		this.orderNumber = eccOrderNumber;
		
		if(eccOrderHeaderList !=null && (!eccOrderHeaderList.isEmpty())){
			createShippingUnits();
		}		

		executeSapFunction();
		
		if (noResultsFound || orderNotFound) {
            return null;
        }
		// we now have some results in the packing slip / item list maps.  Check
        // against the input and put the items into the proper places
        populateShippingUnits();

        //Overall Order Status Based on Individual Shipping Unit Status
        eccOrderDetail.setOrderStatus(orderStatus);
		return eccOrderDetail;
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
    		eccOrderDetail.setShippingUnitBOList(shippingUnitList);
    	}
   }
	
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
	
	private ShippingUnitBO getExistingShippingUnitBO(String packingSlip){
	   
	   ShippingUnitBO shippingUnitBO = null;
	   if (shippingUnitBOList != null) {
		   for(ShippingUnitBO shippingUnitBOTemp: shippingUnitBOList){
			   if(packingSlip.trim().equalsIgnoreCase(shippingUnitBOTemp.getPackingSlip().trim())){
				   shippingUnitBO = shippingUnitBOTemp;
				   break;
			   }
		   }
	   }
	   return shippingUnitBO;
   }
	
	
	@Override
    public String getFunctionName() {
        return SapConstants.GET_SHIPMENT_DETAIL_FUNC_NAME;
    }

	
	@Override
	public void initializeImportParams(JCoParameterList importParams) {
		importParams.setValue(SapConstants.SAP_ORDER_KEY, orderNumber);
	}
	
	@Override
    public List<String> getInTableKeyList() {
        return null;
    }
	
	@Override
	public void initializeInTableParams(String tableName, JCoTable inTable) {
		return;
	}
	
	
	@Override
    public List<String> getOutTableKeyList() {
        List<String> outTables = new ArrayList<String>();
        outTables.add(ITEM_INFO_TABLE_NAME_KEY);
        outTables.add(SHIPMENT_INFO_TABLE_NAME_KEY);
        return outTables;
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
    
    
    @Override
    protected void processExportParamList(JCoParameterList exportParamList) {
        
        // if we didn't find anything, can return null
        if (noResultsFound || orderNotFound) {
            return;
        }
        
        // construct the fully populated ShippedOrderBO based on the 
        // export params
        eccOrderDetail = new ShippedOrderDetail();
        eccOrderDetail.setMasterOrderNumber(exportParamList.getString(SapConstants.MASTER_ORDER_NUMBER_KEY));
        eccOrderDetail.setPoNumber(exportParamList.getString(SapConstants.PO_NUMBER_KEY));
        eccOrderDetail.setOrderNumber(orderNumber);
        eccOrderDetail.setBillToAccount(createBillToAccount(exportParamList));
        eccOrderDetail.setShipToAccount(createShipToAccount(exportParamList));
        
        boolean isEmergency = getSapBoolean(exportParamList.getString(SapConstants.EMERGENCY_FLAG_KEY));
        eccOrderDetail.setOrderType(OrderType.STOCK);
        if (isEmergency) {
        	eccOrderDetail.setOrderType(OrderType.EMERGENCY);
        }
        
        eccOrderDetail.setOrderedBy(exportParamList.getString(SapConstants.ORDERED_BY_KEY));
        
        eccOrderDetail.setExternalSystem(getExternalSystem());

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
        
        eccOrderDetail.setOrderSource(orderSource);
        // orderUnit.setOrderSourceDescription(MessageResourceUtil.getIPOMessage(orderSource, null));
        eccOrderDetail.setOrderDate(exportParamList.getDate(SapConstants.ORDER_DATE_KEY));
        eccOrderDetail.setInvoiceDate(exportParamList.getDate(SapConstants.INVOICE_DATE_KEY));
        eccOrderDetail.setCancelledDate(exportParamList.getDate(SapConstants.CANCEL_DATE_KEY));
        eccOrderDetail.setRequestedDeliveryDate(exportParamList.getDate(SapConstants.REQ_DELIVERY_DATE_KEY));

         // set the comments
        eccOrderDetail.setCommentsList(new ArrayList<String>());
        eccOrderDetail.addComment(exportParamList.getString(SapConstants.FREIGHT_TEXT_KEY));
        eccOrderDetail.addComment(exportParamList.getString(SapConstants.HEADER_TEXT_1_KEY));
        eccOrderDetail.addComment(exportParamList.getString(SapConstants.HEADER_TEXT_2_KEY));
        eccOrderDetail.addComment(exportParamList.getString(SapConstants.HEADER_TEXT_3_KEY));
        
        // retain the order package status key
        orderPackageStatusKey = exportParamList.getString(SapConstants.ORD_PKG_STATUS_KEY);
        
    }
    
    protected String getReturnCode(JCoStructure returnStructure) {
        return returnStructure.getString(SapConstants.RETURN_CODE);
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
    
    protected ExternalSystem getExternalSystem() {
        return ExternalSystem.EVRST;
    }
    
    
    protected Market getMarket() {
        return Market.US_CANADA;
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
        
        //CountryBO country = createShipToCountry(exportParamList);
        //billedToAddress.setCountry(country);
        
        // defaulting SAP to USA
        billToAccount.setAddress(billedToAddress);
        
        return billToAccount;
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
    
    protected CountryBO createShipToCountry(JCoParameterList exportParamList) {
        CountryBO country = new CountryBO();
        country.setCountryAbbreviation(AddressBO.US_CODE);
        country.setIsoCountryCode(AddressBO.US_CODE);
        return country;
    }
    
    @Override
    protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
    }
	
    
    @Override
	protected Logger getLogger() {
		return logger;
	}
	
}

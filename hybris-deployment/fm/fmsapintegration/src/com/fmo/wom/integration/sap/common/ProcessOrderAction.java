package com.fmo.wom.integration.sap.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.SapShippingCodeBO;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConstants;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordField;
import com.sap.conn.jco.JCoRecordFieldIterator;
import com.sap.conn.jco.JCoRuntimeException;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public abstract class ProcessOrderAction extends SapBapiAction {

	private static Logger logger = Logger.getLogger(ProcessOrderAction.class);
    
    private static final String IN_TABLE_NAME_KEY = "ITEM_DATA";
    private static final String IN_ITEM_TABLE_NAME_KEY = "ITEM_DATA";
    private static final String IN_FREIGHT_TABLE_NAME_KEY = "FREIGHT_DATA";
    private static final String RETURN_CODE_SUCCESS = "000";
    private static final String RETURN_CODE_ORDER_SAVED_NO_DELIVERY_CREATED = "261";
    
    protected abstract List<ItemBO> getItemListForSystem(List<ItemBO> inputItemList);
    
    // These are the input parameters
    private OrderBO order;                      // order to process
    private String sapAccountCode;              // sap account code to use
    private String shipToAccountCode;           // sap account code to use
    private CustomerSalesOrgBO customerSalesOrganization;   // sales org for the sap account
    
    private String sapOrderNumber = "";
    private List<ItemBO> sapItemList;
    private Map<Integer, Integer> lineNumberInputNumberMap;
    
	Map<String, DistributionCenterBO> dcMapForFreightCost = new HashMap<String, DistributionCenterBO>();
	
	private boolean orderSavedButNoDeliveryCreatedFlag = false;

    protected ProcessOrderAction() { super(); }

    public ProcessOrderAction(String sapAccountCode, String shipToAccountCode, CustomerSalesOrgBO salesOrg, OrderBO anOrder) {
        super();
        this.order = anOrder;
        this.sapAccountCode = sapAccountCode;
        this.shipToAccountCode = shipToAccountCode;
        this.customerSalesOrganization = salesOrg;
    }
    
    public ProcessOrderAction(String sapAccountCode, String shipToAccountCode, 
                              CustomerSalesOrgBO salesOrg, OrderBO anOrder, SapConnectionManager aCxnMgr) {
        super(aCxnMgr);
        this.order = anOrder;
        this.sapAccountCode = sapAccountCode;
        this.shipToAccountCode = shipToAccountCode;
        this.customerSalesOrganization = salesOrg;
    }

    protected OrderBO getOrder() {
        return order;
    }

    protected String getSapAccountCode() {
		return sapAccountCode;
	}

	protected String getShipToAccountCode() {
		return shipToAccountCode;
	}

	/**
     * Main method to execute this action.  Returns the order number created by the create
     * order function in SAP
     * @throws WOMExternalSystemException
     */
    public String execute() throws WOMExternalSystemException {
        logger.debug("@@ Inside execute : ProcessOrderAction @@ Master Order Number "+order.getMstrOrdNbr());//Added by Abhijit
        logger.debug("@@ Inside execute : ProcessOrderAction @@ Item Display Part Number "+order.getItemList().get(0).getDisplayPartNumber());//Added by Abhijit
        logger.debug("@@ Inside execute : ProcessOrderAction @@ Item External System "+ order.getItemList().get(0).getExternalSystem());//Added by Abhijit
        sapItemList = getItemListForSystem(order.getItemList());
       
        
        List<ItemBO> validItems = validateInputItemList(sapItemList);
        
        
        // if we have items to process for sap, execute the function
        if (sapItemList.size() > 0 && validItems.size() > 0) {
            executeSapFunction();
            order.setOrderSavedButNoDeliveryCreatedFlag(orderSavedButNoDeliveryCreatedFlag);
        }
        
        return sapOrderNumber;
    }
    
    @Override
    protected Logger getLogger() { return logger; }
    
    @Override
    public String getFunctionName() {
        return SapConstants.PROCESS_ORDER_FUNC_NAME;
    }

    @Override
    public void initializeImportParams(JCoParameterList importParams) {
        
    	logger.info("initializeImportParams(...): Order Type: '" + order.getOrderType() + "', " + 
    					"Free Freight Flag: " + getSapYesNo(order.receivesFreeFreight()));
        importParams.setValue(SapConstants.SOLD_TO_CUSTOMER_KEY, sapAccountCode);
        
        // ship to is a required field for check out, so default it to the sold to just in case 
        // it wasn't provided
        importParams.setValue(SapConstants.SHIP_TO_CUSTOMER_KEY, sapAccountCode);
        importParams.setValue(SapConstants.SALES_ORGANIZATION_KEY, customerSalesOrganization.getSalesOrganization().getCode()); 
        importParams.setValue(SapConstants.DISTRIBUTION_CHANNEL_KEY, customerSalesOrganization.getDistributionChannel());
        importParams.setValue(SapConstants.DIVISION_KEY, customerSalesOrganization.getDivision()); 
        importParams.setValue(SapConstants.PO_NUMBER_KEY, order.getCustPoNbr()); 
        importParams.setValue(SapConstants.PO_DATE_KEY, new Date());
        importParams.setValue(SapConstants.FREE_FREIGHT_FLAG_KEY, getSapYesNo(order.receivesFreeFreight()));
        importParams.setValue(SapConstants.MASTER_ORDER_NUMBER_KEY, order.getMstrOrdNbr());
        importParams.setValue(SapConstants.ORDERER_NAME_KEY, order.getOrderedBy());
            
        boolean isEmergency = OrderType.EMERGENCY == order.getOrderType();
        boolean isSmallPackage = OrderType.REGULAR == order.getOrderType();
        
        //importParams.setValue(SapConstants.EMERGENCY_FLAG_KEY, getSapYesNo(isEmergency));
        importParams.setValue(SapConstants.EMERGENCY_FLAG_KEY, order.getOrderType().getBpcsCode());
        importParams.setValue(SapConstants.SHIP_CONDITIONS_KEY,  
        		(isEmergency || isSmallPackage) ? SapConstants.EMERGENCY_DEFAULT_SHIP_CONDITION :SapConstants.STOCK_DEFAULT_SHIP_CONDITION);
		//Mahaveer - to change Shipping conditions here

		if (order.getOrderType() == OrderType.PICKUP)
		{
			final String shipingOption = order.getPkupOrderType();

			logger.info("@@ After  Inside execute : ProcessOrderAction @@ getSessionService() " + shipingOption);
			if (null != shipingOption && shipingOption.equalsIgnoreCase("delivery"))
			{
				// 08 - Truck Delivery  - PKDELI
				importParams.setValue(SapConstants.SHIP_CONDITIONS_KEY, SapConstants.PKUP_DELIVERY_SHIP_CONDITION);
			}
			else if (null != shipingOption && shipingOption.equalsIgnoreCase("shipfrom"))
			{
				// 06 - PKUP Shipping - FMSTD
				importParams.setValue(SapConstants.SHIP_CONDITIONS_KEY, SapConstants.PKUP_STANDARD_SHIP_CONDITION);
			}
			else
			{
				// 11 - PKUP - Default
				importParams.setValue(SapConstants.SHIP_CONDITIONS_KEY, SapConstants.PKUP_DEFAULT_SHIP_CONDITION);
			}
			importParams.setValue(SapConstants.EMERGENCY_FLAG_KEY, OrderType.EMERGENCY.getBpcsCode());
        }

        importParams.setValue(SapConstants.SMALL_PACKAGE_FLAG_KEY, getSapYesNo(isSmallPackage));
        
        importParams.setValue(SapConstants.BACKORD_CANCEL_FLAG_KEY, order.getBackOrderPolicy().getCode());
        
        Date requestedDeliveryDate = getRequestedDeliveryDate();
        if (requestedDeliveryDate != null) {
            importParams.setValue(SapConstants.REQUESTED_DELIVERY_DATE_KEY, requestedDeliveryDate);
        }
        
        setShipToImportParams(importParams);
        
        setOrderComments(importParams);
        
        setCreditCardAuthorizationParams(importParams);
    }

	@Override
    public List<String> getInTableKeyList() {
	    List<String> tables = new ArrayList<String>();
        tables.add(IN_ITEM_TABLE_NAME_KEY);
        tables.add(IN_FREIGHT_TABLE_NAME_KEY);
        return tables;
    }

	private Map<Integer, List<ItemBO>> getLineNumberItemMap(List<ItemBO> eccItemsList){
		
		if(eccItemsList != null && (! eccItemsList.isEmpty())){
			
			Map<Integer, List<ItemBO>> lineNumberItemBOMap = new HashMap<Integer, List<ItemBO>>();
			for(ItemBO anItem : eccItemsList){
				
				int processOrderLineNumber = anItem.getProcessOrderLineNumber();
				List<ItemBO> itemoBOList = lineNumberItemBOMap.get(processOrderLineNumber);
				if(itemoBOList == null){
					itemoBOList = new ArrayList<ItemBO>();
					lineNumberItemBOMap.put(processOrderLineNumber, itemoBOList);
				}
				itemoBOList.add(anItem);
			}
			return lineNumberItemBOMap;
		}
		
		return null;
	}
	
	@Override
    public void initializeInTableParams(String tableName, JCoTable in) {
		
		if (IN_ITEM_TABLE_NAME_KEY.equals(tableName)) {

			List<ItemBO> validItems = validateInputItemList(sapItemList);
	        
	        Map<Integer, List<ItemBO>> lineNumberItemBOMap = getLineNumberItemMap(validItems);
	        if(lineNumberItemBOMap != null && (!lineNumberItemBOMap.isEmpty())){
	        	
	            in.setRow(0);
	            
	        	for(Integer processOrderLineNumber : lineNumberItemBOMap.keySet()){
	        		List<ItemBO> eccItemsList = lineNumberItemBOMap.get(processOrderLineNumber);
	        		
	        		for(ItemBO anItem : eccItemsList){
	        			
	        			if(((order.getOrderType() == OrderType.STOCK) || (order.getOrderType() == OrderType.REGULAR)) && anItem.isHeader()){
	        				continue;
	        			}
	
	                    //@todo implement superceded part if necessary
	                    String partNumber = getPartNumberForCheckout(anItem);
	                    QuantityBO orderQuantity = anItem.getItemQty();
	                    int totalQuantity = orderQuantity.getRequestedQuantity();
	                    
	                    // How many DCs do we need to support here?
	                    // We could support as many as we have once the quantity items needed to know
	                    // if there is inventory at each are available.  For now, just send the selected.
	                    InventoryBO inventoryToUse = anItem.getSelectedInventory();
	                    SapShippingCodeBO sapShippingCode = null;
	                    DistributionCenterBO dc = null;
	                    int assignedQuantity = 0;
	                    if (inventoryToUse != null) {
	                        assignedQuantity = inventoryToUse.getAssignedQty();
	                        dc = inventoryToUse.getDistributionCenter();
	                        if (inventoryToUse.getShippingCode() != null) {
	                            sapShippingCode = inventoryToUse.getShippingCode().getSapShippingCode();
	                        }
	                    }
	                    // if we are getting anything from this one, set the input table for it
	                    if (assignedQuantity > 0 || totalQuantity > 0) {
	                        
	                        in.appendRow();
	                        
	                        // Don't know why we are doing this.!!!
	                        // SAP team suggested this workaround to solve a particular issue.
	                        // the issue was Orders will not go through for Mexico Customers when they use their own material number.
	                        // SAP team agreed this is a workaround - and they will come back to this when time permits. God Knows when. 
	                        //in.setValue(SapConstants.SAP_PART_NUM_KEY, StringUtil.lpadForNumericStringOnly(partNumber, 18, "0"));
	                        // The permanent fix is in place in SAP so removed the padding.
	                        in.setValue(SapConstants.SAP_PART_NUM_KEY, partNumber);
	                        in.setValue(SapConstants.ITEM_TEXT_KEY, anItem.getComment());
	                        
	                        // total - requested from item
	                        // assigned - inventory from asssigned.
	                        in.setValue(SapConstants.QUANTITY_KEY, totalQuantity);
	                        in.setValue(SapConstants.CONFIRMED_QUANTITY_KEY, assignedQuantity);
	                        if (dc != null) {
	                            in.setValue(SapConstants.PLANT_CODE_KEY, dc.getCode());
	                            if (!anItem.isHeader()) {
	                            	// Add the DC to a map which we will use later to populate the FREIGHT_DATA table.
                            		if (!dcMapForFreightCost.containsKey(dc.getCode())) {
                            			logger.info("initializeInTableParams(...): DC Code '" + dc.getCode() + "' does NOT exist in the map. So, it will be added to the map.");
                            			dcMapForFreightCost.put(dc.getCode(), dc);
                            		} else {
                            			logger.info("initializeInTableParams(...): DC Code '" + dc.getCode() + "' already exists in the map. So, it will NOT be added to the map.");
                            		}
	                            }
	                        }
	                        if (sapShippingCode != null) {
	                            in.setValue(SapConstants.CARRIER_KEY, sapShippingCode.getCarrierCode());
	                            in.setValue(SapConstants.INCO_TERMS_KEY, sapShippingCode.getIncoTerms());
	                            in.setValue(SapConstants.ROUTE_KEY, sapShippingCode.getRoute());
	                        }
	                    }
	
	                    if(anItem.isHeader()){
	                    	//for Emergency Orders
	                    	in.setValue("POST_ITEM", "H");
	                    } else {
	                    	if((order.getOrderType() == OrderType.STOCK) || (order.getOrderType() == OrderType.REGULAR)){
	                    		in.setValue("POST_ITEM", "H");
	                    	} else {
	                    		in.setValue("POST_ITEM", "S");
	                    	}
	                    	in.setValue("SELECTED", "X");
	                    }
	        		}
	        	}
	        }
		} else if (IN_FREIGHT_TABLE_NAME_KEY.equals(tableName)) {
			
			if (dcMapForFreightCost != null && dcMapForFreightCost.size() > 0) {
				Set<String> dcCodesSet = (Set<String>) dcMapForFreightCost.keySet();
				for (String dcCode : dcCodesSet) {
					DistributionCenterBO dcBO = dcMapForFreightCost.get(dcCode);
					if (dcBO != null) {
						in.appendRow();
						
						in.setValue(SapConstants.PLANT_CODE_KEY, dcBO.getCode());
						in.setValue(SapConstants.FREIGHT_COST_KEY, (dcBO.getFreightCost() == null ? new BigDecimal(0.00) : dcBO.getFreightCost()));
						in.setValue(SapConstants.FREIGHT_COST_CURRENCY_CODE_KEY, (dcBO.getFreightCostCurrencyCode() == null ? "USD" : dcBO.getFreightCostCurrencyCode()));
					}
				}
			}
		}
	}
	
    @Override
    public List<String> getOutTableKeyList() {
        List<String> outTables = new ArrayList<String>();
        return outTables;
    }

    @Override
    public void processOutTable(String outTableName, JCoTable out) {
        // No out table for this function
        ;
    }
    
    /**
     * This is overridden to get a hook to the slightly return code key and log the code
     */
    @Override
    protected boolean isSuccessReturnStructure(JCoStructure returnStructure) {
        
        boolean isSuccess = true;
        orderSavedButNoDeliveryCreatedFlag = false;

        // Check 'Success' OR 'Order Saved but no Delivery Created' return
        String returnType = returnStructure.getString(SapConstants.RETURN_TYPE);
        logger.info("isSuccessReturnStructure(...): SAP Process Order Call: Return Type: " + returnType);
        String returnCode = returnStructure.getString(SapConstants.RETURN_CODE);
        logger.info("isSuccessReturnStructure(...): SAP Process Order Call: Return Code = " + returnCode);
        // do some logging before returning
        if (RETURN_CODE_SUCCESS.equals(returnCode) == false) {
        	if (OrderSource.IPO == order.getOrderSource() || !RETURN_CODE_ORDER_SAVED_NO_DELIVERY_CREATED.equals(returnCode)) {
        		logger.error("isSuccessReturnStructure(...): SAP Process Order call NOT successful. Return Code = " + returnCode);
        		isSuccess = false;
        	} else if (RETURN_CODE_ORDER_SAVED_NO_DELIVERY_CREATED.equals(returnCode) == true) {
        		logger.info("isSuccessReturnStructure(...): Order successfully saved in SAP, but no Delivery was created!");
        		orderSavedButNoDeliveryCreatedFlag = true;
        	}
        }
        return isSuccess;
    }
    
    /** These are protected so subs can override them */
    protected void checkReturnStructure(JCoStructure returnStructure) throws WOMExternalSystemException {

        if (!isSuccessReturnStructure(returnStructure)) {
            
            String errorMessage = returnStructure.getString(SapConstants.RETURN_MESSAGE);
            String logErrorMessage = "Errors occurred during " + getFunctionName() + ". Message=["+errorMessage+"]";
            // This is going to look weird, so bear with me.  We are getting back a "Vertex is down." message when
            // the user supplies an invalid zip code for the state and account.  We are working with SAP to get this
            // updated, so in the mean time, we'll append a message to that effect.
            if ("Vertex is down.".equals(errorMessage)) {
                errorMessage = "Failure to process order. Please check your state and zip code supplied for shipping.  If correct, contact Customer Service report 'Vertex is down.'";
            }
            getLogger().error(logErrorMessage);
            throw new WOMExternalSystemException(errorMessage);
        }
    }
    /**
     * Process the export param list for this call.  For process order, all we need
     * is the order number returned.
     */
    @Override
    protected void processExportParamList(JCoParameterList exportParamList) {
        sapOrderNumber = exportParamList.getString(SapConstants.SAP_ORDER_KEY);
    }
    
    /**
     * Utility to set ship to info on the import params
     * @param importParams
     */
    private void setShipToImportParams(JCoParameterList importParams) {

        AddressBO shipToAddress = null;
        if (order.getShipToAcct() != null) {
            shipToAddress = order.getShipToAcct().getAddress();
            if (GenericValidator.isBlankOrNull(shipToAccountCode) == false) {
                importParams.setValue(SapConstants.SHIP_TO_CUSTOMER_KEY, shipToAccountCode);
            } 
        }
        
        if (order.isManualShipTo()) {
            shipToAddress = order.getManualShipToAddress().getAddress();
            importParams.setValue(SapConstants.NAME_KEY, order.getManualShipToAddress().getName());
        } else if (order.getShipToAcct() != null){
        	importParams.setValue(SapConstants.NAME_KEY, order.getShipToAcct().getAccountName());
        }

        if ((shipToAddress != null)&& (order.isManualShipTo())){
            importParams.setValue(SapConstants.STREET1_KEY, shipToAddress.getAddrLine1());
            importParams.setValue(SapConstants.STREET2_KEY, shipToAddress.getAddrLine2());
            importParams.setValue(SapConstants.CITY_KEY, shipToAddress.getCity());
            importParams.setValue(SapConstants.STATE_KEY, shipToAddress.getStateOrProv());
            String zipOrPostalCode = shipToAddress.getZipOrPostalCode();
            if (StringUtil.isAValidCanadianPostalCode(zipOrPostalCode) == true) {
                // this is a valid canadian postal code.  Put a space between the Forward Segmentation Area and the Local Delivery Unit sections so
                // NABS reads it as a postal code and not a zip.  Hopefully this was done way up front, but this is a failsafe
                zipOrPostalCode = StringUtil.getFormattedCanadianPostalCode(zipOrPostalCode);
            }
            importParams.setValue(SapConstants.ZIPCODE_KEY, zipOrPostalCode);
            importParams.setValue(SapConstants.COUNTRY_KEY, shipToAddress.getCountry().getIsoCountryCode());
        }
    }
    

    /**
     * utility to set comments on the import params for the order
     * @param importParams
     */
    private void setOrderComments(JCoParameterList importParams) {

        if (order.getComment1() != null) {
            importParams.setValue(SapConstants.HEADER_TEXT_1_KEY, order.getComment1());
        }
        
        if (order.getComment2() != null) {
            importParams.setValue(SapConstants.HEADER_TEXT_2_KEY, order.getComment2());
        }
        if (order.getComment3() != null) {
            importParams.setValue(SapConstants.HEADER_TEXT_3_KEY, order.getComment3());
        }
    }
    
    private void setCreditCardAuthorizationParams(JCoParameterList importParams) {
    	importParams.setValue("ORD_REASON", "");
    	importParams.setValue("REQ_TOKEN", "Ahj/7wSRzTktehP1HBCcIkGbFg1YtWbGe2oxGDdzRT+KpCGvIBT+KpCGvLSB84qTxoZNJMt0gOyUlgTkc05LXoT9RwQnAAAA+xY0");
    	importParams.setValue("REQ_ID", "4254034550720176195662");
    	importParams.setValue("MER_REF_CODE", "1425403418461");
	}
    
    protected String getPartNumberForCheckout(ItemBO anItem) {
        if (GenericValidator.isBlankOrNull(anItem.getPredecessorPartNumber())) {
            return anItem.getPartNumber();
        }
        return anItem.getPredecessorPartNumber();
    }
    
    protected Date getRequestedDeliveryDate() {
        return order.getFutureDate();
    }
}

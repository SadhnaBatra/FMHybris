package com.fmo.wom.integration.sap.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippingUnitBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.GetShipmentOrderStatus;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderSearchFilter;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConstants;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public abstract class GetShipmentInfoAction extends SapBapiAction {

    private static final String IN_TABLE_NAME_KEY = "SOLDTO";
    private static final String OUT_TABLE_NAME_KEY = "ORDER_SEARCH_DATA";
    public static final String RETURN_CODE_NO_RESULTS = "02";
    public static final String RETURN_CODE_SEARCH_LIMIT_REACHED = "04";
    
    protected abstract ExternalSystem getExternalSystem();
    
    // allow reuse across market
    protected abstract Market getMarket();
    
    // These are the input parameters
    private String customerCode;
    
    // @todo migrate this to enum or constants
    private GetShipmentOrderStatus orderStatus = GetShipmentOrderStatus.ALL;;
    private String purchaseOrderNumber;
    private String orderNumber;
    private Date startDate;
    private Date endDate;
    private String sortOrder = "";
    // TODO get from property
    private int recordLimit = 800;
    private List<String> sapAccountCodeList;              // sap account codes to use in search
    private String shipToAccountCode;                     // sap ship to account code to use
    
    // values used for output
    private List<ShippedOrderBO> searchResultsList;
    boolean noResultsFound = false;
    boolean searchLimitReached = false;
    protected String orderReturnFlag;
    
    protected abstract void setOrderRetrunFlag(String orderRetrunFlag);
    
    protected abstract String getOrderRetrunFlag();
    
    public void setNoResultsFound(boolean noResultsFound) {
        this.noResultsFound = noResultsFound;
    }

    public void setSearchLimitReached(boolean searchLimitReached) {
        this.searchLimitReached = searchLimitReached;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public GetShipmentOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public int getRecordLimit() {
        return recordLimit;
    }

    public boolean isNoResultsFound() {
        return noResultsFound;
    }

    protected List<String> getSapAccountCodeList() {
        return sapAccountCodeList;
    }
    
    private GetShipmentInfoAction() { super(); }

    // These are currently the only search parameters supported.  As we move to include more (web or other interfaces), 
    // we'll probably want to migrate this from a constructor based to a setter based approach on input params.
    public GetShipmentInfoAction(String sapAccountCode, String masterOrderNumber, String customerPONumber) {
        super();
        this.sapAccountCodeList = makeAccountCodeList(sapAccountCode);
        this.orderNumber = masterOrderNumber;
        this.purchaseOrderNumber = customerPONumber;
    }
    
    // These are currently the only search parameters supported.  As we move to include more (web or other interfaces), 
    // we'll probably want to migrate this from a constructor based to a setter based approach on input params.
    public GetShipmentInfoAction(String sapAccountCode, String masterOrderNumber, String customerPONumber, Date startDate, Date endDate, OrderSearchFilter filter) {
        super();
        this.sapAccountCodeList = makeAccountCodeList(sapAccountCode);
        this.orderNumber = masterOrderNumber;
        this.purchaseOrderNumber = customerPONumber;
        this.startDate = startDate;
        this.endDate = endDate;
        if (filter != null) {
            orderStatus = filter.getShipmentSearchOrderStatus();
        }
    }
   
    public GetShipmentInfoAction(List<String> sapAccountCodeList, String shipToAccountCode, String masterOrderNumber,
                                String customerPONumber,  Date startDate, Date endDate, OrderSearchFilter filter) {
        super();
        this.sapAccountCodeList = sapAccountCodeList;
        this.shipToAccountCode = shipToAccountCode;
        this.orderNumber = masterOrderNumber;
        this.purchaseOrderNumber = customerPONumber;
        this.startDate = startDate;
        this.endDate = endDate;
        if (filter != null) {
            orderStatus = filter.getShipmentSearchOrderStatus();
        }
    }

    
    public GetShipmentInfoAction(String sapAccountCode, String shipToAccountCode, String masterOrderNumber, String customerPONumber, SapConnectionManager aCxnMgr) {
        super(aCxnMgr);
        this.sapAccountCodeList = makeAccountCodeList(sapAccountCode);
        this.shipToAccountCode = shipToAccountCode;
        this.orderNumber = masterOrderNumber;
        this.purchaseOrderNumber = customerPONumber;
    }

    /**
     * Main method to execute this action.  Returns the results list created by the
     * search function in SAP and processing the out table
     * @throws WOMExternalSystemException
     */
    public List<ShippedOrderBO> execute() throws WOMExternalSystemException {
        
        searchResultsList = new ArrayList<ShippedOrderBO>();
        
        if (isValidInputData() == true) {
            executeSapFunction();
        }
        return searchResultsList;
    }
    
    @Override
    public String getFunctionName() {
        return SapConstants.GET_SHIPMENT_INFO_FUNC_NAME;
    }

    @Override
    public void initializeImportParams(JCoParameterList importParams) {
    	
    	
    	importParams.setValue(SapConstants.PO_NUMBER_KEY, getPurchaseOrderNumber());
        importParams.setValue(SapConstants.ORDER_NUMBER_KEY, orderNumber);
        
        if (GenericValidator.isBlankOrNull(orderNumber)) {
            // if the master order number is not specified, set the start and end times to 
            // one year if they're not set.  This will get results, but not execute a search
            // on the entire system
            if (endDate == null) {
                endDate = new Date();
            }
            
            if (startDate == null) {
                startDate = new Date();
                Calendar startCal = Calendar.getInstance();
                startCal.add(Calendar.YEAR, -1);
                startDate.setTime(startCal.getTimeInMillis());
            }
        } else {
            // order number IS specified.  No need for dates
            startDate = null;
            endDate = null;
        }
        
        if(startDate != null) {
            importParams.setValue(SapConstants.START_DATE_KEY, SapConstants.dateFormatter.format(startDate));
        }
        if(endDate != null) {
            importParams.setValue(SapConstants.END_DATE_KEY, SapConstants.dateFormatter.format(endDate));
        }
        if (shipToAccountCode != null) {
            importParams.setValue(SapConstants.SHIP_TO_KEY, shipToAccountCode);   
        }
        importParams.setValue(SapConstants.REQUESTED_STATUS_KEY, orderStatus.getCode()); 
        importParams.setValue(SapConstants.SORT_ORDER_KEY, sortOrder);
        importParams.setValue(SapConstants.MAX_COUNT_KEY, recordLimit);
        importParams.setValue(SapConstants.ORDER_RETURN_FLAG, getOrderRetrunFlag());
        
    }

    @Override
    public List<String> getInTableKeyList() {
        return constructSingleTableKeyList(IN_TABLE_NAME_KEY);
    }


    @Override
    public void initializeInTableParams(String tableName, JCoTable in) {
       
        if (sapAccountCodeList != null) {
            for (String anAccountCode : sapAccountCodeList) {
                in.appendRow();
                in.setValue(SapConstants.CUSTOMER_KEY, anAccountCode); 
            }
        }
    }   

    @Override
    public List<String> getOutTableKeyList() {
        return constructSingleTableKeyList(OUT_TABLE_NAME_KEY);
    }

    @Override
    public void processOutTable(String outTableName, JCoTable out) {
     
        int numRows = out.getNumRows();
        out.setRow(0);
        int rowIndex = 0;
        
        searchResultsList = new ArrayList<ShippedOrderBO>();
        while (rowIndex < numRows) {
            // populate relevant fields onto shipped order
            ShippedOrderBO aShippedOrder = new ShippedOrderBO();
            
            aShippedOrder.setMasterOrderNumber(out.getString(SapConstants.MASTER_ORDER_NUMBER_KEY));
            aShippedOrder.setCustomerPurchaseOrderNum(out.getString(SapConstants.PO_NUMBER_KEY));
            
            AccountBO billToAccount = new AccountBO();
            billToAccount.setAccountCode(out.getString(SapConstants.CUSTOMER_KEY));
            SapAcctBO sapAccount = new SapAcctBO();
            sapAccount.setSapAccountCode(out.getString(SapConstants.CUSTOMER_KEY));
            billToAccount.setSapAccount(sapAccount);
            aShippedOrder.setBillToAccount(billToAccount);
            
            rowIndex = createOrderUnits(aShippedOrder, out, rowIndex);
            
            if("RETURNS".equalsIgnoreCase(getOrderRetrunFlag())){
            	aShippedOrder.setReturnOrderReason(out.getString(SapConstants.ORDER_RETURN_REASON));
            }
            searchResultsList.add(aShippedOrder);
        }        
    }
    
    /**
     * Check the input data for validity.  Conditions:  
     *  - The order number (if submitted) must start with a character if its over 10 characters long. If not,
     *  SAP blows up.
     * @return true is all conditions met, false otherwise.
     */
    private boolean isValidInputData() {

        boolean validInput = true;
        if (GenericValidator.isBlankOrNull(orderNumber) == false) {
            
            if (orderNumber.length() > 10 && Character.isLetter(orderNumber.charAt(0)) == false) {
                validInput = false;
            }
        }
        
        return validInput;
    }
    
    /**
     * Create order units for the given ShippingOrder and jco out table 
     * @param shippedOrder
     * @param currentOrder
     */
    private int createOrderUnits(ShippedOrderBO shippedOrder, JCoTable currentOrder, int rowIndex) {
        
        List<OrderUnitBO> orderUnitList = new ArrayList<OrderUnitBO>();
        while (isSameShippingOrder(shippedOrder, currentOrder)) {
            OrderUnitBO anOrderUnit = new OrderUnitBO();
            anOrderUnit.setExternalSystem(getExternalSystem());
            anOrderUnit.setOrderNumber( currentOrder.getString(SapConstants.SAP_ORDER_KEY));
            anOrderUnit.setOriginalOrderDate(currentOrder.getDate(SapConstants.ORDER_DATE_KEY));
            
            // order source is done via lookup in status on the order method key
            String orderSource = getSapOrderSource(currentOrder.getString(SapConstants.ORDER_METHOD_KEY));
            anOrderUnit.setOrderSourceKey(orderSource);
            //anOrderUnit.setOrderSourceDescription(MessageResourceUtil.getIPOMessage(orderSource, null));
            
            rowIndex = createShippingUnits(shippedOrder, anOrderUnit, currentOrder, rowIndex);
            orderUnitList.add(anOrderUnit);

            // if we're done, break out.
            if (hasAnotherRow(currentOrder) == false) {
                break;
            }
        }
        
        shippedOrder.setOrderUnitList(orderUnitList);
        return rowIndex;
    }
    
    /**
     * Check if the row we're on constitutes the same ShippingOrder as the 
     * given ShippingOrderBO.  Currently defined as equality of the Master
     * order number, customer PO number and account code
     * @param aShippedOrder order to check
     * @param currentOrder current row in JCo table to check against
     * @return true if all relevant fields are equal, false otherwise
     */
    private boolean isSameShippingOrder(ShippedOrderBO aShippedOrder, JCoTable currentOrder) {
     
        String rowMasterOrderNumber = currentOrder.getString(SapConstants.MASTER_ORDER_NUMBER_KEY);
        String rowCustomerPurchaseOrderNumber = currentOrder.getString(SapConstants.PO_NUMBER_KEY);
        String accountCode = currentOrder.getString(SapConstants.CUSTOMER_KEY);
        
        return fieldsEqual(rowMasterOrderNumber, aShippedOrder.getMasterOrderNumber()) &&
                fieldsEqual(rowCustomerPurchaseOrderNumber, aShippedOrder.getCustomerPurchaseOrderNum()) &&
                fieldsEqual(accountCode, aShippedOrder.getBillToAccount().getSapAccount().getSapAccountCode());
    }
    
    
    /**
     * Create order units for the given ShippingOrder and jco out table 
     * @param shippedOrder
     * @param currentOrder
     */
    private int createShippingUnits(ShippedOrderBO shippedOrder, OrderUnitBO orderUnit, JCoTable currentOrder, int rowIndex) {
        
        List<ShippingUnitBO> shippingUnitList = new ArrayList<ShippingUnitBO>();
        
        // need to make sure the row we're on is both the same shipped order and
        // same order unit.
        while (isSameShippingOrder(shippedOrder, currentOrder) && isSameOrder(orderUnit, currentOrder)) {
            ShippingUnitBO aShippingUnit = new ShippingUnitBO();
            aShippingUnit.setPackingSlip(currentOrder.getString(SapConstants.DELIVERY_NUMBER_KEY));
            
            // translate to our value
            String packingStatus = getSapPackingStatusKey( currentOrder.getString(SapConstants.TRACKING_STATUS_KEY));
            aShippingUnit.setPackingStatus(packingStatus);
           // aShippingUnit.setPackingStatusDescription(MessageResourceUtil.getIPOMessage(packingStatus, null));
            
            aShippingUnit.setShipDate(currentOrder.getDate(SapConstants.SHIP_DATE_KEY));
            aShippingUnit.setShipFrom( createDistributionCenter(getMarket(), currentOrder));           
            aShippingUnit.setCarrierCode(currentOrder.getString(SapConstants.CARRIER_KEY));
            aShippingUnit.setCarrierName(currentOrder.getString("CARRIER_NAME"));
            aShippingUnit.setTrackingNumber(currentOrder.getString(SapConstants.TRACKING_NUMBER_KEY) );
            aShippingUnit.setExternalSystmem(ExternalSystem.EVRST);
           
            
            shippingUnitList.add(aShippingUnit);
            
            // advance the row unless we don't have any left.
            if (currentOrder.nextRow() == false) {
                rowIndex++;
                break;
            }
            rowIndex++;
        }
        
        orderUnit.setShippingUnitList(shippingUnitList);
        return rowIndex;
    }
  
    /**
     *  Check if the row we're on constitutes the same OrderUnit as the 
     * given OrderUnitBO.  Currently defined as equality of the sap
     * order number
     * @param orderUnit order unit to check
     * @param currentOrder current row in jco
     * @return true if order numbers are same, false otherwise
     */
    private boolean isSameOrder(OrderUnitBO orderUnit, JCoTable currentOrder) {
        String orderNumber = currentOrder.getString(SapConstants.SAP_ORDER_KEY);
        return fieldsEqual(orderNumber, orderUnit.getOrderNumber());
    }
    
    /*
     * Couple of extra return codes for this one that don't necessarily mean catastrophic
     * failure.  Check them and retain values for proper processing later
     * @see com.fmo.wom.integration.sap.action.ActionBase#checkReturnStructure(com.sap.conn.jco.JCoStructure)
     */
    protected void checkReturnStructure(JCoStructure returnStructure) throws WOMExternalSystemException {

        String returnCode = returnStructure.getString(SapConstants.RETURN_CODE);
        
        if (!isSuccessReturnStructure(returnStructure)) {
            
            // did not receive success code - check for other return codes
            if (RETURN_CODE_NO_RESULTS.equals(returnCode)) {
                noResultsFound = true;
            } else if (RETURN_CODE_SEARCH_LIMIT_REACHED.equals(returnCode)) {
                searchLimitReached = true;
            } else {
                String errorMessage = returnStructure.getString(SapConstants.RETURN_MESSAGE);
                String logErrorMessage = "Errors occurred during " + getFunctionName() + ". Message=["+errorMessage+"]";
                getLogger().error(logErrorMessage);
                throw new WOMExternalSystemException(logErrorMessage);
            }
        }
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
     * look up the order source key in our system from the given returned sap value
     * @param sapOrderTypeKey
     * @return
     */
    private String getSapOrderSource(String sapOrderTypeKey) {

        String orderSource;
        try {
            orderSource = MessageResourceUtil.getEverestStatusKeyViaStatusCode(sapOrderTypeKey);
        } catch (IllegalArgumentException e) {
            // if we didn't find that on the look up, I'm going to just use the returned key.
            // This should not be a fatal error
            orderSource = sapOrderTypeKey;
        }
        return orderSource;
    }
     
    private List<String> makeAccountCodeList(String singleAccountCode) {
        List<String> accountCodeList = new ArrayList<String>();
        if (singleAccountCode != null) {
            accountCodeList.add(singleAccountCode);
        }
        return accountCodeList;
        
    }
   
}

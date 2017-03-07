package com.fmo.wom.integration.sap.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.ECCShippedOrder;
import com.fmo.wom.domain.OrderSearchCriteria;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class ECCOrderSearchAction extends SapBapiAction{

	private static Logger logger = Logger.getLogger(ECCOrderSearchAction.class);
	private static final String IN_TABLE_NAME_KEY = "SOLDTO";
	private static final String OUT_TABLE_NAME_KEY = "ORDER_SEARCH_DATA";
	public static final String RETURN_CODE_NO_RESULTS = "02";
	public static final String RETURN_CODE_SEARCH_LIMIT_REACHED = "04";
	
	private static int recordLimit = 800;	
	boolean noResultsFound = false;
    boolean searchLimitReached = false;
	
    private String orderNumber = null; 
	
    private List<String> sapAccountCodeList;
	
	private List<ECCShippedOrder> rawECCOrderList = null;
	
	
	private OrderSearchCriteria orderSearchCriteria = null;
	 
	public ECCOrderSearchAction(OrderSearchCriteria anOrderSearchCriteria) {
		super();
		this.orderSearchCriteria = anOrderSearchCriteria;
		if(orderSearchCriteria.isShipToLogin()){
			//Get the ECC A/c Code of all BillTo's of this ShipTo
			this.sapAccountCodeList = makeAccountCodeList(orderSearchCriteria.getBillToAccounts());
		} else {
			this.sapAccountCodeList = makeAccountCodeList(orderSearchCriteria.getECCAccountCode());
		}
		
		if(GenericValidator.isBlankOrNull(orderSearchCriteria.getConfirmationOrOrderNumber())){
			orderNumber = orderSearchCriteria.getOrderNumber();
		}else {
			orderNumber = orderSearchCriteria.getConfirmationOrOrderNumber();
		}
	}

	private List<String> makeAccountCodeList(String singleAccountCode) {
        List<String> accountCodeList = new ArrayList<String>();
        if (singleAccountCode != null) {
            accountCodeList.add(singleAccountCode);
        }
        return accountCodeList;
        
    }
	
	private List<String> makeAccountCodeList(List<AccountBO> billToAccountList){
		List<String> tempAccountCodeList = null;
		if(billToAccountList != null && (!billToAccountList.isEmpty())){
			tempAccountCodeList = new ArrayList<String>();
			for (AccountBO aBillToAccount : billToAccountList) {
				String sapAcctCode = null;
	            SapAcctBO sapAccount = aBillToAccount.getSapAccount();
	            if (sapAccount != null) {
	                sapAcctCode = sapAccount.getSapAccountCode();
	                if (GenericValidator.isBlankOrNull(sapAcctCode) == false) {
		            	tempAccountCodeList.add(sapAcctCode);
		            }
	            }
	        }
		}
		return tempAccountCodeList;
	}
	
	@Override
	public String getFunctionName() {
		 return SapConstants.GET_SHIPMENT_INFO_FUNC_NAME;
	}

	@Override
    public List<String> getInTableKeyList() {
        return constructSingleTableKeyList(IN_TABLE_NAME_KEY);
    }

	@Override
    public List<String> getOutTableKeyList() {
        return constructSingleTableKeyList(OUT_TABLE_NAME_KEY);
    }

	
    protected ExternalSystem getExternalSystem() {
        return ExternalSystem.EVRST;
    }

    
    protected Market getMarket() {
        return Market.US_CANADA;
    }
    
    
    @Override
    public void initializeImportParams(JCoParameterList importParams) {
    	
    	importParams.setValue(SapConstants.PO_NUMBER_KEY, orderSearchCriteria.getPoNumber());
    	
        importParams.setValue(SapConstants.ORDER_NUMBER_KEY, orderNumber);
        
        Date startDate = orderSearchCriteria.getSearchFrom();
        Date endDate = orderSearchCriteria.getSearchTo();
        
        if (GenericValidator.isBlankOrNull(orderNumber)) {
            // if the master order number is not specified, set the start and end times to 
        	// one year if they're not set.  This will get results, but not execute a search
            // on the entire system
            if (orderSearchCriteria.getSearchTo() == null) {
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
        if (orderSearchCriteria.getShipToAccount() != null) {
            importParams.setValue(SapConstants.SHIP_TO_KEY, orderSearchCriteria.getShipToAccount().getSapAccount().getSapAccountCode());   
        }
        importParams.setValue(SapConstants.REQUESTED_STATUS_KEY, orderSearchCriteria.getOrderStatus().getShipmentSearchOrderStatus().getCode()); 
        importParams.setValue(SapConstants.SORT_ORDER_KEY, "");
        importParams.setValue(SapConstants.MAX_COUNT_KEY, recordLimit);
        importParams.setValue(SapConstants.ORDER_RETURN_FLAG, orderSearchCriteria.getOrderOrReturns());
        
    }

	@Override
	public void initializeInTableParams(String tableName, JCoTable inTable) {
		if (sapAccountCodeList != null) {
            for (String anAccountCode : sapAccountCodeList) {
            	inTable.appendRow();
            	inTable.setValue(SapConstants.CUSTOMER_KEY, anAccountCode);
            	if (anAccountCode.length() == 10 && anAccountCode.startsWith("00")){
            		anAccountCode = anAccountCode.substring(2);
            		inTable.appendRow();
                	inTable.setValue(SapConstants.CUSTOMER_KEY, anAccountCode);
            	}
            }
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
     * Main method to execute this action.  Returns the results list created by the
     * search function in SAP and processing the out table
     * @throws WOMExternalSystemException
     */
    public List<ECCShippedOrder> execute() throws WOMExternalSystemException {
        if (isValidInputData() == true) {
            executeSapFunction();
        }
        return rawECCOrderList;
    } 
	
	@Override
	public void processOutTable(String tableName, JCoTable outTable) {
		int numRows = outTable.getNumRows();

		rawECCOrderList = new ArrayList<ECCShippedOrder>();
        for(int i = 0; i < numRows; i++) {
        	outTable.setRow(i);
        	// populate relevant fields onto shipped order
        	ECCShippedOrder eccShippedOrder = new ECCShippedOrder();
        	eccShippedOrder.setExternalSystem(getExternalSystem());
        	eccShippedOrder.setAccountCode(outTable.getString(SapConstants.CUSTOMER_KEY));
        	eccShippedOrder.setMasterOrderNumber(outTable.getString(SapConstants.MASTER_ORDER_NUMBER_KEY));
        	eccShippedOrder.setPoNumber(outTable.getString(SapConstants.PO_NUMBER_KEY));
        	eccShippedOrder.setReturnOrderReason(outTable.getString(SapConstants.ORDER_RETURN_REASON));
        	eccShippedOrder.setOrderNumber(outTable.getString(SapConstants.SAP_ORDER_KEY));
        	eccShippedOrder.setOrderDate(outTable.getDate(SapConstants.ORDER_DATE_KEY));
        	eccShippedOrder.setOrderSource(getSapOrderSource(outTable.getString(SapConstants.ORDER_METHOD_KEY)));
        	eccShippedOrder.setDeliveryNumber(outTable.getString(SapConstants.DELIVERY_NUMBER_KEY));
        	eccShippedOrder.setTrackingStatus(getSapPackingStatusKey(outTable.getString(SapConstants.TRACKING_STATUS_KEY)));
        	eccShippedOrder.setShipDate(outTable.getDate(SapConstants.SHIP_DATE_KEY));
        	eccShippedOrder.setShippingDC(createDistributionCenter(getMarket(), outTable));
        	eccShippedOrder.setCarrierCode(outTable.getString(SapConstants.CARRIER_KEY));
        	eccShippedOrder.setCarrierName(outTable.getString("CARRIER_NAME"));
        	eccShippedOrder.setTrackingNumber(outTable.getString(SapConstants.TRACKING_NUMBER_KEY));
        	rawECCOrderList.add(eccShippedOrder);
        }
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
	
	@Override
    protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
    }

	@Override
	protected Logger getLogger() {
		return logger;
	}

}

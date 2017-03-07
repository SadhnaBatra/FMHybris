package com.fmo.wom.integration.sap.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BackOrderBO;
import com.fmo.wom.domain.BackOrderUnitBO;
import com.fmo.wom.domain.BackOrderedItemBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public abstract class GetBackOrdersAction extends SapBapiAction {

	private static final String IN_TABLE_NAME_KEY = "SOLDTO";
	private static final String OUT_TABLE_NAME_KEY = "BACKORDER_DATA";
    protected static final String RETURN_CODE_INVALID_CUSTOMER_CODES = "01";
    protected static final String RETURN_CODE_NO_DATA_EXISTS = "02";
    protected static final String RETURN_CODE_INVALID_CUSTOMER_CODE = "03";
    protected static final String RETURN_CODE_CUSTOMER_TABLE_EMPTY = "99";

    // Input
    private String shipToAccountCode;
    private List<String> sapAccountCodeList;              // SAP Account Codes to use
    private Date startDate;
    private Date endDate;
    
    // Used for output
    private List<BackOrderBO> searchResultsList;
    private boolean noResultsFound = false;

    // Allow reuse across Markets
    protected abstract Market getMarket();
    
    private GetBackOrdersAction() { 
    	super(); 
    }

    public GetBackOrdersAction(String sapAccountCode) {
        super();
        sapAccountCodeList = new ArrayList<String>();
        if (GenericValidator.isBlankOrNull(sapAccountCode) == false) {
            sapAccountCodeList.add(sapAccountCode);
        }
    }
    
    public GetBackOrdersAction(List<String> soldToAccountList, String shipToAccountCode) {
        super();
        this.sapAccountCodeList = soldToAccountList;
        this.shipToAccountCode = shipToAccountCode;
    }
    
    public GetBackOrdersAction(String sapAccountCode, Date startDate, Date endDate) {
        super();
        sapAccountCodeList = new ArrayList<String>();
        if (GenericValidator.isBlankOrNull(sapAccountCode) == false) {
            sapAccountCodeList.add(sapAccountCode);
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
	protected abstract String getShipToCustomerKey();
	
	private Date getDefaultStartBackOrderDate() {
		Date defaultStartInvoiceDate = null;
		// Set Start Invoice Date to 7 days prior to End Invoice Date.
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDefaultEndBackOrderDate());
		cal.add(Calendar.DATE, -365);
		defaultStartInvoiceDate = cal.getTime();
		return defaultStartInvoiceDate;
	}
	
	private Date getDefaultEndBackOrderDate() {
		Calendar cal = Calendar.getInstance();
		Date defaultEndInvoiceDate = new Date();
		cal.setTime(defaultEndInvoiceDate);
		//cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	@Override
	public void initializeImportParams(JCoParameterList importParams) {
		
		/*if(startDate == null){
			startDate = getDefaultStartBackOrderDate();
		}
		
		if(endDate == null){
			endDate = getDefaultEndBackOrderDate();
		}*/
        
		if(startDate != null) {
            importParams.setValue(SapConstants.START_DATE_KEY, SapConstants.dateFormatter.format(startDate));
        }
        
		if(endDate != null) {
            importParams.setValue(SapConstants.END_DATE_KEY, SapConstants.dateFormatter.format(endDate));
        }
        
        if (GenericValidator.isBlankOrNull(shipToAccountCode) == false) {
            importParams.setValue(SapConstants.SHIP_TO_KEY, shipToAccountCode);
        }
	}

	@Override
	public List<String> getInTableKeyList() {
        return constructSingleTableKeyList(IN_TABLE_NAME_KEY);
	}

	@Override
	public void initializeInTableParams(String tableName, JCoTable inTable) {
	       
        for (String sapAccountCode : sapAccountCodeList) {
            inTable.appendRow();
            inTable.setValue(SapConstants.CUSTOMER_KEY, sapAccountCode);
        }
	}

	@Override
	public List<String> getOutTableKeyList() {
		return constructSingleTableKeyList(OUT_TABLE_NAME_KEY);
	}

    /**
     * Main method to execute this action.  Returns the results list created by the
     * search function in SAP and processing the out table
     * @throws WOMExternalSystemException
     */
    public List<BackOrderBO> execute() throws WOMExternalSystemException {
        
        executeSapFunction();
        
        // if we didn't find anything, return null
        if (noResultsFound) {
            return new ArrayList<BackOrderBO>();
        }
        
        return searchResultsList;
    }
    
    @Override
	public void checkReturnStructure(JCoStructure returnStructure) throws WOMExternalSystemException {
		
        String returnCode = returnStructure.getString(SapConstants.RETURN_CODE);
        
        if (!isSuccessReturnStructure(returnStructure)) {
            // did not receive success code - check for other return codes
            if (RETURN_CODE_NO_DATA_EXISTS.equals(returnCode)) {
                noResultsFound = true;
            } else {
                String errorMessage = returnStructure.getString(SapConstants.RETURN_MESSAGE);
                String logErrorMessage = "Errors occurred during " + getFunctionName() + ". Message=["+errorMessage+"]";
                getLogger().error(logErrorMessage);
                throw new WOMExternalSystemException(logErrorMessage);
            }
        }
	}

	@Override
	public void processOutTable(String outTableName, JCoTable outTable) {

        int numRows = outTable.getNumRows();
        outTable.setRow(0);
        int rowIndex = 0;
        
        searchResultsList = new ArrayList<BackOrderBO>();
        while (rowIndex < numRows) {
        	// Populate relevant fields onto the Back Order
        	BackOrderBO backOrder = new BackOrderBO();
        	
        	AccountBO billToAcct = new AccountBO();
        	billToAcct.setAccountCode(outTable.getString(SapConstants.CUSTOMER_KEY));
        	SapAcctBO sapAcctBillTo = new SapAcctBO();
        	sapAcctBillTo.setSapAccountCode(outTable.getString(SapConstants.CUSTOMER_KEY));
        	billToAcct.setSapAccount(sapAcctBillTo);
        	
        	AccountBO shipToAcct = new AccountBO();
        	shipToAcct.setAccountCode(outTable.getString(getShipToCustomerKey()));
        	SapAcctBO sapAcctShipTo = new SapAcctBO();
        	sapAcctShipTo.setSapAccountCode(outTable.getString(getShipToCustomerKey()));
        	shipToAcct.setSapAccount(sapAcctShipTo);
        	
        	backOrder.setBillToAccount(billToAcct);
        	backOrder.setShipToAccount(shipToAcct);
        	backOrder.setMasterOrderNumber(outTable.getString(SapConstants.MASTER_ORDER_NUMBER_KEY));
        	backOrder.setCustomerPurchaseOrderNumber(outTable.getString(SapConstants.PO_NUMBER_KEY));
        	
            rowIndex = createBackOrderUnits(backOrder, outTable, rowIndex);
            searchResultsList.add(backOrder);        	
        }
	}

    @Override
    protected void processExportParamList(JCoParameterList exportParamList) {
        
        // if we didn't find anything, can return null
        if (noResultsFound) {
            return;
        }
    }

	/**
	 * Create Back Order Units for the given BackOrder and Jco out table.
	 * @param backOrder
	 * @param currentOrder
	 * @param rowIndex  Index of the current row of Jco table
	 * @return Index of the current row of Jco table
	 */
    private int createBackOrderUnits(BackOrderBO backOrder, JCoTable currentOrder, int rowIndex) {
        
        List<BackOrderUnitBO> backOrderUnits = new ArrayList<BackOrderUnitBO>();
        while (isSameBackOrder(backOrder, currentOrder)) {
        	BackOrderUnitBO backOrderUnit = new BackOrderUnitBO();
        	
        	backOrderUnit.setOrderNumber(currentOrder.getString(SapConstants.SAP_ORDER_KEY));
        	backOrderUnit.setExternalSystem(ExternalSystem.EVRST);
        	backOrderUnit.setOrderDate(currentOrder.getDate(SapConstants.ORDER_DATE_KEY));
        	backOrderUnit.setDistributionCenter(createDistributionCenter(getMarket(), currentOrder));
            
            rowIndex = createBackOrderedItems(backOrder, backOrderUnit, currentOrder, rowIndex);
            backOrderUnits.add(backOrderUnit);

            // if we're done, break out.
            if (hasAnotherRow(currentOrder) == false) {
                break;
            }
        }
        
        backOrder.setBackOrderUnits(backOrderUnits);
        return rowIndex;
    }

    /**
     * Create Back Ordered Items for the given BackOrder, BackOrderUnit and Jco out table. 
     * @param backOrder
     * @param backOrderUnit
     * @param currentOrder
     * @param rowIndex Index of the current row of Jco table
     * @return Index of the current row of Jco table
     */
    private int createBackOrderedItems(BackOrderBO backOrder, BackOrderUnitBO backOrderUnit, JCoTable currentOrder, int rowIndex) {
        
        List<BackOrderedItemBO> backOrderedItems = new ArrayList<BackOrderedItemBO>();
        
        // Need to make sure the row we're on is both the same Back Order and
        // same Back Order Unit.
        while (isSameBackOrder(backOrder, currentOrder) && isSameOrder(backOrderUnit, currentOrder)) {
        	
        	BackOrderedItemBO backOrderedItem = new BackOrderedItemBO();
          
        	backOrderedItem.setPartNumber(currentOrder.getString(SapConstants.CUSTOMER_MATERIAL_KEY));
        	backOrderedItem.setOriginalOrderQty(currentOrder.getInt(SapConstants.ORIGINAL_ORDER_QUANTITY_KEY));
        	backOrderedItem.setBackOrderQty(currentOrder.getInt(SapConstants.BACKORDERED_QUANTITY_KEY));
          
        	backOrderedItems.add(backOrderedItem);
            
            // advance the row unless we don't have any left.
            if (currentOrder.nextRow() == false) {
                rowIndex++;
                break;
            }
            rowIndex++;
        }
        
        backOrderUnit.setBackOrderedItems(backOrderedItems);
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
    private boolean isSameBackOrder(BackOrderBO backOrder, JCoTable currentBackOrder) {
     
        String rowAccountCode = currentBackOrder.getString(SapConstants.CUSTOMER_KEY);
        String rowMasterOrderNumber = currentBackOrder.getString(SapConstants.MASTER_ORDER_NUMBER_KEY);
        String rowCustomerPurchaseOrderNumber = currentBackOrder.getString(SapConstants.PO_NUMBER_KEY);
        
        return fieldsEqual(rowAccountCode, backOrder.getBillToAccount().getSapAccount().getSapAccountCode()) &&
        		fieldsEqual(rowMasterOrderNumber, backOrder.getMasterOrderNumber()) &&
                fieldsEqual(rowCustomerPurchaseOrderNumber, backOrder.getCustomerPurchaseOrderNumber());
    }
    
    /**
     *  Check if the row we're on constitutes the same BackOrderUnit as the 
     * given BackOrderUnitBO.  Currently defined as equality of the sap
     * order number
     * @param backOrderUnit Back Order Unit to check
     * @param currentBackOrder Current row in jco
     * @return true if Order Numbers are same, false otherwise
     */
    private boolean isSameOrder(BackOrderUnitBO backOrderUnit, JCoTable currentBackOrder) {
        String orderNumber = currentBackOrder.getString(SapConstants.SAP_ORDER_KEY);
        return fieldsEqual(orderNumber, backOrderUnit.getOrderNumber());
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
     * Pseudo peek ahead a la token parsing. If the given table's pointer is on the last row, return false.
     * True otherwise. This is accomplished via peek ahead with nextRow().  If next row is false, that means
     * we don't have another row.  If it's true, that means the row pointer was advanced so we have to put it back.  
     * @param table
     * @return boolean
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
    
    public boolean isNoResultsFound() {
		return noResultsFound;
	}

	public void setNoResultsFound(boolean noResultsFound) {
        this.noResultsFound = noResultsFound;
    }

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

}

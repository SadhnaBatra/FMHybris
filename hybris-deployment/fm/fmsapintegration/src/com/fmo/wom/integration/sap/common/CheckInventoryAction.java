package com.fmo.wom.integration.sap.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.common.exception.GATPCreditCheckException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.util.MessageResourceConstants;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordField;
import com.sap.conn.jco.JCoRecordFieldIterator;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public abstract class CheckInventoryAction extends SapBapiAction  {

	private static final String IN_TABLE_NAME_KEY = "IN_INV_PARTS1";
	private static final String OUT_TABLE_NAME_KEY = "OUT_INV_PARTS1";
	
	// These are the input parameters
	private List<ItemBO> itemList;    				// parts to check
	private String sapAccountCode;					// sap account code to use
	private String shipToAccountCode;					// sap account code to use
	private CustomerSalesOrgBO customerSalesOrganization; 	// sales org for the sap account

	private String orderType = null;
	private String orderSource = null;
	//private String payementMethod = null;
	
	// allow reuse across market
	protected abstract Market getMarket();
	
	protected CheckInventoryAction() { super(); }

	public CheckInventoryAction(String sapAccountCode, String shipToAccountCode, CustomerSalesOrgBO salesOrg, List<ItemBO> itemList) {
		super();
		this.itemList = itemList;
		this.sapAccountCode = sapAccountCode;
		this.shipToAccountCode = shipToAccountCode;
		this.customerSalesOrganization = salesOrg;
	}
	
	public CheckInventoryAction(String sapAccountCode, String shipToAccountCode, 
	        CustomerSalesOrgBO salesOrg, List<ItemBO> itemList, SapConnectionManager aCxnMgr) {
        super(aCxnMgr);
        this.itemList = itemList;
        this.sapAccountCode = sapAccountCode;
        this.shipToAccountCode = shipToAccountCode;
        this.customerSalesOrganization = salesOrg;
    }
	
	
	public CheckInventoryAction(String sapAccountCode, String shipToAccountCode, 
								CustomerSalesOrgBO salesOrg, List<ItemBO> itemList, 
								String anOrderType, String anOrderSource) { //, String payementMethod) {
		super();
		this.itemList = itemList;
		this.sapAccountCode = sapAccountCode;
		this.shipToAccountCode = shipToAccountCode;
		this.customerSalesOrganization = salesOrg;
		this.orderSource = anOrderSource;
		this.orderType = anOrderType;
		//this.payementMethod = payementMethod;
	}
	

	public List<ItemBO> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemBO> itemList) {
        this.itemList = itemList;
    }

    public String getSapAccountCode() {
        return sapAccountCode;
    }

    public void setSapAccountCode(String sapAccountCode) {
        this.sapAccountCode = sapAccountCode;
    }

    public String getShipToAccountCode() {
        return shipToAccountCode;
    }

    public void setShipToAccountCode(String shipToAccountCode) {
        this.shipToAccountCode = shipToAccountCode;
    }

    public CustomerSalesOrgBO getCustomerSalesOrganization() {
        return customerSalesOrganization;
    }

    public void setCustomerSalesOrganization(
            CustomerSalesOrgBO customerSalesOrganization) {
        this.customerSalesOrganization = customerSalesOrganization;
    }

    /**
	 * Main method to execute this action.  Defines the return type as a list of resolved ItemBOs  
	 * The input list of parts will be augmented in place
	 * @throws WOMExternalSystemException
	 */
	public void execute() throws WOMExternalSystemException {
		executeSapFunction();
	}
	
	@Override
	public void initializeImportParams(JCoParameterList importParams) {

		importParams.setValue(SapConstants.SOLD_TO_CUSTOMER_KEY, sapAccountCode);
		String shipToCodeToUse = shipToAccountCode;
		if (GenericValidator.isBlankOrNull(shipToCodeToUse)) {
		    shipToCodeToUse = sapAccountCode;
		}
		importParams.setValue(SapConstants.SHIP_TO_CUSTOMER_KEY, shipToCodeToUse);
		importParams.setValue(SapConstants.SALES_ORGANIZATION_KEY, customerSalesOrganization.getSalesOrganization().getCode()); 
		importParams.setValue(SapConstants.DISTRIBUTION_CHANNEL_KEY, customerSalesOrganization.getDistributionChannel());
		importParams.setValue(SapConstants.DIVISION_KEY, customerSalesOrganization.getDivision());
		/* code modified for Z_HYBRIS_CHECK_INVENTORY */
		importParams.setValue(SapConstants.ORDER_TYPE, this.orderType); //"YEM"
		importParams.setValue(SapConstants.ORDER_SOURCE, this.orderSource);//"manu";
		//importParams.setValue(SapConstants.PAYMENT_METHOD, payementMethod);;//"T"
	}

	@Override
	public List<String> getInTableKeyList() {
		return constructSingleTableKeyList(IN_TABLE_NAME_KEY);
	}

	@Override
	public void initializeInTableParams(String tableName, JCoTable in) {
		in.setRow(1);
		for (ItemBO anItem : itemList) {
			in.appendRow();
			in.setValue(SapConstants.SAP_PART_NUM_KEY,anItem.getPartNumber().toUpperCase());
			in.setValue(SapConstants.QUANTITY_KEY,anItem.getItemQty().getRequestedQuantity());
			in.setValue(SapConstants.SHIP_TYPE, anItem.getDeliveryMethod());   
			if(this.orderType == OrderType.PICKUP.getEconCode()){
				in.setValue(SapConstants.PLANT_LOCATION, anItem.getPickupStore());
			}
			/*if(anItem.getDeliveryMethod() == SapConstants.SHIP_TYPE_STOREPICKUP){
				
			}*/
        }
	}

	@Override
	public List<String> getOutTableKeyList() {
		ArrayList<String> outTableList = (ArrayList<String>)constructSingleTableKeyList(OUT_TABLE_NAME_KEY); 
		//outTableList.add("ERROR_MSG");
		return outTableList;
	}

	@Override
	public void processOutTable(String outTableName, JCoTable out) {
		
		// Each row on the out table represents an available distribution center 
		// for the parts and the quantity available there.  The key on the output 
		// is the part number so we need to use this to link to the item list for
		// data population. 
	    int numRows = out.getNumRows();
		for (int i = 0; i < numRows; i++) {
			out.setRow(i);
			processItem(out);
		}
	}
	
	/**
	 * process one row of the output table. 
	 * @param outTable
	 */
	private void processItem(JCoTable currentRow) {
	
		// Get the input index for this row
		int inputIndex = currentRow.getInt(SapConstants.INPUT_INDEX_KEY);
		// now get the ItemBO corresponding to this part
		ItemBO currentItem = itemList.get(inputIndex-1);
		
		// create the inventory item for this row
		InventoryBO anInventory = new InventoryBO();
		
		Market market = getMarket();
		DistributionCenterBO distCenter = createActiveDistributionCenter(market, currentRow);
		anInventory.setDistributionCenter(distCenter);
		anInventory.setDistCntrId(distCenter.getDistCenterId());
		
		Integer quantity = Math.round(currentRow.getFloat(SapConstants.AVAILABLE_QUANTITY_KEY));
		anInventory.setAvailableQty(quantity);
		
		// get the inventory list for this item so we can add the new item
        List<InventoryBO> inventoryList = currentItem.getInventory(); 
        
        // this may be the first dc for this part, so check the inventory list.
        // If this is the first one, that means SAP is telling us its the home DC
        if (inventoryList == null) {
            inventoryList = new ArrayList<InventoryBO>();
            anInventory.setMainDC(true);
        }
        inventoryList.add(anInventory);
        currentItem.setInventory(inventoryList);
		
		if (getLogger().isDebugEnabled()) {
        	
		    getLogger().debug("Returned SAP Data:");
			// Loop over all columns in the current row and spit them out 
			JCoRecordFieldIterator it = currentRow.getRecordFieldIterator();
			while (it.hasNextField()) {
				JCoRecordField aField = it.nextRecordField();
				getLogger().debug(aField.getName()+ ":\t"+aField.getString());
			}
		
			getLogger().debug("Populated Item Data: "+ currentItem);
        }
	}

	/** These are protected so subs can override them */
	protected void checkReturnStructure(JCoStructure returnStructure) throws WOMExternalSystemException {
		getLogger().info("CheckInventoryAction.checkReturnStructure(JCoStructure returnStructure)");
		if (!isSuccessReturnStructure(returnStructure)) {
			String bapiReturnCode = returnStructure.getString(SapConstants.RETURN_CODE);
			String bapiReturnMessage = returnStructure.getString(SapConstants.RETURN_MESSAGE);
			if("Q05".equals(bapiReturnCode)){
				getLogger().error("GATP Credtit Check  .. bapiReturnCode "+bapiReturnCode+" bapiReturnMessage "+bapiReturnMessage);
				if("Overall status of credit checks is not ok".equals(bapiReturnMessage.trim())){
					throw new GATPCreditCheckException(bapiReturnMessage);
				}
			} else{
		    	String logErrorMessage = "Errors occurred during " + getFunctionName() + ". Message=["+bapiReturnMessage+"]";
			    getLogger().error(logErrorMessage);
				throw new WOMExternalSystemException(logErrorMessage);
		    }
		}
	}
	
	
	 // utility method to check all parts and return true if one of them is PART_NOT_FOUND
	protected boolean isPartNotFound(ItemBO anItem) {
        
        if (anItem.getProblemItemList() == null) {
            return false;
        }
        
        for (ProblemBO aProblem : anItem.getProblemItemList()) {
            if (MessageResourceConstants.PART_NOT_FOUND.equals(aProblem.getMessageKey())) {
                // found one
                return true;
            }
        }
        
        // made it through
        return false;
    }
}

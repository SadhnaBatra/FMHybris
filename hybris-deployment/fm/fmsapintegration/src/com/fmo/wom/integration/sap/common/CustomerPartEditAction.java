package com.fmo.wom.integration.sap.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.util.MessageResourceConstants;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.WeightBO;
import com.fmo.wom.domain.enums.CustomerBusinessType;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.integration.sap.SapBapiAction;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConstants;
import com.fmo.wom.integration.util.ItemListUtil;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.ProblemBOFactory;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordField;
import com.sap.conn.jco.JCoRecordFieldIterator;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public abstract class CustomerPartEditAction extends SapBapiAction {

	private static final String IN_TABLE_NAME_KEY = "IN_PARTS";
	private static final String OUT_TABLE_NAME_KEY = "OUT_PARTS";
	public static final String SUCCESS_KEY = "00";	
	
	private String bapiReturnCode = null;
	private String bapiReturnMessage = null;
	
	// These are the input parameters
	private List<ItemBO> itemList;    				// parts to check
	private String sapAccountCode;					// sap account code to use
	private CustomerSalesOrgBO customerSalesOrganization; 	// sales org for the sap account
	private List<ItemBO> resolvedPartsList;
	private Map<Integer, Integer> lineNumberInputNumberMap;
	private Map<Integer, ItemBO> lineNumberInputItemMap;
	
	protected abstract ExternalSystem getExternalSystem();
	protected abstract CustomerBusinessType getDefaultCustomerBusinessType();
	protected abstract void createPrice(ItemBO currentInputItem, JCoTable currentRow);
	
    protected CustomerPartEditAction() { ; };

	public CustomerPartEditAction(String sapAccountCode, CustomerSalesOrgBO salesOrg, List<ItemBO> itemList) {
		super();
		this.itemList = itemList;
		this.sapAccountCode = sapAccountCode;
		this.customerSalesOrganization = salesOrg;
		resolvedPartsList = new ArrayList<ItemBO>();
	}

	public CustomerPartEditAction(String sapAccountCode, CustomerSalesOrgBO salesOrg, 
	                              List<ItemBO> itemList, SapConnectionManager aCxnMgr) {
        super(aCxnMgr);
        this.itemList = itemList;
        this.sapAccountCode = sapAccountCode;
        this.customerSalesOrganization = salesOrg;
        resolvedPartsList = new ArrayList<ItemBO>();
    }
	
	
	public List<ItemBO> getItemList() {
        return itemList;
    }

    public String getSapAccountCode() {
        return sapAccountCode;
    }
    
    public void setSapAccountCode(String sapAccountCode) {
        this.sapAccountCode = sapAccountCode;;
    }

    public CustomerSalesOrgBO getCustomerSalesOrganization() {
        return customerSalesOrganization;
    }

    public List<ItemBO> execute() throws WOMExternalSystemException {
    	
	    // create a map for easier retrieval of input items via line number
	    lineNumberInputItemMap = ItemListUtil.createLineNumberItemMap(itemList);
	    
	    List<ItemBO> validItems = validateInputItemList(itemList);
        if (validItems.size() == 0) {
            // no need to execute anything
            return itemList;
        }
        
		executeSapFunction();
		return ItemListUtil.mergeResults(itemList, resolvedPartsList);
	}
	
	@Override
	public String getFunctionName() {
		return SapConstants.CUSTOMER_EDIT_FUNC_NAME;
	}

	@Override
	public void initializeImportParams(JCoParameterList importParams) {
		importParams.setValue(SapConstants.SOLD_TO_CUSTOMER_KEY, sapAccountCode);
		importParams.setValue(SapConstants.SALES_ORGANIZATION_KEY, customerSalesOrganization.getSalesOrganization().getCode()); 
		importParams.setValue(SapConstants.DISTRIBUTION_CHANNEL_KEY, customerSalesOrganization.getDistributionChannel());
		importParams.setValue(SapConstants.DIVISION_KEY, customerSalesOrganization.getDivision()); 
	}

	@Override
	public List<String> getInTableKeyList() {
		return constructSingleTableKeyList(IN_TABLE_NAME_KEY);
	}

	@Override
	public void initializeInTableParams(String tableName, JCoTable in) {
		
	    // initialize this map to retain the linkage between input items and the submitted items.
	    lineNumberInputNumberMap = new HashMap<Integer, Integer>();
	    
	    List<ItemBO> validItems = validateInputItemList(itemList);
	    
	    int index = 1;
		in.setRow(index);
		
		for (ItemBO anItem : validItems) {
		    
		    // we are sending this item
		    lineNumberInputNumberMap.put(index, anItem.getLineNumber());
			in.appendRow();
			String sapPartNumber = "";
			// ProductFlag - sent via IPO / Upload Order 
			// FM-BrandCode - sent via ecatalogue route for NA / Mexico
			// Need to prefix the part no with ProductFlag or FM-BrandCode.
			// Both Product Flag and FM BrandCode will not be populated together. Hope so!!
			sapPartNumber = new StringBuilder()
							.append(anItem.getProductFlag()!=null?anItem.getProductFlag():"")
							.append(anItem.getFmBrandCode()!=null?anItem.getFmBrandCode():"")
							.append(anItem.getDisplayPartNumber())
							.toString();
			//sapPartNumber = PartNumberSquasher.squashSapPartNumber(sapPartNumber); //Commented by Abhijit
	        sapPartNumber = sapPartNumber.toUpperCase().trim();
			in.setValue(SapConstants.MATERIAL_ID_KEY,sapPartNumber);
			in.setValue(SapConstants.QUANTITY_KEY,anItem.getItemQty().getRequestedQuantity());
			index++;
        }
	}

	@Override
    public List<String> getOutTableKeyList() {
	    return constructSingleTableKeyList(OUT_TABLE_NAME_KEY);
    }
	
	@Override
	public void processOutTable(String outTableName, JCoTable out) {
		
		// go through each returned row and process.
	    // Our internal maps start at 1, so this for loop also does.
	    // However, the row numbering in the JCoTable start at 0, hence
	    // the i-1 in here.
	    int numRows = out.getNumRows();
	    for (int i = 1; i <= numRows; i++) {
			out.setRow(i-1);
			processItem(out, i);
		}
	}
	
	/**
	 * process one row of the output table.  The corresponding input part will be retrieved via the 
	 * row number on the given table. Populates all the known information, and then processes
	 * the returned status code, which creates and populates Problem Line Items.
	 * @param outTable
	 */
	private void processItem(JCoTable currentRow, int rowToProcess) {
	
		// the outTable is set to the current row 
		int lineNumberOfInputList = lineNumberInputNumberMap.get(rowToProcess);
		ItemBO currentInputItem = lineNumberInputItemMap.get(lineNumberOfInputList);
		// pseudo deep copy on relevant input fields
		ItemBO newItem = currentInputItem.createInitialCopy();
		resolvedPartsList.add(newItem);
		
		String status = currentRow.getString(STATUS_FLAG_KEY);
	    getLogger().info("Processing output table row "+ rowToProcess+ ". Status = >>"+ status+ "<<");

	    // Set the part info
	    populatePartInfo(newItem, currentRow);
	    
	    processStatus(status, newItem);
	    
	    if (getLogger().isDebugEnabled()) {
        	
        	getLogger().debug("Returned SAP Data:");
			// Loop over all columns in the current row and spit them out 
			JCoRecordFieldIterator it = currentRow.getRecordFieldIterator();
			while (it.hasNextField()) {
				JCoRecordField aField = it.nextRecordField();
				getLogger().debug(aField.getName()+ ":\t"+aField.getString());
			}
		
			getLogger().debug("Populated Item Data: "+ newItem);
        }
	}
	 
	/**
     * Process the given status code and create ProblemBO onto the given ItemBO as necessary.
     * @param status returned SAP status code
     * @param currentInputItem current ItemBO to process
     */
    protected void processStatus(String status, ItemBO currentInputItem) {
        
        // if the status is not success, create a problembo with the db mapped key. 
        if (SUCCESS_KEY.equals(status) == false) {
            String statusKey = MessageResourceUtil.getStatusKeyViaStatusCode(status, getExternalSystem());
            
            if (MessageResourceConstants.OBSOLETE_USE_TO_COMPLETION.equals(statusKey)) {
                if (currentInputItem.getItemQty().isBulk() == true) {
                    // not allowed to get this guy but need a different message
                    statusKey = MessageResourceConstants.NO_BULK_PARTS;
                } else {
                    // if not bulk on this return status, no message necessaryr
                	return;
                	
                	/*if("99".equals(this.bapiReturnCode)){
                		//Treat it as an NOT_ALLOWED_TO_ORDER
                		statusKey = MessageResourceUtil.getStatusKeyViaStatusCode("10", getExternalSystem());
                	} else {
                		return;
					}*/
                }
            }
            ProblemBO realProblem = ProblemBOFactory.createProblem(statusKey, getExternalSystem());
            currentInputItem.addProblemItem(realProblem);
        }
    }
    
    
    @Override
	protected void checkReturnStructure(JCoStructure returnStructure) throws WOMExternalSystemException {
		getLogger().info("CustomerPartEditAction .checkReturnStructure(JCoStructure returnStructure)");
		if (!isSuccessReturnStructure(returnStructure)) {
		    String errorMessage = returnStructure.getString(SapConstants.RETURN_MESSAGE);
		    String logErrorMessage = "Errors occurred during " + getFunctionName() + ". Message=["+errorMessage+"]";
		    getLogger().error(logErrorMessage);
			throw new WOMExternalSystemException(logErrorMessage);
		} else{
			this.bapiReturnCode = returnStructure.getString(SapConstants.RETURN_CODE);
			this.bapiReturnMessage = returnStructure.getString(SapConstants.RETURN_MESSAGE);
			getLogger().info("CustomerPartEditAction .checkReturnStructure(JCoStructure returnStructure) bapiReturnCode "+bapiReturnCode+" bapiReturnMessage "+bapiReturnMessage);
		}
	}
    
    
	/**
	 * retrieves data fields from SAP via the given JCoTable, which has the current row
	 * set to the part we are processing.  The data is populated onto the given ItemBO.
	 * @param currentInputItem current ItemBO to populate date onto
	 * @param currentRow the returned data from SAP in a JCoTable
	 */
	private void populatePartInfo(ItemBO currentInputItem, JCoTable currentRow) {
		
	    currentInputItem.setExternalSystem(getExternalSystem());
		currentInputItem.setPartNumber(currentRow.getString(SapConstants.SAP_PART_NUM_KEY));
        // currentInputItem.setIsPart.setInventoryPartNum(partTable.getString("SAP_MATERIAL"));
        if (currentInputItem.getDisplayPartNumber() == null) {
            currentInputItem.setDisplayPartNumber(currentRow.getString(SapConstants.MATERIAL_ID_KEY));
        }
        
        // See if this part has been superceded 
        if (isPartSuperceded(currentRow)) {
        	// set the part number
        	String newPartNumber = currentRow.getString(SapConstants.SUPERCEDED_PART_KEY);
        	String oldPartNumber = currentInputItem.getPartNumber();
        	if (newPartNumber.equals(oldPartNumber) == false) {
        		// part numbers are not equal
        	    ProblemBO supercededPartProblem = ProblemBOFactory.createSupercededPartProblem(newPartNumber,oldPartNumber);
        		currentInputItem.addProblemItem(supercededPartProblem);
        		currentInputItem.setPartNumber(newPartNumber);
        		currentInputItem.setPredecessorPartNumber(oldPartNumber);
        	}
        }
        
        createQuantity(currentInputItem,currentRow);
        currentInputItem.setDescription( currentRow.getString(SapConstants.MATERIAL_DESC_KEY));
        createPrice(currentInputItem, currentRow);
        createWeight(currentInputItem, currentRow);
       
        // try to make this readable - too many negations in the old way.
        // If SAP tells us the part is not returnable, that means we cannot return it for credit
        currentInputItem.setCanReturnForCredit(true);
        boolean partNotReturnable = SapConstants.YES.equals(currentRow.getString(SapConstants.PART_NOT_RETURNABLE_KEY)); 
        currentInputItem.setCanReturnForCredit(partNotReturnable == false);
	}

	private void createWeight(ItemBO currentInputItem, JCoTable currentRow) {
	    WeightBO weight = currentInputItem.getItemWeight();
        if (weight == null) {
            weight = new WeightBO();
            currentInputItem.setItemWeight(weight);
        }
        
        weight.setWeight(currentRow.getDouble(SapConstants.UNIT_WEIGHT_KEY));
    }

    private void createQuantity(ItemBO currentInputItem, JCoTable currentRow) {
	    QuantityBO qty = currentInputItem.getItemQty();
        if (qty == null) {
            qty = new QuantityBO();
            qty.setItem(currentInputItem);
            currentInputItem.setItemQty(qty);
        }
        
        Integer deliveryUnit = Math.round(currentRow.getFloat(SapConstants.DELIVERY_UNIT_KEY));
        qty.setSoldInMultipleQuantity(deliveryUnit);
        
        // Set main attributes for a part
        Long safetyStock = new Long(Math.round(currentRow.getDouble(SapConstants.SAFETY_STOCK_KEY)));
        qty.setOverQuantity(safetyStock.intValue());
        qty.setQtyUomCd("EA");
        qty.setBulk(SapConstants.YES.equals(currentRow.getString(SapConstants.BULK_PART_KEY)));
    }

	/**
	 * Utility method to determine if a part has been superceded
	 * @param currentPart the row in the JCoTable for this part
	 * @return true if the superceded part key is populated 
	 */
	private boolean isPartSuperceded(JCoTable currentPart) {
		String newPartNumber = currentPart.getString(SapConstants.SUPERCEDED_PART_KEY);
		 return (newPartNumber != null && newPartNumber.trim().length() > 0);
	}
	
	
	
}

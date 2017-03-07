package com.fmo.wom.integration.sap;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.util.DistributionCenterCache;
import com.fmo.wom.integration.util.MessageResourceUtil;
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

public abstract class SapBapiAction {

	// This is just to simplify calls to the cxn manager.  
	protected SapConnectionManager sapCxnMgr;
	protected static final String STATUS_FLAG_KEY = "STATUS_FLAG";
	protected static final String TSC_PREFIX = "45";
	protected static final int TSC_FLAG_TRUE = 1;

	//private final static String CARRIER = "carrierDAO";
	//private CarrierDAO carrierDAO;
	private boolean transactionStarted = false;
	
	public SapBapiAction() {
		getLogger().info("@@ Inside SapBapiAction : SapBapiAction @@");//Added by Abhijit
		sapCxnMgr = SapConnectionManager.getInstance();
	}
	
	/** 
	 * Allow subclasses to set their own connection managers
	 * @param aConnectionManager
	 */
	public SapBapiAction(SapConnectionManager aConnectionManager) {
	    sapCxnMgr = aConnectionManager;
	}

	public abstract String getFunctionName();
	// provide support for multiple input tables
	public abstract List<String> getInTableKeyList();
	// provide support for multiple output tables
	public abstract List<String> getOutTableKeyList();

	public abstract void initializeImportParams(JCoParameterList importParams);
	public abstract void initializeInTableParams(String tableName, JCoTable inTable);
	public abstract void processOutTable(String tableName, JCoTable outTable);
	protected abstract JCoDestination getDestination() throws JCoException, WOMExternalSystemException; 
	protected abstract Logger getLogger();

	public boolean isTransactionStarted() {
        return transactionStarted;
    }

    public void setTransactionStarted(boolean transactionStarted) {
        this.transactionStarted = transactionStarted;
    }

    public void startTransaction() throws WOMExternalSystemException {
	    try {
            JCoDestination destination = getDestination(); 
            JCoContext.begin(destination);
            transactionStarted = true;
        }  catch (JCoException jex) {
            getLogger().error("Error starting SAP txn for "+getFunctionName());
            throw new WOMExternalSystemException("Unable to process "
                    + getFunctionName() + " due to : " + jex.getMessage(), jex);
        }
	}
	
	public void completeTransaction() throws WOMExternalSystemException {
         getLogger().info("SapBapiAction completeTransaction() "+ getFunctionName());
	    if (transactionStarted = false ) {
	        return;
	    }
	       
	    try {
            JCoDestination destination = getDestination(); 
            JCoContext.end(destination);
            transactionStarted = false;
        }  catch (JCoException jex) {
            getLogger().error("Error confirming SAP txn for function "+ getFunctionName());
            throw new WOMExternalSystemException("Unable to process "
                    + getFunctionName() + " due to : " + jex.getMessage(), jex);
        }
    }
	
	/**
	 * Main structure to call a SAP function. Subclasses define function
	 * specific tasks and call this method.  This method handles the common 
	 * calls and common error handling.  
	 * 
	 * @throws WOMExternalSystemException
	 */
	protected void executeSapFunction() throws WOMExternalSystemException {
		
		getLogger().info("SapBapiAction - executeSapFunction() start");
		
		// function instance is specific to this execution
		JCoFunction function = null;
		boolean transactionStartedExternally = true;
		try {
		        
		    if (transactionStarted == false) {
		        startTransaction();
		        // we just started this 
		        transactionStartedExternally = false;
		    }
		    
		    JCoDestination destination = getDestination();
		    		    
		    
			function = getFunction(destination);
			getLogger().info("Executing {"+function.getName()+ "} on { "+destination.getDestinationName()+"}...");
			getLogger().info("Destination Host = "+destination.getApplicationServerHost());
			getLogger().info("Destination System Number = "+destination.getSystemNumber());

			
			// Set the non-table input parameters 
			initializeImportParams(function.getImportParameterList());
			getLogger().info(" Begin display :Import Parameters to the SAP Function >>>> ");
			debugStructure(function.getImportParameterList());
			getLogger().info(" End display :Import Parameters to the SAP Function >>>> \n\n");
			
			// populate input table data.  Now supports multiple input tables.  It is
            // thus up to the implementing classes to switch on table name if they need
            // to support multiple
            List<String> inTableList = getInTableKeyList();
            if (inTableList != null) {
                for (String anInTableName : inTableList) {
                    JCoTable inTable = getTable(function, anInTableName);
                    if (inTable != null) {
                        initializeInTableParams(anInTableName, inTable);
                        getLogger().info(" Begin display :In table= "+anInTableName);
                        debugTable(inTable);
                        getLogger().info(" End display :In table= "+anInTableName);
                    }
                }
            }
            
            // allow subclasses one last chance to set up input data
            
            finalizeInput();
            
			// Execute the function 
			getLogger().info(" Going to execute  "+getFunctionName());
			function.execute(destination);
            getLogger().info(getFunctionName()+" Executed");
			// get the export param list
			JCoParameterList exportParamList = function.getExportParameterList();
			
			// check the return value 
			JCoStructure returnStructure = getReturnStructure(exportParamList) ;
			getLogger().info("Begin display : returnStructure ... ");
			debugStructure(returnStructure);
			getLogger().info("End display : returnStructure ... ");
			checkReturnStructure(returnStructure);

			// process any data we need in the export param list
			getLogger().info("Begin display : Export Parameters from the SAP Function");
			debugStructure(exportParamList);
			getLogger().info("End display : Export Parameters from the SAP Function");
			
			processExportParamList(exportParamList);
			
			// process output table data.  Now supports multiple output tables.  It is
			// thus up to the implementing classes to switch on table name if they need
			// to support multiple
			List<String> outTableList = getOutTableKeyList();
			if (outTableList != null) {
			    for (String anOutTableName : outTableList) {
			        JCoTable outTable = getTable(function, anOutTableName);
			        getLogger().info("Begin display :Out table = "+anOutTableName);
			        debugTable(outTable);
			        getLogger().info("End display :Out table = "+ anOutTableName);
			        try {
						processOutTable(anOutTableName, outTable);
					} catch (Exception e) {
						e.printStackTrace();
					} 
			    }
			}
			
			// all done processing out tables!  Finalize the stuffs
			finalizeOutput();
		    getLogger().info("SapBapiAction - executeSapFunction() End");
		} catch (JCoException jex) {
	            getLogger().error("Error executing SAP Action "+ getFunctionName());
	            throw new WOMExternalSystemException("Unable to process "
	                    + getFunctionName() + " due to : " + jex.getMessage(), jex);
		} catch (JCoRuntimeException jex) {
			getLogger().error("Error executing SAP Action "+ getFunctionName());
			throw new WOMExternalSystemException("Unable to process "
					+ getFunctionName() + " due to : " + jex.getMessage(), jex);
		} finally {
		    if (transactionStartedExternally == false) {
		        completeTransaction();
		    }
		}
		

	}
	/***********************************************************************************/
	
	/** 
	 * overrideable method to allow subclasses a chance to do anything after all input
	 * params and tables have been initialized prior to the function execution.  Default
	 * impl here does nothing;
	 */
	protected void finalizeInput() { }
	
	/** 
     * overrideable method to allow subclasses a chance to do anything after all processing 
     * is complete.  Default impl here does nothing;
     */
    protected void finalizeOutput() { }
    
	protected List<String> constructSingleTableKeyList(String key) {
	    List<String> tables = new ArrayList<String>();
        tables.add(key);
        return tables;
	}
	
	/** These are protected so subs can override them */
	protected void checkReturnStructure(JCoStructure returnStructure) throws WOMExternalSystemException {
		getLogger().info("SapBapiAction.checkReturnStructure(JCoStructure returnStructure)");
		if (!isSuccessReturnStructure(returnStructure)) {
		    String errorMessage = returnStructure.getString(SapConstants.RETURN_MESSAGE);
		    String logErrorMessage = "Errors occurred during " + getFunctionName() + ". Message=["+errorMessage+"]";
		    getLogger().error(logErrorMessage);
			throw new WOMExternalSystemException(logErrorMessage);
		}
	}
	
	protected JCoStructure getReturnStructure(JCoParameterList exportParamList) {
        return exportParamList.getStructure(SapConstants.RETURN);
    }
	
	/**
	 * Utility method to filter an input item list to those without problems on them, or
	 * with problems that are allowed to process
	 * @param inputList
	 * @return
	 */
	protected List<ItemBO> validateInputItemList(List<ItemBO> inputList) {
         
		 getLogger().info("SapBapiAction.validateInputItemList(List<ItemBO> inputList)");
         List<ItemBO> validatedList = new ArrayList<ItemBO>();
         
         if (inputList == null) {
             return validatedList;
         }
         
         for (ItemBO anInputItem : inputList) {

             if (anInputItem.canProcessItem()) {
                 // this item is legit
                 validatedList.add(anInputItem);
             }
         }
         
         return validatedList; //commented by Abhijit and replaced wid below line
         //return inputList;
     }
 
	/*protected CarrierDAO getCarrierDAO() {
        // this is not currently injected.  Need to make this class injectable.
        if (carrierDAO == null) {
            carrierDAO = ApplicationContextProvider.getApplicationContext().getBean(CARRIER, CarrierDAO.class);
        }
        return carrierDAO;
    }*/
	
	/**
	 * process data in the export param list.  By default, only the return structure is checked.
	 * @param exportParamList
	 */
	protected void processExportParamList(JCoParameterList exportParamList) {
	    ;
	}
	
	protected boolean isSuccessReturnStructure(JCoStructure returnStructure) {
        if (SapConstants.RETURN_TYPE_ERROR.equals(returnStructure.getString(SapConstants.RETURN_TYPE))) {
            return false;
        }
        return true;
    }
	
	private JCoTable getTable(JCoFunction function, String tableKey) {
	    if (tableKey == null || "".equals(tableKey)) {
	        // no table key given
	        return null;
	    }
		return function.getTableParameterList().getTable(tableKey);
	}
	
	private JCoFunction getFunction(JCoDestination destination)
			throws JCoException, WOMExternalSystemException {
		
		JCoFunction function =  destination.getRepository().getFunction(getFunctionName().toUpperCase());
		if (function == null) {
			String s = "createFunction(): Cannot create function  for [" + getFunctionName() + "]";
			getLogger().error(s);
			throw new WOMExternalSystemException(s);
		}
		return function;

	}

	protected String getSapYesNo(boolean arg) {
	    return arg ? SapConstants.YES : SapConstants.NO;
	}

	protected boolean getSapBoolean(String arg) {
        return SapConstants.YES.equals(arg);
    }
	
	/**
     * Utility method to look up the active distribution center via the returned code and populate
     * @param currentRow current output data from SAP
     * @return the distribution center associated to the returned code
     */
    protected DistributionCenterBO createActiveDistributionCenter(Market market, JCoTable currentRow) {
        return createDistributionCenter(market, currentRow, false);
	}
	
    /**
     * Utility method to look up inactive or active distribution center via the returned code and populate
     * @param currentRow current output data from SAP
     * @return the distribution center associated to the returned code
     */
    protected DistributionCenterBO createDistributionCenter(Market market, JCoTable currentRow) {
    	return createDistributionCenter(market, currentRow, true);
	}
	
	/**
	 * Utility method to look up the distribution center via the returned code and populate
	 * @param currentRow current output data from SAP
	 * @return the distribution center associated to the returned code
	 */
	private DistributionCenterBO createDistributionCenter(Market market, JCoTable currentRow, boolean allowInactive) {
    
	    String code = currentRow.getString(SapConstants.PLANT_CODE_KEY);
	    DistributionCenterBO distCenter = null; 
	    if (allowInactive) {
	        //distCenter =  DistributionCenterCache.getDistributionCenterWithDefault(market, code);
	        distCenter = new DistributionCenterBO();
	    	distCenter.setCode(code);
	    } else {
	    	distCenter = new DistributionCenterBO();
	    	distCenter.setCode(code);
	    	distCenter.setAvailabilityDate(currentRow.getDate(SapConstants.AVAILABLE_DATE));
	        //distCenter =  getActiveDistributionCenterWithDefault(market, code);
	    }
	    DistributionCenterBO dcFrmCache = DistributionCenterCache.getDistributionCenterCache().getDistributionCenter(code);
	    if(dcFrmCache != null){
	    	distCenter.setName(dcFrmCache.getName());
	    	distCenter.setTscFlag(dcFrmCache.getTscFlag());
	    } else if (StringUtils.startsWith(distCenter.getCode(), TSC_PREFIX)) {
	    	distCenter.setTscFlag(TSC_FLAG_TRUE);
	    }
	    return distCenter;
	}
	
	
	/**
	 * Utility method to do a look up of the given packing status.  In super class as both
	 * get shipment and get detail can use it
	 * @param sapPackingStatus
	 * @return
	 */
	protected String getSapPackingStatusKey(String sapPackingStatus) {
        String packingStatus = "";
        try {
            packingStatus = MessageResourceUtil.getSapPackageStatusKeyViaCode(sapPackingStatus);
        } catch (IllegalArgumentException e) {
            // if we didn't find that on the look up, I'm going to just use the returned key.
            // This should not be a fatal error
            packingStatus = sapPackingStatus;
        }
        return packingStatus;
    }
	
	public void debugTable(JCoTable table) {
	    
		getLogger().info("SapBapiAction.debugTable(JCoTable table)");
	    if (table == null) {
	        return;
	    }
	    
	    getLogger().info("Showing SAP Data:");
	    JCoRecordFieldIterator it = table.getRecordFieldIterator();
        String title = "";
        while (it.hasNextField()) {
            JCoRecordField aField = it.nextRecordField();
            //getLogger().debug("{}", aField.getName() + ":" + aField.getTypeAsString() + "(" + aField.getLength() + ") - " + aField.getDescription());
            title = title + "|" + aField.getName();
        }
        String sep = StringUtil.lpad("", title.length(), "-");
        getLogger().info(sep);
        getLogger().info(title);
        getLogger().info(sep);
   
	    int numRows = table.getNumRows();
        for (int i = 0; i < numRows; i++) {
        
            table.setRow(i);
            // Loop over all columns in the current row and spit them out 
            it = table.getRecordFieldIterator();
            String data = "";
            while (it.hasNextField()) {
                JCoRecordField aField = it.nextRecordField();
                data = data + "|" + aField.getString();
                //getLogger().debug("{}:\t{}", aField.getName(),aField.getString());
                //getLogger().debug("{}", aField.getName() + ":" + aField.getTypeAsString() + "(" + aField.getLength() + ") - " + aField.getDescription());
            }
            getLogger().info("Table Row "+i+" "+data);
        }
	}
	
	public void debugStructure(JCoStructure structure) {
		getLogger().info("SapBapiAction.debugStructure(JCoStructure structure)");
        if (structure == null) {
            return;
        }

        getLogger().info("Showing SAP Structure Info:");
        JCoRecordFieldIterator it = structure.getRecordFieldIterator();
        while (it.hasNextField()) {
            JCoRecordField aField = it.nextRecordField();
            //getLogger().debug("{}", aField.getName() + ":" + aField.getTypeAsString() + "(" + aField.getLength() + ") - " + aField.getDescription());
            getLogger().info(aField.getName()+"\t "+aField.getValue());
        }
    }
	
	public void debugStructure(JCoParameterList params) {
		
		getLogger().info("SapBapiAction.debugStructure(JCoParameterList params) ");
        if (params == null) {
            return;
        }
        
        getLogger().info("Showing SAP Data:");
        JCoFieldIterator it = params.getFieldIterator();
        while (it.hasNextField()) {
            JCoField aField = it.nextField();
            //getLogger().debug("{}", aField.getName() + ":" + aField.getTypeAsString() + "(" + aField.getLength() + ") - " + aField.getDescription());
            getLogger().info(aField.getName()+"\t"+aField.getValue());
        }
    }
}

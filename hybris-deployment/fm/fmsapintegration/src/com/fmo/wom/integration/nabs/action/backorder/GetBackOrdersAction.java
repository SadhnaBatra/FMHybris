package com.fmo.wom.integration.nabs.action.backorder;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.BackOrderBO;
import com.fmo.wom.integration.nabs.action.NabsActionBase;
import com.fmo.wom.integration.nabs.action.NabsInputBase;
import com.fmo.wom.integration.nabs.ims.NabsTransactionHelper;

public class GetBackOrdersAction extends NabsActionBase {

    private static Logger logger = Logger.getLogger(GetBackOrdersAction.class);
    
    private BackOrderUtil backOrderUtil;
    

    public GetBackOrdersAction() {
		super();
		backOrderUtil = new BackOrderUtil();
	}

    
	public Logger getLogger() {
        return logger;
	}

	/**
     * Execute the call to get Back Orders from NABS via DD124.
     * @return All Back Orders, if any, from NABS for specified Account Code
     * @throws WOMExternalSystemException if status message on returned call indicates something
     * bad happened in NABS
     * @throws WOMTransactionException if system failure on socket call
     */
    public List<BackOrderBO> executeAction(String nabsAccountCode)
            throws WOMExternalSystemException, WOMTransactionException {

    	logger.info("GetBackOrdersAction.executeAction(): nabsAccountCode=" + nabsAccountCode);
    	
        // construct specific input for this call.  
        NabsGetBackOrdersInput backOrdersInput = new NabsGetBackOrdersInput(nabsAccountCode);

        // We need to use the specific output for this NABS call
        NabsGetBackOrdersOutput output = new NabsGetBackOrdersOutput();
        
        // call nabs
        executeGetBackOrdersCall(backOrdersInput, output);
       
    	// Return an empty list if no Back Orders were found.
        if (output.isNoResultsFound()) {
        	logger.info("executeAction(): No Back Orders found.");
        	
        	return new ArrayList<BackOrderBO>();
        }
        List<BackOrderBO> backOrdersList = backOrderUtil.loadResponseData(output.getInquiryKey());
        backOrderUtil.releaseResources();
        return backOrdersList;
    }

    private void executeGetBackOrdersCall(NabsInputBase input, NabsGetBackOrdersOutput output)
    		throws WOMExternalSystemException, WOMTransactionException {

    	logger.info("Inside GetBackOrdersAction.executeGetBackOrdersCall()");
    	
		String inputString = input.getInputString();
		NabsTransactionHelper nabsTransactionHelper = new NabsTransactionHelper();
		String outputString = nabsTransactionHelper.callNabsTransaction(input.getTransactionCode(), inputString);
		getLogger().info("NABS Call executed. INPUT=["+inputString+"] OUTPUT=["+outputString+"].");
		output.parseOutputString(outputString);
		
		logger.info("executeGetBackOrdersCall(): output.isReturnStatusSuccessful(): ["+output.isReturnStatusSuccessful()+"]"); 
		logger.info("executeGetBackOrdersCall(): output.isNoResultsFound(): ["+output.isNoResultsFound()+"]"); 

		if (!output.isReturnStatusSuccessful() && !output.isNoResultsFound()) {
		    String logMessage = "NABS " + input.getTransactionCode() + " call failure. ID = " + input.getNabsCallID() + 
		                        ". Status = " + output.getStatusCode() + ". Error message = " + output.getStatusMessage();
		    getLogger().error(logMessage);
		    getLogger().error("NABS Tran Code " + input.getTransactionCode() + ": INPUT: " +
		    	input.getInputString() + ", OUTPUT: " + outputString);
		    throw new WOMExternalSystemException(logMessage);
		}
		
		// some quick logging to note this fact if it applies
		if (output.isNoResultsFound()) {
		    String logMessage = "NABS " + input.getTransactionCode() + " call returned no reults. ID = " + 
		    					input.getNabsCallID() + ". Status = " + output.getStatusCode() + 
		    					". Error message = " + output.getStatusMessage();
		    getLogger().info(logMessage);
		    getLogger().info("NABS Tran Code " + input.getTransactionCode() + ":  INPUT: " + input.getInputString() +
		    	" OUTPUT: "+ outputString);
		}
    }

}

package com.fmo.wom.integration.nabs.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.integration.nabs.ims.NabsTransactionHelper;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.ProblemBOFactory;

public abstract class NabsActionBase {
	
	Logger logger = Logger.getLogger(NabsActionBase.class); 
	
	public abstract Logger getLogger();
	
	protected List<ItemBO> createValidInputItemList(List<ItemBO> inputList) {
	        
        List<ItemBO> validatedList = new ArrayList<ItemBO>();
        if (inputList == null) {
            return validatedList;
        }

        for (ItemBO anInputItem : inputList) {
            if (isValidInputItem(anInputItem) == true) {
                validatedList.add(anInputItem);
            }
        }
        
        return validatedList;
	}
	
	 protected boolean isValidInputItem(ItemBO inputItem) {
	        
        if (inputItem == null) {
            return false;
        }
        // if the product flag passed in is over one character,
        // this is an issue for NABS.
        String productFlag = inputItem.getProductFlag();
        if (productFlag != null && productFlag.length() > 1) {
            inputItem.addProblemItem(ProblemBOFactory.createProblem(MessageResourceUtil.PART_NOT_FOUND));
            logger.error(inputItem.getDisplayPartNumber()+ " has invalid product flag for NABS call: "+inputItem.getProductFlag());
        }
        
        if (inputItem.canProcessItem()) {
            // this item is legit
            return true;
        }
        
        return false;
    }
	 
	/**
     * Execute the call to NABS using the input object.  Subclasses must implement and provide the input object
     * which will define its own format for calling NABS. This uses a default NabsOutput,which encapulates most
     * of the Nabs output message formats
     * @param input
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    protected void executeNabsCall(NabsInputBase input)
            throws WOMExternalSystemException, WOMTransactionException {
        
        NabsOutputParseable output = new NabsOutput();
        executeNabsCall(input, output);
    }
	    
    /**
     * Execute the call to NABS using the input object.  Subclasses must implement and provide the input object
     * which will define its own format for calling NABS. This uses a the given NabsOutputParseable interface
     * to parse and check the return status
     * @param input input message
     * @param output NabsOutputParseable to parse and chck the return status
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
	    
    protected void executeNabsCall(NabsInputBase input, NabsOutputParseable output)
        throws WOMExternalSystemException, WOMTransactionException {

        String inputString = input.getInputString();
        Date startDate = new Date();
        NabsTransactionHelper nabsTransactionHelper = new NabsTransactionHelper();
        String outputString = nabsTransactionHelper.callNabsTransaction(input.getTransactionCode(), inputString);
        Date endDate = new Date();
        
        String callDetails = "INPUT=["+inputString+"] OUTPUT=["+outputString+"]";
        logger.info("NABS Call executed. "+ callDetails + "Total time= "+(endDate.getTime() - startDate.getTime()));
        
        output.parseOutputString(outputString);
        
        if (output.isReturnStatusSuccessful() == false) {
            String logMessage = "NABS " + input.getTransactionCode() + " call failure. ID = " + input.getNabsCallID() + 
                                ". Status = " + output.getStatusCode() + ". Error message = "+ output.getStatusMessage();
            logger.error(logMessage);
            logger.error("NABS Tran ID " + input.getTransactionCode() + ":  INPUT: "+input.getInputString() +" OUTPUT: "+ outputString);
            throw new WOMExternalSystemException(logMessage);
        }
    }

}

package com.fmo.wom.integration.nabs.action.upload;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.integration.dao.TransactionIdSequenceDAO;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.NabsActionBase;

public class ProcessUploadOrderAction extends NabsActionBase {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ProcessUploadOrderAction.class);
   
    private UploadOrderUtil nabsUploadOrderUtil;
    
    private TransactionIdSequenceDAO db2TransIdSequenceDAO;
    
    
    public ProcessUploadOrderAction() {
		super();
		db2TransIdSequenceDAO = new TransactionIdSequenceDAO();
		nabsUploadOrderUtil = new UploadOrderUtil();
    }

	public Logger getLogger() {
        return logger;
    }
    
    /**
     * Execute the NABS call. Calls the util to store the data in the NABS input tables and then calls NABS.  
     * After successful call, returns.  Would like some status, but not checking yet.
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public int executeAction(OrderBO theOrder) throws WOMTransactionException {
        
        // check if we have nabs processable parts
        if (hasNabsProcessableParts(theOrder) == false) {
            logger.info("executeAction() Cust po no="+ theOrder.getCustPoNbr()+ " has no nabs processable parts. Aborting.");
            return -1;        
        }
        
        // persisting input data, so need an id
        int transactionId = db2TransIdSequenceDAO.nextVal();
        logger.info("executeAction() Persisting upload order in NABs for cust po no= "+ theOrder.getCustPoNbr()+ "with transaction sequence id= "+  NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId);
        
        // persist the order data
        nabsUploadOrderUtil.storeOrderData(transactionId, theOrder);
       
        // there is no call to nabs to notify them of this transaction.  The persisted
        // order data will be read by a batch program at NABS's convenience.
        // Hence nothing on the throws for this guy either
        logger.info("executeAction() Persistance of upload order in NABs for cust po no= "+ theOrder.getCustPoNbr()+ " with transaction sequence id= "+ NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId+ " complete.");
        nabsUploadOrderUtil.releaseResource();
        return transactionId;
    }
    
    /**
     * Update the upload order data with the transaction sequence id to the given status code.
     * This lets us either move something to processable OR cancelled after all other processing is complete
     */
    public void executeUpdate(int transactionSequenceId, String statusCode) throws WOMTransactionException{
        // call into the txn boundary
        nabsUploadOrderUtil.updateOrderData(transactionSequenceId, statusCode);
    }

    private boolean hasNabsProcessableParts(OrderBO theOrder) {
        for (ItemBO anItem : theOrder.getItemList()) {
            if (ExternalSystem.NABS == anItem.getExternalSystem() &&
                anItem.canProcessItem() == true) {
                // found one
                return true;
            }
        }
        // didn't find anything;
        return false;
    }
    
  
}

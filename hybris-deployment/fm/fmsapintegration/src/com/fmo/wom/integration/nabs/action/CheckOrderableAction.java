package com.fmo.wom.integration.nabs.action;


import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.integration.dao.TransactionIdSequenceDAO;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.partresolution.NABSPartResolutionUtil;
import com.fmo.wom.integration.util.ItemListUtil;

public class CheckOrderableAction extends NabsActionBase {

    private static Logger logger = Logger.getLogger(CheckOrderableAction.class);
    
    //private PartResolutionUtil partResolutionUtil;
    private NABSPartResolutionUtil partResolutionUtil;
    private TransactionIdSequenceDAO db2TransIdSequenceDAO;
    
    public Logger getLogger() {
        return logger;
    }
	
	public CheckOrderableAction() {
		db2TransIdSequenceDAO = new TransactionIdSequenceDAO();
		//partResolutionUtil    =  new PartResolutionUtil();
		partResolutionUtil = new NABSPartResolutionUtil();
	}
    /**
     * Execute the DD114 NABS call. This calls NABS to execute DD114 with it.  It then reads the response, and assuming
     * a successful return, loads and returns the response data
     * @return list of resolved parts.
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public List<ItemBO> executeAction(String masterOrderNumber, List<ItemBO> itemList, String billToAccountCode, String shipToAccountCode)
         throws WOMExternalSystemException, WOMTransactionException {
        
        List<ItemBO> validItemsList = createValidInputItemList(itemList);
        
        // If there was no valid input, we're done.
        if (validItemsList.size() == 0) {
            return itemList;
        }
        
        // persisting input data, so need an id

        int transactionId = db2TransIdSequenceDAO.nextVal();
        logger.info("Part resolution DB2 transaction id "+transactionId+" for masterOrderNumber "+masterOrderNumber);
        // store it with display part number = true  
        partResolutionUtil.storeInputItems(transactionId, NabsConstants.NABS_ORDERABLE_TRANS_CODE, masterOrderNumber, validItemsList, true);
       
        // create an input object
        NabsCheckOrderableInput inputObject = new NabsCheckOrderableInput(transactionId, billToAccountCode, shipToAccountCode);
        
        // call nabs
        executeNabsCall(inputObject);
        
        // response was ok - load response data
        List<ItemBO> resolvedParts = partResolutionUtil.loadPartResolutionResponseData(transactionId, NabsConstants.NABS_ORDERABLE_TRANS_CODE, validItemsList);
        
        //partResolutionUtil.releaseResource();
        // merge the resolved with the input list as we may not have sent all parts to NABS
        return ItemListUtil.mergeResults(itemList, resolvedParts);
    }
}

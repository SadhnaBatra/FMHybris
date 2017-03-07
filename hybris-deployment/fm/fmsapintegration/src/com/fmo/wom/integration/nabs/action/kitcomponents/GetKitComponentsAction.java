package com.fmo.wom.integration.nabs.action.kitcomponents;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.KitBO;
import com.fmo.wom.integration.dao.TransactionIdSequenceDAO;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.NabsActionBase;
import com.fmo.wom.integration.nabs.action.partresolution.PartResolutionUtil;

public class GetKitComponentsAction extends NabsActionBase {

    private static Logger logger = Logger.getLogger(GetKitComponentsAction.class);
    
    private PartResolutionUtil partResolutionUtil;
    
    private KitComponentUtil kitComponentUtil;
   
    private TransactionIdSequenceDAO db2TransIdSequenceDAO;
    
    public Logger getLogger() {
        return logger;
    }
    
    
    public GetKitComponentsAction() {
		db2TransIdSequenceDAO = new TransactionIdSequenceDAO();
		partResolutionUtil    =  new PartResolutionUtil();
		kitComponentUtil      = new KitComponentUtil();
	}
    
    
    /**
     * Execute the DD116 NABS call. This calls NABS to execute DD116 with the seq id.  It then reads the response, and assuming
     * a successful return, loads and returns the response data
     * @return kit bo with all kit component info in it
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public void executeAction(String masterOrderNumber, KitBO inputKit, String billToAccountCode)
         throws WOMExternalSystemException, WOMTransactionException {
        
        // If there was no valid input, we're done.
        if (isValidInputItem(inputKit) == false) {
            return;
        }
        
        // persisting input data, so need an id
        int transactionId = db2TransIdSequenceDAO.nextVal();
   
        List<ItemBO> inputList = new ArrayList<ItemBO>();
        inputList.add(inputKit);
        
        // store it with display part number = true  
        partResolutionUtil.storeInputItems(transactionId, NabsConstants.NABS_KIT_COMPONENTS_TRANS_CODE, masterOrderNumber, inputList, false);
       
        // create an input object
        NabsGetKitComponentsInput inputObject = new NabsGetKitComponentsInput(transactionId, billToAccountCode);
        
        // call nabs
        executeNabsCall(inputObject);
        
        // response was ok - load response data and return it
        KitBO resolvedKit = kitComponentUtil.loadKitComponentResponseData(transactionId, NabsConstants.NABS_KIT_COMPONENTS_TRANS_CODE);
        inputKit.setShortShipAllowed(resolvedKit.isShortShipAllowed());
        inputKit.setAddOtherComponentsAllowed(resolvedKit.isAddOtherComponentsAllowed());
        inputKit.setEngExpressAllowed(resolvedKit.isEngExpressAllowed());
        inputKit.setType(resolvedKit.getType());
        inputKit.setKitComponentCategoryMap(resolvedKit.getKitComponentCategoryMap());
    }
    
    public void releaseResorce(){
    	if(partResolutionUtil != null){
    		partResolutionUtil.releaseResource();
    	}
    	if(kitComponentUtil != null){
    		kitComponentUtil.releaseResource();	
    	}
    }
}

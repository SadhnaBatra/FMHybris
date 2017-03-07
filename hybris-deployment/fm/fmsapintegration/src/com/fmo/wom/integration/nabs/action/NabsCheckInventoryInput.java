package com.fmo.wom.integration.nabs.action;

import com.fmo.wom.integration.nabs.NabsConstants;

public class NabsCheckInventoryInput extends NabsInput {

    private NabsCheckInventoryInput() {;}
    
    public NabsCheckInventoryInput(String transactionId, String billToAcctCode, String shipToAcctCode) {
        
        super(transactionId, NabsConstants.NABS_CHECK_INVENTORY_TRANS_CODE, 
                billToAcctCode,  shipToAcctCode != null ? shipToAcctCode : "");
    }
    
    public NabsCheckInventoryInput(int transactionId, String billToAcctCode, String shipToAcctCode) {
        this(NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId, billToAcctCode,  shipToAcctCode);
    }
}

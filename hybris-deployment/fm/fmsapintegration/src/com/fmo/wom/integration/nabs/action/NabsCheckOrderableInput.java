package com.fmo.wom.integration.nabs.action;

import com.fmo.wom.integration.nabs.NabsConstants;

public class NabsCheckOrderableInput extends NabsInput {

    private NabsCheckOrderableInput() { ; }
    
    /**
     * Constructor to store all the relevent input data
     * @param transactionId key for input/output tables for this call 
     * @param billToAcctCode
     * @param shipToAcctCode
     */
    public NabsCheckOrderableInput(int transactionId, String billToAcctCode, String shipToAcctCode) {
        this(NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId, billToAcctCode,  shipToAcctCode);
    }
    
    public NabsCheckOrderableInput(String transactionId, String billToAcctCode, String shipToAcctCode) {
        super(transactionId, NabsConstants.NABS_ORDERABLE_TRANS_CODE, 
              billToAcctCode,  shipToAcctCode != null ? shipToAcctCode : "");
    }
    
    /**
     * Constructor with no shipToAcct as this is optional
     * @param transactionId key for input/output tables for this call
     * @param billToAcct nabs acct code for bill to
     */
    public NabsCheckOrderableInput(String transactionId, String billToAcctCode) {
        super(transactionId, NabsConstants.NABS_ORDERABLE_TRANS_CODE, billToAcctCode,  "");
    }
    
    public NabsCheckOrderableInput(int transactionId, String billToAcctCode) {
        this(""+transactionId, billToAcctCode,  "");
    }
    
}

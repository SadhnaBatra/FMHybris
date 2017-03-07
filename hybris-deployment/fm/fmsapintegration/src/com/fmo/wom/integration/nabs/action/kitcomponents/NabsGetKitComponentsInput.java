package com.fmo.wom.integration.nabs.action.kitcomponents;

import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.NabsInput;

public class NabsGetKitComponentsInput extends NabsInput {

    private NabsGetKitComponentsInput() { ; }
    
    /**
     * Constructor with no shipToAcct as this is optional
     * @param transactionId key for input/output tables for this call
     * @param billToAcct nabs acct code for bill to
     */
    public NabsGetKitComponentsInput(String transactionId, String billToAcctCode) {
        super(transactionId, NabsConstants.NABS_KIT_COMPONENTS_TRANS_CODE, billToAcctCode,  "");
    }
    
    public NabsGetKitComponentsInput(int transactionId, String billToAcctCode) {
        this(""+transactionId, billToAcctCode);
    }
    
    /**
     * This input string follows the standard format but with no optional ship
     * to account code at the last 5 character position.  One option is generate
     * the input string and truncate, or simply override the generate without
     * the shipping code field.  I've chosen the latter for readability and simplicity.
     */
    @Override
    public String getInputString() {
        StringBuffer input = new StringBuffer();
        input.append(InputFields.TRANSACTION_CODE.getPaddedValue(transactionCode));
        input.append(InputFields.WOM8_IDENTIFIER.getPaddedValue(WOM8_IDENTIFIER));
        input.append(InputFields.TRANSACTION_ID.getPaddedValue(transactionId));
        input.append(InputFields.BILL_TO_ACCT_CODE.getPaddedValue(billToAccountCode));
        return input.toString();
    }
}

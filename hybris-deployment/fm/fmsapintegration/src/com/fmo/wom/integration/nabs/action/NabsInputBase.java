package com.fmo.wom.integration.nabs.action;

import java.util.Date;

import com.fmo.wom.integration.nabs.NabsConstants;

public abstract class NabsInputBase {

    public static final String WOM8_IDENTIFIER = "WOM8";
    
    protected String transactionCode;
    protected String transactionId;
    
    public abstract String getInputString(); 
    
    /**
     * defaulat method for returning the key information for the NABS
     * call.  Standard impl is transaction ID but subs override as necessary
     * @return identifying input info for the NABS call.
     */
    public String getNabsCallID() {
        return getTransactionId() + "";
    }
    
    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getNabsDateString(Date aDate) {
        if (aDate == null) return null;
        
        return NabsConstants.dateFormatter.format(aDate);
    }
}

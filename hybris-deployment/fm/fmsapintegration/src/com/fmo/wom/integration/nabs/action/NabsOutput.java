package com.fmo.wom.integration.nabs.action;




import org.apache.log4j.Logger;

import com.fmo.wom.integration.nabs.NabsConstants;

public class NabsOutput implements NabsOutputParseable  {
    
    private static Logger logger = Logger.getLogger(NabsOutput.class);
    
    private String transactionId;
    private String statusCode;
    private String statusMessage;
    
    public NabsOutput() {;}
    
    @Override
    public boolean isReturnStatusSuccessful() {
        
        // if return = nabs error, no successful
        return (NabsConstants.NABS_CALL_FAILURE.equals(getStatusCode()) || 
                NabsConstants.NABS_INVALID_SHIP_TO.equals(getStatusCode()) ||
                NabsConstants.NABS_MISSING_MON.equals(getStatusCode()) ||
                NabsConstants.NABS_ACCOUNT_BEING_DELETED.equals(getStatusCode()) ||
                getStatusCode() == null) == false; 
    }
    
    @Override
    public void parseOutputString(String outputString) {
        
        if (outputString == null || outputString.length() < OutputField.getTotalSize()) {
            return;
        }
            
        // get the status code
        statusCode = getField(OutputField.STATUS_CODE, outputString);

        // if all is good, parse
        if (NabsConstants.NABS_SUCCESS.equals(statusCode)) {
            
            // get the trans id
            transactionId = getField(OutputField.TRANSACTION_ID, outputString);
            
         // get the status message
            statusMessage = getField(OutputField.STATUS_MESSAGE, outputString);
        } else {
            // non success status.  Let's set the status message to the whole string.
            statusMessage = outputString;
        }
        
    }
    
    public String getTransactionId() {
        return transactionId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public String toString() {
        return "NabsOutput [transactionId=" + transactionId
                + ", statusCode=" + statusCode + ", statusMessage="
                + statusMessage + "]";
    }

    private String getField(OutputField aField, String outputString) {
        return outputString.substring(aField.getOffSet(), aField.getOffSet() + aField.getFieldSize());
    }
    
    private long getLongField(OutputField aField, String outputString) {
        return Long.parseLong(outputString.substring(aField.getOffSet(), aField.getOffSet() + aField.getFieldSize()).trim());
    }
    
    /**
     * 1. TRANSACTION_ID LENGTH(14) 
     * 2. STATUS_CD    LENGTH(2) 
     * 3. STATUS_MSG   LENGTH(80)
     */
    private enum OutputField {
        
        // THIS ORDER MATTERS!  The beginning position of an entry
        // is determined by the size of the entries prior. 
        STATUS_CODE(2), 
        TRANSACTION_ID(9), 
        STATUS_MESSAGE(65);

        private int fieldSize;

        private OutputField(int fieldSize) {
            this.fieldSize = fieldSize;
        }

        public int getFieldSize() {
            return fieldSize;
        }
        
        public int getOffSet() {
            int offSet = 0;
            for (OutputField f: OutputField.values()) {
                if (f == this) {
                    // done!
                    break;
                } else {
                    // add it in
                    offSet += f.fieldSize;
                }
            }
            return offSet;
        }
        
        public static int getTotalSize() {
            int total = 0;
            for (OutputField f: OutputField.values()) {
                total += f.fieldSize;
            }
            return total;
        }
    }

  

}

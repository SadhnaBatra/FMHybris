package com.fmo.wom.integration.nabs.action;




import org.apache.log4j.Logger;

import com.fmo.wom.integration.nabs.NabsConstants;

public class NabsProcessOrderOutput implements NabsOutputParseable  {
    
    private static Logger logger = Logger.getLogger(NabsProcessOrderOutput.class);
    
    private String statusCode;
    private String statusMessage;
    
    public NabsProcessOrderOutput() {}
    
    @Override
    public boolean isReturnStatusSuccessful() {
        
        // There are a multitude of return codes from this action, as well as different messages for 
        // individual return codes depending on the input data.  Rather than enumerate all the failure
        // conditions, we will return not successful if the return code is not 00. 
        return NabsConstants.NABS_SUCCESS.equals(getStatusCode());
    }
    
    @Override
    public void parseOutputString(String outputString) {
        
        if (outputString == null || outputString.length() < OutputField.getTotalSize()) {
            return;
        }
        
        // get the status code
        statusCode = getField(OutputField.STATUS_CODE, outputString);
        // get the status message
        statusMessage = getField(OutputField.STATUS_MESSAGE, outputString);
    }
    
    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public String toString() {
        return "NabsOutput [statusCode=" + statusCode + ", statusMessage="
                + statusMessage + "]";
    }


    private String getField(OutputField aField, String outputString) {
        return outputString.substring(aField.getOffSet(), aField.getOffSet() + aField.getFieldSize());
    }
    
    private enum OutputField {
        
        // THIS ORDER MATTERS!  The beginning position of an entry
        // is determined by the size of the entries prior. 
        STATUS_CODE(2), 
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

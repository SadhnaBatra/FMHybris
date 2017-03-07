package com.fmo.wom.integration.nabs.action.shipment;




import org.apache.log4j.Logger;

import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.NabsOutputParseable;
import com.fmo.wom.integration.nabs.action.NabsProcessOrderOutput;


public class NabsGetShipmentInfoOutput implements NabsOutputParseable  {
    
    private static Logger logger = Logger.getLogger(NabsProcessOrderOutput.class);
    
    private String inquiryKey;
    private String statusCode;
    private String statusMessage;
    
    public NabsGetShipmentInfoOutput() {}
    
    @Override
    public boolean isReturnStatusSuccessful() {
        
        // There are a multitude of return codes from this action, as well as different messages for 
        // individual return codes depending on the input data.  Rather than enumerate all the failure
        // conditions, we will return not successful if the return code is not 00. 
        return NabsConstants.NABS_SUCCESS.equals(getStatusCode());
    }
    
    public boolean isNoResultsFound() {
        return NabsConstants.NABS_NO_RESULTS_FOUND.equals(getStatusCode());
    }
    
    @Override
    public void parseOutputString(String outputString) {
        
        // get the status code
    	logger.info("parseOutputString() ... "+outputString);
        statusCode = getField(OutputField.STATUS_CODE, outputString);
        logger.info("parseOutputString ... statusCode +"+statusCode);
        // get the inquiry key if this was successful
        if (isReturnStatusSuccessful()) {
        	logger.info("isReturnStatusSuccessful()  ... true ");
        	inquiryKey = getField(OutputField.INQUIRY_KEY, outputString);
            // get the status message
            statusMessage = getField(OutputField.STATUS_MESSAGE, outputString);
            logger.info("isReturnStatusSuccessful()  ... true statusMessage "+statusMessage);
        } else {
        	logger.info("  isReturnStatusSuccessful()  ... false  ");
            statusMessage = outputString.substring(3, outputString.length());
            logger.info("  isReturnStatusSuccessful()  ... statusMessage  "+statusMessage);
        }
    }
    
    public String getStatusCode() {
        return statusCode;
    }

    public String getInquiryKey() {
        return inquiryKey;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public String toString() {
        return "NabsGetShipmentInfoOutput [inquiryKey=" + inquiryKey
                + ",statusCode=" + statusCode + ", statusMessage="
                + statusMessage + "]";
    }

    private String getField(OutputField aField, String outputString) {
        return outputString.substring(aField.getOffSet(), aField.getOffSet() + aField.getFieldSize());
    }
    
   private enum OutputField {
        
        // THIS ORDER MATTERS!  The beginning position of an entry
        // is determined by the size of the entries prior. 
        STATUS_CODE(2),
        INQUIRY_KEY(26),
        STATUS_MESSAGE(80);

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

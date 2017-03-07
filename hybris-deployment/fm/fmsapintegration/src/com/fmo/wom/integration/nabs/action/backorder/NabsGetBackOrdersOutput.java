package com.fmo.wom.integration.nabs.action.backorder;




import org.apache.log4j.Logger;

import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.NabsOutputParseable;

public class NabsGetBackOrdersOutput implements NabsOutputParseable {

    private static Logger logger = Logger.getLogger(NabsGetBackOrdersOutput.class);
    
    private String inquiryKey;
    private String statusCode;
    private String statusMessage;
    
    public NabsGetBackOrdersOutput() {}
    
	@Override
	public void parseOutputString(String outputString) {
        
        // get the status code
        statusCode = getField(OutputField.STATUS_CODE, outputString);
        logger.debug("parseOutputString(): statusCode: ["+ statusCode+ "]");
        // get the inquiry key if this was successful
        if (isReturnStatusSuccessful()) {
        	logger.debug("parseOutputString(): Inside 'if (isReturnStatusSuccessful())'");
            inquiryKey = getField(OutputField.INQUIRY_KEY, outputString);
            logger.debug("parseOutputString(): inquiryKey: ["+ inquiryKey+ "]");
            // get the status message
            statusMessage = getField(OutputField.STATUS_MESSAGE, outputString);
        } else {
        	logger.debug("parseOutputString(): Inside 'else'");
            statusMessage = outputString.substring(3, outputString.length());
        }
        logger.debug("parseOutputString(): statusMessage: ["+ statusMessage+ "]");
	}

	@Override
	public boolean isReturnStatusSuccessful() {
    	logger.debug("isReturnStatusSuccessful(): statusCode: ["+ getStatusCode()+ "]");
        return NabsConstants.NABS_SUCCESS.equals(getStatusCode());
	}

    public boolean isNoResultsFound() {
    	logger.debug("isNoResultsFound(): statusCode: ["+ getStatusCode()+ "]");
        return NabsConstants.NABS_NO_RESULTS_FOUND.equals(getStatusCode());
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
        return "NabsGetBackOrdersOutput [inquiryKey=" + inquiryKey
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
            for (OutputField f : OutputField.values()) {
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
            for (OutputField f : OutputField.values()) {
                total += f.fieldSize;
            }
            return total;
        }
    }

}

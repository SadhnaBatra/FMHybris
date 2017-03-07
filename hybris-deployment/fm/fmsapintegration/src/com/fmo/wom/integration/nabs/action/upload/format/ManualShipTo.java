package com.fmo.wom.integration.nabs.action.upload.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.ManualShipToAddressBO;
import com.fmo.wom.domain.OrderBO;

public class ManualShipTo extends Record {
       
    private static final String code = "5"; 
        
    public ManualShipTo() {
        setCode(code);
    }
    
    @Override
    public List<String> getFormattedStringList(int transactionId, OrderBO theOrder) {

        List<String> results = new ArrayList<String>();
        if (theOrder.isManualShipTo() == false) {
            return results;
        }
        
        StringBuffer input = new StringBuffer();
        
        input.append(getPaddedCodeValue());
        
        ManualShipToAddressBO manualShipTo = theOrder.getManualShipToAddress();
         
        input.append(HeaderFieldFormats.NAME.getPaddedValue(manualShipTo.getName()));    
        results.add(input.toString());
        
        return results;
    }


    private enum HeaderFieldFormats {
        
        NAME(60, ' ');
        
        private int fieldSize;
        private char padder;
        
        private HeaderFieldFormats(int fieldSize, char padder) {
            this.fieldSize = fieldSize;
            this.padder = padder;
        }
        
        public int getFieldSize() {
            return fieldSize;
        }
        
        public char getPadder() {
            return padder;
        }
        
        public String getPaddedValue(String value) {
            return StringUtil.stringPad(value, padder, fieldSize);
        }

        public String getPaddedValue(Date value) {
            String dateString = "";
            if (value != null) {
                dateString = dateFormat.format(value);
            }
            return StringUtil.stringPad(dateString, padder, fieldSize);
        }
    
    }
}
    
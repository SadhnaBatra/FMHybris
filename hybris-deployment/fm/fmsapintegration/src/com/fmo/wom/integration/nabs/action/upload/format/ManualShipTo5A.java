package com.fmo.wom.integration.nabs.action.upload.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.ManualShipToAddressBO;
import com.fmo.wom.domain.OrderBO;

public class ManualShipTo5A extends Record {
       
    private static final String code = "5A"; 
        
    public ManualShipTo5A() {
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
         
        input.append(HeaderFieldFormats.ADDRESS_LINE_1.getPaddedValue(manualShipTo.getAddress().getAddrLine1()));    
        input.append(HeaderFieldFormats.FILLER1.getPaddedValue(""));    
        input.append(HeaderFieldFormats.ADDRESS_LINE_2.getPaddedValue(manualShipTo.getAddress().getAddrLine2()));    
        results.add(input.toString());
        
        return results;
    }


    private enum HeaderFieldFormats {
        
        
        ADDRESS_LINE_1(35, ' '),
        FILLER1(2, ' '),
        ADDRESS_LINE_2(35, ' ');
        
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
    
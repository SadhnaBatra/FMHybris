package com.fmo.wom.integration.nabs.action.upload.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.integration.nabs.NabsConstants;

public class Trailer extends Record {
       
    private static final String code = "6"; 
    
    public Trailer() {
        setCode(code);
    }
    
    @Override
    public List<String> getFormattedStringList(int transactionId, OrderBO theOrder) {

        List<String> results = new ArrayList<String>();
        
        StringBuffer input = new StringBuffer();
        
        input.append(getPaddedCodeValue());
        
        input.append(FieldFormats.TRANSACTION_SEQ_ID.getPaddedValue(NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId));
        results.add(input.toString());
        
        return results;
    }


    private enum FieldFormats {
       
        TRANSACTION_SEQ_ID(10, ' ');
        
        private int fieldSize;
        private char padder;
        
        private FieldFormats(int fieldSize, char padder) {
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
            return StringUtil.stringPad(dateFormat.format(value), padder, fieldSize);
        }
    
        public String getPaddedValue() {
            return StringUtil.stringPad("", padder, fieldSize);
        }
    }
}
    
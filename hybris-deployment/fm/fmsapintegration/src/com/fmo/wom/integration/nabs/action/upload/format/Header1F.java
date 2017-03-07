package com.fmo.wom.integration.nabs.action.upload.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.OrderBO;

public class Header1F extends Record {
       
    private static final String code = "1F"; 
    private static final String WOM_UPLOAD = "WU";
    
    public Header1F() {
        setCode(code);
    }
    
    @Override
    public List<String> getFormattedStringList(int transactionId, OrderBO theOrder) {

        List<String> results = new ArrayList<String>();
        
        StringBuffer input = new StringBuffer();
        
        input.append(getPaddedCodeValue());
         
        input.append(FieldFormats.FILLER0.getPaddedValue());
        input.append(FieldFormats.USER_CREATED_BY.getPaddedValue(theOrder.getUserId()));
        input.append(FieldFormats.N.getPaddedValue("N"));
        input.append(FieldFormats.FILLER1.getPaddedValue(theOrder.getCustPoNbr()));
        input.append(FieldFormats.MASTER_ORDER_NUMBER.getPaddedValue(theOrder.getMstrOrdNbr()));
        input.append(FieldFormats.FILLER2.getPaddedValue());
        input.append(FieldFormats.ENTRY_METHOD.getPaddedValue(WOM_UPLOAD));
        
        results.add(input.toString());
        
        return results;
    }


    private enum FieldFormats {
//   12345678901234567890123456789012345678901234567890123456789012345678901234567890
// 1FTESTMAN3       TCSR01  N                     W1305150241564 <------------------------ this is where the FM master order number should be passed to NABS           
    	FILLER0(15, ' '),
        USER_CREATED_BY(8,' '),
        N(1, ' '),
        FILLER1(21, ' '), 
        MASTER_ORDER_NUMBER(15, ' '),
        FILLER2(5,' '),
        ENTRY_METHOD(2,' ');
    
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
    